package sqlancer.tidb;

import java.sql.SQLException;

import sqlancer.AbstractAction;
import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.TableQueryGenerator;
import sqlancer.common.query.ExpectedErrors;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLQueryProvider;
import sqlancer.tidb.TiDBProvider.TiDBGlobalState;
import sqlancer.tidb.gen.TiDBAlterTableGenerator;
import sqlancer.tidb.gen.TiDBAnalyzeTableGenerator;
import sqlancer.tidb.gen.TiDBDeleteGenerator;
import sqlancer.tidb.gen.TiDBDropTableGenerator;
import sqlancer.tidb.gen.TiDBDropViewGenerator;
import sqlancer.tidb.gen.TiDBIndexGenerator;
import sqlancer.tidb.gen.TiDBInsertGenerator;
import sqlancer.tidb.gen.TiDBSetGenerator;
import sqlancer.tidb.gen.TiDBTableGenerator;
import sqlancer.tidb.gen.TiDBUpdateGenerator;
import sqlancer.tidb.gen.TiDBViewGenerator;

public class TiDBTableQueryGenerator extends TableQueryGenerator {

    public enum Action implements AbstractAction<TiDBGlobalState> {
        CREATE_TABLE(TiDBTableGenerator::createRandomTableStatement), // 0
        CREATE_INDEX(TiDBIndexGenerator::getQuery), // 1
        VIEW_GENERATOR(TiDBViewGenerator::getQuery), // 2
        INSERT(TiDBInsertGenerator::getQuery), // 3
        ALTER_TABLE(TiDBAlterTableGenerator::getQuery), // 4
        TRUNCATE((g) -> new SQLQueryAdapter("TRUNCATE " + g.getSchema().getRandomTable(t -> !t.isView()).getName())), // 5
        UPDATE(TiDBUpdateGenerator::getQuery), // 6
        DELETE(TiDBDeleteGenerator::getQuery), // 7
        SET(TiDBSetGenerator::getQuery), // 8
        ADMIN_CHECKSUM_TABLE(
                (g) -> new SQLQueryAdapter("ADMIN CHECKSUM TABLE " + g.getSchema().getRandomTable().getName())), // 9
        ANALYZE_TABLE(TiDBAnalyzeTableGenerator::getQuery), // 10
        DROP_TABLE(TiDBDropTableGenerator::dropTable), // 11
        DROP_VIEW(TiDBDropViewGenerator::dropView); // 12

        private final SQLQueryProvider<TiDBGlobalState> sqlQueryProvider;

        Action(SQLQueryProvider<TiDBGlobalState> sqlQueryProvider) {
            this.sqlQueryProvider = sqlQueryProvider;
        }

        @Override
        public SQLQueryAdapter getQuery(TiDBGlobalState state) throws Exception {
            return sqlQueryProvider.getQuery(state);
        }
    }

    public TiDBTableQueryGenerator(TiDBGlobalState globalState) {
        super(Action.values().length, globalState);
    }

    private int mapActions(Action a) {
        Randomly r = globalState.getRandomly();
        switch (a) {
        case ANALYZE_TABLE:
        case CREATE_INDEX:
            return r.getInteger(0, 2);
        case INSERT:
            return r.getInteger(0, globalState.getOptions().getMaxNumberInserts());
        case TRUNCATE:
        case DELETE:
        case ADMIN_CHECKSUM_TABLE:
            return r.getInteger(0, 2);
        case SET:
        case UPDATE:
            return r.getInteger(0, 5);
        case VIEW_GENERATOR:
            // https://github.com/tidb-challenge-program/bug-hunting-issue/issues/8
            return r.getInteger(0, 2);
        case ALTER_TABLE:
            return r.getInteger(0, 10); // https://github.com/tidb-challenge-program/bug-hunting-issue/issues/10
        case CREATE_TABLE:
        case DROP_TABLE:
        case DROP_VIEW:
            return 0;
        default:
            throw new AssertionError(a);
        }
    }

    private void generate() {
        for (Action action : Action.values()) {
            int nrPerformed = mapActions(action);
            nrActions[action.ordinal()] = nrPerformed;
            total += nrPerformed;
        }
    }

    @Override
    public void generateNExecute() throws Exception {
        generate();

        TiDBGlobalState globalState = (TiDBGlobalState) super.globalState;

        while (!isFinished()) {
            TiDBTableQueryGenerator.Action nextAction = Action.values()[getRandNextAction()];
            assert nextAction != null;
            SQLQueryAdapter query = null;
            try {
                boolean success = false;
                int nrTries = 0;
                do {
                    query = nextAction.getQuery(globalState);
                    success = globalState.executeStatement(query);
                } while (nextAction.canBeRetried() && !success
                        && nrTries++ < globalState.getOptions().getNrStatementRetryCount());
            } catch (IgnoreMeException e) {

            } catch (SQLException e) {
                if (e.getMessage().contains(
                        "references invalid table(s) or column(s) or function(s) or definer/invoker of view lack rights to use them")) {
                    throw new IgnoreMeException(); // TODO: drop view instead
                } else {
                    throw new AssertionError(e);
                }
            }
            if (query != null && query.couldAffectSchema()) {
                globalState.updateSchema();
                throw new IgnoreMeException();
            }
            if (globalState.getSchema().getDatabaseTables().isEmpty()) {
                throw new IgnoreMeException();
            }
        }

        if (globalState.getDbmsSpecificOptions().getTestOracleFactory().stream()
                .anyMatch((o) -> o == TiDBOracleFactory.CERT)) {
            // Disable strict Group By constraints for ROW oracle
            globalState.executeStatement(new SQLQueryAdapter(
                    "SET @@sql_mode='STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';"));

            // Enfore statistic collected for all tables
            ExpectedErrors errors = new ExpectedErrors();
            TiDBErrors.addExpressionErrors(errors);
            for (TiDBSchema.TiDBTable table : globalState.getSchema().getDatabaseTables()) {
                if (!table.isView()) {
                    globalState.executeStatement(new SQLQueryAdapter("ANALYZE TABLE " + table.getName() + ";", errors));
                }
            }
        }

        // TiFlash replication settings
        if (globalState.getDbmsSpecificOptions().tiflash) {
            ExpectedErrors errors = new ExpectedErrors();
            TiDBErrors.addExpressionErrors(errors);
            for (TiDBSchema.TiDBTable table : globalState.getSchema().getDatabaseTables()) {
                if (!table.isView()) {
                    globalState.executeStatement(
                            new SQLQueryAdapter("ALTER TABLE " + table.getName() + " SET TIFLASH REPLICA 1;", errors));
                }
            }
            if (Randomly.getBoolean()) {
                globalState.executeStatement(new SQLQueryAdapter("set @@tidb_enforce_mpp=1;"));
            }
        }
    }
}

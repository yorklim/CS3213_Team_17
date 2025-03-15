package sqlancer.tidb;

import java.sql.SQLException;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.TableCreator;
import sqlancer.common.query.ExpectedErrors;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.tidb.TiDBProvider.TiDBGlobalState;
import sqlancer.tidb.gen.TiDBTableGenerator;

public class TiDBTableCreator extends TableCreator {

    private final TiDBGlobalState globalState;

    public TiDBTableCreator(TiDBGlobalState globalState) {
        this.globalState = globalState;
    }

    @Override
    public void create() throws Exception {
        createTable();

        TiDBTableQueryGenerator generator = new TiDBTableQueryGenerator(globalState);
        generator.generate();

        while (!generator.isFinished()) {
            TiDBTableQueryGenerator.Action nextAction = generator.getRandNextAction();
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

    private void createTable() throws Exception {
        for (int i = 0; i < Randomly.fromOptions(1, 2); i++) {
            boolean success;
            do {
                SQLQueryAdapter qt = new TiDBTableGenerator().getQuery(globalState);
                success = globalState.executeStatement(qt);
            } while (!success);
        }
    }
}

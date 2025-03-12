package sqlancer.sqlite3;

import sqlancer.AbstractAction;
import sqlancer.Randomly;
import sqlancer.common.TableQueryGenerator;
import sqlancer.common.query.ExpectedErrors;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLQueryProvider;
import sqlancer.sqlite3.gen.SQLite3AnalyzeGenerator;
import sqlancer.sqlite3.gen.SQLite3CreateVirtualRtreeTabelGenerator;
import sqlancer.sqlite3.gen.SQLite3ExplainGenerator;
import sqlancer.sqlite3.gen.SQLite3PragmaGenerator;
import sqlancer.sqlite3.gen.SQLite3ReindexGenerator;
import sqlancer.sqlite3.gen.SQLite3TransactionGenerator;
import sqlancer.sqlite3.gen.SQLite3VacuumGenerator;
import sqlancer.sqlite3.gen.SQLite3VirtualFTSTableCommandGenerator;
import sqlancer.sqlite3.gen.ddl.SQLite3AlterTable;
import sqlancer.sqlite3.gen.ddl.SQLite3CreateTriggerGenerator;
import sqlancer.sqlite3.gen.ddl.SQLite3CreateVirtualFTSTableGenerator;
import sqlancer.sqlite3.gen.ddl.SQLite3DropIndexGenerator;
import sqlancer.sqlite3.gen.ddl.SQLite3DropTableGenerator;
import sqlancer.sqlite3.gen.ddl.SQLite3IndexGenerator;
import sqlancer.sqlite3.gen.ddl.SQLite3TableGenerator;
import sqlancer.sqlite3.gen.ddl.SQLite3ViewGenerator;
import sqlancer.sqlite3.gen.dml.SQLite3DeleteGenerator;
import sqlancer.sqlite3.gen.dml.SQLite3InsertGenerator;
import sqlancer.sqlite3.gen.dml.SQLite3StatTableGenerator;
import sqlancer.sqlite3.gen.dml.SQLite3UpdateGenerator;
import sqlancer.sqlite3.schema.SQLite3Schema;

public class SQLite3TableQueryGenerator implements TableQueryGenerator {

    public enum Action implements AbstractAction<SQLite3GlobalState> {
        PRAGMA(SQLite3PragmaGenerator::insertPragma), // 0
        CREATE_INDEX(SQLite3IndexGenerator::insertIndex), // 1
        CREATE_VIEW(SQLite3ViewGenerator::generate), // 2
        CREATE_TRIGGER(SQLite3CreateTriggerGenerator::create), // 3
        CREATE_TABLE(SQLite3TableGenerator::createRandomTableStatement), // 4
        CREATE_VIRTUALTABLE(SQLite3CreateVirtualFTSTableGenerator::createRandomTableStatement), // 5
        CREATE_RTREETABLE(SQLite3CreateVirtualRtreeTabelGenerator::createRandomTableStatement), // 6
        INSERT(SQLite3InsertGenerator::insertRow), // 7
        DELETE(SQLite3DeleteGenerator::deleteContent), // 8
        ALTER(SQLite3AlterTable::alterTable), // 9
        UPDATE(SQLite3UpdateGenerator::updateRow), // 10
        DROP_INDEX(SQLite3DropIndexGenerator::dropIndex), // 11
        DROP_TABLE(SQLite3DropTableGenerator::dropTable), // 12
        DROP_VIEW(SQLite3ViewGenerator::dropView), // 13
        VACUUM(SQLite3VacuumGenerator::executeVacuum), // 14
        REINDEX(SQLite3ReindexGenerator::executeReindex), // 15
        ANALYZE(SQLite3AnalyzeGenerator::generateAnalyze), // 16
        EXPLAIN(SQLite3ExplainGenerator::explain), // 17
        CHECK_RTREE_TABLE((g) -> {
            SQLite3Schema.SQLite3Table table = g.getSchema().getRandomTableOrBailout(t -> t.getName().startsWith("r"));
            String format = String.format("SELECT rtreecheck('%s');", table.getName());
            return new SQLQueryAdapter(format, ExpectedErrors.from("The database file is locked"));
        }), // 18
        VIRTUAL_TABLE_ACTION(SQLite3VirtualFTSTableCommandGenerator::create), // 19
        MANIPULATE_STAT_TABLE(SQLite3StatTableGenerator::getQuery), // 20
        TRANSACTION_START(SQLite3TransactionGenerator::generateBeginTransaction) {
            @Override
            public boolean canBeRetried() {
                return false;
            }

        }, // 21
        ROLLBACK_TRANSACTION(SQLite3TransactionGenerator::generateRollbackTransaction) {
            @Override
            public boolean canBeRetried() {
                return false;
            }
        }, // 22
        COMMIT(SQLite3TransactionGenerator::generateCommit) {
            @Override
            public boolean canBeRetried() {
                return false;
            }
        }; // 23

        private final SQLQueryProvider<SQLite3GlobalState> sqlQueryProvider;

        Action(SQLQueryProvider<SQLite3GlobalState> sqlQueryProvider) {
            this.sqlQueryProvider = sqlQueryProvider;
        }

        @Override
        public SQLQueryAdapter getQuery(SQLite3GlobalState state) throws Exception {
            return sqlQueryProvider.getQuery(state);
        }
    }

    private SQLite3GlobalState globalState;
    private int total;
    private int[] nrActions;

    public SQLite3TableQueryGenerator(SQLite3GlobalState globalState) {
        this.globalState = globalState;
        this.total = 0;
        this.nrActions = new int[Action.values().length];
    }

    @Override
    public void generate() {
        for (Action action : Action.values()) {
            int nrPerformed = mapActions(action);
            nrActions[action.ordinal()] = nrPerformed;
            total += nrPerformed;
        }
    }

    @Override
    public Action getRandNextAction() {
        int selection = globalState.getRandomly().getInteger(0, total);
        int previousRange = 0;
        for (int i = 0; i < nrActions.length; i++) {
            if (previousRange <= selection && selection < previousRange + nrActions[i]) {
                assert nrActions[i] > 0;
                nrActions[i]--;
                total--;
                return Action.values()[i];
            } else {
                previousRange += nrActions[i];
            }
        }
        throw new AssertionError();
    }

    @Override
    public boolean isFinished() {
        return total == 0;
    }

    private int mapActions(Action a) {
        int nrPerformed = 0;
        Randomly r = globalState.getRandomly();
        switch (a) {
        case CREATE_VIEW:
            nrPerformed = r.getInteger(0, 2);
            break;
        case DELETE:
        case DROP_VIEW:
        case DROP_INDEX:
            nrPerformed = r.getInteger(0, 0);
            break;
        case ALTER:
            nrPerformed = r.getInteger(0, 0);
            break;
        case EXPLAIN:
        case CREATE_TRIGGER:
        case DROP_TABLE:
            nrPerformed = r.getInteger(0, 0);
            break;
        case VACUUM:
        case CHECK_RTREE_TABLE:
            nrPerformed = r.getInteger(0, 3);
            break;
        case INSERT:
            nrPerformed = r.getInteger(0, globalState.getOptions().getMaxNumberInserts());
            break;
        case MANIPULATE_STAT_TABLE:
            nrPerformed = r.getInteger(0, 5);
            break;
        case CREATE_INDEX:
            nrPerformed = r.getInteger(0, 5);
            break;
        case VIRTUAL_TABLE_ACTION:
        case UPDATE:
            nrPerformed = r.getInteger(0, 30);
            break;
        case PRAGMA:
            nrPerformed = r.getInteger(0, 20);
            break;
        case CREATE_TABLE:
        case CREATE_VIRTUALTABLE:
        case CREATE_RTREETABLE:
            nrPerformed = 0;
            break;
        case TRANSACTION_START:
        case REINDEX:
        case ANALYZE:
        case ROLLBACK_TRANSACTION:
        case COMMIT:
        default:
            nrPerformed = r.getInteger(1, 10);
            break;
        }
        return nrPerformed;
    }

}

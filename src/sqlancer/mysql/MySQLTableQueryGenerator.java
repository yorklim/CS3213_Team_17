package sqlancer.mysql;

import sqlancer.AbstractAction;
import sqlancer.Randomly;
import sqlancer.cnosdb.CnosDBGlobalState;
import sqlancer.common.TableQueryGenerator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLQueryProvider;
import sqlancer.mysql.gen.MySQLAlterTable;
import sqlancer.mysql.gen.MySQLDeleteGenerator;
import sqlancer.mysql.gen.MySQLDropIndex;
import sqlancer.mysql.gen.MySQLInsertGenerator;
import sqlancer.mysql.gen.MySQLSetGenerator;
import sqlancer.mysql.gen.MySQLTruncateTableGenerator;
import sqlancer.mysql.gen.MySQLUpdateGenerator;
import sqlancer.mysql.gen.admin.MySQLFlush;
import sqlancer.mysql.gen.admin.MySQLReset;
import sqlancer.mysql.gen.datadef.MySQLIndexGenerator;
import sqlancer.mysql.gen.tblmaintenance.MySQLAnalyzeTable;
import sqlancer.mysql.gen.tblmaintenance.MySQLCheckTable;
import sqlancer.mysql.gen.tblmaintenance.MySQLChecksum;
import sqlancer.mysql.gen.tblmaintenance.MySQLOptimize;
import sqlancer.mysql.gen.tblmaintenance.MySQLRepair;

public class MySQLTableQueryGenerator implements TableQueryGenerator {
    enum Action implements AbstractAction<MySQLGlobalState> {
        SHOW_TABLES((g) -> new SQLQueryAdapter("SHOW TABLES")), //
        INSERT(MySQLInsertGenerator::insertRow), //
        SET_VARIABLE(MySQLSetGenerator::set), //
        REPAIR(MySQLRepair::repair), //
        OPTIMIZE(MySQLOptimize::optimize), //
        CHECKSUM(MySQLChecksum::checksum), //
        CHECK_TABLE(MySQLCheckTable::check), //
        ANALYZE_TABLE(MySQLAnalyzeTable::analyze), //
        FLUSH(MySQLFlush::create), RESET(MySQLReset::create), CREATE_INDEX(MySQLIndexGenerator::create), //
        ALTER_TABLE(MySQLAlterTable::create), //
        TRUNCATE_TABLE(MySQLTruncateTableGenerator::generate), //
        SELECT_INFO((g) -> new SQLQueryAdapter(
                "select TABLE_NAME, ENGINE from information_schema.TABLES where table_schema = '" + g.getDatabaseName()
                        + "'")), //
        UPDATE(MySQLUpdateGenerator::create), //
        DELETE(MySQLDeleteGenerator::delete), //
        DROP_INDEX(MySQLDropIndex::generate);

        private final SQLQueryProvider<MySQLGlobalState> sqlQueryProvider;

        Action(SQLQueryProvider<MySQLGlobalState> sqlQueryProvider) {
            this.sqlQueryProvider = sqlQueryProvider;
        }

        @Override
        public SQLQueryAdapter getQuery(MySQLGlobalState globalState) throws Exception {
            return sqlQueryProvider.getQuery(globalState);
        }
    }

    private final MySQLGlobalState globalState;
    private int total;
    private int[] nrActions;

    public MySQLTableQueryGenerator(MySQLGlobalState globalState) {
        this.globalState = globalState;
        this.total = 0;
        this.nrActions = new int[Action.values().length];
    }

    private int mapActions(Action a) {
        Randomly r = globalState.getRandomly();
        int nrPerformed = 0;
        switch (a) {
        case DROP_INDEX:
            nrPerformed = r.getInteger(0, 2);
            break;
        case SHOW_TABLES:
            nrPerformed = r.getInteger(0, 1);
            break;
        case INSERT:
            nrPerformed = r.getInteger(0, globalState.getOptions().getMaxNumberInserts());
            break;
        case REPAIR:
            nrPerformed = r.getInteger(0, 1);
            break;
        case SET_VARIABLE:
            nrPerformed = r.getInteger(0, 5);
            break;
        case CREATE_INDEX:
            nrPerformed = r.getInteger(0, 5);
            break;
        case FLUSH:
            nrPerformed = Randomly.getBooleanWithSmallProbability() ? r.getInteger(0, 1) : 0;
            break;
        case OPTIMIZE:
            // seems to yield low CPU utilization
            nrPerformed = Randomly.getBooleanWithSmallProbability() ? r.getInteger(0, 1) : 0;
            break;
        case RESET:
            // affects the global state, so do not execute
            nrPerformed = globalState.getOptions().getNumberConcurrentThreads() == 1 ? r.getInteger(0, 1) : 0;
            break;
        case CHECKSUM:
        case CHECK_TABLE:
        case ANALYZE_TABLE:
            nrPerformed = r.getInteger(0, 2);
            break;
        case ALTER_TABLE:
            nrPerformed = r.getInteger(0, 5);
            break;
        case TRUNCATE_TABLE:
            nrPerformed = r.getInteger(0, 2);
            break;
        case SELECT_INFO:
            nrPerformed = r.getInteger(0, 10);
            break;
        case UPDATE:
            nrPerformed = r.getInteger(0, 10);
            break;
        case DELETE:
            nrPerformed = r.getInteger(0, 10);
            break;
        default:
            throw new AssertionError(a);
        }
        return nrPerformed;
    }

    private void generateNrActions() {
        for (Action action : Action.values()) {
            int nrPerformed = mapActions(action);
            nrActions[action.ordinal()] = nrPerformed;
            total += nrPerformed;
        }
    }

    @Override
    public void generate() {
        generateNrActions();
    }

    @Override
    public boolean isFinished() {
        return total == 0;
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
}

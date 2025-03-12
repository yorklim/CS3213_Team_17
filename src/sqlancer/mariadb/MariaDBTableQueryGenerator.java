package sqlancer.mariadb;

import sqlancer.AbstractAction;
import sqlancer.MainOptions;
import sqlancer.common.TableQueryGenerator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLQueryProvider;
import sqlancer.mariadb.MariaDBProvider.MariaDBGlobalState;
import sqlancer.mariadb.gen.MariaDBIndexGenerator;
import sqlancer.mariadb.gen.MariaDBInsertGenerator;
import sqlancer.mariadb.gen.MariaDBSetGenerator;
import sqlancer.mariadb.gen.MariaDBTableAdminCommandGenerator;
import sqlancer.mariadb.gen.MariaDBTruncateGenerator;
import sqlancer.mariadb.gen.MariaDBUpdateGenerator;

public class MariaDBTableQueryGenerator implements TableQueryGenerator {
    public enum Action implements AbstractAction<MariaDBProvider.MariaDBGlobalState> {
        ANALYZE_TABLE(MariaDBTableAdminCommandGenerator::analyzeTable), //
        CHECKSUM(MariaDBTableAdminCommandGenerator::checksumTable), //
        CHECK_TABLE(MariaDBTableAdminCommandGenerator::checkTable), //
        CREATE_INDEX(MariaDBIndexGenerator::generate), //
        INSERT(MariaDBInsertGenerator::insert), //
        OPTIMIZE(MariaDBTableAdminCommandGenerator::optimizeTable), //
        REPAIR_TABLE(MariaDBTableAdminCommandGenerator::repairTable), //
        SET(MariaDBSetGenerator::set), //
        TRUNCATE(MariaDBTruncateGenerator::truncate), //
        UPDATE(MariaDBUpdateGenerator::update),; //

        private final SQLQueryProvider<MariaDBGlobalState> sqlQueryProvider;

        Action(SQLQueryProvider<MariaDBGlobalState> sqlQueryProvider) {
            this.sqlQueryProvider = sqlQueryProvider;
        }

        public SQLQueryAdapter getQuery(MariaDBGlobalState state) throws Exception {
            return sqlQueryProvider.getQuery(state);
        }
    }

    private final MariaDBGlobalState globalState;
    private int total;
    private int[] nrActions;

    public MariaDBTableQueryGenerator(MariaDBGlobalState globalState) {
        this.globalState = globalState;
        this.total = 0;
        this.nrActions = new int[Action.values().length];
    }

    private int mapActions(Action a) {
        MainOptions options = globalState.getOptions();
        switch (a) {
        case INSERT:
            return globalState.getRandomly().getInteger(0, options.getMaxNumberInserts());
        case CHECKSUM:
        case CHECK_TABLE:
        case TRUNCATE:
        case REPAIR_TABLE:
        case OPTIMIZE:
        case ANALYZE_TABLE:
        case UPDATE:
        case CREATE_INDEX:
            return globalState.getRandomly().getInteger(0, 2);
        case SET:
            return 20;
        default:
            throw new AssertionError(a);
        }
    }

    private void generateNrActions() {
        for (int i = 0; i < Action.values().length; i++) {
            int nrPerformed = mapActions(Action.values()[i]);
            Action action = Action.values()[i];
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
        Action nextAction = null;
        int selection = globalState.getRandomly().getInteger(0, total);
        int previousRange = 0;
        for (int i = 0; i < nrActions.length; i++) {
            if (previousRange <= selection && selection < previousRange + nrActions[i]) {
                nextAction = Action.values()[i];
                nrActions[i]--;
                total--;
                return nextAction;
            } else {
                previousRange += nrActions[i];
            }
        }
        throw new AssertionError();
    }
}

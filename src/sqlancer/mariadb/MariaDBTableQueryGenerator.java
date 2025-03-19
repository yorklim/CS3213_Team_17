package sqlancer.mariadb;

import sqlancer.AbstractAction;
import sqlancer.IgnoreMeException;
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

public class MariaDBTableQueryGenerator extends TableQueryGenerator {
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

        @Override
        public SQLQueryAdapter getQuery(MariaDBGlobalState state) throws Exception {
            return sqlQueryProvider.getQuery(state);
        }
    }

    public MariaDBTableQueryGenerator(MariaDBGlobalState globalState) {
        super(Action.values().length, globalState);
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

    public void generate() {
        for (int i = 0; i < Action.values().length; i++) {
            int nrPerformed = mapActions(Action.values()[i]);
            Action action = Action.values()[i];
            nrActions[action.ordinal()] = nrPerformed;
            total += nrPerformed;
        }
    }

    @Override
    public void generateNExecute() throws Exception {
        generate();

        MariaDBGlobalState globalState = (MariaDBGlobalState) super.globalState;

        // Generates Random Queries
        while (!isFinished()) {
            MariaDBTableQueryGenerator.Action nextAction = Action.values()[getRandNextAction()];
            assert nextAction != null;
            SQLQueryAdapter query = null;
            try {
                boolean success = false;
                int nrTries = 0;
                do {
                    query = nextAction.getQuery(globalState);
                    success = globalState.executeStatement(query);
                } while (!success && nrTries++ < 1000);
            } catch (IgnoreMeException e) {

            }
            if (globalState.getSchema().getDatabaseTables().isEmpty()) {
                throw new IgnoreMeException();
            }
        }
    }

}

package sqlancer.mariadb;

import sqlancer.IgnoreMeException;
import sqlancer.MainOptions;
import sqlancer.common.DBMSCommon;
import sqlancer.common.TableQueryGenerator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLQueryProvider;
import sqlancer.doris.DorisProvider;
import sqlancer.mariadb.gen.MariaDBIndexGenerator;
import sqlancer.mariadb.gen.MariaDBInsertGenerator;
import sqlancer.mariadb.gen.MariaDBSetGenerator;
import sqlancer.mariadb.gen.MariaDBTableAdminCommandGenerator;
import sqlancer.mariadb.gen.MariaDBTableGenerator;
import sqlancer.mariadb.gen.MariaDBTruncateGenerator;
import sqlancer.mariadb.gen.MariaDBUpdateGenerator;

import java.util.ArrayList;
import java.util.List;

public class MariaDBTableQueryGenerator implements TableQueryGenerator {
    public enum Action {
        ANALYZE_TABLE, //
        CHECKSUM, //
        CHECK_TABLE, //
        CREATE_INDEX, //
        INSERT, //
        OPTIMIZE, //
        REPAIR_TABLE, //
        SET, //
        TRUNCATE, //
        UPDATE, //
        private final SQLQueryProvider<MariaDBProvider.MariaDBGlobalState> sqlQueryProvider;

        Action(SQLQueryProvider<MariaDBProvider.MariaDBGlobalState> sqlQueryProvider) {
            this.sqlQueryProvider = sqlQueryProvider;
        }

        public SQLQueryAdapter getQuery(MariaDBProvider.MariaDBGlobalState state) throws Exception {
            return sqlQueryProvider.getQuery(state);
        }
    }
    private final MariaDBProvider.MariaDBGlobalState globalState;
    private int total;
    private int[] nrActions;

    public MariaDBTableQueryGenerator(MariaDBProvider.MariaDBGlobalState globalState) {
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


//        while (globalState.getSchema().getDatabaseTables().size() < Randomly.getNotCachedInteger(1, 3)) {
//        String tableName = DBMSCommon.createTableName(globalState.getSchema().getDatabaseTables().size());
//        SQLQueryAdapter createTable = MariaDBTableGenerator.generate(tableName, globalState.getRandomly(),
//                globalState.getSchema());
//        globalState.executeStatement(createTable);
//    }
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
        MainOptions options = globalState.getOptions();
        while (total != 0) {
            Action nextAction = null;
            int selection = globalState.getRandomly().getInteger(0, total);
            int previousRange = 0;
            for (int i = 0; i < nrActions.length; i++) {
                if (previousRange <= selection && selection < previousRange + nrActions[i]) {
                    assert nrActions[nextAction.ordinal()] > 0;
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
//
//            nrActions[nextAction.ordinal()]--;
        SQLQueryAdapter query;
//        try {
//            switch (nextAction) {
//                case CHECKSUM:
//                    query = MariaDBTableAdminCommandGenerator.checksumTable(globalState.getSchema());
//                    break;
//                case CHECK_TABLE:
//                    query = MariaDBTableAdminCommandGenerator.checkTable(globalState.getSchema());
//                    break;
//                case TRUNCATE:
//                    query = MariaDBTruncateGenerator.truncate(globalState.getSchema());
//                    break;
//                case REPAIR_TABLE:
//                    query = MariaDBTableAdminCommandGenerator.repairTable(globalState.getSchema());
//                    break;
//                case INSERT:
//                    query = MariaDBInsertGenerator.insert(globalState.getSchema(), globalState.getRandomly());
//                    break;
//                case OPTIMIZE:
//                    query = MariaDBTableAdminCommandGenerator.optimizeTable(globalState.getSchema());
//                    break;
//                case ANALYZE_TABLE:
//                    query = MariaDBTableAdminCommandGenerator.analyzeTable(globalState.getSchema());
//                    break;
//                case UPDATE:
//                    query = MariaDBUpdateGenerator.update(globalState.getSchema(), globalState.getRandomly());
//                    break;
//                case CREATE_INDEX:
//                    query = MariaDBIndexGenerator.generate(globalState.getSchema());
//                    break;
//                case SET:
//                    query = MariaDBSetGenerator.set(globalState.getRandomly(), options);
//                    break;
//                default:
//                    throw new AssertionError(nextAction);
//            }
//        } catch (IgnoreMeException e) {
//            total--;
//            continue;
//        }
//        try {
//            globalState.executeStatement(query);
//        } catch (Throwable t) {
//            System.err.println(query.getQueryString());
////            throw t;
//        }
//        total--;
//    }
    }
}

package sqlancer.mariadb;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.DBMSCommon;
import sqlancer.common.TableCreator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.mariadb.gen.MariaDBTableGenerator;

public class MariaDBTableCreator extends TableCreator {
    private final MariaDBProvider.MariaDBGlobalState globalState;

    public MariaDBTableCreator(MariaDBProvider.MariaDBGlobalState globalState) {
        this.globalState = globalState;
    }

    private void createTable() throws Exception {
        while (globalState.getSchema().getDatabaseTables().size() < Randomly.getNotCachedInteger(1, 3)) {
            String tableName = DBMSCommon.createTableName(globalState.getSchema().getDatabaseTables().size());
            SQLQueryAdapter createTable = MariaDBTableGenerator.generate(tableName, globalState.getRandomly(),
                    globalState.getSchema());
            globalState.executeStatement(createTable);
        }
    }

    @Override
    public void create() throws Exception {
        createTable();
        // Generates random queries (Insert, Update, Delete, etc.)
        MariaDBTableQueryGenerator generator = new MariaDBTableQueryGenerator(globalState);
        generator.generate();
        // Generates Random Queries
        while (!generator.isFinished()) {
            SQLQueryAdapter nextAction = generator.getRandNextAction();
            assert nextAction != null;
            SQLQueryAdapter query = null;
            try {
                boolean success = false;
                int nrTries = 0;
                do {
                    query = nextAction;
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

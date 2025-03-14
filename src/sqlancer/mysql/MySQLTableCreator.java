package sqlancer.mysql;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.DBMSCommon;
import sqlancer.common.TableCreator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.mysql.gen.MySQLTableGenerator;

public class MySQLTableCreator extends TableCreator {
    private final MySQLGlobalState globalState;

    public MySQLTableCreator(MySQLGlobalState globalState) {
        this.globalState = globalState;
    }

    private void createTable() throws Exception {
        while (globalState.getSchema().getDatabaseTables().size() < Randomly.getNotCachedInteger(1, 2)) {
            String tableName = DBMSCommon.createTableName(globalState.getSchema().getDatabaseTables().size());
            SQLQueryAdapter createTable = MySQLTableGenerator.generate(globalState, tableName);
            globalState.executeStatement(createTable);
        }
    }

    @Override
    public void create() throws Exception {
        createTable();
        MySQLTableQueryGenerator generator = new MySQLTableQueryGenerator(globalState);
        generator.generate();
        // Generates Random Queries
        while (!generator.isFinished()) {
            MySQLTableQueryGenerator.Action nextAction = generator.getRandNextAction();
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
            if (query != null && query.couldAffectSchema()) {
                globalState.updateSchema();
                if (globalState.getSchema().getDatabaseTables().isEmpty()) {
                    throw new IgnoreMeException();
                }
            }
        }

    }
}

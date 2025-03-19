package sqlancer.mariadb;

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

    @Override
    public void create() throws Exception {
        while (globalState.getSchema().getDatabaseTables().size() < Randomly.getNotCachedInteger(1, 3)) {
            String tableName = DBMSCommon.createTableName(globalState.getSchema().getDatabaseTables().size());
            SQLQueryAdapter createTable = MariaDBTableGenerator.generate(tableName, globalState.getRandomly(),
                    globalState.getSchema());
            globalState.executeStatement(createTable);
        }
    }
}

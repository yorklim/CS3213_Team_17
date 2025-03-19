package sqlancer.mysql;

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

    @Override
    public void create() throws Exception {
        while (globalState.getSchema().getDatabaseTables().size() < Randomly.getNotCachedInteger(1, 2)) {
            String tableName = DBMSCommon.createTableName(globalState.getSchema().getDatabaseTables().size());
            SQLQueryAdapter createTable = MySQLTableGenerator.generate(globalState, tableName);
            globalState.executeStatement(createTable);
        }
    }
}

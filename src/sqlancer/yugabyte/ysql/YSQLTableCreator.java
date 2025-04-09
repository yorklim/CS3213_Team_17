package sqlancer.yugabyte.ysql;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.DBMSCommon;
import sqlancer.common.TableCreator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.yugabyte.ysql.gen.YSQLTableGenerator;

public class YSQLTableCreator extends TableCreator {
    private final YSQLGlobalState globalState;

    public YSQLTableCreator(YSQLGlobalState globalState) {
        this.globalState = globalState;
    }

    @Override
    public void create() throws Exception {
        synchronized (YSQLProvider.DDL_LOCK) {
            boolean prevCreationFailed = false; // small optimization - wait only after failed requests
            int numTables = Randomly.fromOptions(4, 5, 6);
            while (globalState.getSchema().getDatabaseTables().size() < numTables) {
                if (!prevCreationFailed) {
                    YSQLProvider.exceptionLessSleep(5000);
                }

                try {
                    String tableName = DBMSCommon.createTableName(globalState.getSchema().getDatabaseTables().size());
                    SQLQueryAdapter createTable = YSQLTableGenerator.generate(tableName, globalState);
                    globalState.executeStatement(createTable);
                    prevCreationFailed = false;
                } catch (IgnoreMeException e) {
                    prevCreationFailed = true;
                }
            }
        }
    }
}

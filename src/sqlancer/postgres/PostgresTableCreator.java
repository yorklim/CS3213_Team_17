package sqlancer.postgres;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.DBMSCommon;
import sqlancer.common.TableCreator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.postgres.gen.PostgresTableGenerator;

public class PostgresTableCreator extends TableCreator {
    private final PostgresGlobalState globalState;

    public PostgresTableCreator(PostgresGlobalState globalState) {
        this.globalState = globalState;
    }

    @Override
    public void create() throws Exception {
        int numTables = Randomly.fromOptions(4, 5, 6);
        while (globalState.getSchema().getDatabaseTables().size() < numTables) {
            try {
                String tableName = DBMSCommon.createTableName(globalState.getSchema().getDatabaseTables().size());
                SQLQueryAdapter createTable = PostgresTableGenerator.generate(tableName, globalState.getSchema(),
                        globalState);
                globalState.executeStatement(createTable);
            } catch (IgnoreMeException e) {

            }
        }
    }
}

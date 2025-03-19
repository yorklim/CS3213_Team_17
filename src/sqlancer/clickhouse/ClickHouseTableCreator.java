package sqlancer.clickhouse;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.clickhouse.ClickHouseProvider.ClickHouseGlobalState;
import sqlancer.clickhouse.gen.ClickHouseCommon;
import sqlancer.clickhouse.gen.ClickHouseTableGenerator;
import sqlancer.common.TableCreator;
import sqlancer.common.query.SQLQueryAdapter;

public class ClickHouseTableCreator extends TableCreator {

    private final ClickHouseGlobalState globalState;

    public ClickHouseTableCreator(ClickHouseGlobalState globalState) {
        this.globalState = globalState;
    }

    @Override
    public void create() throws Exception {
        for (int i = 0; i < Randomly.fromOptions(1, 2, 3, 4, 5); i++) {
            boolean success;
            do {
                String tableName = ClickHouseCommon.createTableName(i);
                SQLQueryAdapter qt = ClickHouseTableGenerator.createTableStatement(tableName, globalState);
                success = globalState.executeStatement(qt);
            } while (!success);
        }
        if (globalState.getSchema().getDatabaseTables().isEmpty()) {
            throw new IgnoreMeException(); // TODO
        }
    }
}

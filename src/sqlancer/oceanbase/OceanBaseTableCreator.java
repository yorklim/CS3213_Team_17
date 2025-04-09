package sqlancer.oceanbase;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.DBMSCommon;
import sqlancer.common.TableCreator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.oceanbase.gen.OceanBaseTableGenerator;

public class OceanBaseTableCreator extends TableCreator {
    private final OceanBaseGlobalState globalState;

    public OceanBaseTableCreator(OceanBaseGlobalState globalState) {
        this.globalState = globalState;
    }

    @Override
    public void create() throws Exception {
        for (int i = 0; i < Randomly.smallNumber() + 1; i++) {
            boolean success = false;
            do {
                try {
                    String tableName = DBMSCommon.createTableName(globalState.getSchema().getDatabaseTables().size());
                    SQLQueryAdapter createTable = OceanBaseTableGenerator.generate(globalState, tableName);
                    success = globalState.executeStatement(createTable);
                } catch (IgnoreMeException e) {

                }
            } while (!success);
        }
    }
}

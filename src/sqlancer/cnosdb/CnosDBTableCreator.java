package sqlancer.cnosdb;

import sqlancer.Randomly;
import sqlancer.cnosdb.gen.CnosDBTableGenerator;
import sqlancer.cnosdb.query.CnosDBOtherQuery;
import sqlancer.common.TableCreator;

public class CnosDBTableCreator extends TableCreator {

    private final CnosDBGlobalState globalState;

    public CnosDBTableCreator(CnosDBGlobalState globalState) {
        this.globalState = globalState;
    }

    @Override
    public void create() throws Exception {
        int numTables = Randomly.fromOptions(4, 5, 6);
        while (globalState.getSchema().getDatabaseTables().size() < numTables) {
            String tableName = String.format("m%d", globalState.getSchema().getDatabaseTables().size());
            CnosDBOtherQuery createTable = CnosDBTableGenerator.generate(tableName);
            globalState.executeStatement(createTable);
        }
    }
}

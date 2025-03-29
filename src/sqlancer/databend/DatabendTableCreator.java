package sqlancer.databend;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.TableCreator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.databend.DatabendProvider.DatabendGlobalState;
import sqlancer.databend.gen.DatabendTableGenerator;

public class DatabendTableCreator extends TableCreator {

    private final DatabendGlobalState globalState;

    public DatabendTableCreator(DatabendGlobalState globalState) {
        this.globalState = globalState;
    }

    @Override
    public void create() throws Exception {
        for (int i = 0; i < Randomly.fromOptions(3, 4); i++) {
            boolean success;
            do {
                SQLQueryAdapter qt = new DatabendTableGenerator().getQuery(globalState);
                success = globalState.executeStatement(qt);
            } while (!success);
        }
        if (globalState.getSchema().getDatabaseTables().isEmpty()) {
            throw new IgnoreMeException(); // TODO
        }
    }

}

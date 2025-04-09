package sqlancer.doris;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.TableCreator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.doris.gen.DorisTableGenerator;

public class DorisTableCreator extends TableCreator {
    private final DorisProvider.DorisGlobalState globalState;

    public DorisTableCreator(DorisProvider.DorisGlobalState globalState) {
        this.globalState = globalState;
    }

    @Override
    public void create() throws Exception {

        for (int i = 0; i < Randomly.fromOptions(1, 2); i++) {
            boolean success = false;
            do {
                SQLQueryAdapter qt = new DorisTableGenerator().getQuery(globalState);
                if (qt != null) {
                    success = globalState.executeStatement(qt);
                }
            } while (!success);
        }
        if (globalState.getSchema().getDatabaseTables().isEmpty()) {
            throw new IgnoreMeException();
        }
    }
}

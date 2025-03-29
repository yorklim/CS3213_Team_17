package sqlancer.yugabyte.ycql;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.TableCreator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.yugabyte.ycql.gen.YCQLTableGenerator;

public class YCQLTableCreator extends TableCreator {
    private final YCQLGlobalState globalState;

    public YCQLTableCreator(YCQLGlobalState globalState) {
        this.globalState = globalState;
    }

    @Override
    public void create() throws Exception {
        for (int i = 0; i < Randomly.fromOptions(1, 2); i++) {
            boolean success = false;
            do {
                try {
                    SQLQueryAdapter qt = new YCQLTableGenerator().getQuery(globalState);
                    success = globalState.executeStatement(qt);
                } catch (IgnoreMeException e) {

                }
            } while (!success);
        }
        if (globalState.getSchema().getDatabaseTables().isEmpty()) {
            throw new IgnoreMeException();
        }
    }
}

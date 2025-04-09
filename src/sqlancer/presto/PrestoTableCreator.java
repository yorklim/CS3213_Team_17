package sqlancer.presto;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.TableCreator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.presto.gen.PrestoTableGenerator;

public class PrestoTableCreator extends TableCreator {
    private final PrestoGlobalState globalState;

    public PrestoTableCreator(PrestoGlobalState globalState) {
        this.globalState = globalState;
    }

    @Override
    public void create() throws Exception {
        for (int i = 0; i < Randomly.fromOptions(1, 2); i++) {
            boolean success = false;
            do {
                try {
                    SQLQueryAdapter qt = new PrestoTableGenerator().getQuery(globalState);
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

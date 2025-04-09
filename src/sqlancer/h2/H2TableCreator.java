package sqlancer.h2;

import sqlancer.Randomly;
import sqlancer.common.TableCreator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.h2.H2Provider.H2GlobalState;

public class H2TableCreator extends TableCreator {

    private final H2GlobalState globalState;

    public H2TableCreator(H2GlobalState globalState) {
        this.globalState = globalState;
    }

    @Override
    public void create() throws Exception {
        if (Randomly.getBoolean()) {
            H2SetGenerator.getQuery(globalState).execute(globalState);
        }
        boolean success;
        for (int i = 0; i < Randomly.fromOptions(1, 2, 3); i++) {
            do {
                SQLQueryAdapter qt = new H2TableGenerator().getQuery(globalState);
                success = globalState.executeStatement(qt);
            } while (!success);
        }
    }
}

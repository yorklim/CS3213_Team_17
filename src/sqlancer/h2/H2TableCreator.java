package sqlancer.h2;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.TableCreator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.h2.H2Provider.H2GlobalState;

public class H2TableCreator extends TableCreator {

    private final H2GlobalState globalState;

    public H2TableCreator(H2GlobalState globalState) {
        this.globalState = globalState;
    }

    private void createTable() throws Exception {
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

    @Override
    public void create() throws Exception {
        createTable();

        H2TableQueryGenerator generator = new H2TableQueryGenerator(globalState);
        generator.generate();

        while (!generator.isFinished()) {
            H2TableQueryGenerator.Action nextAction = generator.getRandNextAction();
            assert nextAction != null;
            SQLQueryAdapter query = null;
            try {
                boolean success = false;
                int nrTries = 0;
                do {
                    query = nextAction.getQuery(globalState);
                    success = globalState.executeStatement(query);
                } while (nextAction.canBeRetried() && !success
                        && nrTries++ < globalState.getOptions().getNrStatementRetryCount());
            } catch (IgnoreMeException e) {

            }
            if (query != null && query.couldAffectSchema()) {
                globalState.updateSchema();
                // throw new IgnoreMeException();
                if (globalState.getSchema().getDatabaseTables().isEmpty()) {
                    throw new IgnoreMeException();
                }
            }
            // if (globalState.getSchema().getDatabaseTables().isEmpty()) {
            //     throw new IgnoreMeException();
            // }
        }
    }

}

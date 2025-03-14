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

    private void createTable() throws Exception {

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

    @Override
    public void create() throws Exception {
        // Creates tables
        createTable();
        // Generates random queries (Insert, Update, Delete, etc.)
        DorisTableQueryGenerator generator = new DorisTableQueryGenerator(globalState);
        generator.generate();
        // Execute queries in random order
        while (!generator.isFinished()) {
            DorisTableQueryGenerator.Action nextAction = generator.getRandNextAction();
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
                if (globalState.getSchema().getDatabaseTables().isEmpty()) {
                    throw new IgnoreMeException();
                }
            }
        }
    }
}

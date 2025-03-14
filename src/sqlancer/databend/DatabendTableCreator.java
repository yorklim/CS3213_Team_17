package sqlancer.databend;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.TableCreator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.databend.DatabendProvider.DatabendGlobalState;
import sqlancer.databend.DatabendTableQueryGenerator.Action;
import sqlancer.databend.gen.DatabendTableGenerator;

public class DatabendTableCreator extends TableCreator {

    private final DatabendGlobalState globalState;

    public DatabendTableCreator(DatabendGlobalState globalState) {
        this.globalState = globalState;
    }

    private void createTable() throws Exception {
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

    @Override
    public void create() throws Exception {
        // Creates tables
        createTable();

        // Generates random queries (Insert, Update, Delete, etc.)
        DatabendTableQueryGenerator generator = new DatabendTableQueryGenerator(globalState);
        generator.generate();

        // Execute queries in random order
        while (!generator.isFinished()) {
            Action nextAction = generator.getRandNextAction();
            assert nextAction != null;
            SQLQueryAdapter query = null;
            try {
                boolean success = false;
                int nrTries = 0;
                do {
                    query = nextAction.getQuery(globalState);
                    success = globalState.executeStatement(query);
                } while (!success && nrTries++ < 1000);
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

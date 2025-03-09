package sqlancer.cnosdb;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.cnosdb.gen.CnosDBTableGenerator;
import sqlancer.cnosdb.query.CnosDBOtherQuery;
import sqlancer.common.TableCreator;

public class CnosDBTableCreator extends TableCreator {

    private final CnosDBGlobalState globalState;

    public CnosDBTableCreator(CnosDBGlobalState globalState) {
        this.globalState = globalState;
    }

    private void createTable() throws Exception {
        int numTables = Randomly.fromOptions(4, 5, 6);
        while (globalState.getSchema().getDatabaseTables().size() < numTables) {
            String tableName = String.format("m%d", globalState.getSchema().getDatabaseTables().size());
            CnosDBOtherQuery createTable = CnosDBTableGenerator.generate(tableName);
            globalState.executeStatement(createTable);
        }
    }

    @Override
    public void create() throws Exception {
        // Creates tables
        createTable();
        // Generates random queries (Insert, Update, Delete, etc.)
        CnosDBTableQueryGenerator generator = new CnosDBTableQueryGenerator(globalState);
        generator.generate();
        // Generates Random Queries
        while (!generator.isFinished()) {
            CnosDBTableQueryGenerator.Action nextAction = generator.getRandNextAction();
            assert nextAction != null;
            CnosDBOtherQuery query = null;
            try {
                boolean success = false;
                int nrTries = 0;
                do {
                    query = nextAction.getQuery(globalState);
                    success = globalState.executeStatement(query);
                } while (!success && nrTries++ < 1000);
            } catch (IgnoreMeException e) {

            }
            if (globalState.getSchema().getDatabaseTables().isEmpty()) {
                throw new IgnoreMeException();
            }
        }

    }
}

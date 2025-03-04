package sqlancer.cockroachdb;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.cockroachdb.CockroachDBProvider.CockroachDBGlobalState;
import sqlancer.cockroachdb.CockroachDBTableQueryGenerator.Action;
import sqlancer.cockroachdb.gen.CockroachDBTableGenerator;
import sqlancer.common.TableCreator;
import sqlancer.common.query.SQLQueryAdapter;

public class CockroachDBTableCreator extends TableCreator {
    CockroachDBGlobalState globalState;

    public CockroachDBTableCreator(CockroachDBProvider.CockroachDBGlobalState globalState) {
        this.globalState = globalState;
    }

    private void createTable() throws Exception {
        for (int i = 0; i < Randomly.fromOptions(2, 3); i++) {
            boolean success = false;
            do {
                try {
                    SQLQueryAdapter q = CockroachDBTableGenerator.generate(globalState);
                    success = globalState.executeStatement(q);
                } catch (IgnoreMeException e) {
                    // continue trying
                }
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
        CockroachDBTableQueryGenerator generator = new CockroachDBTableQueryGenerator(globalState);
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
            if (query != null && query.couldAffectSchema() && globalState.getSchema().getDatabaseTables().isEmpty()) {
                throw new IgnoreMeException();
            }
        }
    }
}

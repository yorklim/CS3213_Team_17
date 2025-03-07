package sqlancer.duckdb;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.TableCreator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.duckdb.DuckDBProvider.DuckDBGlobalState;
import sqlancer.duckdb.DuckDBTableQueryGenerator.Action;
import sqlancer.duckdb.gen.DuckDBTableGenerator;

public class DuckDBTableCreator extends TableCreator {
    private final DuckDBGlobalState globalState;

    public DuckDBTableCreator(DuckDBGlobalState globalState) {
        this.globalState = globalState;
    }

    private void createTable() throws Exception {
        for (int i = 0; i < Randomly.fromOptions(1, 2); i++) {
            boolean success;
            do {
                SQLQueryAdapter qt = new DuckDBTableGenerator().getQuery(globalState);
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
        DuckDBTableQueryGenerator generator = new DuckDBTableQueryGenerator(globalState);
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
                } while (nextAction.canBeRetried() && !success
                        && nrTries++ < globalState.getOptions().getNrStatementRetryCount());
            } catch (IgnoreMeException e) {

            }
            if (query != null && query.couldAffectSchema()) {
                globalState.updateSchema();
                throw new IgnoreMeException();
            }
            if (globalState.getSchema().getDatabaseTables().isEmpty()) {
                throw new IgnoreMeException();
            }
        }
    }
}

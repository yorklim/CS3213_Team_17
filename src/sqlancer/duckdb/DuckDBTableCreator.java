package sqlancer.duckdb;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.TableCreator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.duckdb.DuckDBProvider.DuckDBGlobalState;
import sqlancer.duckdb.gen.DuckDBTableGenerator;

public class DuckDBTableCreator extends TableCreator {
    private final DuckDBGlobalState globalState;

    public DuckDBTableCreator(DuckDBGlobalState globalState) {
        this.globalState = globalState;
    }

    @Override
    public void create() throws Exception {
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
}

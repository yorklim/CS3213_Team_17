package sqlancer.duckdb;

import sqlancer.AbstractAction;
import sqlancer.IgnoreMeException;
import sqlancer.MainOptions;
import sqlancer.Randomly;
import sqlancer.common.TableQueryGenerator;
import sqlancer.common.query.ExpectedErrors;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLQueryProvider;
import sqlancer.duckdb.DuckDBProvider.DuckDBGlobalState;
import sqlancer.duckdb.gen.DuckDBDeleteGenerator;
import sqlancer.duckdb.gen.DuckDBIndexGenerator;
import sqlancer.duckdb.gen.DuckDBInsertGenerator;
import sqlancer.duckdb.gen.DuckDBRandomQuerySynthesizer;
import sqlancer.duckdb.gen.DuckDBUpdateGenerator;
import sqlancer.duckdb.gen.DuckDBViewGenerator;

public class DuckDBTableQueryGenerator extends TableQueryGenerator {
    public enum Action implements AbstractAction<DuckDBGlobalState> {

        INSERT(DuckDBInsertGenerator::getQuery), //
        CREATE_INDEX(DuckDBIndexGenerator::getQuery), //
        VACUUM(g -> new SQLQueryAdapter("VACUUM;")), //
        ANALYZE(g -> new SQLQueryAdapter("ANALYZE;")), //
        DELETE(DuckDBDeleteGenerator::generate), //
        UPDATE(DuckDBUpdateGenerator::getQuery), //
        CREATE_VIEW(DuckDBViewGenerator::generate), //
        EXPLAIN(g -> {
            ExpectedErrors errors = new ExpectedErrors();
            DuckDBErrors.addExpressionErrors(errors);
            DuckDBErrors.addGroupByErrors(errors);
            return new SQLQueryAdapter(
                    "EXPLAIN " + DuckDBToStringVisitor
                            .asString(DuckDBRandomQuerySynthesizer.generateSelect(g, Randomly.smallNumber() + 1)),
                    errors);
        });

        private final SQLQueryProvider<DuckDBGlobalState> sqlQueryProvider;

        Action(SQLQueryProvider<DuckDBGlobalState> sqlQueryProvider) {
            this.sqlQueryProvider = sqlQueryProvider;
        }

        @Override
        public SQLQueryAdapter getQuery(DuckDBGlobalState state) throws Exception {
            return sqlQueryProvider.getQuery(state);
        }
    }

    public DuckDBTableQueryGenerator(DuckDBGlobalState globalState) {
        super(Action.values().length, globalState);
    }

    private int mapActions(Action action) {
        Randomly r = globalState.getRandomly();
        MainOptions options = globalState.getOptions();
        switch (action) {
        case INSERT:
            return r.getInteger(0, options.getMaxNumberInserts());
        case CREATE_INDEX:
            if (!((DuckDBGlobalState) globalState).getDbmsSpecificOptions().testIndexes) {
                return 0;
            }
            // fall through
        case UPDATE:
            return r.getInteger(0, ((DuckDBGlobalState) globalState).getDbmsSpecificOptions().maxNumUpdates + 1);
        case VACUUM: // seems to be ignored
        case ANALYZE: // seems to be ignored
        case EXPLAIN:
            return r.getInteger(0, 2);
        case DELETE:
            return r.getInteger(0, ((DuckDBGlobalState) globalState).getDbmsSpecificOptions().maxNumDeletes + 1);
        case CREATE_VIEW:
            return r.getInteger(0, ((DuckDBGlobalState) globalState).getDbmsSpecificOptions().maxNumViews + 1);
        default:
            throw new AssertionError(action);
        }
    }

    private void generate() {
        for (Action action : Action.values()) {
            int nrPerformed = mapActions(action);
            nrActions[action.ordinal()] = nrPerformed;
            total += nrPerformed;
        }
    }

    @Override
    public void generateNExecute() throws Exception {
        generate();

        DuckDBGlobalState globalState = (DuckDBGlobalState) super.globalState;

        // Execute queries in random order
        while (!isFinished()) {
            Action nextAction = Action.values()[getRandNextAction()];
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

package sqlancer.databend;

import sqlancer.AbstractAction;
import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.TableQueryGenerator;
import sqlancer.common.query.ExpectedErrors;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLQueryProvider;
import sqlancer.databend.DatabendProvider.DatabendGlobalState;
import sqlancer.databend.gen.DatabendDeleteGenerator;
import sqlancer.databend.gen.DatabendInsertGenerator;
import sqlancer.databend.gen.DatabendRandomQuerySynthesizer;
import sqlancer.databend.gen.DatabendViewGenerator;

public class DatabendTableQueryGenerator extends TableQueryGenerator {
    public enum Action implements AbstractAction<DatabendGlobalState> {

        INSERT(DatabendInsertGenerator::getQuery), DELETE(DatabendDeleteGenerator::generate),
        // TODO 等待databend实现update
        // UPDATE(DatabendUpdateGenerator::getQuery), //
        CREATE_VIEW(DatabendViewGenerator::generate), EXPLAIN(g -> {
            ExpectedErrors errors = new ExpectedErrors();
            DatabendErrors.addExpressionErrors(errors);
            DatabendErrors.addGroupByErrors(errors);
            return new SQLQueryAdapter(
                    "EXPLAIN " + DatabendToStringVisitor
                            .asString(DatabendRandomQuerySynthesizer.generateSelect(g, Randomly.smallNumber() + 1)),
                    errors);
        });

        private final SQLQueryProvider<DatabendGlobalState> sqlQueryProvider;

        Action(SQLQueryProvider<DatabendGlobalState> sqlQueryProvider) {
            this.sqlQueryProvider = sqlQueryProvider;
        }

        @Override
        public SQLQueryAdapter getQuery(DatabendGlobalState state) throws Exception {
            return sqlQueryProvider.getQuery(state);
        }
    }

    public DatabendTableQueryGenerator(DatabendGlobalState globalState) {
        super(Action.values().length, globalState);
    }

    private int mapActions(Action a) {
        DatabendGlobalState globalState = (DatabendGlobalState) super.globalState;
        Randomly r = globalState.getRandomly();
        switch (a) {
        case INSERT:
            return r.getInteger(0, globalState.getOptions().getMaxNumberInserts());
        case EXPLAIN:
            return r.getInteger(0, 2);
        // TODO 等待databend实现update && delete
        // case UPDATE:
        // return r.getInteger(0, globalState.getDbmsSpecificOptions().maxNumUpdates + 1);
        case DELETE:
            return r.getInteger(0, globalState.getDbmsSpecificOptions().maxNumDeletes + 1);
        case CREATE_VIEW:
            return r.getInteger(0, globalState.getDbmsSpecificOptions().maxNumViews + 1);
        default:
            throw new AssertionError(a);
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
        // Generates random queries (Insert, Update, Delete, etc.)
        generate();

        DatabendGlobalState globalState = (DatabendGlobalState) super.globalState;
        // Execute queries in random order
        while (isFinished()) {
            Action nextAction = Action.values()[getRandNextAction()];
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

package sqlancer.databend;

import sqlancer.AbstractAction;
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

public class DatabendTableQueryGenerator implements TableQueryGenerator {
    public enum Action implements AbstractAction<DatabendProvider.DatabendGlobalState> {

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

        private final SQLQueryProvider<DatabendProvider.DatabendGlobalState> sqlQueryProvider;

        Action(SQLQueryProvider<DatabendProvider.DatabendGlobalState> sqlQueryProvider) {
            this.sqlQueryProvider = sqlQueryProvider;
        }

        @Override
        public SQLQueryAdapter getQuery(DatabendProvider.DatabendGlobalState state) throws Exception {
            return sqlQueryProvider.getQuery(state);
        }
    }

    private final DatabendGlobalState globalState;
    private int total;
    private int[] nrActions;

    public DatabendTableQueryGenerator(DatabendGlobalState globalState) {
        this.globalState = globalState;
        this.total = 0;
        this.nrActions = new int[Action.values().length];
    }

    private int mapActions(Action a) {
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

    private void generateNrActions() {
        for (Action action : Action.values()) {
            int nrPerformed = mapActions(action);
            nrActions[action.ordinal()] = nrPerformed;
            total += nrPerformed;
        }
    }

    @Override
    public void generate() {
        generateNrActions();
    }

    @Override
    public boolean isFinished() {
        return total == 0;
    }

    @Override
    public Action getRandNextAction() {
        int selection = globalState.getRandomly().getInteger(0, total);
        int previousRange = 0;
        for (int i = 0; i < nrActions.length; i++) {
            if (previousRange <= selection && selection < previousRange + nrActions[i]) {
                assert nrActions[i] > 0;
                nrActions[i]--;
                total--;
                return Action.values()[i];
            } else {
                previousRange += nrActions[i];
            }
        }
        throw new AssertionError();
    }
}

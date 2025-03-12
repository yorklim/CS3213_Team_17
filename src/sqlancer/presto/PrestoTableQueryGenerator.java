package sqlancer.presto;

import java.util.Objects;

import sqlancer.AbstractAction;
import sqlancer.Randomly;
import sqlancer.common.TableQueryGenerator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLQueryProvider;
import sqlancer.presto.gen.PrestoInsertGenerator;

public class PrestoTableQueryGenerator implements TableQueryGenerator {

    public enum Action implements AbstractAction<PrestoGlobalState> {
        // SHOW_TABLES((g) -> new SQLQueryAdapter("SHOW TABLES", new ExpectedErrors(), false, false)), //
        INSERT(PrestoInsertGenerator::getQuery);
        // TODO : check actions based on connector
        // DELETE(PrestoDeleteGenerator::generate), //
        // UPDATE(PrestoUpdateGenerator::getQuery), //
        // CREATE_VIEW(PrestoViewGenerator::generate), //
        // EXPLAIN((g) -> {
        // ExpectedErrors errors = new ExpectedErrors();
        // PrestoErrors.addExpressionErrors(errors);
        // PrestoErrors.addGroupByErrors(errors);
        // return new SQLQueryAdapter(
        // "EXPLAIN " + PrestoToStringVisitor
        // .asString(PrestoRandomQuerySynthesizer.generateSelect(g, Randomly.smallNumber() + 1)),
        // errors);
        // });

        private final SQLQueryProvider<PrestoGlobalState> sqlQueryProvider;

        Action(SQLQueryProvider<PrestoGlobalState> sqlQueryProvider) {
            this.sqlQueryProvider = sqlQueryProvider;
        }

        @Override
        public SQLQueryAdapter getQuery(PrestoGlobalState state) throws Exception {
            return sqlQueryProvider.getQuery(state);
        }
    }

    private final PrestoGlobalState globalState;
    private int total;
    private int[] nrActions;

    public PrestoTableQueryGenerator(PrestoGlobalState globalState) {
        this.globalState = globalState;
        this.total = 0;
        this.nrActions = new int[Action.values().length];
    }

    private int mapActions(Action action) {
        Randomly r = globalState.getRandomly();
        if (Objects.requireNonNull(action) == Action.INSERT) {
            return r.getInteger(0, globalState.getOptions().getMaxNumberInserts());
            // case UPDATE:
            // return r.getInteger(0, globalState.getDbmsSpecificOptions().maxNumUpdates + 1);
            // case EXPLAIN:
            // return r.getInteger(0, 2);
            // case DELETE:
            // return r.getInteger(0, globalState.getDbmsSpecificOptions().maxNumDeletes + 1);
            // case CREATE_VIEW:
            // return r.getInteger(0, globalState.getDbmsSpecificOptions().maxNumViews + 1);
        }
        throw new AssertionError(action);
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

package sqlancer.h2;

import sqlancer.AbstractAction;
import sqlancer.Randomly;
import sqlancer.common.TableQueryGenerator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLQueryProvider;
import sqlancer.h2.H2Provider.H2GlobalState;

public class H2TableQueryGenerator implements TableQueryGenerator {

    public enum Action implements AbstractAction<H2GlobalState> {

        INSERT(H2InsertGenerator::getQuery), //
        INDEX(H2IndexGenerator::getQuery), //
        ANALYZE(g -> new SQLQueryAdapter("ANALYZE")), //
        CREATE_VIEW(H2ViewGenerator::getQuery), //
        UPDATE(H2UpdateGenerator::getQuery), //
        DELETE(H2DeleteGenerator::getQuery), //
        SET(H2SetGenerator::getQuery);

        private final SQLQueryProvider<H2GlobalState> sqlQueryProvider;

        Action(SQLQueryProvider<H2GlobalState> sqlQueryProvider) {
            this.sqlQueryProvider = sqlQueryProvider;
        }

        @Override
        public SQLQueryAdapter getQuery(H2GlobalState state) throws Exception {
            return sqlQueryProvider.getQuery(state);
        }
    }

    private H2GlobalState globalState;
    private int total;
    private int[] nrActions;

    public H2TableQueryGenerator(H2GlobalState globalState) {
        this.globalState = globalState;
        this.total = 0;
        this.nrActions = new int[Action.values().length];
    }

    private int mapActions(Action a) {
        Randomly r = globalState.getRandomly();
        switch (a) {
        case INSERT:
            return r.getInteger(0, globalState.getOptions().getMaxNumberInserts());
        case ANALYZE:
            return r.getInteger(0, 5);
        case INDEX:
        case SET:
            return r.getInteger(0, 5);
        case CREATE_VIEW:
            return r.getInteger(0, 2);
        case UPDATE:
        case DELETE:
            return r.getInteger(0, 10);
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

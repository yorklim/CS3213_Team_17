package sqlancer.hsqldb;

import sqlancer.AbstractAction;
import sqlancer.Randomly;
import sqlancer.common.TableQueryGenerator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLQueryProvider;
import sqlancer.hsqldb.HSQLDBProvider.HSQLDBGlobalState;
import sqlancer.hsqldb.gen.HSQLDBInsertGenerator;
import sqlancer.hsqldb.gen.HSQLDBUpdateGenerator;

public class HSQLDBTableQueryGenerator implements TableQueryGenerator {

    public enum Action implements AbstractAction<HSQLDBGlobalState> {
        INSERT(HSQLDBInsertGenerator::getQuery), UPDATE(HSQLDBUpdateGenerator::getQuery);

        private final SQLQueryProvider<HSQLDBProvider.HSQLDBGlobalState> sqlQueryProvider;

        Action(SQLQueryProvider<HSQLDBProvider.HSQLDBGlobalState> sqlQueryProvider) {
            this.sqlQueryProvider = sqlQueryProvider;
        }

        @Override
        public SQLQueryAdapter getQuery(HSQLDBProvider.HSQLDBGlobalState state) throws Exception {
            return sqlQueryProvider.getQuery(state);
        }
    }

    private HSQLDBGlobalState globalState;
    private int total;
    private int[] nrActions;

    public HSQLDBTableQueryGenerator(HSQLDBGlobalState globalState) {
        this.globalState = globalState;
        this.total = 0;
        this.nrActions = new int[Action.values().length];
    }

    private int mapActions(Action a) {
        Randomly r = globalState.getRandomly();
        switch (a) {
        case INSERT:
            return r.getInteger(0, globalState.getOptions().getMaxNumberInserts());
        case UPDATE:
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

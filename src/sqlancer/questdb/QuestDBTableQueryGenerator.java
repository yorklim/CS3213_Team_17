package sqlancer.questdb;

import sqlancer.AbstractAction;
import sqlancer.Randomly;
import sqlancer.common.TableQueryGenerator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLQueryProvider;
import sqlancer.questdb.QuestDBProvider.QuestDBGlobalState;
import sqlancer.questdb.gen.QuestDBAlterIndexGenerator;
import sqlancer.questdb.gen.QuestDBInsertGenerator;
import sqlancer.questdb.gen.QuestDBTruncateGenerator;

public class QuestDBTableQueryGenerator implements TableQueryGenerator {

    public enum Action implements AbstractAction<QuestDBGlobalState> {
        INSERT(QuestDBInsertGenerator::getQuery), //
        ALTER_INDEX(QuestDBAlterIndexGenerator::getQuery), //
        TRUNCATE(QuestDBTruncateGenerator::generate); //
        // TODO (anxing): maybe implement these later
        // UPDATE(QuestDBUpdateGenerator::getQuery), //
        // CREATE_VIEW(QuestDBViewGenerator::generate), //

        private final SQLQueryProvider<QuestDBGlobalState> sqlQueryProvider;

        Action(SQLQueryProvider<QuestDBGlobalState> sqlQueryProvider) {
            this.sqlQueryProvider = sqlQueryProvider;
        }

        @Override
        public SQLQueryAdapter getQuery(QuestDBGlobalState state) throws Exception {
            return sqlQueryProvider.getQuery(state);
        }
    }

    private QuestDBGlobalState globalState;
    private int total;
    private int[] nrActions;

    public QuestDBTableQueryGenerator(QuestDBGlobalState globalState) {
        this.globalState = globalState;
        this.total = 0;
        this.nrActions = new int[Action.values().length];
    }

    @Override
    public void generate() {
        for (Action action : Action.values()) {
            int nrPerformed = mapActions(globalState, action);
            nrActions[action.ordinal()] = nrPerformed;
            total += nrPerformed;
        }
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

    private static int mapActions(QuestDBGlobalState globalState, Action a) {
        Randomly r = globalState.getRandomly();
        switch (a) {
        case INSERT:
            return r.getInteger(0, globalState.getOptions().getMaxNumberInserts());
        case ALTER_INDEX:
            return r.getInteger(0, 3);
        case TRUNCATE:
            return r.getInteger(0, 5);
        default:
            throw new AssertionError("Unknown action: " + a);
        }
    }

}

package sqlancer.common;

import sqlancer.GlobalState;

public abstract class TableQueryGenerator extends FileExecutor {
    protected int total;
    protected int[] nrActions;
    protected GlobalState<?, ?, ?> globalState;

    public TableQueryGenerator(int actionsLength, GlobalState<?, ?, ?> globalState) {
        this.total = 0;
        this.nrActions = new int[actionsLength];
        this.globalState = globalState;

    }

    protected int getRandNextAction() {
        int selection = globalState.getRandomly().getInteger(0, total);
        int previousRange = 0;
        for (int i = 0; i < nrActions.length; i++) {
            if (previousRange <= selection && selection < previousRange + nrActions[i]) {
                assert nrActions[i] > 0;
                nrActions[i]--;
                total--;
                return i;
            } else {
                previousRange += nrActions[i];
            }
        }
        throw new AssertionError();
    }

    protected boolean isFinished() {
        return total == 0;
    }

    public abstract void generateNExecute() throws Exception;
}

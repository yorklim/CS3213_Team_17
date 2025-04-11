package sqlancer.common;

import java.io.BufferedReader;
import java.io.FileReader;

import sqlancer.GlobalState;
import sqlancer.cnosdb.CnosDBGlobalState;
import sqlancer.cnosdb.query.CnosDBOtherQuery;

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

    public void runQueryFromFileCnos(String file, CnosDBGlobalState globalState) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String cur = br.readLine();
            while (cur != null) {
                globalState.executeStatement(new CnosDBOtherQuery(cur, null));
                cur = br.readLine();
            }
            br.close();
            fr.close();
        } catch (Exception e) {
        }
    }

    protected boolean isFinished() {
        return total == 0;
    }

    public abstract void generateNExecute() throws Exception;
}

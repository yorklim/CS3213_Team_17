package sqlancer.yugabyte.ycql;

import sqlancer.AbstractAction;
import sqlancer.Randomly;
import sqlancer.common.TableQueryGenerator;
import sqlancer.common.query.ExpectedErrors;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLQueryProvider;
import sqlancer.yugabyte.ycql.gen.YCQLAlterTableGenerator;
import sqlancer.yugabyte.ycql.gen.YCQLDeleteGenerator;
import sqlancer.yugabyte.ycql.gen.YCQLIndexGenerator;
import sqlancer.yugabyte.ycql.gen.YCQLInsertGenerator;
import sqlancer.yugabyte.ycql.gen.YCQLRandomQuerySynthesizer;
import sqlancer.yugabyte.ycql.gen.YCQLUpdateGenerator;

public class YCQLTableQueryGenerator implements TableQueryGenerator {

    public enum Action implements AbstractAction<YCQLGlobalState> {
        ALTER(YCQLAlterTableGenerator::getQuery), //
        INSERT(YCQLInsertGenerator::getQuery), //
        CREATE_INDEX(YCQLIndexGenerator::getQuery), //
        DELETE(YCQLDeleteGenerator::generate), //
        UPDATE(YCQLUpdateGenerator::getQuery), //
        EXPLAIN((g) -> {
            ExpectedErrors errors = new ExpectedErrors();
            YCQLErrors.addExpressionErrors(errors);
            return new SQLQueryAdapter(
                    "EXPLAIN " + YCQLToStringVisitor
                            .asString(YCQLRandomQuerySynthesizer.generateSelect(g, Randomly.smallNumber() + 1)),
                    errors);
        });

        private final SQLQueryProvider<YCQLGlobalState> sqlQueryProvider;

        Action(SQLQueryProvider<YCQLGlobalState> sqlQueryProvider) {
            this.sqlQueryProvider = sqlQueryProvider;
        }

        @Override
        public SQLQueryAdapter getQuery(YCQLGlobalState state) throws Exception {
            return sqlQueryProvider.getQuery(state);
        }
    }

    private final YCQLGlobalState globalState;
    private int total;
    private int[] nrActions;

    public YCQLTableQueryGenerator(YCQLGlobalState globalState) {
        this.globalState = globalState;
        this.total = 0;
        this.nrActions = new int[Action.values().length];
    }

    private int mapActions(Action action) {
        Randomly r = globalState.getRandomly();
        switch (action) {
        case ALTER:
            return r.getInteger(0, 10);
        case INSERT:
            return r.getInteger(0, globalState.getOptions().getMaxNumberInserts());
        case CREATE_INDEX:
        case UPDATE:
            return r.getInteger(0, globalState.getDbmsSpecificOptions().maxNumUpdates + 1);
        case EXPLAIN:
            return r.getInteger(0, 2);
        case DELETE:
            return r.getInteger(0, globalState.getDbmsSpecificOptions().maxNumDeletes + 1);
        default:
            throw new AssertionError(action);
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

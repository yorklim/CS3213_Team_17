package sqlancer.yugabyte.ycql;

import sqlancer.AbstractAction;
import sqlancer.IgnoreMeException;
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

public class YCQLTableQueryGenerator extends TableQueryGenerator {

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

    public YCQLTableQueryGenerator(YCQLGlobalState globalState) {
        super(Action.values().length, globalState);
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
            return r.getInteger(0, ((YCQLGlobalState) globalState).getDbmsSpecificOptions().maxNumUpdates + 1);
        case EXPLAIN:
            return r.getInteger(0, 2);
        case DELETE:
            return r.getInteger(0, ((YCQLGlobalState) globalState).getDbmsSpecificOptions().maxNumDeletes + 1);
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

        YCQLGlobalState globalState = (YCQLGlobalState) super.globalState;

        while (!isFinished()) {
            YCQLTableQueryGenerator.Action nextAction = Action.values()[getRandNextAction()];
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

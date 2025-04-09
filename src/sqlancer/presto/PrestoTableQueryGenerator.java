package sqlancer.presto;

import java.util.Objects;

import sqlancer.AbstractAction;
import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.TableQueryGenerator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLQueryProvider;
import sqlancer.presto.gen.PrestoInsertGenerator;

public class PrestoTableQueryGenerator extends TableQueryGenerator {

    public enum Action implements AbstractAction<PrestoGlobalState> {
        INSERT(PrestoInsertGenerator::getQuery);

        private final SQLQueryProvider<PrestoGlobalState> sqlQueryProvider;

        Action(SQLQueryProvider<PrestoGlobalState> sqlQueryProvider) {
            this.sqlQueryProvider = sqlQueryProvider;
        }

        @Override
        public SQLQueryAdapter getQuery(PrestoGlobalState state) throws Exception {
            return sqlQueryProvider.getQuery(state);
        }
    }

    public PrestoTableQueryGenerator(PrestoGlobalState globalState) {
        super(Action.values().length, globalState);
    }

    private int mapActions(Action action) {
        Randomly r = globalState.getRandomly();
        if (Objects.requireNonNull(action) == Action.INSERT) {
            return r.getInteger(0, globalState.getOptions().getMaxNumberInserts());
        }
        throw new AssertionError(action);
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

        PrestoGlobalState globalState = (PrestoGlobalState) super.globalState;

        while (!isFinished()) {
            PrestoTableQueryGenerator.Action nextAction = Action.values()[getRandNextAction()];
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

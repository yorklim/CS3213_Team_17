package sqlancer.h2;

import sqlancer.AbstractAction;
import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.TableQueryGenerator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLQueryProvider;
import sqlancer.h2.H2Provider.H2GlobalState;

public class H2TableQueryGenerator extends TableQueryGenerator {

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

    public H2TableQueryGenerator(H2GlobalState globalState) {
        super(Action.values().length, globalState);

    }

    private int mapActions(Action a) {
        Randomly r = globalState.getRandomly();
        switch (a) {
        case INSERT:
            return r.getInteger(0, globalState.getOptions().getMaxNumberInserts());
        case ANALYZE:
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

        while (!isFinished()) {
            H2TableQueryGenerator.Action nextAction = Action.values()[getRandNextAction()];
            assert nextAction != null;
            SQLQueryAdapter query = null;
            try {
                boolean success = false;
                int nrTries = 0;
                do {
                    query = nextAction.getQuery((H2GlobalState) globalState);
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

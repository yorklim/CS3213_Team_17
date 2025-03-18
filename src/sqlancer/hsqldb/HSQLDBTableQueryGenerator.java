package sqlancer.hsqldb;

import sqlancer.AbstractAction;
import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.TableQueryGenerator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLQueryProvider;
import sqlancer.hsqldb.HSQLDBProvider.HSQLDBGlobalState;
import sqlancer.hsqldb.gen.HSQLDBInsertGenerator;
import sqlancer.hsqldb.gen.HSQLDBUpdateGenerator;

public class HSQLDBTableQueryGenerator extends TableQueryGenerator {

    public enum Action implements AbstractAction<HSQLDBGlobalState> {
        INSERT(HSQLDBInsertGenerator::getQuery), UPDATE(HSQLDBUpdateGenerator::getQuery);

        private final SQLQueryProvider<HSQLDBGlobalState> sqlQueryProvider;

        Action(SQLQueryProvider<HSQLDBGlobalState> sqlQueryProvider) {
            this.sqlQueryProvider = sqlQueryProvider;
        }

        @Override
        public SQLQueryAdapter getQuery(HSQLDBGlobalState state) throws Exception {
            return sqlQueryProvider.getQuery(state);
        }
    }

    public HSQLDBTableQueryGenerator(HSQLDBGlobalState globalState) {
        super(Action.values().length, globalState);
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
            HSQLDBTableQueryGenerator.Action nextAction = Action.values()[getRandNextAction()];
            assert nextAction != null;
            SQLQueryAdapter query = null;
            try {
                boolean success = false;
                int nrTries = 0;
                do {
                    query = nextAction.getQuery((HSQLDBGlobalState) globalState);
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

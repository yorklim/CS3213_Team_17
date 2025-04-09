package sqlancer.clickhouse;

import sqlancer.AbstractAction;
import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.clickhouse.ClickHouseProvider.ClickHouseGlobalState;
import sqlancer.clickhouse.gen.ClickHouseInsertGenerator;
import sqlancer.common.TableQueryGenerator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLQueryProvider;

public class ClickHouseTableQueryGenerator extends TableQueryGenerator {
    public enum Action implements AbstractAction<ClickHouseGlobalState> {

        INSERT(ClickHouseInsertGenerator::getQuery);

        private final SQLQueryProvider<ClickHouseGlobalState> sqlQueryProvider;

        Action(SQLQueryProvider<ClickHouseGlobalState> sqlQueryProvider) {
            this.sqlQueryProvider = sqlQueryProvider;
        }

        @Override
        public SQLQueryAdapter getQuery(ClickHouseGlobalState state) throws Exception {
            return sqlQueryProvider.getQuery(state);
        }
    }

    public ClickHouseTableQueryGenerator(ClickHouseGlobalState globalState) {
        super(Action.values().length, globalState);
    }

    protected int mapActions(Action a) {
        Randomly r = globalState.getRandomly();
        switch (a) {
        case INSERT:
            return r.getInteger(0, globalState.getOptions().getMaxNumberInserts());
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
        // Generates random queries (Insert, Update, Delete, etc.)
        generate();
        ClickHouseGlobalState globalState = (ClickHouseGlobalState) super.globalState;
        // Execute queries in random order
        while (!isFinished()) {
            Action nextAction = Action.values()[getRandNextAction()];
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

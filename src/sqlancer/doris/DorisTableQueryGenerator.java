package sqlancer.doris;

import sqlancer.AbstractAction;
import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.TableQueryGenerator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLQueryProvider;
import sqlancer.doris.DorisProvider.DorisGlobalState;
import sqlancer.doris.gen.DorisAlterTableGenerator;
import sqlancer.doris.gen.DorisDeleteGenerator;
import sqlancer.doris.gen.DorisDropTableGenerator;
import sqlancer.doris.gen.DorisDropViewGenerator;
import sqlancer.doris.gen.DorisIndexGenerator;
import sqlancer.doris.gen.DorisInsertGenerator;
import sqlancer.doris.gen.DorisTableGenerator;
import sqlancer.doris.gen.DorisUpdateGenerator;
import sqlancer.doris.gen.DorisViewGenerator;

public class DorisTableQueryGenerator extends TableQueryGenerator {
    public enum Action implements AbstractAction<DorisGlobalState> {
        CREATE_TABLE(DorisTableGenerator::createRandomTableStatement), CREATE_VIEW(DorisViewGenerator::getQuery),
        CREATE_INDEX(DorisIndexGenerator::getQuery), INSERT(DorisInsertGenerator::getQuery),
        DELETE(DorisDeleteGenerator::generate), UPDATE(DorisUpdateGenerator::getQuery),
        ALTER_TABLE(DorisAlterTableGenerator::getQuery),
        TRUNCATE(
                g -> new SQLQueryAdapter("TRUNCATE TABLE " + g.getSchema().getRandomTable(t -> !t.isView()).getName())),
        DROP_TABLE(DorisDropTableGenerator::dropTable), DROP_VIEW(DorisDropViewGenerator::dropView);

        private final SQLQueryProvider<DorisGlobalState> sqlQueryProvider;

        Action(SQLQueryProvider<DorisGlobalState> sqlQueryProvider) {
            this.sqlQueryProvider = sqlQueryProvider;
        }

        @Override
        public SQLQueryAdapter getQuery(DorisGlobalState state) throws Exception {
            return sqlQueryProvider.getQuery(state);
        }
    }

    public DorisTableQueryGenerator(DorisGlobalState globalState) {
        super(Action.values().length, globalState);
    }

    private int mapActions(Action a) {
        Randomly r = globalState.getRandomly();
        switch (a) {
        case INSERT:
            return r.getInteger(0, globalState.getOptions().getMaxNumberInserts());
        case DELETE:
            return r.getInteger(0, ((DorisGlobalState) globalState).getDbmsSpecificOptions().maxNumDeletes);
        case UPDATE:
            return r.getInteger(0, ((DorisGlobalState) globalState).getDbmsSpecificOptions().maxNumUpdates);
        case ALTER_TABLE:
            return r.getInteger(0, ((DorisGlobalState) globalState).getDbmsSpecificOptions().maxNumTableAlters);
        case TRUNCATE:
            return r.getInteger(0, 2);
        case CREATE_TABLE:
        case CREATE_INDEX:
        case CREATE_VIEW:
        case DROP_TABLE:
        case DROP_VIEW:
            return 0;
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
        // Generates Random Queries

        DorisGlobalState globalState = (DorisGlobalState) super.globalState;

        while (!isFinished()) {
            DorisTableQueryGenerator.Action nextAction = Action.values()[getRandNextAction()];
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

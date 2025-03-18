package sqlancer.cnosdb;

import java.util.Objects;

import sqlancer.AbstractAction;
import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.cnosdb.gen.CnosDBInsertGenerator;
import sqlancer.cnosdb.query.CnosDBOtherQuery;
import sqlancer.cnosdb.query.CnosDBQueryProvider;
import sqlancer.common.TableQueryGenerator;

public class CnosDBTableQueryGenerator extends TableQueryGenerator {
    public enum Action implements AbstractAction<CnosDBGlobalState> {
        INSERT(CnosDBInsertGenerator::insert);

        private final CnosDBQueryProvider<CnosDBGlobalState> sqlQueryProvider;

        Action(CnosDBQueryProvider<CnosDBGlobalState> sqlQueryProvider) {
            this.sqlQueryProvider = sqlQueryProvider;
        }

        @Override
        public CnosDBOtherQuery getQuery(CnosDBGlobalState state) throws Exception {
            return new CnosDBOtherQuery(sqlQueryProvider.getQuery(state).getQueryString(),
                    CnosDBExpectedError.expectedErrors());
        }
    }

    public CnosDBTableQueryGenerator(CnosDBGlobalState globalState) {
        super(Action.values().length, globalState);
    }

    protected int mapActions(Action a) {
        Randomly r = globalState.getRandomly();
        int nrPerformed;
        if (Objects.requireNonNull(a) == Action.INSERT) {
            nrPerformed = r.getInteger(0, globalState.getOptions().getMaxNumberInserts());
        } else {
            throw new AssertionError(a);
        }
        return nrPerformed;
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
        CnosDBGlobalState globalState = (CnosDBGlobalState) this.globalState;
        while (!isFinished()) {
            CnosDBTableQueryGenerator.Action nextAction = Action.values()[getRandNextAction()];
            assert nextAction != null;
            CnosDBOtherQuery query = null;
            try {
                boolean success = false;
                int nrTries = 0;
                do {
                    query = nextAction.getQuery(globalState);
                    success = globalState.executeStatement(query);
                } while (!success && nrTries++ < 1000);
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

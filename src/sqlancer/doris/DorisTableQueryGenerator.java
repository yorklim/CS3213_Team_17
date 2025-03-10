package sqlancer.doris;

import sqlancer.AbstractAction;
import sqlancer.Randomly;
import sqlancer.common.TableQueryGenerator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLQueryProvider;
import sqlancer.doris.gen.DorisAlterTableGenerator;
import sqlancer.doris.gen.DorisDeleteGenerator;
import sqlancer.doris.gen.DorisDropTableGenerator;
import sqlancer.doris.gen.DorisDropViewGenerator;
import sqlancer.doris.gen.DorisIndexGenerator;
import sqlancer.doris.gen.DorisInsertGenerator;
import sqlancer.doris.gen.DorisTableGenerator;
import sqlancer.doris.gen.DorisUpdateGenerator;
import sqlancer.doris.gen.DorisViewGenerator;

public class DorisTableQueryGenerator implements TableQueryGenerator {
    public enum Action implements AbstractAction<DorisProvider.DorisGlobalState> {
         CREATE_TABLE(DorisTableGenerator::createRandomTableStatement), CREATE_VIEW(DorisViewGenerator::getQuery),
         CREATE_INDEX(DorisIndexGenerator::getQuery), INSERT(DorisInsertGenerator::getQuery),
         DELETE(DorisDeleteGenerator::generate), UPDATE(DorisUpdateGenerator::getQuery),
         ALTER_TABLE(DorisAlterTableGenerator::getQuery),
         TRUNCATE(
                 g -> new SQLQueryAdapter("TRUNCATE TABLE " + g.getSchema().getRandomTable(t -> !t.isView()).getName())),
         DROP_TABLE(DorisDropTableGenerator::dropTable), DROP_VIEW(DorisDropViewGenerator::dropView);

         private final SQLQueryProvider<DorisProvider.DorisGlobalState> sqlQueryProvider;

         Action(SQLQueryProvider<DorisProvider.DorisGlobalState> sqlQueryProvider) {
             this.sqlQueryProvider = sqlQueryProvider;
         }

         @Override
         public SQLQueryAdapter getQuery(DorisProvider.DorisGlobalState state) throws Exception {
             return sqlQueryProvider.getQuery(state);
         }
     }




    private final DorisProvider.DorisGlobalState globalState;
    private int total;
    private int[] nrActions;

    public DorisTableQueryGenerator(DorisProvider.DorisGlobalState globalState) {
        this.globalState = globalState;
        this.total = 0;
        this.nrActions = new int[Action.values().length];
    }

     private int mapActions(Action a) {
         Randomly r = globalState.getRandomly();
         switch (a) {
         case INSERT:
             return r.getInteger(0, globalState.getOptions().getMaxNumberInserts());
         case DELETE:
             return r.getInteger(0, globalState.getDbmsSpecificOptions().maxNumDeletes);
         case UPDATE:
             return r.getInteger(0, globalState.getDbmsSpecificOptions().maxNumUpdates);
         case ALTER_TABLE:
             return r.getInteger(0, globalState.getDbmsSpecificOptions().maxNumTableAlters);
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
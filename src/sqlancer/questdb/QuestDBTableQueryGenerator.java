package sqlancer.questdb;

import sqlancer.AbstractAction;
import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.TableQueryGenerator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLQueryProvider;
import sqlancer.questdb.QuestDBProvider.QuestDBGlobalState;
import sqlancer.questdb.gen.QuestDBAlterIndexGenerator;
import sqlancer.questdb.gen.QuestDBInsertGenerator;
import sqlancer.questdb.gen.QuestDBTruncateGenerator;

public class QuestDBTableQueryGenerator extends TableQueryGenerator {

    public enum Action implements AbstractAction<QuestDBGlobalState> {
        INSERT(QuestDBInsertGenerator::getQuery), //
        ALTER_INDEX(QuestDBAlterIndexGenerator::getQuery), //
        TRUNCATE(QuestDBTruncateGenerator::generate); //
        // TODO (anxing): maybe implement these later
        // UPDATE(QuestDBUpdateGenerator::getQuery), //
        // CREATE_VIEW(QuestDBViewGenerator::generate), //

        private final SQLQueryProvider<QuestDBGlobalState> sqlQueryProvider;

        Action(SQLQueryProvider<QuestDBGlobalState> sqlQueryProvider) {
            this.sqlQueryProvider = sqlQueryProvider;
        }

        @Override
        public SQLQueryAdapter getQuery(QuestDBGlobalState state) throws Exception {
            return sqlQueryProvider.getQuery(state);
        }
    }

    public QuestDBTableQueryGenerator(QuestDBGlobalState globalState) {
        super(Action.values().length, globalState);
    }

    private int mapActions(Action a) {
        Randomly r = globalState.getRandomly();
        switch (a) {
            case INSERT:
                return r.getInteger(0, globalState.getOptions().getMaxNumberInserts());
            case ALTER_INDEX:
                return r.getInteger(0, 3);
            case TRUNCATE:
                return r.getInteger(0, 5);
            default:
                throw new AssertionError("Unknown action: " + a);
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

        QuestDBGlobalState globalState = (QuestDBGlobalState) this.globalState;

        while (!isFinished()) {
            QuestDBTableQueryGenerator.Action nextAction = Action.values()[getRandNextAction()];
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
                throw new IgnoreMeException();
            }
            if (globalState.getSchema().getDatabaseTables().isEmpty()) {
                throw new IgnoreMeException();
            }
        }
    }
}

package sqlancer.yugabyte.ysql;

import sqlancer.AbstractAction;
import sqlancer.Randomly;
import sqlancer.common.TableQueryGenerator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLQueryProvider;
import sqlancer.yugabyte.ysql.gen.YSQLAlterTableGenerator;
import sqlancer.yugabyte.ysql.gen.YSQLAnalyzeGenerator;
import sqlancer.yugabyte.ysql.gen.YSQLCommentGenerator;
import sqlancer.yugabyte.ysql.gen.YSQLDeleteGenerator;
import sqlancer.yugabyte.ysql.gen.YSQLDiscardGenerator;
import sqlancer.yugabyte.ysql.gen.YSQLDropIndexGenerator;
import sqlancer.yugabyte.ysql.gen.YSQLIndexGenerator;
import sqlancer.yugabyte.ysql.gen.YSQLInsertGenerator;
import sqlancer.yugabyte.ysql.gen.YSQLNotifyGenerator;
import sqlancer.yugabyte.ysql.gen.YSQLSequenceGenerator;
import sqlancer.yugabyte.ysql.gen.YSQLSetGenerator;
import sqlancer.yugabyte.ysql.gen.YSQLTableGroupGenerator;
import sqlancer.yugabyte.ysql.gen.YSQLTransactionGenerator;
import sqlancer.yugabyte.ysql.gen.YSQLTruncateGenerator;
import sqlancer.yugabyte.ysql.gen.YSQLUpdateGenerator;
import sqlancer.yugabyte.ysql.gen.YSQLVacuumGenerator;
import sqlancer.yugabyte.ysql.gen.YSQLViewGenerator;

public class YSQLTableQueryGenerator implements TableQueryGenerator {

    public enum Action implements AbstractAction<YSQLGlobalState> {
        ANALYZE(YSQLAnalyzeGenerator::create), //
        ALTER_TABLE(g -> YSQLAlterTableGenerator.create(g.getSchema().getRandomTable(t -> !t.isView()), g)), //
        COMMIT(g -> {
            SQLQueryAdapter query;
            if (Randomly.getBoolean()) {
                query = new SQLQueryAdapter("COMMIT", true);
            } else if (Randomly.getBoolean()) {
                query = YSQLTransactionGenerator.executeBegin();
            } else {
                query = new SQLQueryAdapter("ROLLBACK", true);
            }
            return query;
        }), //
        DELETE(YSQLDeleteGenerator::create), //
        DISCARD(YSQLDiscardGenerator::create), //
        DROP_INDEX(YSQLDropIndexGenerator::create), //
        CREATE_INDEX(YSQLIndexGenerator::generate), //
        INSERT(YSQLInsertGenerator::insert), //
        UPDATE(YSQLUpdateGenerator::create), //
        TRUNCATE(YSQLTruncateGenerator::create), //
        TABLEGROUP(YSQLTableGroupGenerator::create), //
        VACUUM(YSQLVacuumGenerator::create), //
        SET(YSQLSetGenerator::create), // TODO insert yugabyte sets
        SET_CONSTRAINTS((g) -> {
            String sb = "SET CONSTRAINTS ALL " + Randomly.fromOptions("DEFERRED", "IMMEDIATE");
            return new SQLQueryAdapter(sb);
        }), //
        RESET_ROLE((g) -> new SQLQueryAdapter("RESET ROLE")), //
        COMMENT_ON(YSQLCommentGenerator::generate), //
        RESET((g) -> new SQLQueryAdapter("RESET ALL") /*
                                                       * https://www.postgres.org/docs/devel/sql-reset.html TODO: also
                                                       * configuration parameter
                                                       */), //
        NOTIFY(YSQLNotifyGenerator::createNotify), //
        LISTEN((g) -> YSQLNotifyGenerator.createListen()), //
        UNLISTEN((g) -> YSQLNotifyGenerator.createUnlisten()), //
        CREATE_SEQUENCE(YSQLSequenceGenerator::createSequence), //
        CREATE_VIEW(YSQLViewGenerator::create);

        private final SQLQueryProvider<YSQLGlobalState> sqlQueryProvider;

        Action(SQLQueryProvider<YSQLGlobalState> sqlQueryProvider) {
            this.sqlQueryProvider = sqlQueryProvider;
        }

        @Override
        public SQLQueryAdapter getQuery(YSQLGlobalState state) throws Exception {
            return sqlQueryProvider.getQuery(state);
        }
    }

    private final YSQLGlobalState globalState;
    private int total;
    private int[] nrActions;

    public YSQLTableQueryGenerator(YSQLGlobalState globalState) {
        this.globalState = globalState;
        this.total = 0;
        this.nrActions = new int[Action.values().length];
    }

    private int mapActions(Action action) {
        Randomly r = globalState.getRandomly();
        switch (action) {
        case CREATE_INDEX:
        case RESET:
        case ANALYZE:
        case TABLEGROUP:
            return r.getInteger(0, 3);
        case DISCARD:
        case DROP_INDEX:
        case ALTER_TABLE:
            return r.getInteger(0, 5);
        case COMMIT:
            return r.getInteger(0, 0);
        case DELETE:
        case RESET_ROLE:
        case VACUUM:
        case SET_CONSTRAINTS:
        case SET:
        case COMMENT_ON:
        case NOTIFY:
        case LISTEN:
        case UNLISTEN:
        case CREATE_SEQUENCE:
        case TRUNCATE:
        case CREATE_VIEW:
            return r.getInteger(0, 2);
        case UPDATE:
            return r.getInteger(0, 10);
        case INSERT:
            return r.getInteger(0, globalState.getOptions().getMaxNumberInserts());
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

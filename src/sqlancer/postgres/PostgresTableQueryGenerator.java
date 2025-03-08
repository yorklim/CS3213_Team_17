package sqlancer.postgres;

import sqlancer.AbstractAction;
import sqlancer.Randomly;
import sqlancer.common.TableQueryGenerator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLQueryProvider;
import sqlancer.postgres.gen.PostgresAlterTableGenerator;
import sqlancer.postgres.gen.PostgresAnalyzeGenerator;
import sqlancer.postgres.gen.PostgresClusterGenerator;
import sqlancer.postgres.gen.PostgresCommentGenerator;
import sqlancer.postgres.gen.PostgresDeleteGenerator;
import sqlancer.postgres.gen.PostgresDiscardGenerator;
import sqlancer.postgres.gen.PostgresDropIndexGenerator;
import sqlancer.postgres.gen.PostgresIndexGenerator;
import sqlancer.postgres.gen.PostgresInsertGenerator;
import sqlancer.postgres.gen.PostgresNotifyGenerator;
import sqlancer.postgres.gen.PostgresReindexGenerator;
import sqlancer.postgres.gen.PostgresSequenceGenerator;
import sqlancer.postgres.gen.PostgresSetGenerator;
import sqlancer.postgres.gen.PostgresStatisticsGenerator;
import sqlancer.postgres.gen.PostgresTransactionGenerator;
import sqlancer.postgres.gen.PostgresTruncateGenerator;
import sqlancer.postgres.gen.PostgresUpdateGenerator;
import sqlancer.postgres.gen.PostgresVacuumGenerator;
import sqlancer.postgres.gen.PostgresViewGenerator;

public class PostgresTableQueryGenerator implements TableQueryGenerator {

    public enum Action implements AbstractAction<PostgresGlobalState> {
        ANALYZE(PostgresAnalyzeGenerator::create), //
        ALTER_TABLE(g -> PostgresAlterTableGenerator.create(g.getSchema().getRandomTable(t -> !t.isView()), g,
                PostgresProvider.generateOnlyKnown)), //
        CLUSTER(PostgresClusterGenerator::create), //
        COMMIT(g -> {
            SQLQueryAdapter query;
            if (Randomly.getBoolean()) {
                query = new SQLQueryAdapter("COMMIT", true);
            } else if (Randomly.getBoolean()) {
                query = PostgresTransactionGenerator.executeBegin();
            } else {
                query = new SQLQueryAdapter("ROLLBACK", true);
            }
            return query;
        }), //
        CREATE_STATISTICS(PostgresStatisticsGenerator::insert), //
        DROP_STATISTICS(PostgresStatisticsGenerator::remove), //
        DELETE(PostgresDeleteGenerator::create), //
        DISCARD(PostgresDiscardGenerator::create), //
        DROP_INDEX(PostgresDropIndexGenerator::create), //
        INSERT(PostgresInsertGenerator::insert), //
        UPDATE(PostgresUpdateGenerator::create), //
        TRUNCATE(PostgresTruncateGenerator::create), //
        VACUUM(PostgresVacuumGenerator::create), //
        REINDEX(PostgresReindexGenerator::create), //
        SET(PostgresSetGenerator::create), //
        CREATE_INDEX(PostgresIndexGenerator::generate), //
        SET_CONSTRAINTS((g) -> {
            StringBuilder sb = new StringBuilder();
            sb.append("SET CONSTRAINTS ALL ");
            sb.append(Randomly.fromOptions("DEFERRED", "IMMEDIATE"));
            return new SQLQueryAdapter(sb.toString());
        }), //
        RESET_ROLE((g) -> new SQLQueryAdapter("RESET ROLE")), //
        COMMENT_ON(PostgresCommentGenerator::generate), //
        RESET((g) -> new SQLQueryAdapter("RESET ALL") /*
                                                       * https://www.postgresql.org/docs/devel/sql-reset.html TODO: also
                                                       * configuration parameter
                                                       */), //
        NOTIFY(PostgresNotifyGenerator::createNotify), //
        LISTEN((g) -> PostgresNotifyGenerator.createListen()), //
        UNLISTEN((g) -> PostgresNotifyGenerator.createUnlisten()), //
        CREATE_SEQUENCE(PostgresSequenceGenerator::createSequence), //
        CREATE_VIEW(PostgresViewGenerator::create);

        private final SQLQueryProvider<PostgresGlobalState> sqlQueryProvider;

        Action(SQLQueryProvider<PostgresGlobalState> sqlQueryProvider) {
            this.sqlQueryProvider = sqlQueryProvider;
        }

        @Override
        public SQLQueryAdapter getQuery(PostgresGlobalState state) throws Exception {
            return sqlQueryProvider.getQuery(state);
        }
    }

    private final PostgresGlobalState globalState;
    private int total;
    private int[] nrActions;

    public PostgresTableQueryGenerator(PostgresGlobalState globalState) {
        this.globalState = globalState;
        this.total = 0;
        this.nrActions = new int[Action.values().length];
    }

    // For Citus coupling...
    public int mapActions(PostgresGlobalState globalState, Action action) {
        return mapActions(action);
    }

    private int mapActions(Action action) {
        Randomly r = globalState.getRandomly();
        switch (action) {
        case CREATE_INDEX:
        case CLUSTER:
        case REINDEX:
        case RESET:
        case ANALYZE:
            return r.getInteger(0, 3);
        case ALTER_TABLE:
        case CREATE_STATISTICS:
        case DISCARD:
        case DROP_INDEX:
        case DELETE:
        case RESET_ROLE:
        case SET:
            return r.getInteger(0, 5);
        case COMMIT:
            return r.getInteger(0, 0);
        case VACUUM:
        case SET_CONSTRAINTS:
        case COMMENT_ON:
        case NOTIFY:
        case LISTEN:
        case UNLISTEN:
        case CREATE_SEQUENCE:
        case DROP_STATISTICS:
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

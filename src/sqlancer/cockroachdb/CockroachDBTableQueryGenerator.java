package sqlancer.cockroachdb;

import sqlancer.MainOptions;
import sqlancer.Randomly;
import sqlancer.common.TableQueryGenerator;
import sqlancer.common.query.ExpectedErrors;
import sqlancer.common.query.SQLQueryAdapter;

import sqlancer.cockroachdb.gen.CockroachDBCommentOnGenerator;
import sqlancer.cockroachdb.gen.CockroachDBCreateStatisticsGenerator;
import sqlancer.cockroachdb.gen.CockroachDBDeleteGenerator;
import sqlancer.cockroachdb.gen.CockroachDBDropTableGenerator;
import sqlancer.cockroachdb.gen.CockroachDBDropViewGenerator;
import sqlancer.cockroachdb.gen.CockroachDBIndexGenerator;
import sqlancer.cockroachdb.gen.CockroachDBInsertGenerator;
import sqlancer.cockroachdb.gen.CockroachDBRandomQuerySynthesizer;
import sqlancer.cockroachdb.gen.CockroachDBSetClusterSettingGenerator;
import sqlancer.cockroachdb.gen.CockroachDBSetSessionGenerator;
import sqlancer.cockroachdb.gen.CockroachDBShowGenerator;
import sqlancer.cockroachdb.gen.CockroachDBTableGenerator;
import sqlancer.cockroachdb.gen.CockroachDBTruncateGenerator;
import sqlancer.cockroachdb.gen.CockroachDBUpdateGenerator;
import sqlancer.cockroachdb.gen.CockroachDBViewGenerator;
import sqlancer.common.query.SQLQueryProvider;

public class CockroachDBTableQueryGenerator implements TableQueryGenerator {
    public enum Action {
        CREATE_TABLE(CockroachDBTableGenerator::generate), CREATE_INDEX(CockroachDBIndexGenerator::create), //
        CREATE_VIEW(CockroachDBViewGenerator::generate), //
        CREATE_STATISTICS(CockroachDBCreateStatisticsGenerator::create), //
        INSERT(CockroachDBInsertGenerator::insert), //
        UPDATE(CockroachDBUpdateGenerator::gen), //
        SET_SESSION(CockroachDBSetSessionGenerator::create), //
        SET_CLUSTER_SETTING(CockroachDBSetClusterSettingGenerator::create), //
        DELETE(CockroachDBDeleteGenerator::delete), //
        TRUNCATE(CockroachDBTruncateGenerator::truncate), //
        DROP_TABLE(CockroachDBDropTableGenerator::drop), //
        DROP_VIEW(CockroachDBDropViewGenerator::drop), //
        COMMENT_ON(CockroachDBCommentOnGenerator::comment), //
        SHOW(CockroachDBShowGenerator::show), //
        TRANSACTION(g -> {
            String s = Randomly.fromOptions("BEGIN", "ROLLBACK", "COMMIT");
            return new SQLQueryAdapter(s, ExpectedErrors.from("there is no transaction in progress",
                    "there is already a transaction in progress", "current transaction is aborted"));
        }), EXPLAIN(g -> {
            StringBuilder sb = new StringBuilder("EXPLAIN ");
            ExpectedErrors errors = new ExpectedErrors();
            if (Randomly.getBoolean()) {
                sb.append("(");
                sb.append(Randomly.fromOptions("VERBOSE", "TYPES", "OPT", "DISTSQL", "VEC"));
                sb.append(") ");
                errors.add("cannot set EXPLAIN mode more than once");
                errors.add("unable to vectorize execution plan");
                errors.add("unsupported type");
                errors.add("vectorize is set to 'off'");
            }
            sb.append(CockroachDBRandomQuerySynthesizer.generate(g, Randomly.smallNumber() + 1));
            CockroachDBErrors.addExpressionErrors(errors);
            return new SQLQueryAdapter(sb.toString(), errors);
        }), //
        SCRUB(g -> new SQLQueryAdapter(
                "EXPERIMENTAL SCRUB table " + g.getSchema().getRandomTable(t -> !t.isView()).getName(),
                // https://github.com/cockroachdb/cockroach/issues/46401
                ExpectedErrors.from("scrub-fk: column \"t.rowid\" does not exist",
                        "check-constraint: cannot access temporary tables of other sessions" /*
                                                                                              * https:// github. com/
                                                                                              * cockroachdb / cockroach
                                                                                              * /issues/ 47031
                                                                                              */))), //
        SPLIT(g -> {
            StringBuilder sb = new StringBuilder("ALTER INDEX ");
            CockroachDBSchema.CockroachDBTable randomTable = g.getSchema().getRandomTable();
            sb.append(randomTable.getName());
            sb.append("@");
            sb.append(randomTable.getRandomIndex());
            if (Randomly.getBoolean()) {
                sb.append(" SPLIT AT VALUES (true), (false);");
            } else {
                sb.append(" SPLIT AT VALUES (NULL);");
            }
            return new SQLQueryAdapter(sb.toString(), ExpectedErrors.from("must be of type"));
        });

        private final SQLQueryProvider<CockroachDBProvider.CockroachDBGlobalState> sqlQueryProvider;

        Action(SQLQueryProvider<CockroachDBProvider.CockroachDBGlobalState> sqlQueryProvider) {
            this.sqlQueryProvider = sqlQueryProvider;
        }

        public SQLQueryAdapter getQuery(CockroachDBProvider.CockroachDBGlobalState state) throws Exception {
            return sqlQueryProvider.getQuery(state);
        }
    }

    private final CockroachDBProvider.CockroachDBGlobalState globalState;
    private int total;
    private int[] nrActions;

    public CockroachDBTableQueryGenerator(CockroachDBProvider.CockroachDBGlobalState globalState) {
        this.globalState = globalState;
        this.total = 0;
        this.nrActions = new int[Action.values().length];
    }

    private int mapActions(Action action) {
        MainOptions options = globalState.getOptions();
        int nrPerformed = 0;
        switch (action) {
        case INSERT:
            nrPerformed = globalState.getRandomly().getInteger(0, options.getMaxNumberInserts());
            break;
        case UPDATE:
        case SPLIT:
            nrPerformed = globalState.getRandomly().getInteger(0, 3);
            break;
        case EXPLAIN:
            nrPerformed = globalState.getRandomly().getInteger(0, 10);
            break;
        case SHOW:
        case TRUNCATE:
        case DELETE:
        case CREATE_STATISTICS:
            nrPerformed = globalState.getRandomly().getInteger(0, 2);
            break;
        case CREATE_VIEW:
            nrPerformed = globalState.getRandomly().getInteger(0, 2);
            break;
        case SET_SESSION:
        case SET_CLUSTER_SETTING:
            nrPerformed = globalState.getRandomly().getInteger(0, 3);
            break;
        case CREATE_INDEX:
            nrPerformed = globalState.getRandomly().getInteger(0, 10);
            break;
        case COMMENT_ON:
        case SCRUB:
            nrPerformed = 0; /*
                              * there are a number of open SCRUB bugs, of which
                              * https://github.com/cockroachdb/cockroach/issues/47116 crashes the server
                              */
            break;
        case TRANSACTION:
        case CREATE_TABLE:
        case DROP_TABLE:
        case DROP_VIEW:
            nrPerformed = 0; // r.getInteger(0, 0);
            break;
        default:
            throw new AssertionError(action);
        }
        return nrPerformed;
    }

    private void generateNrActions() {
        for (Action action : Action.values()) {
            int nrPerformed = mapActions(action);
            nrActions[action.ordinal()] = nrPerformed;
            total += nrPerformed;
        }
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
                nrActions[i]--;
                total--;
                return Action.values()[i];
            } else {
                previousRange += nrActions[i];
            }
        }
        throw new AssertionError();
    }

    @Override
    public void generate() {
        generateNrActions();
    }
}

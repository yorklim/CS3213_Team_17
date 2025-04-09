package sqlancer.yugabyte.ysql.oracle;

import static sqlancer.yugabyte.ysql.YSQLProvider.DDL_LOCK;

import java.util.Arrays;
import java.util.List;

import sqlancer.IgnoreMeException;
import sqlancer.Main;
import sqlancer.MainOptions;
import sqlancer.SQLConnection;
import sqlancer.common.DBMSCommon;
import sqlancer.common.oracle.TestOracle;
import sqlancer.common.query.ExpectedErrors;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.yugabyte.ysql.YSQLErrors;
import sqlancer.yugabyte.ysql.YSQLGlobalState;
import sqlancer.yugabyte.ysql.YSQLTableQueryGenerator;
import sqlancer.yugabyte.ysql.gen.YSQLTableGenerator;

public class YSQLCatalog implements TestOracle<YSQLGlobalState> {
    protected final YSQLGlobalState state;

    protected final ExpectedErrors errors = new ExpectedErrors();
    protected final Main.StateLogger logger;
    protected final MainOptions options;
    protected final SQLConnection con;

    private final List<YSQLTableQueryGenerator.Action> dmlActions = Arrays.asList(YSQLTableQueryGenerator.Action.INSERT,
            YSQLTableQueryGenerator.Action.UPDATE, YSQLTableQueryGenerator.Action.DELETE);
    private final List<YSQLTableQueryGenerator.Action> catalogActions = Arrays.asList(
            YSQLTableQueryGenerator.Action.CREATE_VIEW, YSQLTableQueryGenerator.Action.CREATE_SEQUENCE,
            YSQLTableQueryGenerator.Action.ALTER_TABLE, YSQLTableQueryGenerator.Action.SET_CONSTRAINTS,
            YSQLTableQueryGenerator.Action.DISCARD, YSQLTableQueryGenerator.Action.DROP_INDEX,
            YSQLTableQueryGenerator.Action.COMMENT_ON, YSQLTableQueryGenerator.Action.RESET_ROLE,
            YSQLTableQueryGenerator.Action.RESET);
    private final List<YSQLTableQueryGenerator.Action> diskActions = Arrays
            .asList(YSQLTableQueryGenerator.Action.TRUNCATE, YSQLTableQueryGenerator.Action.VACUUM);

    public YSQLCatalog(YSQLGlobalState globalState) {
        this.state = globalState;
        this.con = state.getConnection();
        this.logger = state.getLogger();
        this.options = state.getOptions();
        YSQLErrors.addCommonExpressionErrors(errors);
        YSQLErrors.addCommonFetchErrors(errors);
    }

    private YSQLTableQueryGenerator.Action getRandomAction(List<YSQLTableQueryGenerator.Action> actions) {
        return actions.get(state.getRandomly().getInteger(0, actions.size()));
    }

    protected void createTables(YSQLGlobalState globalState, int numTables) throws Exception {
        synchronized (DDL_LOCK) {
            while (globalState.getSchema().getDatabaseTables().size() < numTables) {
                // TODO concurrent DDLs may produce a lot of noise in test logs so its disabled right now
                // added timeout to avoid possible catalog collisions
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new AssertionError();
                }

                try {
                    String tableName = DBMSCommon.createTableName(globalState.getSchema().getDatabaseTables().size());
                    SQLQueryAdapter createTable = YSQLTableGenerator.generate(tableName, globalState);
                    globalState.executeStatement(createTable);
                    globalState.getManager().incrementSelectQueryCount();
                    globalState.executeStatement(new SQLQueryAdapter("COMMIT", true));
                } catch (IgnoreMeException e) {
                    // do nothing
                }
            }
        }
    }

    @Override
    public void check() throws Exception {
        // create table or evaluate catalog test
        int seed = state.getRandomly().getInteger(1, 100);
        if (seed > 95) {
            createTables(state, 1);
        } else {
            YSQLTableQueryGenerator.Action randomAction;

            if (seed > 40) {
                randomAction = getRandomAction(dmlActions);
            } else if (seed > 10) {
                randomAction = getRandomAction(catalogActions);
            } else {
                randomAction = getRandomAction(diskActions);
            }

            state.executeStatement(randomAction.getQuery(state));
        }
        state.getManager().incrementSelectQueryCount();
    }
}

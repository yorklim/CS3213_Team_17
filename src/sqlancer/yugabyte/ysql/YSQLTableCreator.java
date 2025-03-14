package sqlancer.yugabyte.ysql;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.DBMSCommon;
import sqlancer.common.TableCreator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.yugabyte.ysql.gen.YSQLTableGenerator;

public class YSQLTableCreator extends TableCreator {
    private final YSQLGlobalState globalState;

    public YSQLTableCreator(YSQLGlobalState globalState) {
        this.globalState = globalState;
    }

    private void createTable() throws Exception {
        synchronized (YSQLProvider.DDL_LOCK) {
            boolean prevCreationFailed = false; // small optimization - wait only after failed requests
            int numTables = Randomly.fromOptions(4, 5, 6);
            while (globalState.getSchema().getDatabaseTables().size() < numTables) {
                if (!prevCreationFailed) {
                    YSQLProvider.exceptionLessSleep(5000);
                }

                try {
                    String tableName = DBMSCommon.createTableName(globalState.getSchema().getDatabaseTables().size());
                    SQLQueryAdapter createTable = YSQLTableGenerator.generate(tableName, YSQLProvider.generateOnlyKnown,
                            globalState);
                    globalState.executeStatement(createTable);
                    prevCreationFailed = false;
                } catch (IgnoreMeException e) {
                    prevCreationFailed = true;
                }
            }
        }
    }

    @Override
    public void create() throws Exception {
        createTable();

        YSQLTableQueryGenerator generator = new YSQLTableQueryGenerator(globalState);
        generator.generate();

        while (!generator.isFinished()) {
            YSQLTableQueryGenerator.Action nextAction = generator.getRandNextAction();
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

        globalState.executeStatement(new SQLQueryAdapter("COMMIT", true));
        globalState.executeStatement(new SQLQueryAdapter("SET SESSION statement_timeout = 15000;\n"));
    }
}

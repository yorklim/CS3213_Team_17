package sqlancer.postgres;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.DBMSCommon;
import sqlancer.common.TableCreator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.postgres.gen.PostgresTableGenerator;

public class PostgresTableCreator extends TableCreator {
    private final PostgresGlobalState globalState;
    private final boolean generateOnlyKnown;

    public PostgresTableCreator(PostgresGlobalState globalState, Boolean generateOnlyKnown) {
        this.globalState = globalState;
        this.generateOnlyKnown = generateOnlyKnown;
    }

    private void createTable() throws Exception {
        for (int i = 0; i < Randomly.fromOptions(4, 5, 6); i++) {
            boolean success = false;
            do {
                try {
                    String tableName = DBMSCommon.createTableName(globalState.getSchema().getDatabaseTables().size());
                    SQLQueryAdapter createTable = PostgresTableGenerator.generate(tableName, globalState.getSchema(),
                            generateOnlyKnown, globalState);
                    success = globalState.executeStatement(createTable);
                } catch (IgnoreMeException e) {

                }
            } while (!success);
        }
    }

    @Override
    public void create() throws Exception {
        createTable();

        PostgresTableQueryGenerator generator = new PostgresTableQueryGenerator(globalState);
        generator.generate();

        while (!generator.isFinished()) {
            PostgresTableQueryGenerator.Action nextAction = generator.getRandNextAction();
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

        globalState.executeStatement(new SQLQueryAdapter("COMMIT", true));
        globalState.executeStatement(new SQLQueryAdapter("SET SESSION statement_timeout = 5000;\n", true));
    }
}

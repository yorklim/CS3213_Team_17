package sqlancer.oceanbase;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.DBMSCommon;
import sqlancer.common.TableCreator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.oceanbase.gen.OceanBaseTableGenerator;

public class OceanBaseTableCreator extends TableCreator {
    private final OceanBaseGlobalState globalState;

    public OceanBaseTableCreator(OceanBaseGlobalState globalState) {
        this.globalState = globalState;
    }

    private void createTable() throws Exception {
        for (int i = 0; i < Randomly.smallNumber() + 1; i++) {
            boolean success = false;
            do {
                try {
                    String tableName = DBMSCommon.createTableName(globalState.getSchema().getDatabaseTables().size());
                    SQLQueryAdapter createTable = OceanBaseTableGenerator.generate(globalState, tableName);
                    success = globalState.executeStatement(createTable);
                } catch (IgnoreMeException e) {

                }
            } while (!success);
        }
    }

    @Override
    public void create() throws Exception {
        createTable();

        OceanBaseTableQueryGenerator generator = new OceanBaseTableQueryGenerator(globalState);
        generator.generate();

        while (!generator.isFinished()) {
            OceanBaseTableQueryGenerator.Action nextAction = generator.getRandNextAction();
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

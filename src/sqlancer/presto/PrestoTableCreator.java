package sqlancer.presto;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.TableCreator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.presto.gen.PrestoTableGenerator;

public class PrestoTableCreator extends TableCreator {
    private final PrestoGlobalState globalState;

    public PrestoTableCreator(PrestoGlobalState globalState) {
        this.globalState = globalState;
    }

    private void createTable() throws Exception {
        for (int i = 0; i < Randomly.fromOptions(1, 2); i++) {
            boolean success = false;
            do {
                try {
                    SQLQueryAdapter qt = new PrestoTableGenerator().getQuery(globalState);
                    success = globalState.executeStatement(qt);
                } catch (IgnoreMeException e) {

                }
            } while (!success);
        }
        if (globalState.getSchema().getDatabaseTables().isEmpty()) {
            throw new IgnoreMeException();
        }
    }

    @Override
    public void create() throws Exception {
        createTable();

        PrestoTableQueryGenerator generator = new PrestoTableQueryGenerator(globalState);
        generator.generate();

        while (!generator.isFinished()) {
            PrestoTableQueryGenerator.Action nextAction = generator.getRandNextAction();
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

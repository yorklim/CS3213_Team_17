package sqlancer.cockroachdb;

import sqlancer.IgnoreMeException;
import sqlancer.common.TableCreator;
import sqlancer.cockroachdb.CockroachDBTableQueryGenerator.Action;
import sqlancer.common.query.SQLQueryAdapter;

public class CockroachDBTableCreator extends TableCreator {
    CockroachDBProvider.CockroachDBGlobalState globalState;

    public CockroachDBTableCreator(CockroachDBProvider.CockroachDBGlobalState globalState) {
        this.globalState = globalState;
    }
    @Override
    public void create() throws Exception{
        CockroachDBTableQueryGenerator generator = new CockroachDBTableQueryGenerator(globalState);
        generator.generate();
        while (!generator.isFinished()) {
            Action nextAction = generator.getRandNextAction();
            assert nextAction != null;
            SQLQueryAdapter query = null;
            try {
                boolean success = false;
                int nrTries = 0;
                do {
                       query = nextAction.getQuery(globalState);
                       success = globalState.executeStatement(query);
                } while (!success && nrTries++ < 1000);
            } catch (IgnoreMeException e) {

            }
            if (query != null && query.couldAffectSchema() && globalState.getSchema().getDatabaseTables().isEmpty()) {
                throw new IgnoreMeException();
            }
        }
    }
}

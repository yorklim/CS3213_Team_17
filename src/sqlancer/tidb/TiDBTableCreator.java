package sqlancer.tidb;

import java.sql.SQLException;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.TableCreator;
import sqlancer.common.query.ExpectedErrors;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.tidb.TiDBProvider.TiDBGlobalState;
import sqlancer.tidb.gen.TiDBTableGenerator;

public class TiDBTableCreator extends TableCreator {

    private final TiDBGlobalState globalState;

    public TiDBTableCreator(TiDBGlobalState globalState) {
        this.globalState = globalState;
    }


    @Override
    public void create() throws Exception {
        for (int i = 0; i < Randomly.fromOptions(1, 2); i++) {
            boolean success;
            do {
                SQLQueryAdapter qt = new TiDBTableGenerator().getQuery(globalState);
                success = globalState.executeStatement(qt);
            } while (!success);
        }
    }
}

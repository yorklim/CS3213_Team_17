package sqlancer.questdb;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.TableCreator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.questdb.QuestDBProvider.QuestDBGlobalState;
import sqlancer.questdb.gen.QuestDBTableGenerator;

public class QuestDBTableCreator extends TableCreator {

    private final QuestDBGlobalState globalState;

    public QuestDBTableCreator(QuestDBGlobalState globalState) {
        this.globalState = globalState;
    }

    @Override
    public void create() throws Exception {
        for (int i = 0; i < Randomly.fromOptions(1, 2); i++) {
            boolean success;
            do {
                SQLQueryAdapter qt = new QuestDBTableGenerator().getQuery(globalState, null);
                success = globalState.executeStatement(qt);
            } while (!success);
        }
    }

}

package sqlancer.oceanbase;

import sqlancer.AbstractAction;
import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.DBMSCommon;
import sqlancer.common.TableQueryGenerator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLQueryProvider;
import sqlancer.oceanbase.gen.OceanBaseAlterTable;
import sqlancer.oceanbase.gen.OceanBaseDeleteGenerator;
import sqlancer.oceanbase.gen.OceanBaseDropIndex;
import sqlancer.oceanbase.gen.OceanBaseInsertGenerator;
import sqlancer.oceanbase.gen.OceanBaseTableGenerator;
import sqlancer.oceanbase.gen.OceanBaseTruncateTableGenerator;
import sqlancer.oceanbase.gen.OceanBaseUpdateGenerator;
import sqlancer.oceanbase.gen.datadef.OceanBaseIndexGenerator;

public class OceanBaseTableQueryGenerator extends TableQueryGenerator {

    public enum Action implements AbstractAction<OceanBaseGlobalState> {
        SHOW_TABLES((g) -> new SQLQueryAdapter("SHOW TABLES")), INSERT(OceanBaseInsertGenerator::insertRow),
        CREATE_INDEX(OceanBaseIndexGenerator::create), ALTER_TABLE(OceanBaseAlterTable::create),
        TRUNCATE_TABLE(OceanBaseTruncateTableGenerator::generate),
        SELECT_INFO((g) -> new SQLQueryAdapter(
                "select TABLE_NAME, ENGINE from information_schema.TABLES where table_schema = '" + g.getDatabaseName()
                        + "'")),
        CREATE_TABLE((g) -> {
            String tableName = DBMSCommon.createTableName(g.getSchema().getDatabaseTables().size());

            return OceanBaseTableGenerator.generate(g, tableName);
        }), DELETE(OceanBaseDeleteGenerator::delete), UPDATE(OceanBaseUpdateGenerator::update),
        DROP_INDEX(OceanBaseDropIndex::generate);

        private final SQLQueryProvider<OceanBaseGlobalState> sqlQueryProvider;

        Action(SQLQueryProvider<OceanBaseGlobalState> sqlQueryProvider) {
            this.sqlQueryProvider = sqlQueryProvider;
        }

        @Override
        public SQLQueryAdapter getQuery(OceanBaseGlobalState globalState) throws Exception {
            return sqlQueryProvider.getQuery(globalState);
        }
    }

    public OceanBaseTableQueryGenerator(OceanBaseGlobalState globalState) {
        super(Action.values().length, globalState);
    }

    private int mapActions(Action action) {
        Randomly r = globalState.getRandomly();
        switch (action) {
        case DROP_INDEX:
        case TRUNCATE_TABLE:
            return r.getInteger(0, 2);
        case SHOW_TABLES:
        case CREATE_TABLE:
            return r.getInteger(0, 1);
        case INSERT:
            return r.getInteger(0, globalState.getOptions().getMaxNumberInserts());
        case CREATE_INDEX:
        case ALTER_TABLE:
        case UPDATE:
            return r.getInteger(0, 5);
        case SELECT_INFO:
        case DELETE:
            return r.getInteger(0, 10);
        default:
            throw new AssertionError(action);
        }
    }

    private void generate() {
        for (Action action : Action.values()) {
            int nrPerformed = mapActions(action);
            nrActions[action.ordinal()] = nrPerformed;
            total += nrPerformed;
        }
    }

    @Override
    public void generateNExecute() throws Exception {
        generate();

        OceanBaseGlobalState globalState = (OceanBaseGlobalState) super.globalState;

        while (!isFinished()) {
            OceanBaseTableQueryGenerator.Action nextAction = Action.values()[getRandNextAction()];
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

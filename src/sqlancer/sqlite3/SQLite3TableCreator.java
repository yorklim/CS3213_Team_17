package sqlancer.sqlite3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.DBMSCommon;
import sqlancer.common.TableCreator;
import sqlancer.common.query.ExpectedErrors;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.sqlite3.gen.SQLite3CreateVirtualRtreeTabelGenerator;
import sqlancer.sqlite3.gen.ddl.SQLite3CreateVirtualFTSTableGenerator;
import sqlancer.sqlite3.gen.ddl.SQLite3TableGenerator;
import sqlancer.sqlite3.schema.SQLite3Schema;

public class SQLite3TableCreator extends TableCreator {

    private final SQLite3GlobalState globalState;

    // PRAGMAS to achieve good performance
    private static final List<String> DEFAULT_PRAGMAS = Arrays.asList("PRAGMA cache_size = 50000;",
            "PRAGMA temp_store=MEMORY;", "PRAGMA synchronous=off;");

    private enum TableType {
        NORMAL, FTS, RTREE
    }

    public SQLite3TableCreator(SQLite3GlobalState globalState) {
        this.globalState = globalState;
    }

    @Override
    public void create() throws Exception {
        boolean isCreatingTable = createTable();
        if (!isCreatingTable) {
            return;
        }

    }

    private boolean createTable() throws Exception {
        Randomly r = new Randomly(SQLite3SpecialStringGenerator::generate);
        globalState.setRandomly(r);

        if (!globalState.getDbmsSpecificOptions().generateDatabase) {
            return false;
        }

        addSensiblePragmaDefaults(globalState);

        int nrTablesToCreate = 1;
        if (Randomly.getBoolean()) {
            nrTablesToCreate++;
        }
        while (Randomly.getBooleanWithSmallProbability()) {
            nrTablesToCreate++;
        }
        int i = 0;
        do {
            SQLQueryAdapter tableQuery = getTableQuery(globalState, i++);
            globalState.executeStatement(tableQuery);
        } while (globalState.getSchema().getDatabaseTables().size() < nrTablesToCreate);

        assert globalState.getSchema().getTables().getTables().size() == nrTablesToCreate;

        checkTablesForGeneratedColumnLoops(globalState);

        if (globalState.getDbmsSpecificOptions().testDBStats && Randomly.getBooleanWithSmallProbability()) {
            SQLQueryAdapter tableQuery = new SQLQueryAdapter(
                    "CREATE VIRTUAL TABLE IF NOT EXISTS stat USING dbstat(main)");
            globalState.executeStatement(tableQuery);
        }

        return true;
    }

    private void addSensiblePragmaDefaults(SQLite3GlobalState globalState) throws Exception {
        List<String> pragmasToExecute = new ArrayList<>();
        if (!Randomly.getBooleanWithSmallProbability()) {
            pragmasToExecute.addAll(DEFAULT_PRAGMAS);
        }
        if (Randomly.getBoolean() && globalState.getDbmsSpecificOptions().oracles != SQLite3OracleFactory.PQS) {
            // the PQS implementation currently assumes the default behavior of LIKE
            pragmasToExecute.add("PRAGMA case_sensitive_like=ON;");
        }
        if (Randomly.getBoolean() && globalState.getDbmsSpecificOptions().oracles != SQLite3OracleFactory.PQS) {
            // the encoding has an influence how binary strings are cast
            pragmasToExecute.add(String.format("PRAGMA encoding = '%s';",
                    Randomly.fromOptions("UTF-8", "UTF-16", "UTF-16le", "UTF-16be")));
        }
        for (String s : pragmasToExecute) {
            globalState.executeStatement(new SQLQueryAdapter(s));
        }
    }

    private SQLQueryAdapter getTableQuery(SQLite3GlobalState globalState, int i) throws AssertionError {
        SQLQueryAdapter tableQuery;
        List<TableType> options = new ArrayList<>(Arrays.asList(TableType.values()));
        if (!globalState.getDbmsSpecificOptions().testFts) {
            options.remove(TableType.FTS);
        }
        if (!globalState.getDbmsSpecificOptions().testRtree) {
            options.remove(TableType.RTREE);
        }
        switch (Randomly.fromList(options)) {
        case NORMAL:
            String tableName = DBMSCommon.createTableName(i);
            tableQuery = SQLite3TableGenerator.createTableStatement(tableName, globalState);
            break;
        case FTS:
            String ftsTableName = "v" + DBMSCommon.createTableName(i);
            tableQuery = SQLite3CreateVirtualFTSTableGenerator.createTableStatement(ftsTableName,
                    globalState.getRandomly());
            break;
        case RTREE:
            String rTreeTableName = "rt" + i;
            tableQuery = SQLite3CreateVirtualRtreeTabelGenerator.createTableStatement(rTreeTableName, globalState);
            break;
        default:
            throw new AssertionError();
        }
        return tableQuery;
    }

    private void checkTablesForGeneratedColumnLoops(SQLite3GlobalState globalState) throws Exception {
        for (SQLite3Schema.SQLite3Table table : globalState.getSchema().getDatabaseTables()) {
            SQLQueryAdapter q = new SQLQueryAdapter("SELECT * FROM " + table.getName(),
                    ExpectedErrors.from("needs an odd number of arguments", " requires an even number of arguments",
                            "generated column loop", "integer overflow", "malformed JSON",
                            "JSON cannot hold BLOB values", "JSON path error", "labels must be TEXT",
                            "table does not support scanning"));
            if (!q.execute(globalState)) {
                throw new IgnoreMeException();
            }
        }
    }

}

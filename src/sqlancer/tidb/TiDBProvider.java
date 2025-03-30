package sqlancer.tidb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

import com.google.auto.service.AutoService;

import sqlancer.DatabaseProvider;
import sqlancer.MainOptions;
import sqlancer.SQLConnection;
import sqlancer.SQLGlobalState;
import sqlancer.SQLProviderAdapter;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLancerResultSet;
import sqlancer.tidb.TiDBProvider.TiDBGlobalState;
import sqlancer.tidb.TiDBSchema.TiDBTable;
import sqlancer.tidb.gen.TiDBInsertGenerator;

@AutoService(DatabaseProvider.class)
public class TiDBProvider extends SQLProviderAdapter<TiDBGlobalState, TiDBOptions> {

    public TiDBProvider() {
        super(TiDBGlobalState.class, TiDBOptions.class);
    }

    public static class TiDBGlobalState extends SQLGlobalState<TiDBOptions, TiDBSchema> {

        @Override
        protected TiDBSchema readSchema() throws SQLException {
            return TiDBSchema.fromConnection(getConnection());
        }

    }

    @Override
    public void generateDatabase(TiDBGlobalState globalState) throws Exception {
        // Table creation (Creates Schema * Insert data into tables)
        TiDBTableCreator tableCreator = new TiDBTableCreator(globalState);
        // Generate random queries (Insert, Update, Delete, etc.)
        TiDBTableQueryGenerator tableQueryGenerator = new TiDBTableQueryGenerator(globalState);

        String staticTable = System.getProperty("staticTable");
        // For Future Custom Queries for Testing (Table Creation)
        if (staticTable == null || !staticTable.equals("true")) {
            tableCreator.create();
        } else {
            tableCreator.runQueryFromFile("staticTable.sql", globalState);
        }

        String staticQuery = System.getProperty("staticQuery");
        // For Future Custom Queries for Testing (Table Query Generation)
        if (staticTable == null || !staticQuery.equals("true")) {
            tableQueryGenerator.generateNExecute();
        } else {
            tableQueryGenerator.runQueryFromFile("staticQuery.sql", globalState);
        }

    }

    @Override
    public SQLConnection createDatabase(TiDBGlobalState globalState) throws SQLException {
        String host = globalState.getOptions().getHost();
        int port = globalState.getOptions().getPort();
        if (host == null) {
            host = TiDBOptions.DEFAULT_HOST;
        }
        if (port == MainOptions.NO_SET_PORT) {
            port = TiDBOptions.DEFAULT_PORT;
        }

        String databaseName = globalState.getDatabaseName();
        String url = String.format("jdbc:mysql://%s:%d/", host, port);
        Connection con = DriverManager.getConnection(url, globalState.getOptions().getUserName(),
                globalState.getOptions().getPassword());
        globalState.getState().logStatement("USE test");
        globalState.getState().logStatement("DROP DATABASE IF EXISTS " + databaseName);
        String createDatabaseCommand = "CREATE DATABASE " + databaseName;
        globalState.getState().logStatement(createDatabaseCommand);
        globalState.getState().logStatement("USE " + databaseName);
        try (Statement s = con.createStatement()) {
            s.execute("DROP DATABASE IF EXISTS " + databaseName);
            if (globalState.getDbmsSpecificOptions().nonPreparePlanCache) {
                s.execute("set global tidb_enable_non_prepared_plan_cache=ON;");
            }
        }
        try (Statement s = con.createStatement()) {
            s.execute(createDatabaseCommand);
        }
        con.close();
        con = DriverManager.getConnection(url + databaseName, globalState.getOptions().getUserName(),
                globalState.getOptions().getPassword());
        return new SQLConnection(con);
    }

    @Override
    public String getDBMSName() {
        return "tidb";
    }

    @Override
    public String getQueryPlan(String selectStr, TiDBGlobalState globalState) throws Exception {
        String queryPlan = "";
        if (globalState.getOptions().logEachSelect()) {
            globalState.getLogger().writeCurrent(selectStr);
            try {
                globalState.getLogger().getCurrentFileWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        SQLQueryAdapter q = new SQLQueryAdapter("EXPLAIN FORMAT=brief " + selectStr);
        try (SQLancerResultSet rs = q.executeAndGet(globalState)) {
            if (rs != null) {
                while (rs.next()) {
                    String targetQueryPlan = rs.getString(1).replace("├─", "").replace("└─", "").replace("│", "").trim()
                            + ";"; // Unify format
                    queryPlan += targetQueryPlan;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return queryPlan;
    }

    @Override
    protected double[] initializeWeightedAverageReward() {
        return new double[TiDBTableQueryGenerator.Action.values().length];
    }

    @Override
    protected void executeMutator(int index, TiDBGlobalState globalState) throws Exception {
        SQLQueryAdapter queryMutateTable = TiDBTableQueryGenerator.Action.values()[index].getQuery(globalState);
        globalState.executeStatement(queryMutateTable);
    }

    @Override
    public boolean addRowsToAllTables(TiDBGlobalState globalState) throws Exception {
        List<TiDBTable> tablesNoRow = globalState.getSchema().getDatabaseTables().stream()
                .filter(t -> t.getNrRows(globalState) == 0).collect(Collectors.toList());
        for (TiDBTable table : tablesNoRow) {
            SQLQueryAdapter queryAddRows = TiDBInsertGenerator.getQuery(globalState, table);
            globalState.executeStatement(queryAddRows);
        }
        return true;
    }

}

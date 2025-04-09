package sqlancer.cockroachdb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.auto.service.AutoService;

import sqlancer.DatabaseProvider;
import sqlancer.Main.QueryManager;
import sqlancer.MainOptions;
import sqlancer.SQLConnection;
import sqlancer.SQLGlobalState;
import sqlancer.SQLProviderAdapter;
import sqlancer.cockroachdb.CockroachDBProvider.CockroachDBGlobalState;
import sqlancer.cockroachdb.CockroachDBSchema.CockroachDBTable;
import sqlancer.cockroachdb.CockroachDBTableQueryGenerator.Action;
import sqlancer.cockroachdb.gen.CockroachDBInsertGenerator;
import sqlancer.common.query.ExpectedErrors;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLancerResultSet;

@AutoService(DatabaseProvider.class)
public class CockroachDBProvider extends SQLProviderAdapter<CockroachDBGlobalState, CockroachDBOptions> {

    public CockroachDBProvider() {
        super(CockroachDBGlobalState.class, CockroachDBOptions.class);
    }

    public static class CockroachDBGlobalState extends SQLGlobalState<CockroachDBOptions, CockroachDBSchema> {

        @Override
        protected CockroachDBSchema readSchema() throws SQLException {
            return CockroachDBSchema.fromConnection(getConnection());
        }

    }

    @Override
    public void generateDatabase(CockroachDBGlobalState globalState) throws Exception {
        QueryManager<SQLConnection> manager = globalState.getManager();
        List<String> standardSettings = new ArrayList<>();
        standardSettings.add("--Don't send automatic bug reports");
        standardSettings.add("SET CLUSTER SETTING debug.panic_on_failed_assertions = true;");
        standardSettings.add("SET CLUSTER SETTING diagnostics.reporting.enabled    = false;");
        standardSettings.add("SET CLUSTER SETTING diagnostics.reporting.send_crash_reports = false;");

        standardSettings.add("-- Disable the collection of metrics and hope that it helps performance");
        standardSettings.add("SET CLUSTER SETTING sql.metrics.statement_details.enabled = 'off'");
        standardSettings.add("SET CLUSTER SETTING sql.metrics.statement_details.plan_collection.enabled = 'off'");
        standardSettings.add("SET CLUSTER SETTING sql.stats.automatic_collection.enabled = 'off'");
        standardSettings.add("SET CLUSTER SETTING timeseries.storage.enabled = 'off'");

        if (globalState.getDbmsSpecificOptions().testHashIndexes) {
            standardSettings.add("set experimental_enable_hash_sharded_indexes='on';");
        }
        if (globalState.getDbmsSpecificOptions().testTempTables) {
            standardSettings.add("SET experimental_enable_temp_tables = 'on'");
        }
        for (String s : standardSettings) {
            manager.execute(new SQLQueryAdapter(s));
        }

        // Table creation (Creates Schema & Insert data into tables)
        CockroachDBTableCreator tableCreator = new CockroachDBTableCreator(globalState);
        // Generate random queries (Insert, Update, Delete, etc.)
        CockroachDBTableQueryGenerator tableQueryGenerator = new CockroachDBTableQueryGenerator(globalState);

        String staticTable = System.getProperty("staticTable");
        // For Future Custom Queries for Testing (Table Creation)
        if (staticTable == null) {
            tableCreator.create();
        } else {
            tableCreator.runQueryFromFile(staticTable, globalState);
        }

        String staticQuery = System.getProperty("staticQuery");
        // For Future Custom Queries for Testing (Table Query Generation)
        if (staticQuery == null) {
            tableQueryGenerator.generateNExecute();
        } else {
            tableQueryGenerator.runQueryFromFile(staticQuery, globalState);
        }

        if (globalState.getDbmsSpecificOptions().getTestOracleFactory().stream()
                .anyMatch(o -> o == CockroachDBOracleFactory.CERT)) {
            // Enfore statistic collected for all tables
            ExpectedErrors errors = new ExpectedErrors();
            CockroachDBErrors.addExpressionErrors(errors);
            for (CockroachDBTable table : globalState.getSchema().getDatabaseTables()) {
                globalState.executeStatement(new SQLQueryAdapter("ANALYZE " + table.getName() + ";", errors));
            }
        }
    }

    @Override
    public SQLConnection createDatabase(CockroachDBGlobalState globalState) throws SQLException {
        String host = globalState.getOptions().getHost();
        int port = globalState.getOptions().getPort();
        if (host == null) {
            host = CockroachDBOptions.DEFAULT_HOST;
        }
        if (port == MainOptions.NO_SET_PORT) {
            port = CockroachDBOptions.DEFAULT_PORT;
        }
        String databaseName = globalState.getDatabaseName();
        String url = String.format("jdbc:postgresql://%s:%d/test", host, port);
        Connection con = DriverManager.getConnection(url, globalState.getOptions().getUserName(),
                globalState.getOptions().getPassword());
        globalState.getState().logStatement("USE test");
        globalState.getState().logStatement("DROP DATABASE IF EXISTS " + databaseName + " CASCADE");
        String createDatabaseCommand = "CREATE DATABASE " + databaseName;
        globalState.getState().logStatement(createDatabaseCommand);
        globalState.getState().logStatement("USE " + databaseName);
        try (Statement s = con.createStatement()) {
            s.execute("DROP DATABASE IF EXISTS " + databaseName);
        }
        try (Statement s = con.createStatement()) {
            s.execute(createDatabaseCommand);
        }
        con.close();
        con = DriverManager.getConnection(String.format("jdbc:postgresql://%s:%d/%s", host, port, databaseName),
                globalState.getOptions().getUserName(), globalState.getOptions().getPassword());
        return new SQLConnection(con);
    }

    @Override
    public String getDBMSName() {
        return "cockroachdb";
    }

    @Override
    public String getQueryPlan(String selectStr, CockroachDBGlobalState globalState) throws Exception {
        String queryPlan = "";
        String explainQuery = "EXPLAIN (OPT) " + selectStr;
        if (globalState.getOptions().logEachSelect()) {
            globalState.getLogger().writeCurrent(explainQuery);
            try {
                globalState.getLogger().getCurrentFileWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        SQLQueryAdapter q = new SQLQueryAdapter(explainQuery);
        boolean afterProjection = false; // Remove the concrete expression after each Projection operator
        try (SQLancerResultSet rs = q.executeAndGet(globalState)) {
            if (rs != null) {
                while (rs.next()) {
                    String targetQueryPlan = rs.getString(1).replace("└──", "").replace("├──", "").replace("│", "")
                            .trim() + ";"; // Unify format
                    if (afterProjection) {
                        afterProjection = false;
                        continue;
                    }
                    if (targetQueryPlan.startsWith("projections")) {
                        afterProjection = true;
                    }
                    // Remove all concrete expressions by keywords
                    if (targetQueryPlan.contains(">") || targetQueryPlan.contains("<") || targetQueryPlan.contains("=")
                            || targetQueryPlan.contains("*") || targetQueryPlan.contains("+")
                            || targetQueryPlan.contains("'")) {
                        continue;
                    }
                    queryPlan += targetQueryPlan;
                }
            }
        } catch (AssertionError e) {
            throw new AssertionError("Explain failed: " + explainQuery);
        }

        return queryPlan;
    }

    @Override
    protected double[] initializeWeightedAverageReward() {
        return new double[Action.values().length];
    }

    @Override
    protected void executeMutator(int index, CockroachDBGlobalState globalState) throws Exception {
        SQLQueryAdapter queryMutateTable = Action.values()[index].getQuery(globalState);
        globalState.executeStatement(queryMutateTable);
    }

    @Override
    public boolean addRowsToAllTables(CockroachDBGlobalState globalState) throws Exception {
        List<CockroachDBTable> tablesNoRow = globalState.getSchema().getDatabaseTables().stream()
                .filter(t -> t.getNrRows(globalState) == 0).collect(Collectors.toList());
        for (CockroachDBTable table : tablesNoRow) {
            SQLQueryAdapter queryAddRows = CockroachDBInsertGenerator.insert(globalState, table);
            globalState.executeStatement(queryAddRows);
        }
        return true;
    }

}

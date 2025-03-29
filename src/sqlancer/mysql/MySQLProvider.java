package sqlancer.mysql;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.google.auto.service.AutoService;

import sqlancer.DatabaseProvider;
import sqlancer.MainOptions;
import sqlancer.SQLConnection;
import sqlancer.SQLProviderAdapter;
import sqlancer.common.DatabaseUtils;
import sqlancer.common.query.ExpectedErrors;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.mysql.MySQLSchema.MySQLColumn;
import sqlancer.mysql.MySQLSchema.MySQLTable;
import sqlancer.mysql.gen.MySQLInsertGenerator;

@AutoService(DatabaseProvider.class)
public class MySQLProvider extends SQLProviderAdapter<MySQLGlobalState, MySQLOptions> {

    public MySQLProvider() {
        super(MySQLGlobalState.class, MySQLOptions.class);
    }

    @Override
    public void generateDatabase(MySQLGlobalState globalState) throws Exception {
        // Table creation (Creates Schema & Insert data into tables)
        MySQLTableCreator tableCreator = new MySQLTableCreator(globalState);
        // Generate random queries (Insert, Update, Delete, etc.)
        MySQLTableQueryGenerator tableQueryGenerator = new MySQLTableQueryGenerator(globalState);

        String staticTable = System.getProperty("staticTable");
        // For Future Custom Queries for Testing (Table Creation)
        if (staticTable != null && !staticTable.equals("true")) {
            tableCreator.create();
        } else {
            tableCreator.runQueryFromFile("staticTable.sql", globalState);
        }

        String staticQuery = System.getProperty("staticQuery");
        // For Future Custom Queries for Testing (Table Query Generation)
        if (staticTable != null && !staticQuery.equals("true")) {
            tableQueryGenerator.generateNExecute();
        } else {
            tableQueryGenerator.runQueryFromFile("staticQuery.sql", globalState);
        }

        if (globalState.getDbmsSpecificOptions().getTestOracleFactory().stream()
                .anyMatch((o) -> o == MySQLOracleFactory.CERT)) {
            // Enfore statistic collected for all tables
            ExpectedErrors errors = new ExpectedErrors();
            MySQLErrors.addExpressionErrors(errors);
            for (MySQLTable table : globalState.getSchema().getDatabaseTables()) {
                StringBuilder sb = new StringBuilder();
                sb.append("ANALYZE TABLE ");
                sb.append(table.getName());
                sb.append(" UPDATE HISTOGRAM ON ");
                String columns = table.getColumns().stream().map(MySQLColumn::getName)
                        .collect(Collectors.joining(", "));
                sb.append(columns + ";");
                globalState.executeStatement(new SQLQueryAdapter(sb.toString(), errors));
            }
        }
    }

    @Override
    public SQLConnection createDatabase(MySQLGlobalState globalState) throws SQLException {
        String username = globalState.getOptions().getUserName();
        String password = globalState.getOptions().getPassword();
        String host = globalState.getOptions().getHost();
        int port = globalState.getOptions().getPort();
        if (host == null) {
            host = MySQLOptions.DEFAULT_HOST;
        }
        if (port == MainOptions.NO_SET_PORT) {
            port = MySQLOptions.DEFAULT_PORT;
        }

        String url = String.format("jdbc:mysql://%s:%d?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true",
                host, port);
        return DatabaseUtils.setupDatabase(globalState, url, username, password);
    }

    @Override
    public String getDBMSName() {
        return "mysql";
    }

    @Override
    public boolean addRowsToAllTables(MySQLGlobalState globalState) throws Exception {
        List<MySQLTable> tablesNoRow = globalState.getSchema().getDatabaseTables().stream()
                .filter(t -> t.getNrRows(globalState) == 0).collect(Collectors.toList());
        for (MySQLTable table : tablesNoRow) {
            SQLQueryAdapter queryAddRows = MySQLInsertGenerator.insertRow(globalState, table);
            globalState.executeStatement(queryAddRows);
        }
        return true;
    }

}

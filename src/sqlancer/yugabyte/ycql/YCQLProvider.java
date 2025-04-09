package sqlancer.yugabyte.ycql;

import static sqlancer.yugabyte.ycql.YCQLSchema.getTableNames;
import static sqlancer.yugabyte.ysql.YSQLProvider.DDL_LOCK;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.google.auto.service.AutoService;

import sqlancer.DatabaseProvider;
import sqlancer.MainOptions;
import sqlancer.SQLConnection;
import sqlancer.SQLProviderAdapter;

@AutoService(DatabaseProvider.class)
public class YCQLProvider extends SQLProviderAdapter<YCQLGlobalState, YCQLOptions> {

    public YCQLProvider() {
        super(YCQLGlobalState.class, YCQLOptions.class);
    }

    @Override
    public void generateDatabase(YCQLGlobalState globalState) throws Exception {
        // Table creation (Creates Schema & Insert data into tables)
        YCQLTableCreator tableCreator = new YCQLTableCreator(globalState);
        // Generate random queries (Insert, Update, Delete, etc.)
        YCQLTableQueryGenerator tableQueryGenerator = new YCQLTableQueryGenerator(globalState);

        String staticTable = System.getProperty("staticTable");
        // For Future Custom Queries for Testing (Table Creation)
        if (staticTable == null) {
            tableCreator.create();
        } else {
            Path path = Paths.get(staticTable);
            if (Files.notExists(path)) {
                throw new IllegalArgumentException("File does not exist: " + staticTable);
            }
            tableCreator.runQueryFromFile(staticTable, globalState);
        }

        String staticQuery = System.getProperty("staticQuery");
        // For Future Custom Queries for Testing (Table Query Generation)

        if (staticQuery == null) {
            tableQueryGenerator.generateNExecute();
        } else {
            Path path = Paths.get(staticQuery);
            if (Files.notExists(path)) {
                throw new IllegalArgumentException("File does not exist: " + staticQuery);
            }
            tableQueryGenerator.runQueryFromFile(staticQuery, globalState);
        }
    }

    @Override
    public SQLConnection createDatabase(YCQLGlobalState globalState) throws SQLException {
        try {
            Class.forName("com.ing.data.cassandra.jdbc.CassandraDriver");
        } catch (ClassNotFoundException e) {
            throw new AssertionError();
        }

        String host = globalState.getOptions().getHost();
        int port = globalState.getOptions().getPort();

        if (host == null) {
            host = YCQLOptions.DEFAULT_HOST;
        }
        if (port == MainOptions.NO_SET_PORT) {
            port = YCQLOptions.DEFAULT_PORT;
        }

        final String url = "jdbc:cassandra://%s:%s/%s?localdatacenter=%s";
        final Connection connection = DriverManager.getConnection(
                String.format(url, host, port, "system_schema", globalState.getDbmsSpecificOptions().datacenter));

        synchronized (DDL_LOCK) {
            try (Statement stmt = connection.createStatement()) {
                try {
                    stmt.execute("DROP KEYSPACE IF EXISTS " + globalState.getDatabaseName());
                } catch (Exception se) {
                    // try again
                    List<String> tableNames = getTableNames(
                            new SQLConnection(DriverManager.getConnection(String.format(url, host, port,
                                    globalState.getDatabaseName(), globalState.getDbmsSpecificOptions().datacenter))),
                            globalState.getDatabaseName());
                    for (String tableName : tableNames) {
                        stmt.execute("DROP TABLE " + globalState.getDatabaseName() + "." + tableName);
                    }
                    stmt.execute("DROP KEYSPACE IF EXISTS " + globalState.getDatabaseName());
                }

                stmt.execute("CREATE KEYSPACE IF NOT EXISTS " + globalState.getDatabaseName());
            }
        }

        return new SQLConnection(DriverManager.getConnection(String.format(url, host, port,
                globalState.getDatabaseName(), globalState.getDbmsSpecificOptions().datacenter)));
    }

    @Override
    public String getDBMSName() {
        return "ycql";
    }

}

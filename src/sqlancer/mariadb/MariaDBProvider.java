package sqlancer.mariadb;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.auto.service.AutoService;

import sqlancer.DatabaseProvider;
import sqlancer.MainOptions;
import sqlancer.SQLConnection;
import sqlancer.SQLGlobalState;
import sqlancer.SQLProviderAdapter;
import sqlancer.mariadb.MariaDBProvider.MariaDBGlobalState;

@AutoService(DatabaseProvider.class)
public class MariaDBProvider extends SQLProviderAdapter<MariaDBGlobalState, MariaDBOptions> {

    public static final int MAX_EXPRESSION_DEPTH = 3;

    public MariaDBProvider() {
        super(MariaDBGlobalState.class, MariaDBOptions.class);
    }

    @Override
    public void generateDatabase(MariaDBGlobalState globalState) throws Exception {
        // Table creation (Creates Schema & Insert data into tables)
        MariaDBTableCreator tableCreator = new MariaDBTableCreator(globalState);
        // Generate random queries (Insert, Update, Delete, etc.)
        MariaDBTableQueryGenerator tableQueryGenerator = new MariaDBTableQueryGenerator(globalState);

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

    public static class MariaDBGlobalState extends SQLGlobalState<MariaDBOptions, MariaDBSchema> {

        @Override
        protected MariaDBSchema readSchema() throws SQLException {
            return MariaDBSchema.fromConnection(getConnection(), getDatabaseName());
        }

    }

    @Override
    public SQLConnection createDatabase(MariaDBGlobalState globalState) throws SQLException {
        globalState.getState().logStatement("DROP DATABASE IF EXISTS " + globalState.getDatabaseName());
        globalState.getState().logStatement("CREATE DATABASE " + globalState.getDatabaseName());
        globalState.getState().logStatement("USE " + globalState.getDatabaseName());
        String username = globalState.getOptions().getUserName();
        String password = globalState.getOptions().getPassword();
        String host = globalState.getOptions().getHost();
        int port = globalState.getOptions().getPort();
        if (host == null) {
            host = MariaDBOptions.DEFAULT_HOST;
        }
        if (port == MainOptions.NO_SET_PORT) {
            port = MariaDBOptions.DEFAULT_PORT;
        }
        String url = String.format("jdbc:mariadb://%s:%d", host, port);
        Connection con = DriverManager.getConnection(url, username, password);
        try (Statement s = con.createStatement()) {
            s.execute("DROP DATABASE IF EXISTS " + globalState.getDatabaseName());
        }
        try (Statement s = con.createStatement()) {
            s.execute("CREATE DATABASE " + globalState.getDatabaseName());
        }
        try (Statement s = con.createStatement()) {
            s.execute("USE " + globalState.getDatabaseName());
        }
        return new SQLConnection(con);
    }

    @Override
    public String getDBMSName() {
        return "mariadb";
    }

}

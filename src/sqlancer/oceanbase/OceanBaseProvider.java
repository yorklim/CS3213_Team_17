package sqlancer.oceanbase;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.google.auto.service.AutoService;

import sqlancer.DatabaseProvider;
import sqlancer.MainOptions;
import sqlancer.SQLConnection;
import sqlancer.SQLProviderAdapter;

@AutoService(DatabaseProvider.class)
public class OceanBaseProvider extends SQLProviderAdapter<OceanBaseGlobalState, OceanBaseOptions> {

    public OceanBaseProvider() {
        super(OceanBaseGlobalState.class, OceanBaseOptions.class);
    }

    @Override
    public void generateDatabase(OceanBaseGlobalState globalState) throws Exception {
        // Table creation (Creates Schema & Insert data into tables)
        OceanBaseTableCreator tableCreator = new OceanBaseTableCreator(globalState);
        // Generate random queries (Insert, Update, Delete, etc.)
        OceanBaseTableQueryGenerator tableQueryGenerator = new OceanBaseTableQueryGenerator(globalState);

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
    public SQLConnection createDatabase(OceanBaseGlobalState globalState) throws Exception {
        String username = globalState.getOptions().getUserName();
        String password = globalState.getOptions().getPassword();
        String host = globalState.getOptions().getHost();
        int port = globalState.getOptions().getPort();
        if (host == null) {
            host = OceanBaseOptions.DEFAULT_HOST;
        }
        if (port == MainOptions.NO_SET_PORT) {
            port = OceanBaseOptions.DEFAULT_PORT;
        }
        if (username.endsWith("sys") || username.equals("root")) {
            throw new OceanBaseUserCheckException(
                    "please don't use sys tenant to test! Firstly create tenant then test");
        }
        String databaseName = globalState.getDatabaseName();
        globalState.getState().logStatement("DROP DATABASE IF EXISTS " + databaseName);
        globalState.getState().logStatement("CREATE DATABASE " + databaseName);
        globalState.getState().logStatement("USE " + databaseName);
        String url = String.format("jdbc:mysql://%s:%d?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true",
                host, port);
        Connection con = DriverManager.getConnection(url, username, password);

        try (Statement s = con.createStatement()) {
            s.execute("set ob_query_timeout=" + globalState.getDbmsSpecificOptions().queryTimeout);
        }
        try (Statement s = con.createStatement()) {
            s.execute("set ob_trx_timeout=" + globalState.getDbmsSpecificOptions().trxTimeout);
        }
        try (Statement s = con.createStatement()) {
            s.execute("DROP DATABASE IF EXISTS " + databaseName);
        }
        try (Statement s = con.createStatement()) {
            s.execute("CREATE DATABASE " + databaseName);
        }
        try (Statement s = con.createStatement()) {
            s.execute("USE " + databaseName);
        }
        return new SQLConnection(con);
    }

    @Override
    public String getDBMSName() {
        return "oceanbase";
    }

}

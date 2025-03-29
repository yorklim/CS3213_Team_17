package sqlancer.databend;

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
import sqlancer.databend.DatabendProvider.DatabendGlobalState;

@AutoService(DatabaseProvider.class)
public class DatabendProvider extends SQLProviderAdapter<DatabendGlobalState, DatabendOptions> {

    public DatabendProvider() {
        super(DatabendGlobalState.class, DatabendOptions.class);
    }

    public static class DatabendGlobalState extends SQLGlobalState<DatabendOptions, DatabendSchema> {

        @Override
        protected DatabendSchema readSchema() throws SQLException {
            return DatabendSchema.fromConnection(getConnection(), getDatabaseName());
        }

    }

    @Override
    public void generateDatabase(DatabendGlobalState globalState) throws Exception {
        // Table creation (Creates Schema * Insert data into tables)
        DatabendTableCreator tableCreator = new DatabendTableCreator(globalState);
        // Generate random queries (Insert, Update, Delete, etc.)
        DatabendTableQueryGenerator tableQueryGenerator = new DatabendTableQueryGenerator(globalState);

        String staticTable = System.getProperty("staticTable");
        // For Future Custom Queries for Testing (Table Creation)
        if (!staticTable.equals("true")) {
            tableCreator.create();
        } else {
            tableCreator.runQueryFromFile("staticTable.sql", globalState);
        }

        String staticQuery = System.getProperty("staticQuery");
        // For Future Custom Queries for Testing (Table Query Generation)
        if (!staticQuery.equals("true")) {
            tableQueryGenerator.generateNExecute();
        } else {
            tableQueryGenerator.runQueryFromFile("staticQuery.sql", globalState);
        }
    }

    @Override
    public SQLConnection createDatabase(DatabendGlobalState globalState) throws SQLException {
        String username = globalState.getOptions().getUserName();
        String password = globalState.getOptions().getPassword();
        String host = globalState.getOptions().getHost();
        int port = globalState.getOptions().getPort();
        if (host == null) {
            host = DatabendOptions.DEFAULT_HOST;
        }
        if (port == MainOptions.NO_SET_PORT) {
            port = DatabendOptions.DEFAULT_PORT;
        }
        String databaseName = globalState.getDatabaseName();
        String url = String.format("jdbc:mysql://%s:%d?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true",
                host, port);
        Connection con = DriverManager.getConnection(url, username, password);
        try (Statement s = con.createStatement()) {
            s.execute("DROP DATABASE IF EXISTS " + databaseName);
            globalState.getState().logStatement("DROP DATABASE IF EXISTS " + databaseName);
            s.execute("CREATE DATABASE " + databaseName);
            globalState.getState().logStatement("CREATE DATABASE " + databaseName);
            s.execute("USE " + databaseName);
            globalState.getState().logStatement("USE " + databaseName);
        }
        if (DatabendBugs.bug15569) {
            con.close();
            String urlWithRetry = String.format(
                    "jdbc:mysql://%s:%d/%s?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true&autoReconnect=true",
                    host, port, databaseName);
            con = DriverManager.getConnection(urlWithRetry, username, password);
        }

        return new SQLConnection(con);
    }

    @Override
    public String getDBMSName() {
        return "databend"; // 用于DatabendOptions
    }

}

package sqlancer.hsqldb;

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

@AutoService(DatabaseProvider.class)
public class HSQLDBProvider extends SQLProviderAdapter<HSQLDBProvider.HSQLDBGlobalState, HSQLDBOptions> {

    private static final String HSQLDB = "hsqldb";

    public HSQLDBProvider() {
        super(HSQLDBGlobalState.class, HSQLDBOptions.class);
    }

    @Override
    public SQLConnection createDatabase(HSQLDBGlobalState globalState) throws Exception {
        String databaseName = globalState.getDatabaseName();
        String url = "jdbc:hsqldb:file:" + databaseName;
        MainOptions options = globalState.getOptions();
        Connection connection = DriverManager.getConnection(url, options.getUserName(), options.getPassword());
        // When a server instance is started, or when a connection is made to an in-process database,
        // a new, empty database is created if no database exists at the given path.
        try (Statement s = connection.createStatement()) {
            s.execute("DROP SCHEMA PUBLIC CASCADE");
            s.execute("SET DATABASE SQL DOUBLE NAN FALSE");
        }
        return new SQLConnection(connection);
    }

    @Override
    public String getDBMSName() {
        return HSQLDB;
    }

    @Override
    public void generateDatabase(HSQLDBGlobalState globalState) throws Exception {
        // Table creation (Creates Schema & Insert data into tables)
        HSQLDBTableCreator tableCreator = new HSQLDBTableCreator(globalState);
        // Generate random queries (Insert, Update, Delete, etc.)
        HSQLDBTableQueryGenerator tableQueryGenerator = new HSQLDBTableQueryGenerator(globalState);

        tableCreator.create();
        tableQueryGenerator.generateNExecute();

//        // For Future Custom Queries for Testing (Table Creation)
//        if (true) {
//            tableCreator.create();
//        } else {
//            tableCreator.runQueryFromFile("placeholder", globalState);
//        }
//
//        // For Future Custom Queries for Testing (Table Query Generation)
//        if (true) {
//            tableQueryGenerator.generateNExecute();
//        } else {
//            tableQueryGenerator.runQueryFromFile("placeholder", globalState);
//        }
    }

    public static class HSQLDBGlobalState extends SQLGlobalState<HSQLDBOptions, HSQLDBSchema> {

        @Override
        protected HSQLDBSchema readSchema() throws SQLException {
            return HSQLDBSchema.fromConnection(getConnection(), getDatabaseName());
        }

    }
}

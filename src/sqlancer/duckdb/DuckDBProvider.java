package sqlancer.duckdb;

import java.io.File;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.google.auto.service.AutoService;

import sqlancer.DatabaseProvider;
import sqlancer.MainOptions;
import sqlancer.SQLConnection;
import sqlancer.SQLGlobalState;
import sqlancer.SQLProviderAdapter;
import sqlancer.common.DriverLoader;
import sqlancer.duckdb.DuckDBProvider.DuckDBGlobalState;

@AutoService(DatabaseProvider.class)
public class DuckDBProvider extends SQLProviderAdapter<DuckDBGlobalState, DuckDBOptions> {

    private static Class<?> driverClass;
    private static URLClassLoader driverLoader; // Store the class loader

    private static synchronized void initializeDriver() throws SQLException {
        if (driverClass == null) {
            try {
                DriverLoader.DriverLoadResult result = DriverLoader.loadDriver("org.duckdb.DuckDBDriver", "duckdb");
                driverClass = result.driverClass;
                driverLoader = result.classLoader;
                Runtime.getRuntime().addShutdownHook(new Thread(() -> closeResources()));
            } catch (Exception e) {
                throw new SQLException("Failed to initialize DuckDB driver", e);
            }
        }
    }

    public DuckDBProvider() {
        super(DuckDBGlobalState.class, DuckDBOptions.class);
    }

    public static void closeResources() {
        if (driverLoader != null) {
            try {
                driverLoader.close();
            } catch (Exception e) {
                // Ignore
            }
        }
    }

    public static class DuckDBGlobalState extends SQLGlobalState<DuckDBOptions, DuckDBSchema> {

        @Override
        protected DuckDBSchema readSchema() throws SQLException {
            return DuckDBSchema.fromConnection(getConnection());
        }
    }

    @Override
    public void generateDatabase(DuckDBGlobalState globalState) throws Exception {
        // Table creation (Creates Schema & Insert data into tables)
        DuckDBTableCreator tableCreator = new DuckDBTableCreator(globalState);
        // Generate random queries (Insert, Update, Delete, etc.)
        DuckDBTableQueryGenerator tableQueryGenerator = new DuckDBTableQueryGenerator(globalState);

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
    }

    public void tryDeleteFile(String fname) {
        try {
            File f = new File(fname);
            f.delete();
        } catch (Exception e) {
        }
    }

    public void tryDeleteDatabase(String dbpath) {
        if (dbpath.equals("") || dbpath.equals(":memory:")) {
            return;
        }
        tryDeleteFile(dbpath);
        tryDeleteFile(dbpath + ".wal");
    }

    @Override
    public SQLConnection createDatabase(DuckDBGlobalState globalState) throws SQLException {
        // Initialize the dynamic driver
        DuckDBProvider.initializeDriver();

        String databaseFile = System.getProperty("duckdb.database.file", "");
        String url = "jdbc:duckdb:" + databaseFile;
        tryDeleteDatabase(databaseFile);

        MainOptions options = globalState.getOptions();
        if (!(options.isDefaultUsername() && options.isDefaultPassword())) {
            throw new AssertionError("DuckDB doesn't support credentials (username/password)");
        }

        // Manually instantiate and use the driver
        try {
            Driver driver = (Driver) driverClass.getDeclaredConstructor().newInstance();
            Properties props = new Properties();
            Connection conn = driver.connect(url, props);

            Statement stmt = conn.createStatement();
            stmt.execute("PRAGMA checkpoint_threshold='1 byte';");
            stmt.close();

            return new SQLConnection(conn);
        } catch (Exception e) {
            throw new SQLException("Failed to create connection", e);
        }
    }

    @Override
    public String getDBMSName() {
        return "duckdb";
    }

}

package sqlancer.duckdb;

import java.io.File;
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
import sqlancer.duckdb.DuckDBProvider.DuckDBGlobalState;

@AutoService(DatabaseProvider.class)
public class DuckDBProvider extends SQLProviderAdapter<DuckDBGlobalState, DuckDBOptions> {

    public DuckDBProvider() {
        super(DuckDBGlobalState.class, DuckDBOptions.class);
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
        if (staticTable == null || !staticTable.equals("true")) {
            tableCreator.create();
        } else {
            tableCreator.runQueryFromFile("staticTable.sql", globalState);
        }

        String staticQuery = System.getProperty("staticQuery");
        // For Future Custom Queries for Testing (Table Query Generation)
        if (staticQuery == null || !staticQuery.equals("true")) {
            tableQueryGenerator.generateNExecute();
        } else {
            tableQueryGenerator.runQueryFromFile("staticQuery.sql", globalState);
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
        String databaseFile = System.getProperty("duckdb.database.file", "");
        String url = "jdbc:duckdb:" + databaseFile;
        tryDeleteDatabase(databaseFile);

        MainOptions options = globalState.getOptions();
        if (!(options.isDefaultUsername() && options.isDefaultPassword())) {
            throw new AssertionError("DuckDB doesn't support credentials (username/password)");
        }

        Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement();
        stmt.execute("PRAGMA checkpoint_threshold='1 byte';");
        stmt.close();
        return new SQLConnection(conn);
    }

    @Override
    public String getDBMSName() {
        return "duckdb";
    }

}

package sqlancer.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import sqlancer.SQLConnection;
import sqlancer.SQLGlobalState;

public class DatabaseUtils {
    public static SQLConnection setupDatabase(SQLGlobalState<?, ?> globalState, String jdbcUrl, String username, String password) throws SQLException {
        String databaseName = globalState.getDatabaseName();
        globalState.getState().logStatement("DROP DATABASE IF EXISTS " + databaseName);
        globalState.getState().logStatement("CREATE DATABASE " + databaseName);
        globalState.getState().logStatement("USE " + databaseName);

        Connection con = DriverManager.getConnection(jdbcUrl, username, password);
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
}

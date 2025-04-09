package sqlancer.questdb;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.google.auto.service.AutoService;

import sqlancer.DatabaseProvider;
import sqlancer.SQLConnection;
import sqlancer.SQLGlobalState;
import sqlancer.SQLProviderAdapter;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.questdb.QuestDBProvider.QuestDBGlobalState;
import sqlancer.questdb.gen.QuestDBTableGenerator;

@AutoService(DatabaseProvider.class)
public class QuestDBProvider extends SQLProviderAdapter<QuestDBGlobalState, QuestDBOptions> {
    public QuestDBProvider() {
        super(QuestDBGlobalState.class, QuestDBOptions.class);
    }

    public static class QuestDBGlobalState extends SQLGlobalState<QuestDBOptions, QuestDBSchema> {

        @Override
        protected QuestDBSchema readSchema() throws SQLException {
            return QuestDBSchema.fromConnection(getConnection());
        }

    }

    @Override
    public void generateDatabase(QuestDBGlobalState globalState) throws Exception {
        // Table creation (Creates Schema * Insert data into tables)
        QuestDBTableCreator tableCreator = new QuestDBTableCreator(globalState);
        // Generate random queries (Insert, Update, Delete, etc.)
        QuestDBTableQueryGenerator tableQueryGenerator = new QuestDBTableQueryGenerator(globalState);

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
    public SQLConnection createDatabase(QuestDBGlobalState globalState) throws Exception {
        String host = globalState.getOptions().getHost();
        int port = globalState.getOptions().getPort();
        if (host == null) {
            host = QuestDBOptions.DEFAULT_HOST;
        }
        if (port == sqlancer.MainOptions.NO_SET_PORT) {
            port = QuestDBOptions.DEFAULT_PORT;
        }
        // TODO(anxing): maybe not hardcode here...
        String databaseName = "qdb";
        String tableName = "sqlancer_test";
        String url = String.format("jdbc:postgresql://%s:%d/%s", host, port, databaseName);
        // use QuestDB default username & password for Postgres JDBC
        Properties properties = new Properties();
        properties.setProperty("user", globalState.getDbmsSpecificOptions().getUserName());
        properties.setProperty("password", globalState.getDbmsSpecificOptions().getPassword());
        properties.setProperty("sslmode", "disable");

        Connection con = DriverManager.getConnection(url, properties);
        // QuestDB cannot create or drop `DATABASE`, can only create or drop `TABLE`
        globalState.getState().logStatement("DROP TABLE IF EXISTS " + tableName + " CASCADE");
        SQLQueryAdapter createTableCommand = new QuestDBTableGenerator().getQuery(globalState, tableName);
        globalState.getState().logStatement(createTableCommand);
        globalState.getState().logStatement("DROP TABLE IF EXISTS " + tableName);

        try (Statement s = con.createStatement()) {
            s.execute("DROP TABLE IF EXISTS " + tableName);
        }
        try (Statement s = con.createStatement()) {
            s.execute(createTableCommand.getQueryString());
        }
        // drop test table
        try (Statement s = con.createStatement()) {
            s.execute("DROP TABLE IF EXISTS " + tableName);
        }
        con.close();
        con = DriverManager.getConnection(url, properties);
        return new SQLConnection(con);
    }

    @Override
    public String getDBMSName() {
        return "questdb";
    }

}

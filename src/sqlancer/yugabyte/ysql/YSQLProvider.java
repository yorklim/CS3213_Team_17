package sqlancer.yugabyte.ysql;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import com.google.auto.service.AutoService;

import sqlancer.DatabaseProvider;
import sqlancer.MainOptions;
import sqlancer.Randomly;
import sqlancer.SQLConnection;
import sqlancer.SQLProviderAdapter;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLancerResultSet;

@AutoService(DatabaseProvider.class)
public class YSQLProvider extends SQLProviderAdapter<YSQLGlobalState, YSQLOptions> {

    // TODO Due to yugabyte problems with parallel DDL we need this lock object
    public static final Object DDL_LOCK = new Object();
    /**
     * Generate only data types and expressions that are understood by PQS.
     */
    public static boolean generateOnlyKnown;
    protected String entryURL;
    protected String username;
    protected String password;
    protected String entryPath;
    protected String host;
    protected int port;
    protected String testURL;
    protected String databaseName;
    protected String createDatabaseCommand;

    public YSQLProvider() {
        super(YSQLGlobalState.class, YSQLOptions.class);
    }

    protected YSQLProvider(Class<YSQLGlobalState> globalClass, Class<YSQLOptions> optionClass) {
        super(globalClass, optionClass);
    }

    @Override
    public void generateDatabase(YSQLGlobalState globalState) throws Exception {
        readFunctions(globalState);

        // Table creation (Creates Schema & Insert data into tables)
        YSQLTableCreator tableCreator = new YSQLTableCreator(globalState);
        // Generate random queries (Insert, Update, Delete, etc.)
        YSQLTableQueryGenerator tableQueryGenerator = new YSQLTableQueryGenerator(globalState);

        String staticTable = System.getProperty("staticTable");
        // For Future Custom Queries for Testing (Table Creation)
        if (staticTable == null || !staticTable.equals("true")) {
            tableCreator.create();
        } else {
            tableCreator.runQueryFromFile("staticTable.sql", globalState);
        }

        String staticQuery = System.getProperty("staticQuery");
        // For Future Custom Queries for Testing (Table Query Generation)
        if (staticTable == null || !staticQuery.equals("true")) {
            tableQueryGenerator.generateNExecute();
        } else {
            tableQueryGenerator.runQueryFromFile("staticQuery.sql", globalState);
        }
    }

    @Override
    public SQLConnection createDatabase(YSQLGlobalState globalState) throws SQLException {
        username = globalState.getOptions().getUserName();
        password = globalState.getOptions().getPassword();
        host = globalState.getOptions().getHost();
        port = globalState.getOptions().getPort();
        entryPath = "/yugabyte";
        entryURL = globalState.getDbmsSpecificOptions().connectionURL;
        String entryDatabaseName = entryPath.substring(1);
        databaseName = globalState.getDatabaseName();

        if (host == null) {
            host = YSQLOptions.DEFAULT_HOST;
        }
        if (port == MainOptions.NO_SET_PORT) {
            port = YSQLOptions.DEFAULT_PORT;
        }

        try {
            URI uri = new URI(entryURL);
            String userInfoURI = uri.getUserInfo();
            String pathURI = uri.getPath();
            if (userInfoURI != null) {
                // username and password specified in URL take precedence
                if (userInfoURI.contains(":")) {
                    String[] userInfo = userInfoURI.split(":", 2);
                    username = userInfo[0];
                    password = userInfo[1];
                } else {
                    username = userInfoURI;
                    password = null;
                }
                int userInfoIndex = entryURL.indexOf(userInfoURI);
                String preUserInfo = entryURL.substring(0, userInfoIndex);
                String postUserInfo = entryURL.substring(userInfoIndex + userInfoURI.length() + 1);
                entryURL = preUserInfo + postUserInfo;
            }
            if (pathURI != null) {
                entryPath = pathURI;
            }
            if (host == null) {
                host = uri.getHost();
            }
            if (port == MainOptions.NO_SET_PORT) {
                port = uri.getPort();
            }
            entryURL = String.format("jdbc:yugabytedb://%s:%d/%s", host, port, entryDatabaseName);
        } catch (URISyntaxException e) {
            throw new AssertionError(e);
        }

        createDatabaseSync(globalState, entryDatabaseName);

        int databaseIndex = entryURL.indexOf("/" + entryDatabaseName) + 1;
        String preDatabaseName = entryURL.substring(0, databaseIndex);
        String postDatabaseName = entryURL.substring(databaseIndex + entryDatabaseName.length());
        testURL = preDatabaseName + databaseName + postDatabaseName;
        globalState.getState().logStatement(String.format("\\c %s;", databaseName));

        return new SQLConnection(createConnectionSafely(testURL, username, password));
    }

    @Override
    public String getDBMSName() {
        return "ysql";
    }

    // for some reason yugabyte unable to create few databases simultaneously
    private void createDatabaseSync(YSQLGlobalState globalState, String entryDatabaseName) throws SQLException {
        synchronized (DDL_LOCK) {
            exceptionLessSleep(5000);

            Connection con = createConnectionSafely(entryURL, username, password);
            globalState.getState().logStatement(String.format("\\c %s;", entryDatabaseName));
            globalState.getState().logStatement("DROP DATABASE IF EXISTS " + databaseName);
            createDatabaseCommand = getCreateDatabaseCommand(globalState);
            globalState.getState().logStatement(createDatabaseCommand);
            try (Statement s = con.createStatement()) {
                s.execute("DROP DATABASE IF EXISTS " + databaseName);
            }
            try (Statement s = con.createStatement()) {
                s.execute(createDatabaseCommand);
            }
            con.close();
        }
    }

    private Connection createConnectionSafely(String entryURL, String user, String password) {
        Connection con = null;
        IllegalStateException lastException = new IllegalStateException("Empty exception");
        long endTime = System.currentTimeMillis() + 30000;
        while (System.currentTimeMillis() < endTime) {
            try {
                con = DriverManager.getConnection(entryURL, user, password);
                break;
            } catch (SQLException throwables) {
                lastException = new IllegalStateException(throwables);
            }
        }

        if (con == null) {
            throw lastException;
        }

        return con;
    }

    protected void readFunctions(YSQLGlobalState globalState) throws SQLException {
        SQLQueryAdapter query = new SQLQueryAdapter("SELECT proname, provolatile FROM pg_proc;");
        SQLancerResultSet rs = query.executeAndGet(globalState);
        while (rs.next()) {
            String functionName = rs.getString(1);
            Character functionType = rs.getString(2).charAt(0);
            globalState.addFunctionAndType(functionName, functionType);
        }
    }

    protected static void exceptionLessSleep(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            throw new AssertionError();
        }
    }

    private String getCreateDatabaseCommand(YSQLGlobalState state) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE DATABASE ").append(databaseName).append(" ");
        if (Randomly.getBoolean() && state.getDbmsSpecificOptions().testCollations) {
            sb.append("WITH ");
            if (Randomly.getBoolean()) {
                sb.append("ENCODING '");
                sb.append(Randomly.fromOptions("utf8"));
                sb.append("' ");
            }

            if (Randomly.getBoolean()) {
                sb.append("COLOCATED = true ");
            }

            for (String lc : Arrays.asList("LC_COLLATE", "LC_CTYPE")) {
                if (!state.getCollates().isEmpty() && Randomly.getBoolean()) {
                    sb.append(String.format(" %s = '%s'", lc, Randomly.fromList(state.getCollates())));
                }
            }
            sb.append(" TEMPLATE template0");

        }
        return sb.toString();
    }

}

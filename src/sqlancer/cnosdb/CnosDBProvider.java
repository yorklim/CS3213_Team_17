package sqlancer.cnosdb;

import com.google.auto.service.AutoService;

import sqlancer.DatabaseProvider;
import sqlancer.ProviderAdapter;
import sqlancer.cnosdb.client.CnosDBClient;
import sqlancer.cnosdb.client.CnosDBConnection;
import sqlancer.common.log.LoggableFactory;

@AutoService(DatabaseProvider.class)
public class CnosDBProvider extends ProviderAdapter<CnosDBGlobalState, CnosDBOptions, CnosDBConnection> {

    protected String username;
    protected String password;
    protected String host;
    protected int port;
    protected String databaseName;

    public CnosDBProvider() {
        super(CnosDBGlobalState.class, CnosDBOptions.class);
    }

    protected CnosDBProvider(Class<CnosDBGlobalState> globalClass, Class<CnosDBOptions> optionClass) {
        super(globalClass, optionClass);
    }

    @Override
    protected void checkViewsAreValid(CnosDBGlobalState globalState) {
    }

    @Override
    public void generateDatabase(CnosDBGlobalState globalState) throws Exception {
        // Table Creation (Creates Schema & Insert data into tables)
        CnosDBTableCreator tableCreator = new CnosDBTableCreator(globalState);
        tableCreator.create();
    }

    @Override
    public CnosDBConnection createDatabase(CnosDBGlobalState globalState) throws Exception {

        username = globalState.getOptions().getUserName();
        password = globalState.getOptions().getPassword();
        host = globalState.getOptions().getHost();
        port = globalState.getOptions().getPort();
        databaseName = globalState.getDatabaseName();
        CnosDBClient client = new CnosDBClient(host, port, username, password, databaseName);
        CnosDBConnection connection = new CnosDBConnection(client);
        client.execute("DROP DATABASE IF EXISTS " + databaseName);
        globalState.getState().logStatement("DROP DATABASE IF EXISTS " + databaseName);
        client.execute("CREATE DATABASE " + databaseName);
        globalState.getState().logStatement("CREATE DATABASE " + databaseName);

        return connection;
    }

    @Override
    public String getDBMSName() {
        return "CnosDB".toLowerCase();
    }

    @Override
    public LoggableFactory getLoggableFactory() {
        return new CnosDBLoggableFactory();
    }

}

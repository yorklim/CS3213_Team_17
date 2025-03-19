package sqlancer.doris;

import java.sql.SQLException;

import com.google.auto.service.AutoService;

import sqlancer.DatabaseProvider;
import sqlancer.MainOptions;
import sqlancer.SQLConnection;
import sqlancer.SQLGlobalState;
import sqlancer.SQLProviderAdapter;
import sqlancer.common.DatabaseUtils;
import sqlancer.doris.DorisProvider.DorisGlobalState;

@AutoService(DatabaseProvider.class)
public class DorisProvider extends SQLProviderAdapter<DorisGlobalState, DorisOptions> {

    public DorisProvider() {
        super(DorisGlobalState.class, DorisOptions.class);
    }

    public static class DorisGlobalState extends SQLGlobalState<DorisOptions, DorisSchema> {

        @Override
        protected DorisSchema readSchema() throws SQLException {
            return DorisSchema.fromConnection(getConnection(), getDatabaseName());
        }

    }

    @Override
    public void generateDatabase(DorisGlobalState globalState) throws Exception {
        // Table creation (Creates Schema & Insert data into tables)
        DorisTableCreator tableCreator = new DorisTableCreator(globalState);
        // Generate random queries (Insert, Update, Delete, etc.)
        DorisTableQueryGenerator tableQueryGenerator = new DorisTableQueryGenerator(globalState);

        tableCreator.create();
        tableQueryGenerator.generateNExecute();
        // // For Future Custom Queries for Testing (Table Creation)
        // if (true) {
        // tableCreator.create();
        // } else {
        // tableCreator.runQueryFromFile("placeholder", globalState);
        // }
        //
        // // For Future Custom Queries for Testing (Table Query Generation)
        // if (true) {
        // tableQueryGenerator.generateNExecute();
        // } else {
        // tableQueryGenerator.runQueryFromFile("placeholder", globalState);
        // }
    }

    @Override
    public SQLConnection createDatabase(DorisGlobalState globalState) throws SQLException {
        String username = globalState.getOptions().getUserName();
        String password = globalState.getOptions().getPassword();
        if (password.equals("\"\"")) {
            password = "";
        }
        String host = globalState.getOptions().getHost();
        int port = globalState.getOptions().getPort();
        if (host == null) {
            host = DorisOptions.DEFAULT_HOST;
        }
        if (port == MainOptions.NO_SET_PORT) {
            port = DorisOptions.DEFAULT_PORT;
        }

        String url = String.format("jdbc:mysql://%s:%d?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true",
                host, port);
        return DatabaseUtils.setupDatabase(globalState, url, username, password);
    }

    @Override
    public String getDBMSName() {
        return "doris";
    }

}

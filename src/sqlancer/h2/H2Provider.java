package sqlancer.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.auto.service.AutoService;

import sqlancer.DatabaseProvider;
import sqlancer.SQLConnection;
import sqlancer.SQLGlobalState;
import sqlancer.SQLProviderAdapter;
import sqlancer.h2.H2Provider.H2GlobalState;

@AutoService(DatabaseProvider.class)
public class H2Provider extends SQLProviderAdapter<H2GlobalState, H2Options> {

    public H2Provider() {
        super(H2GlobalState.class, H2Options.class);
    }

    public static class H2GlobalState extends SQLGlobalState<H2Options, H2Schema> {

        @Override
        protected H2Schema readSchema() throws SQLException {
            return H2Schema.fromConnection(getConnection(), getDatabaseName());
        }

    }

    @Override
    public void generateDatabase(H2GlobalState globalState) throws Exception {
        H2TableCreator tableCreator = new H2TableCreator(globalState);
        tableCreator.create();
    }

    @Override
    public SQLConnection createDatabase(H2GlobalState globalState) throws SQLException {
        String connectionString = "jdbc:h2:~/" + globalState.getDatabaseName() + ";DB_CLOSE_ON_EXIT=FALSE";
        Connection connection = DriverManager.getConnection(connectionString, "sa", "");
        connection.createStatement().execute("DROP ALL OBJECTS DELETE FILES");
        connection.close();
        connection = DriverManager.getConnection(connectionString, "sa", "");
        return new SQLConnection(connection);
    }

    @Override
    public String getDBMSName() {
        return "h2";
    }

}

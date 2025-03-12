package sqlancer.yugabyte.ycql;

import java.sql.SQLException;

import sqlancer.SQLGlobalState;

public class YCQLGlobalState extends SQLGlobalState<YCQLOptions, YCQLSchema> {
    @Override
    protected YCQLSchema readSchema() throws SQLException {
        return YCQLSchema.fromConnection(getConnection(), getDatabaseName());
    }
}

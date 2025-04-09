package sqlancer.yugabyte.ycql;

import java.sql.SQLException;

import sqlancer.OracleFactory;
import sqlancer.common.oracle.TestOracle;
import sqlancer.yugabyte.ycql.test.YCQLFuzzer;

public enum YCQLOracleFactory implements OracleFactory<YCQLGlobalState> {
    FUZZER {
        @Override
        public TestOracle<YCQLGlobalState> create(YCQLGlobalState globalState) throws SQLException {
            return new YCQLFuzzer(globalState);
        }

    }
}

package sqlancer.dbms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Test;

import sqlancer.Main;

public class TestCockroachDBTLP {

    @Test
    public void testCockroachDBTLP() {
        assumeTrue(TestConfig.isEnvironmentTrue(TestConfig.COCKROACHDB_ENV));
        assertEquals(0, Main.executeMain(new String[] { "--random-seed", "0", "--timeout-seconds", TestConfig.SECONDS,
                "--num-queries", TestConfig.NUM_QUERIES, "cockroachdb", "--oracle", "QUERY_PARTITIONING" }));
    }

}

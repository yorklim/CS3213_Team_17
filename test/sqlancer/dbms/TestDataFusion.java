package sqlancer.dbms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Test;

import sqlancer.Main;

public class TestDataFusion {
    @Test
    public void testDataFusion() {
        assumeTrue(TestConfig.isEnvironmentTrue(TestConfig.DATAFUSION_ENV));

        assertEquals(0, Main.executeMain("--random-seed", "0", "--num-threads", "1", // TODO(datafusion) update when
                                                                                     // multithread is supported
                "--timeout-seconds", TestConfig.SECONDS, "--num-queries", TestConfig.NUM_QUERIES, "datafusion"));
    }
}

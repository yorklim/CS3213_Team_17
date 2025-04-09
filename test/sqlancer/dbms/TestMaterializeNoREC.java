package sqlancer.dbms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import sqlancer.Main;

public class TestMaterializeNoREC {

    @Test
    @Disabled("This test is failing on main repo.")
    public void test() {
        assumeTrue(TestConfig.isEnvironmentTrue(TestConfig.MATERIALIZE_ENV));
        assertEquals(0,
                Main.executeMain(new String[] { "--random-seed", "0", "--timeout-seconds", TestConfig.SECONDS,
                        "--num-threads", "4", "--num-queries", TestConfig.NUM_QUERIES, "--username", "materialize",
                        "materialize", "--oracle", "NOREC", "--set-max-tables-mvs", "true" }));
    }

}

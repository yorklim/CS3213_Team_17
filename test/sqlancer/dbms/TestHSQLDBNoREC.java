package sqlancer.dbms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import sqlancer.Main;

public class TestHSQLDBNoREC {
    @Test
    public void testHSQLDBNoREC() {
        assertEquals(0, Main.executeMain(new String[] { "--random-seed", "0", "--timeout-seconds", TestConfig.SECONDS,
                "--num-threads", "1", "--num-queries", TestConfig.NUM_QUERIES, "hsqldb", "--oracle", "NOREC" }));
    }
}

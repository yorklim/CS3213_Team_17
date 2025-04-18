/*
 * This file was automatically generated by EvoSuite
 * Mon Apr 14 05:08:06 GMT 2025
 */

package sqlancer.cockroachdb.gen;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.Random;
import org.junit.runner.RunWith;
import sqlancer.cockroachdb.CockroachDBProvider;
import sqlancer.cockroachdb.gen.CockroachDBShowGenerator;
import sqlancer.common.query.SQLQueryAdapter;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class CockroachDBShowGenerator_ESTest extends CockroachDBShowGenerator_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      Random.setNextRandom((-1437));
      CockroachDBProvider.CockroachDBGlobalState cockroachDBProvider_CockroachDBGlobalState0 = new CockroachDBProvider.CockroachDBGlobalState();
      SQLQueryAdapter sQLQueryAdapter0 = CockroachDBShowGenerator.show(cockroachDBProvider_CockroachDBGlobalState0);
      assertEquals("SHOW LOCALITY;", sQLQueryAdapter0.getLogString());
      
      SQLQueryAdapter sQLQueryAdapter1 = CockroachDBShowGenerator.show(cockroachDBProvider_CockroachDBGlobalState0);
      assertEquals("SHOW SEQUENCES;", sQLQueryAdapter1.getQueryString());
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      Random.setNextRandom((-2848));
      CockroachDBProvider.CockroachDBGlobalState cockroachDBProvider_CockroachDBGlobalState0 = new CockroachDBProvider.CockroachDBGlobalState();
      // Undeclared exception!
      try { 
        CockroachDBShowGenerator.show(cockroachDBProvider_CockroachDBGlobalState0);
        fail("Expecting exception: AssertionError");
      
      } catch(AssertionError e) {
         //
         // null
         //
      }
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      CockroachDBProvider.CockroachDBGlobalState cockroachDBProvider_CockroachDBGlobalState0 = new CockroachDBProvider.CockroachDBGlobalState();
      Random.setNextRandom(2931);
      SQLQueryAdapter sQLQueryAdapter0 = CockroachDBShowGenerator.show(cockroachDBProvider_CockroachDBGlobalState0);
      assertEquals("SHOW JOBS;", sQLQueryAdapter0.getQueryString());
  }

  @Test(timeout = 4000)
  public void test3()  throws Throwable  {
      CockroachDBProvider.CockroachDBGlobalState cockroachDBProvider_CockroachDBGlobalState0 = new CockroachDBProvider.CockroachDBGlobalState();
      Random.setNextRandom((-534));
      SQLQueryAdapter sQLQueryAdapter0 = CockroachDBShowGenerator.show(cockroachDBProvider_CockroachDBGlobalState0);
      assertEquals("SHOW DATABASES;", sQLQueryAdapter0.getLogString());
  }

  @Test(timeout = 4000)
  public void test4()  throws Throwable  {
      CockroachDBProvider.CockroachDBGlobalState cockroachDBProvider_CockroachDBGlobalState0 = new CockroachDBProvider.CockroachDBGlobalState();
      Random.setNextRandom(68);
      SQLQueryAdapter sQLQueryAdapter0 = CockroachDBShowGenerator.show(cockroachDBProvider_CockroachDBGlobalState0);
      assertEquals("SHOW TRACE FOR SESSION;", sQLQueryAdapter0.getLogString());
  }

  @Test(timeout = 4000)
  public void test5()  throws Throwable  {
      // Undeclared exception!
      try { 
        CockroachDBShowGenerator.show((CockroachDBProvider.CockroachDBGlobalState) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.cockroachdb.gen.CockroachDBShowGenerator", e);
      }
  }
}

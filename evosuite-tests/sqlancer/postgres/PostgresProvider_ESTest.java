/*
 * This file was automatically generated by EvoSuite
 * Mon Apr 14 11:59:08 GMT 2025
 */

package sqlancer.postgres;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import sqlancer.citus.CitusGlobalState;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.postgres.PostgresGlobalState;
import sqlancer.postgres.PostgresOptions;
import sqlancer.postgres.PostgresProvider;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class PostgresProvider_ESTest extends PostgresProvider_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.RESET;
      SQLQueryAdapter sQLQueryAdapter0 = postgresProvider_Action0.getQuery((PostgresGlobalState) citusGlobalState0);
      assertEquals("RESET ALL;", sQLQueryAdapter0.getLogString());
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.RESET_ROLE;
      SQLQueryAdapter sQLQueryAdapter0 = postgresProvider_Action0.getQuery((PostgresGlobalState) citusGlobalState0);
      assertEquals("RESET ROLE;", sQLQueryAdapter0.getQueryString());
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.SET_CONSTRAINTS;
      SQLQueryAdapter sQLQueryAdapter0 = postgresProvider_Action0.getQuery((PostgresGlobalState) null);
      assertEquals("SET CONSTRAINTS ALL DEFERRED;", sQLQueryAdapter0.getQueryString());
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      PostgresProvider postgresProvider0 = new PostgresProvider();
      PostgresGlobalState postgresGlobalState0 = new PostgresGlobalState();
      // Undeclared exception!
      try { 
        postgresProvider0.readFunctions(postgresGlobalState0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.common.query.SQLQueryAdapter", e);
      }
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider postgresProvider0 = new PostgresProvider();
      try { 
        postgresProvider0.generateDatabase((PostgresGlobalState) citusGlobalState0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.common.query.SQLQueryAdapter", e);
      }
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      PostgresProvider postgresProvider0 = new PostgresProvider();
      try { 
        postgresProvider0.createTables((PostgresGlobalState) null, 2022338513);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      PostgresProvider postgresProvider0 = new PostgresProvider();
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      // Undeclared exception!
      try { 
        postgresProvider0.createTables(citusGlobalState0, 's');
        fail("Expecting exception: AssertionError");
      
      } catch(AssertionError e) {
         //
         // null
         //
      }
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider postgresProvider0 = new PostgresProvider();
      // Undeclared exception!
      try { 
        postgresProvider0.createDatabase((PostgresGlobalState) citusGlobalState0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.CREATE_VIEW;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(citusGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.CREATE_SEQUENCE;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(citusGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.UNLISTEN;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(citusGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.LISTEN;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(citusGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.NOTIFY;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(citusGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test13()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.RESET;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(citusGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test14()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.COMMENT_ON;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(citusGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test15()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.RESET_ROLE;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(citusGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test16()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.SET_CONSTRAINTS;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(citusGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test17()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.CREATE_INDEX;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(citusGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test18()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.SET;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(citusGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test19()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.REINDEX;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(citusGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test20()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.VACUUM;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(citusGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test21()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.TRUNCATE;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(citusGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test22()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.UPDATE;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(citusGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test23()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.INSERT;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(citusGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test24()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.DROP_INDEX;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(citusGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test25()  throws Throwable  {
      PostgresGlobalState postgresGlobalState0 = new PostgresGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.DISCARD;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(postgresGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test26()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.DELETE;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(citusGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test27()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.DROP_STATISTICS;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(citusGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test28()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.CREATE_STATISTICS;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(citusGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test29()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.CLUSTER;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(citusGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test30()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.ALTER_TABLE;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(citusGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test31()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.COMMIT;
      // Undeclared exception!
      try { 
        PostgresProvider.mapActions(citusGlobalState0, postgresProvider_Action0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }

  @Test(timeout = 4000)
  public void test32()  throws Throwable  {
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      PostgresProvider.Action postgresProvider_Action0 = PostgresProvider.Action.COMMIT;
      SQLQueryAdapter sQLQueryAdapter0 = postgresProvider_Action0.getQuery((PostgresGlobalState) citusGlobalState0);
      assertEquals("ROLLBACK;", sQLQueryAdapter0.getLogString());
      assertTrue(sQLQueryAdapter0.couldAffectSchema());
  }

  @Test(timeout = 4000)
  public void test33()  throws Throwable  {
      Class<PostgresGlobalState> class0 = PostgresGlobalState.class;
      Class<PostgresOptions> class1 = PostgresOptions.class;
      PostgresProvider postgresProvider0 = new PostgresProvider(class0, class1);
      String string0 = postgresProvider0.getDBMSName();
      assertEquals("postgres", string0);
  }

  @Test(timeout = 4000)
  public void test34()  throws Throwable  {
      PostgresProvider postgresProvider0 = new PostgresProvider();
      CitusGlobalState citusGlobalState0 = new CitusGlobalState();
      try { 
        postgresProvider0.prepareTables(citusGlobalState0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.postgres.PostgresProvider", e);
      }
  }
}

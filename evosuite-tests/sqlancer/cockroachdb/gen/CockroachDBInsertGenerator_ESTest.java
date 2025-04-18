/*
 * This file was automatically generated by EvoSuite
 * Mon Apr 14 04:55:15 GMT 2025
 */

package sqlancer.cockroachdb.gen;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.LinkedList;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import sqlancer.cockroachdb.CockroachDBProvider;
import sqlancer.cockroachdb.CockroachDBSchema;
import sqlancer.cockroachdb.gen.CockroachDBInsertGenerator;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.schema.TableIndex;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class CockroachDBInsertGenerator_ESTest extends CockroachDBInsertGenerator_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      CockroachDBSchema.CockroachDBCompositeDataType cockroachDBSchema_CockroachDBCompositeDataType0 = CockroachDBSchema.CockroachDBCompositeDataType.getBit(782694408);
      CockroachDBSchema.CockroachDBColumn cockroachDBSchema_CockroachDBColumn0 = new CockroachDBSchema.CockroachDBColumn("m!:pP", cockroachDBSchema_CockroachDBCompositeDataType0, false, false);
      List<CockroachDBSchema.CockroachDBColumn> list0 = List.of(cockroachDBSchema_CockroachDBColumn0, cockroachDBSchema_CockroachDBColumn0);
      LinkedList<TableIndex> linkedList0 = new LinkedList<TableIndex>();
      CockroachDBSchema.CockroachDBTable cockroachDBSchema_CockroachDBTable0 = new CockroachDBSchema.CockroachDBTable("m!:pP", list0, linkedList0, false);
      CockroachDBProvider.CockroachDBGlobalState cockroachDBProvider_CockroachDBGlobalState0 = new CockroachDBProvider.CockroachDBGlobalState();
      // Undeclared exception!
      CockroachDBInsertGenerator.insert(cockroachDBProvider_CockroachDBGlobalState0, cockroachDBSchema_CockroachDBTable0);
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      CockroachDBProvider.CockroachDBGlobalState cockroachDBProvider_CockroachDBGlobalState0 = new CockroachDBProvider.CockroachDBGlobalState();
      // Undeclared exception!
      try { 
        CockroachDBInsertGenerator.insert(cockroachDBProvider_CockroachDBGlobalState0, (CockroachDBSchema.CockroachDBTable) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.cockroachdb.gen.CockroachDBInsertGenerator", e);
      }
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      LinkedList<CockroachDBSchema.CockroachDBColumn> linkedList0 = new LinkedList<CockroachDBSchema.CockroachDBColumn>();
      LinkedList<TableIndex> linkedList1 = new LinkedList<TableIndex>();
      CockroachDBSchema.CockroachDBTable cockroachDBSchema_CockroachDBTable0 = new CockroachDBSchema.CockroachDBTable("violates foreign key constraint", linkedList0, linkedList1, false);
      // Undeclared exception!
      try { 
        CockroachDBInsertGenerator.insert((CockroachDBProvider.CockroachDBGlobalState) null, cockroachDBSchema_CockroachDBTable0);
        fail("Expecting exception: AssertionError");
      
      } catch(AssertionError e) {
         //
         // [] 1
         //
      }
  }

  @Test(timeout = 4000)
  public void test3()  throws Throwable  {
      CockroachDBProvider.CockroachDBGlobalState cockroachDBProvider_CockroachDBGlobalState0 = new CockroachDBProvider.CockroachDBGlobalState();
      // Undeclared exception!
      try { 
        CockroachDBInsertGenerator.insert(cockroachDBProvider_CockroachDBGlobalState0);
        fail("Expecting exception: AssertionError");
      
      } catch(AssertionError e) {
         //
         // null
         //
      }
  }

  @Test(timeout = 4000)
  public void test4()  throws Throwable  {
      CockroachDBSchema.CockroachDBCompositeDataType cockroachDBSchema_CockroachDBCompositeDataType0 = CockroachDBSchema.CockroachDBCompositeDataType.getVarBit(0);
      CockroachDBSchema.CockroachDBColumn cockroachDBSchema_CockroachDBColumn0 = new CockroachDBSchema.CockroachDBColumn("l{l_ur@@S3oYk`P", cockroachDBSchema_CockroachDBCompositeDataType0, false, false);
      List<CockroachDBSchema.CockroachDBColumn> list0 = List.of(cockroachDBSchema_CockroachDBColumn0, cockroachDBSchema_CockroachDBColumn0);
      LinkedList<TableIndex> linkedList0 = new LinkedList<TableIndex>();
      CockroachDBSchema.CockroachDBTable cockroachDBSchema_CockroachDBTable0 = new CockroachDBSchema.CockroachDBTable("tinyint(4)", list0, linkedList0, false);
      CockroachDBProvider.CockroachDBGlobalState cockroachDBProvider_CockroachDBGlobalState0 = new CockroachDBProvider.CockroachDBGlobalState();
      SQLQueryAdapter sQLQueryAdapter0 = CockroachDBInsertGenerator.insert(cockroachDBProvider_CockroachDBGlobalState0, cockroachDBSchema_CockroachDBTable0);
      SQLQueryAdapter sQLQueryAdapter1 = CockroachDBInsertGenerator.insert(cockroachDBProvider_CockroachDBGlobalState0, cockroachDBSchema_CockroachDBTable0);
      assertNotSame(sQLQueryAdapter1, sQLQueryAdapter0);
  }

  @Test(timeout = 4000)
  public void test5()  throws Throwable  {
      CockroachDBSchema.CockroachDBDataType cockroachDBSchema_CockroachDBDataType0 = CockroachDBSchema.CockroachDBDataType.VARBIT;
      cockroachDBSchema_CockroachDBDataType0.get();
      LinkedList<CockroachDBSchema.CockroachDBColumn> linkedList0 = new LinkedList<CockroachDBSchema.CockroachDBColumn>();
      LinkedList<TableIndex> linkedList1 = new LinkedList<TableIndex>();
      CockroachDBSchema.CockroachDBTable cockroachDBSchema_CockroachDBTable0 = new CockroachDBSchema.CockroachDBTable("q9hc#&Kn$sCl{x", linkedList0, linkedList1, false);
      CockroachDBProvider.CockroachDBGlobalState cockroachDBProvider_CockroachDBGlobalState0 = new CockroachDBProvider.CockroachDBGlobalState();
      SQLQueryAdapter sQLQueryAdapter0 = CockroachDBInsertGenerator.insert(cockroachDBProvider_CockroachDBGlobalState0, cockroachDBSchema_CockroachDBTable0);
      assertEquals("INSERT INTO q9hc#&Kn$sCl{x DEFAULT VALUES;", sQLQueryAdapter0.getLogString());
  }

  @Test(timeout = 4000)
  public void test6()  throws Throwable  {
      // Undeclared exception!
      try { 
        CockroachDBInsertGenerator.insert((CockroachDBProvider.CockroachDBGlobalState) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.cockroachdb.gen.CockroachDBInsertGenerator", e);
      }
  }
}

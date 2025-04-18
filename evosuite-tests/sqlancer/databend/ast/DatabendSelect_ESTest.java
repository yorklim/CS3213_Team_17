/*
 * This file was automatically generated by EvoSuite
 * Mon Apr 14 08:48:42 GMT 2025
 */

package sqlancer.databend.ast;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.LinkedList;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import sqlancer.databend.DatabendSchema;
import sqlancer.databend.ast.DatabendExpression;
import sqlancer.databend.ast.DatabendJoin;
import sqlancer.databend.ast.DatabendSelect;
import sqlancer.databend.ast.DatabendTableReference;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class DatabendSelect_ESTest extends DatabendSelect_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      DatabendSelect databendSelect0 = new DatabendSelect();
      databendSelect0.setDistinct(true);
      boolean boolean0 = databendSelect0.isDistinct();
      assertTrue(boolean0);
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      DatabendSelect databendSelect0 = new DatabendSelect();
      LinkedList<DatabendJoin> linkedList0 = new LinkedList<DatabendJoin>();
      DatabendTableReference databendTableReference0 = new DatabendTableReference((DatabendSchema.DatabendTable) null);
      DatabendJoin.OuterType databendJoin_OuterType0 = DatabendJoin.OuterType.RIGHT;
      DatabendJoin databendJoin0 = DatabendJoin.createNaturalJoin(databendTableReference0, databendTableReference0, databendJoin_OuterType0);
      linkedList0.add(databendJoin0);
      databendSelect0.setJoinClauses(linkedList0);
      List<DatabendJoin> list0 = databendSelect0.getJoinClauses();
      assertEquals(1, list0.size());
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      DatabendSelect databendSelect0 = new DatabendSelect();
      // Undeclared exception!
      try { 
        databendSelect0.setJoinClauses((List<DatabendJoin>) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.databend.ast.DatabendSelect", e);
      }
  }

  @Test(timeout = 4000)
  public void test3()  throws Throwable  {
      DatabendSelect databendSelect0 = new DatabendSelect();
      databendSelect0.setJoinList((List<DatabendExpression>) null);
      // Undeclared exception!
      try { 
        databendSelect0.getJoinClauses();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.databend.ast.DatabendSelect", e);
      }
  }

  @Test(timeout = 4000)
  public void test4()  throws Throwable  {
      DatabendSelect databendSelect0 = new DatabendSelect();
      List<DatabendExpression> list0 = List.of((DatabendExpression) databendSelect0, (DatabendExpression) databendSelect0);
      databendSelect0.setJoinList(list0);
      // Undeclared exception!
      try { 
        databendSelect0.getJoinClauses();
        fail("Expecting exception: ClassCastException");
      
      } catch(ClassCastException e) {
         //
         // class sqlancer.databend.ast.DatabendSelect cannot be cast to class sqlancer.databend.ast.DatabendJoin (sqlancer.databend.ast.DatabendSelect and sqlancer.databend.ast.DatabendJoin are in unnamed module of loader org.evosuite.instrumentation.InstrumentingClassLoader @2d98e9f5)
         //
         verifyException("sqlancer.databend.ast.DatabendSelect", e);
      }
  }

  @Test(timeout = 4000)
  public void test5()  throws Throwable  {
      DatabendSelect databendSelect0 = new DatabendSelect();
      List<DatabendExpression> list0 = List.of((DatabendExpression) databendSelect0, (DatabendExpression) databendSelect0, (DatabendExpression) databendSelect0, (DatabendExpression) databendSelect0, (DatabendExpression) databendSelect0, (DatabendExpression) databendSelect0, (DatabendExpression) databendSelect0, (DatabendExpression) databendSelect0, (DatabendExpression) databendSelect0, (DatabendExpression) databendSelect0);
      databendSelect0.setFetchColumns(list0);
      // Undeclared exception!
      try { 
        databendSelect0.asString();
        fail("Expecting exception: StackOverflowError");
      
      } catch(StackOverflowError e) {
      }
  }

  @Test(timeout = 4000)
  public void test6()  throws Throwable  {
      DatabendSelect databendSelect0 = new DatabendSelect();
      boolean boolean0 = databendSelect0.isDistinct();
      assertFalse(boolean0);
  }

  @Test(timeout = 4000)
  public void test7()  throws Throwable  {
      DatabendSelect databendSelect0 = new DatabendSelect();
      List<DatabendJoin> list0 = databendSelect0.getJoinClauses();
      assertTrue(list0.isEmpty());
  }

  @Test(timeout = 4000)
  public void test8()  throws Throwable  {
      DatabendSelect databendSelect0 = new DatabendSelect();
      // Undeclared exception!
      try { 
        databendSelect0.asString();
        fail("Expecting exception: IllegalStateException");
      
      } catch(IllegalStateException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.common.ast.SelectBase", e);
      }
  }
}

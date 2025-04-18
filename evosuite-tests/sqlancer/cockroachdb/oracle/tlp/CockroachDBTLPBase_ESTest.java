/*
 * This file was automatically generated by EvoSuite
 * Mon Apr 14 05:20:42 GMT 2025
 */

package sqlancer.cockroachdb.oracle.tlp;

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
import sqlancer.cockroachdb.ast.CockroachDBConstant;
import sqlancer.cockroachdb.ast.CockroachDBExpression;
import sqlancer.cockroachdb.ast.CockroachDBFunction;
import sqlancer.cockroachdb.ast.CockroachDBFunctionCall;
import sqlancer.cockroachdb.gen.CockroachDBExpressionGenerator;
import sqlancer.cockroachdb.oracle.tlp.CockroachDBTLPBase;
import sqlancer.common.gen.ExpressionGenerator;
import sqlancer.common.schema.TableIndex;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class CockroachDBTLPBase_ESTest extends CockroachDBTLPBase_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      CockroachDBProvider.CockroachDBGlobalState cockroachDBProvider_CockroachDBGlobalState0 = new CockroachDBProvider.CockroachDBGlobalState();
      CockroachDBConstant.CockroachDBNullConstant cockroachDBConstant_CockroachDBNullConstant0 = new CockroachDBConstant.CockroachDBNullConstant();
      List<CockroachDBExpression> list0 = List.of((CockroachDBExpression) cockroachDBConstant_CockroachDBNullConstant0, (CockroachDBExpression) cockroachDBConstant_CockroachDBNullConstant0, (CockroachDBExpression) cockroachDBConstant_CockroachDBNullConstant0, (CockroachDBExpression) cockroachDBConstant_CockroachDBNullConstant0, (CockroachDBExpression) cockroachDBConstant_CockroachDBNullConstant0, (CockroachDBExpression) cockroachDBConstant_CockroachDBNullConstant0, (CockroachDBExpression) cockroachDBConstant_CockroachDBNullConstant0);
      List<CockroachDBExpression> list1 = CockroachDBTLPBase.getJoins(list0, cockroachDBProvider_CockroachDBGlobalState0);
      assertEquals(0, list1.size());
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      CockroachDBProvider.CockroachDBGlobalState cockroachDBProvider_CockroachDBGlobalState0 = new CockroachDBProvider.CockroachDBGlobalState();
      CockroachDBTLPBase cockroachDBTLPBase0 = new CockroachDBTLPBase(cockroachDBProvider_CockroachDBGlobalState0);
      CockroachDBExpressionGenerator cockroachDBExpressionGenerator0 = new CockroachDBExpressionGenerator(cockroachDBProvider_CockroachDBGlobalState0);
      cockroachDBTLPBase0.gen = cockroachDBExpressionGenerator0;
      ExpressionGenerator<CockroachDBExpression> expressionGenerator0 = cockroachDBTLPBase0.getGen();
      assertNotNull(expressionGenerator0);
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      CockroachDBProvider.CockroachDBGlobalState cockroachDBProvider_CockroachDBGlobalState0 = new CockroachDBProvider.CockroachDBGlobalState();
      // Undeclared exception!
      try { 
        CockroachDBTLPBase.getJoins((List<CockroachDBExpression>) null, cockroachDBProvider_CockroachDBGlobalState0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.cockroachdb.oracle.tlp.CockroachDBTLPBase", e);
      }
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      CockroachDBProvider.CockroachDBGlobalState cockroachDBProvider_CockroachDBGlobalState0 = new CockroachDBProvider.CockroachDBGlobalState();
      CockroachDBTLPBase cockroachDBTLPBase0 = new CockroachDBTLPBase(cockroachDBProvider_CockroachDBGlobalState0);
      // Undeclared exception!
      try { 
        cockroachDBTLPBase0.check();
        fail("Expecting exception: AssertionError");
      
      } catch(AssertionError e) {
         //
         // null
         //
      }
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      LinkedList<CockroachDBExpression> linkedList0 = new LinkedList<CockroachDBExpression>();
      CockroachDBFunction cockroachDBFunction0 = CockroachDBFunction.LENGTH1;
      CockroachDBFunctionCall cockroachDBFunctionCall0 = new CockroachDBFunctionCall(cockroachDBFunction0, linkedList0);
      CockroachDBProvider.CockroachDBGlobalState cockroachDBProvider_CockroachDBGlobalState0 = new CockroachDBProvider.CockroachDBGlobalState();
      linkedList0.add((CockroachDBExpression) cockroachDBFunctionCall0);
      linkedList0.add((CockroachDBExpression) cockroachDBFunctionCall0);
      List<CockroachDBExpression> list0 = CockroachDBTLPBase.getJoins(linkedList0, cockroachDBProvider_CockroachDBGlobalState0);
      assertEquals(0, list0.size());
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      LinkedList<CockroachDBExpression> linkedList0 = new LinkedList<CockroachDBExpression>();
      List<CockroachDBExpression> list0 = CockroachDBTLPBase.getJoins(linkedList0, (CockroachDBProvider.CockroachDBGlobalState) null);
      assertTrue(list0.isEmpty());
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      CockroachDBProvider.CockroachDBGlobalState cockroachDBProvider_CockroachDBGlobalState0 = new CockroachDBProvider.CockroachDBGlobalState();
      CockroachDBTLPBase cockroachDBTLPBase0 = new CockroachDBTLPBase(cockroachDBProvider_CockroachDBGlobalState0);
      LinkedList<CockroachDBSchema.CockroachDBTable> linkedList0 = new LinkedList<CockroachDBSchema.CockroachDBTable>();
      CockroachDBSchema.CockroachDBTables cockroachDBSchema_CockroachDBTables0 = new CockroachDBSchema.CockroachDBTables(linkedList0);
      cockroachDBTLPBase0.targetTables = cockroachDBSchema_CockroachDBTables0;
      List<CockroachDBExpression> list0 = cockroachDBTLPBase0.generateFetchColumns();
      assertEquals(1, list0.size());
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      CockroachDBProvider.CockroachDBGlobalState cockroachDBProvider_CockroachDBGlobalState0 = new CockroachDBProvider.CockroachDBGlobalState();
      CockroachDBTLPBase cockroachDBTLPBase0 = new CockroachDBTLPBase(cockroachDBProvider_CockroachDBGlobalState0);
      LinkedList<CockroachDBSchema.CockroachDBTable> linkedList0 = new LinkedList<CockroachDBSchema.CockroachDBTable>();
      CockroachDBSchema.CockroachDBDataType cockroachDBSchema_CockroachDBDataType0 = CockroachDBSchema.CockroachDBDataType.BOOL;
      CockroachDBSchema.CockroachDBCompositeDataType cockroachDBSchema_CockroachDBCompositeDataType0 = new CockroachDBSchema.CockroachDBCompositeDataType(cockroachDBSchema_CockroachDBDataType0, 3);
      CockroachDBSchema.CockroachDBColumn cockroachDBSchema_CockroachDBColumn0 = new CockroachDBSchema.CockroachDBColumn("", cockroachDBSchema_CockroachDBCompositeDataType0, true, true);
      List<CockroachDBSchema.CockroachDBColumn> list0 = List.of(cockroachDBSchema_CockroachDBColumn0);
      LinkedList<TableIndex> linkedList1 = new LinkedList<TableIndex>();
      CockroachDBSchema.CockroachDBTable cockroachDBSchema_CockroachDBTable0 = new CockroachDBSchema.CockroachDBTable("", list0, linkedList1, true);
      linkedList0.add(cockroachDBSchema_CockroachDBTable0);
      CockroachDBSchema.CockroachDBTables cockroachDBSchema_CockroachDBTables0 = new CockroachDBSchema.CockroachDBTables(linkedList0);
      cockroachDBTLPBase0.targetTables = cockroachDBSchema_CockroachDBTables0;
      List<CockroachDBExpression> list1 = cockroachDBTLPBase0.generateFetchColumns();
      assertEquals(1, list1.size());
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      CockroachDBProvider.CockroachDBGlobalState cockroachDBProvider_CockroachDBGlobalState0 = new CockroachDBProvider.CockroachDBGlobalState();
      CockroachDBTLPBase cockroachDBTLPBase0 = new CockroachDBTLPBase(cockroachDBProvider_CockroachDBGlobalState0);
      // Undeclared exception!
      try { 
        cockroachDBTLPBase0.generateFetchColumns();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.cockroachdb.oracle.tlp.CockroachDBTLPBase", e);
      }
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      CockroachDBTLPBase cockroachDBTLPBase0 = new CockroachDBTLPBase((CockroachDBProvider.CockroachDBGlobalState) null);
      // Undeclared exception!
      try { 
        cockroachDBTLPBase0.check();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.cockroachdb.oracle.tlp.CockroachDBTLPBase", e);
      }
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      CockroachDBProvider.CockroachDBGlobalState cockroachDBProvider_CockroachDBGlobalState0 = new CockroachDBProvider.CockroachDBGlobalState();
      CockroachDBTLPBase cockroachDBTLPBase0 = new CockroachDBTLPBase(cockroachDBProvider_CockroachDBGlobalState0);
      ExpressionGenerator<CockroachDBExpression> expressionGenerator0 = cockroachDBTLPBase0.getGen();
      assertNull(expressionGenerator0);
  }
}

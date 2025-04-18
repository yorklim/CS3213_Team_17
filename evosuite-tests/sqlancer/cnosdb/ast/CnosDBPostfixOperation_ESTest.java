/*
 * This file was automatically generated by EvoSuite
 * Mon Apr 14 04:09:41 GMT 2025
 */

package sqlancer.cnosdb.ast;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import sqlancer.cnosdb.CnosDBSchema;
import sqlancer.cnosdb.ast.CnosDBConstant;
import sqlancer.cnosdb.ast.CnosDBExpression;
import sqlancer.cnosdb.ast.CnosDBPostfixOperation;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class CnosDBPostfixOperation_ESTest extends CnosDBPostfixOperation_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      CnosDBPostfixOperation.PostfixOperator cnosDBPostfixOperation_PostfixOperator0 = CnosDBPostfixOperation.PostfixOperator.IS_NOT_UNKNOWN;
      CnosDBConstant cnosDBConstant0 = CnosDBConstant.createIntConstant((-427L));
      CnosDBPostfixOperation cnosDBPostfixOperation0 = (CnosDBPostfixOperation)CnosDBPostfixOperation.create(cnosDBConstant0, cnosDBPostfixOperation_PostfixOperator0);
      CnosDBConstant.IntConstant cnosDBConstant_IntConstant0 = (CnosDBConstant.IntConstant)cnosDBPostfixOperation0.getExpression();
      assertEquals((-427.0), cnosDBConstant_IntConstant0.asDouble(), 0.01);
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      CnosDBPostfixOperation cnosDBPostfixOperation0 = null;
      try {
        cnosDBPostfixOperation0 = new CnosDBPostfixOperation((CnosDBExpression) null, (CnosDBPostfixOperation.PostfixOperator) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.cnosdb.ast.CnosDBPostfixOperation$PostfixOperator", e);
      }
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      CnosDBPostfixOperation.PostfixOperator cnosDBPostfixOperation_PostfixOperator0 = CnosDBPostfixOperation.PostfixOperator.IS_FALSE;
      CnosDBSchema.CnosDBDataType[] cnosDBSchema_CnosDBDataTypeArray0 = cnosDBPostfixOperation_PostfixOperator0.getInputDataTypes();
      assertEquals(1, cnosDBSchema_CnosDBDataTypeArray0.length);
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      CnosDBPostfixOperation.PostfixOperator cnosDBPostfixOperation_PostfixOperator0 = CnosDBPostfixOperation.PostfixOperator.IS_NOT_UNKNOWN;
      CnosDBSchema.CnosDBDataType[] cnosDBSchema_CnosDBDataTypeArray0 = cnosDBPostfixOperation_PostfixOperator0.getInputDataTypes();
      assertEquals(1, cnosDBSchema_CnosDBDataTypeArray0.length);
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      CnosDBPostfixOperation.PostfixOperator cnosDBPostfixOperation_PostfixOperator0 = CnosDBPostfixOperation.PostfixOperator.IS_TRUE;
      CnosDBSchema.CnosDBDataType[] cnosDBSchema_CnosDBDataTypeArray0 = cnosDBPostfixOperation_PostfixOperator0.getInputDataTypes();
      assertEquals(1, cnosDBSchema_CnosDBDataTypeArray0.length);
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      CnosDBPostfixOperation.PostfixOperator cnosDBPostfixOperation_PostfixOperator0 = CnosDBPostfixOperation.PostfixOperator.IS_TRUE;
      CnosDBPostfixOperation cnosDBPostfixOperation0 = new CnosDBPostfixOperation((CnosDBExpression) null, cnosDBPostfixOperation_PostfixOperator0);
      String string0 = cnosDBPostfixOperation0.getOperatorTextRepresentation();
      assertEquals("IS TRUE", string0);
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      CnosDBPostfixOperation.PostfixOperator cnosDBPostfixOperation_PostfixOperator0 = CnosDBPostfixOperation.PostfixOperator.IS_UNKNOWN;
      CnosDBPostfixOperation cnosDBPostfixOperation0 = new CnosDBPostfixOperation((CnosDBExpression) null, cnosDBPostfixOperation_PostfixOperator0);
      CnosDBExpression cnosDBExpression0 = cnosDBPostfixOperation0.getExpression();
      assertNull(cnosDBExpression0);
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      CnosDBPostfixOperation.PostfixOperator cnosDBPostfixOperation_PostfixOperator0 = CnosDBPostfixOperation.PostfixOperator.IS_NOT_UNKNOWN;
      CnosDBConstant cnosDBConstant0 = CnosDBConstant.createIntConstant((-427L));
      CnosDBExpression cnosDBExpression0 = CnosDBPostfixOperation.create(cnosDBConstant0, cnosDBPostfixOperation_PostfixOperator0);
      CnosDBSchema.CnosDBDataType cnosDBSchema_CnosDBDataType0 = cnosDBExpression0.getExpressionType();
      assertEquals(CnosDBSchema.CnosDBDataType.BOOLEAN, cnosDBSchema_CnosDBDataType0);
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      CnosDBConstant cnosDBConstant0 = CnosDBConstant.createTrue();
      // Undeclared exception!
      try { 
        CnosDBPostfixOperation.create(cnosDBConstant0, (CnosDBPostfixOperation.PostfixOperator) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.cnosdb.ast.CnosDBPostfixOperation$PostfixOperator", e);
      }
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      CnosDBPostfixOperation.PostfixOperator cnosDBPostfixOperation_PostfixOperator0 = CnosDBPostfixOperation.PostfixOperator.IS_NOT_UNKNOWN;
      String string0 = cnosDBPostfixOperation_PostfixOperator0.getTextRepresentation();
      assertEquals("IS_NOT_UNKNOWN", string0);
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      CnosDBPostfixOperation.PostfixOperator cnosDBPostfixOperation_PostfixOperator0 = CnosDBPostfixOperation.PostfixOperator.IS_UNKNOWN;
      CnosDBSchema.CnosDBDataType[] cnosDBSchema_CnosDBDataTypeArray0 = cnosDBPostfixOperation_PostfixOperator0.getInputDataTypes();
      assertEquals(1, cnosDBSchema_CnosDBDataTypeArray0.length);
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      CnosDBPostfixOperation.PostfixOperator cnosDBPostfixOperation_PostfixOperator0 = CnosDBPostfixOperation.PostfixOperator.IS_NOT_NULL;
      CnosDBSchema.CnosDBDataType[] cnosDBSchema_CnosDBDataTypeArray0 = cnosDBPostfixOperation_PostfixOperator0.getInputDataTypes();
      assertEquals(6, cnosDBSchema_CnosDBDataTypeArray0.length);
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      CnosDBPostfixOperation.PostfixOperator cnosDBPostfixOperation_PostfixOperator0 = CnosDBPostfixOperation.PostfixOperator.getRandom();
      CnosDBSchema.CnosDBDataType[] cnosDBSchema_CnosDBDataTypeArray0 = cnosDBPostfixOperation_PostfixOperator0.getInputDataTypes();
      assertEquals(6, cnosDBSchema_CnosDBDataTypeArray0.length);
  }
}

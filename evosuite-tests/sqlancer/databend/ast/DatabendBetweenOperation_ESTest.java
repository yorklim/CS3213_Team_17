/*
 * This file was automatically generated by EvoSuite
 * Mon Apr 14 08:43:27 GMT 2025
 */

package sqlancer.databend.ast;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import sqlancer.databend.DatabendSchema;
import sqlancer.databend.ast.DatabendAlias;
import sqlancer.databend.ast.DatabendBetweenOperation;
import sqlancer.databend.ast.DatabendBinaryLogicalOperation;
import sqlancer.databend.ast.DatabendColumnReference;
import sqlancer.databend.ast.DatabendColumnValue;
import sqlancer.databend.ast.DatabendConstant;
import sqlancer.databend.ast.DatabendExpression;
import sqlancer.databend.ast.DatabendFunctionOperation;
import sqlancer.databend.ast.DatabendUnaryPostfixOperation;
import sqlancer.databend.ast.DatabendUnaryPrefixOperation;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class DatabendBetweenOperation_ESTest extends DatabendBetweenOperation_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      DatabendBetweenOperation databendBetweenOperation0 = new DatabendBetweenOperation((DatabendExpression) null, (DatabendExpression) null, (DatabendExpression) null, true);
      DatabendExpression databendExpression0 = databendBetweenOperation0.getRightExpr();
      assertNull(databendExpression0);
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      DatabendConstant.DatabendNullConstant databendConstant_DatabendNullConstant0 = new DatabendConstant.DatabendNullConstant();
      DatabendBetweenOperation databendBetweenOperation0 = new DatabendBetweenOperation(databendConstant_DatabendNullConstant0, (DatabendExpression) null, databendConstant_DatabendNullConstant0, true);
      DatabendExpression databendExpression0 = databendBetweenOperation0.getMiddleExpr();
      assertNull(databendExpression0);
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      LinkedList<DatabendExpression> linkedList0 = new LinkedList<DatabendExpression>();
      DatabendFunctionOperation<Object> databendFunctionOperation0 = new DatabendFunctionOperation<Object>(linkedList0, linkedList0);
      DatabendUnaryPostfixOperation.DatabendUnaryPostfixOperator databendUnaryPostfixOperation_DatabendUnaryPostfixOperator0 = DatabendUnaryPostfixOperation.DatabendUnaryPostfixOperator.IS_NOT_NULL;
      DatabendUnaryPostfixOperation databendUnaryPostfixOperation0 = new DatabendUnaryPostfixOperation(databendFunctionOperation0, databendUnaryPostfixOperation_DatabendUnaryPostfixOperator0);
      DatabendAlias databendAlias0 = new DatabendAlias(databendUnaryPostfixOperation0, "FQo| XnsFwY?`^^Ba|");
      DatabendBetweenOperation databendBetweenOperation0 = new DatabendBetweenOperation(databendAlias0, databendAlias0, databendFunctionOperation0, true);
      DatabendExpression databendExpression0 = databendBetweenOperation0.getLeftExpr();
      assertSame(databendAlias0, databendExpression0);
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      LinkedList<DatabendExpression> linkedList0 = new LinkedList<DatabendExpression>();
      DatabendFunctionOperation<Object> databendFunctionOperation0 = new DatabendFunctionOperation<Object>(linkedList0, linkedList0);
      DatabendUnaryPostfixOperation.DatabendUnaryPostfixOperator databendUnaryPostfixOperation_DatabendUnaryPostfixOperator0 = DatabendUnaryPostfixOperation.DatabendUnaryPostfixOperator.IS_NOT_NULL;
      DatabendUnaryPostfixOperation databendUnaryPostfixOperation0 = new DatabendUnaryPostfixOperation(databendFunctionOperation0, databendUnaryPostfixOperation_DatabendUnaryPostfixOperator0);
      DatabendAlias databendAlias0 = new DatabendAlias(databendUnaryPostfixOperation0, "FQo| XnsFwY?`^^Ba|");
      DatabendBetweenOperation databendBetweenOperation0 = new DatabendBetweenOperation(databendAlias0, databendAlias0, databendFunctionOperation0, true);
      DatabendConstant databendConstant0 = databendBetweenOperation0.getExpectedValue();
      assertNull(databendConstant0);
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      DatabendSchema.DatabendDataType databendSchema_DatabendDataType0 = DatabendSchema.DatabendDataType.VARCHAR;
      DatabendSchema.DatabendCompositeDataType databendSchema_DatabendCompositeDataType0 = new DatabendSchema.DatabendCompositeDataType(databendSchema_DatabendDataType0, (-2837));
      DatabendSchema.DatabendColumn databendSchema_DatabendColumn0 = new DatabendSchema.DatabendColumn("", databendSchema_DatabendCompositeDataType0, true, true);
      DatabendConstant.DatabendIntConstant databendConstant_DatabendIntConstant0 = new DatabendConstant.DatabendIntConstant((-2837));
      DatabendColumnValue databendColumnValue0 = new DatabendColumnValue(databendSchema_DatabendColumn0, databendConstant_DatabendIntConstant0);
      DatabendBetweenOperation databendBetweenOperation0 = new DatabendBetweenOperation(databendColumnValue0, databendColumnValue0, databendColumnValue0, false);
      DatabendConstant databendConstant0 = databendBetweenOperation0.getExpectedValue();
      assertFalse(databendConstant0.isFloat());
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      DatabendConstant.DatabendTimestampConstant databendConstant_DatabendTimestampConstant0 = new DatabendConstant.DatabendTimestampConstant(634L);
      DatabendBinaryLogicalOperation.DatabendBinaryLogicalOperator databendBinaryLogicalOperation_DatabendBinaryLogicalOperator0 = DatabendBinaryLogicalOperation.DatabendBinaryLogicalOperator.OR;
      DatabendBinaryLogicalOperation databendBinaryLogicalOperation0 = new DatabendBinaryLogicalOperation((DatabendExpression) null, databendConstant_DatabendTimestampConstant0, databendBinaryLogicalOperation_DatabendBinaryLogicalOperator0);
      DatabendColumnReference databendColumnReference0 = new DatabendColumnReference((DatabendSchema.DatabendColumn) null);
      DatabendBetweenOperation databendBetweenOperation0 = new DatabendBetweenOperation(databendBinaryLogicalOperation0, databendColumnReference0, (DatabendExpression) null, false);
      // Undeclared exception!
      try { 
        databendBetweenOperation0.getExpectedValue();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.databend.ast.DatabendBinaryLogicalOperation", e);
      }
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      DatabendSchema.DatabendColumn databendSchema_DatabendColumn0 = new DatabendSchema.DatabendColumn("/#", (DatabendSchema.DatabendCompositeDataType) null, false, false);
      DatabendConstant.DatabendIntConstant databendConstant_DatabendIntConstant0 = new DatabendConstant.DatabendIntConstant((-2955L));
      DatabendColumnValue databendColumnValue0 = new DatabendColumnValue(databendSchema_DatabendColumn0, databendConstant_DatabendIntConstant0);
      DatabendUnaryPostfixOperation.DatabendUnaryPostfixOperator databendUnaryPostfixOperation_DatabendUnaryPostfixOperator0 = DatabendUnaryPostfixOperation.DatabendUnaryPostfixOperator.IS_NOT_NULL;
      DatabendUnaryPostfixOperation databendUnaryPostfixOperation0 = new DatabendUnaryPostfixOperation(databendColumnValue0, databendUnaryPostfixOperation_DatabendUnaryPostfixOperator0);
      DatabendBetweenOperation databendBetweenOperation0 = new DatabendBetweenOperation(databendUnaryPostfixOperation0, databendConstant_DatabendIntConstant0, databendColumnValue0, false);
      // Undeclared exception!
      try { 
        databendBetweenOperation0.getExpectedValue();
        fail("Expecting exception: AssertionError");
      
      } catch(AssertionError e) {
         //
         // -2955
         //
      }
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      LinkedList<DatabendExpression> linkedList0 = new LinkedList<DatabendExpression>();
      DatabendFunctionOperation<Object> databendFunctionOperation0 = new DatabendFunctionOperation<Object>(linkedList0, linkedList0);
      DatabendUnaryPostfixOperation.DatabendUnaryPostfixOperator databendUnaryPostfixOperation_DatabendUnaryPostfixOperator0 = DatabendUnaryPostfixOperation.DatabendUnaryPostfixOperator.IS_NOT_NULL;
      DatabendUnaryPostfixOperation databendUnaryPostfixOperation0 = new DatabendUnaryPostfixOperation(databendFunctionOperation0, databendUnaryPostfixOperation_DatabendUnaryPostfixOperator0);
      DatabendAlias databendAlias0 = new DatabendAlias(databendUnaryPostfixOperation0, "FQo| XnsFwY?`^^Ba|");
      DatabendBetweenOperation databendBetweenOperation0 = new DatabendBetweenOperation(databendAlias0, databendAlias0, databendFunctionOperation0, true);
      DatabendExpression databendExpression0 = databendBetweenOperation0.getMiddleExpr();
      assertSame(databendExpression0, databendAlias0);
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      LinkedList<DatabendExpression> linkedList0 = new LinkedList<DatabendExpression>();
      DatabendFunctionOperation<Object> databendFunctionOperation0 = new DatabendFunctionOperation<Object>(linkedList0, linkedList0);
      DatabendUnaryPostfixOperation.DatabendUnaryPostfixOperator databendUnaryPostfixOperation_DatabendUnaryPostfixOperator0 = DatabendUnaryPostfixOperation.DatabendUnaryPostfixOperator.IS_NOT_NULL;
      DatabendUnaryPostfixOperation databendUnaryPostfixOperation0 = new DatabendUnaryPostfixOperation(databendFunctionOperation0, databendUnaryPostfixOperation_DatabendUnaryPostfixOperator0);
      DatabendAlias databendAlias0 = new DatabendAlias(databendUnaryPostfixOperation0, "FQo| XnsFwY?`^^Ba|");
      DatabendBetweenOperation databendBetweenOperation0 = new DatabendBetweenOperation(databendAlias0, databendAlias0, databendFunctionOperation0, true);
      DatabendExpression databendExpression0 = databendBetweenOperation0.getRightExpr();
      assertNotNull(databendExpression0);
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      DatabendConstant.DatabendStringConstant databendConstant_DatabendStringConstant0 = new DatabendConstant.DatabendStringConstant("1969-12-31 23:59:59");
      DatabendBetweenOperation databendBetweenOperation0 = new DatabendBetweenOperation((DatabendExpression) null, (DatabendExpression) null, databendConstant_DatabendStringConstant0, false);
      DatabendExpression databendExpression0 = databendBetweenOperation0.getLeftExpr();
      assertNull(databendExpression0);
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      DatabendSchema.DatabendCompositeDataType databendSchema_DatabendCompositeDataType0 = DatabendSchema.DatabendCompositeDataType.getRandomWithoutNull();
      DatabendSchema.DatabendColumn databendSchema_DatabendColumn0 = new DatabendSchema.DatabendColumn("sqlancer.databend.ast.DatabendBetweenOperation", databendSchema_DatabendCompositeDataType0, true, true);
      DatabendConstant.DatabendNullConstant databendConstant_DatabendNullConstant0 = new DatabendConstant.DatabendNullConstant();
      DatabendColumnValue databendColumnValue0 = new DatabendColumnValue(databendSchema_DatabendColumn0, databendConstant_DatabendNullConstant0);
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.UNARY_PLUS;
      DatabendUnaryPrefixOperation databendUnaryPrefixOperation0 = new DatabendUnaryPrefixOperation(databendColumnValue0, databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0);
      DatabendBetweenOperation databendBetweenOperation0 = new DatabendBetweenOperation(databendUnaryPrefixOperation0, databendColumnValue0, databendColumnValue0, true);
      DatabendSchema.DatabendDataType databendSchema_DatabendDataType0 = databendBetweenOperation0.getExpectedType();
      assertEquals(DatabendSchema.DatabendDataType.BOOLEAN, databendSchema_DatabendDataType0);
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      DatabendSchema.DatabendCompositeDataType databendSchema_DatabendCompositeDataType0 = DatabendSchema.DatabendCompositeDataType.getRandomWithoutNull();
      DatabendSchema.DatabendColumn databendSchema_DatabendColumn0 = new DatabendSchema.DatabendColumn("sqlancer.databend.ast.DatabendBetweenOperation", databendSchema_DatabendCompositeDataType0, true, true);
      DatabendConstant.DatabendNullConstant databendConstant_DatabendNullConstant0 = new DatabendConstant.DatabendNullConstant();
      DatabendColumnValue databendColumnValue0 = new DatabendColumnValue(databendSchema_DatabendColumn0, databendConstant_DatabendNullConstant0);
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.UNARY_PLUS;
      DatabendUnaryPrefixOperation databendUnaryPrefixOperation0 = new DatabendUnaryPrefixOperation(databendColumnValue0, databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0);
      DatabendBetweenOperation databendBetweenOperation0 = new DatabendBetweenOperation(databendUnaryPrefixOperation0, databendColumnValue0, databendColumnValue0, true);
      DatabendConstant databendConstant0 = databendBetweenOperation0.getExpectedValue();
      assertFalse(databendConstant0.isInt());
  }
}

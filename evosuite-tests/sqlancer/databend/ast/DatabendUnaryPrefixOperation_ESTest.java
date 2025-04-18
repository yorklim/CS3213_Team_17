/*
 * This file was automatically generated by EvoSuite
 * Mon Apr 14 08:56:48 GMT 2025
 */

package sqlancer.databend.ast;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import sqlancer.databend.DatabendSchema;
import sqlancer.databend.ast.DatabendBinaryArithmeticOperation;
import sqlancer.databend.ast.DatabendBinaryComparisonOperation;
import sqlancer.databend.ast.DatabendColumnValue;
import sqlancer.databend.ast.DatabendConstant;
import sqlancer.databend.ast.DatabendExpression;
import sqlancer.databend.ast.DatabendLikeOperation;
import sqlancer.databend.ast.DatabendSelect;
import sqlancer.databend.ast.DatabendUnaryPrefixOperation;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class DatabendUnaryPrefixOperation_ESTest extends DatabendUnaryPrefixOperation_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      DatabendConstant.DatabendBooleanConstant databendConstant_DatabendBooleanConstant0 = new DatabendConstant.DatabendBooleanConstant(false);
      DatabendUnaryPrefixOperation databendUnaryPrefixOperation0 = new DatabendUnaryPrefixOperation(databendConstant_DatabendBooleanConstant0, (DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator) null);
      databendUnaryPrefixOperation0.getOp();
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      DatabendConstant.DatabendFloatConstant databendConstant_DatabendFloatConstant0 = new DatabendConstant.DatabendFloatConstant(1280.3895562259006);
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.UNARY_MINUS;
      DatabendUnaryPrefixOperation databendUnaryPrefixOperation0 = new DatabendUnaryPrefixOperation(databendConstant_DatabendFloatConstant0, databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0);
      DatabendConstant.DatabendFloatConstant databendConstant_DatabendFloatConstant1 = (DatabendConstant.DatabendFloatConstant)databendUnaryPrefixOperation0.getExpression();
      assertFalse(databendConstant_DatabendFloatConstant1.isString());
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      DatabendSchema.DatabendDataType databendSchema_DatabendDataType0 = DatabendSchema.DatabendDataType.VARCHAR;
      DatabendSchema.DatabendCompositeDataType databendSchema_DatabendCompositeDataType0 = new DatabendSchema.DatabendCompositeDataType(databendSchema_DatabendDataType0, 0);
      DatabendSchema.DatabendColumn databendSchema_DatabendColumn0 = new DatabendSchema.DatabendColumn("><<*.}K", databendSchema_DatabendCompositeDataType0, true, true);
      DatabendConstant databendConstant0 = DatabendConstant.createStringConstant("");
      DatabendColumnValue databendColumnValue0 = new DatabendColumnValue(databendSchema_DatabendColumn0, databendConstant0);
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.UNARY_PLUS;
      DatabendUnaryPrefixOperation databendUnaryPrefixOperation0 = new DatabendUnaryPrefixOperation(databendColumnValue0, databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0);
      DatabendConstant databendConstant1 = databendUnaryPrefixOperation0.getExpectedValue();
      assertFalse(databendConstant1.isBoolean());
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      DatabendConstant.DatabendFloatConstant databendConstant_DatabendFloatConstant0 = new DatabendConstant.DatabendFloatConstant(1280.3895562259006);
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.UNARY_MINUS;
      DatabendUnaryPrefixOperation databendUnaryPrefixOperation0 = new DatabendUnaryPrefixOperation(databendConstant_DatabendFloatConstant0, databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0);
      databendUnaryPrefixOperation0.getExpectedType();
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      DatabendSelect databendSelect0 = new DatabendSelect();
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.NOT;
      DatabendUnaryPrefixOperation databendUnaryPrefixOperation0 = new DatabendUnaryPrefixOperation(databendSelect0, databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0);
      DatabendSchema.DatabendDataType databendSchema_DatabendDataType0 = databendUnaryPrefixOperation0.getExpectedType();
      assertEquals(DatabendSchema.DatabendDataType.BOOLEAN, databendSchema_DatabendDataType0);
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      DatabendConstant.DatabendFloatConstant databendConstant_DatabendFloatConstant0 = new DatabendConstant.DatabendFloatConstant(1735.2277804);
      DatabendColumnValue databendColumnValue0 = new DatabendColumnValue((DatabendSchema.DatabendColumn) null, databendConstant_DatabendFloatConstant0);
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.UNARY_MINUS;
      DatabendLikeOperation.DatabendLikeOperator databendLikeOperation_DatabendLikeOperator0 = DatabendLikeOperation.DatabendLikeOperator.LIKE_OPERATOR;
      DatabendLikeOperation databendLikeOperation0 = new DatabendLikeOperation(databendColumnValue0, databendColumnValue0, databendLikeOperation_DatabendLikeOperator0);
      DatabendUnaryPrefixOperation databendUnaryPrefixOperation0 = new DatabendUnaryPrefixOperation(databendLikeOperation0, databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0);
      // Undeclared exception!
      try { 
        databendUnaryPrefixOperation0.getExpectedValue();
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // sqlancer.databend.ast.DatabendConstant$DatabendFloatConstant@0000000001
         //
         verifyException("sqlancer.databend.ast.DatabendConstant", e);
      }
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.UNARY_MINUS;
      DatabendUnaryPrefixOperation databendUnaryPrefixOperation0 = new DatabendUnaryPrefixOperation((DatabendExpression) null, databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0);
      // Undeclared exception!
      try { 
        databendUnaryPrefixOperation0.getExpectedValue();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.databend.ast.DatabendUnaryPrefixOperation", e);
      }
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      DatabendConstant.DatabendFloatConstant databendConstant_DatabendFloatConstant0 = new DatabendConstant.DatabendFloatConstant((-1.0));
      DatabendColumnValue databendColumnValue0 = new DatabendColumnValue((DatabendSchema.DatabendColumn) null, databendConstant_DatabendFloatConstant0);
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.UNARY_MINUS;
      DatabendBinaryArithmeticOperation databendBinaryArithmeticOperation0 = new DatabendBinaryArithmeticOperation(databendColumnValue0, databendColumnValue0, databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0);
      DatabendUnaryPrefixOperation databendUnaryPrefixOperation0 = new DatabendUnaryPrefixOperation(databendBinaryArithmeticOperation0, databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0);
      // Undeclared exception!
      try { 
        databendUnaryPrefixOperation0.getExpectedValue();
        fail("Expecting exception: ClassCastException");
      
      } catch(ClassCastException e) {
         //
         // class sqlancer.databend.ast.DatabendUnaryPrefixOperation$DatabendUnaryPrefixOperator$3 cannot be cast to class sqlancer.databend.ast.DatabendBinaryArithmeticOperation$DatabendBinaryArithmeticOperator (sqlancer.databend.ast.DatabendUnaryPrefixOperation$DatabendUnaryPrefixOperator$3 and sqlancer.databend.ast.DatabendBinaryArithmeticOperation$DatabendBinaryArithmeticOperator are in unnamed module of loader org.evosuite.instrumentation.InstrumentingClassLoader @3b39bacf)
         //
         verifyException("sqlancer.databend.ast.DatabendBinaryArithmeticOperation", e);
      }
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      DatabendConstant.DatabendStringConstant databendConstant_DatabendStringConstant0 = new DatabendConstant.DatabendStringConstant("i00y2Q");
      DatabendColumnValue databendColumnValue0 = new DatabendColumnValue((DatabendSchema.DatabendColumn) null, databendConstant_DatabendStringConstant0);
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.NOT;
      DatabendUnaryPrefixOperation databendUnaryPrefixOperation0 = new DatabendUnaryPrefixOperation(databendColumnValue0, databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0);
      // Undeclared exception!
      try { 
        databendUnaryPrefixOperation0.getExpectedValue();
        fail("Expecting exception: AssertionError");
      
      } catch(AssertionError e) {
         //
         // string: i00y2Q, cannot be forced to boolean
         //
      }
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      DatabendSchema.DatabendColumn databendSchema_DatabendColumn0 = new DatabendSchema.DatabendColumn((String) null, (DatabendSchema.DatabendCompositeDataType) null, false, false);
      DatabendConstant.DatabendIntConstant databendConstant_DatabendIntConstant0 = new DatabendConstant.DatabendIntConstant(0L);
      DatabendColumnValue databendColumnValue0 = new DatabendColumnValue(databendSchema_DatabendColumn0, databendConstant_DatabendIntConstant0);
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.UNARY_MINUS;
      DatabendUnaryPrefixOperation databendUnaryPrefixOperation0 = new DatabendUnaryPrefixOperation(databendColumnValue0, databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0);
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator1 = databendUnaryPrefixOperation0.getOp();
      assertSame(databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0, databendUnaryPrefixOperation_DatabendUnaryPrefixOperator1);
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.UNARY_PLUS;
      DatabendUnaryPrefixOperation databendUnaryPrefixOperation0 = new DatabendUnaryPrefixOperation((DatabendExpression) null, databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0);
      DatabendExpression databendExpression0 = databendUnaryPrefixOperation0.getExpression();
      assertNull(databendExpression0);
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      DatabendConstant.DatabendFloatConstant databendConstant_DatabendFloatConstant0 = new DatabendConstant.DatabendFloatConstant((-2348.44));
      DatabendColumnValue databendColumnValue0 = new DatabendColumnValue((DatabendSchema.DatabendColumn) null, databendConstant_DatabendFloatConstant0);
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.UNARY_MINUS;
      DatabendUnaryPrefixOperation databendUnaryPrefixOperation0 = new DatabendUnaryPrefixOperation(databendColumnValue0, databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0);
      DatabendConstant databendConstant0 = databendUnaryPrefixOperation0.getExpectedValue();
      assertFalse(databendConstant0.isString());
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.UNARY_MINUS;
      DatabendConstant.DatabendIntConstant databendConstant_DatabendIntConstant0 = new DatabendConstant.DatabendIntConstant(1015L);
      DatabendColumnValue databendColumnValue0 = new DatabendColumnValue((DatabendSchema.DatabendColumn) null, databendConstant_DatabendIntConstant0);
      DatabendUnaryPrefixOperation databendUnaryPrefixOperation0 = new DatabendUnaryPrefixOperation(databendColumnValue0, databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0);
      DatabendConstant databendConstant0 = databendUnaryPrefixOperation0.getExpectedValue();
      assertNotSame(databendConstant_DatabendIntConstant0, databendConstant0);
  }

  @Test(timeout = 4000)
  public void test13()  throws Throwable  {
      DatabendConstant.DatabendNullConstant databendConstant_DatabendNullConstant0 = new DatabendConstant.DatabendNullConstant();
      DatabendColumnValue databendColumnValue0 = new DatabendColumnValue((DatabendSchema.DatabendColumn) null, databendConstant_DatabendNullConstant0);
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.UNARY_MINUS;
      DatabendUnaryPrefixOperation databendUnaryPrefixOperation0 = new DatabendUnaryPrefixOperation(databendColumnValue0, databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0);
      DatabendConstant databendConstant0 = databendUnaryPrefixOperation0.getExpectedValue();
      assertFalse(databendConstant0.isString());
  }

  @Test(timeout = 4000)
  public void test14()  throws Throwable  {
      DatabendSchema.DatabendDataType databendSchema_DatabendDataType0 = DatabendSchema.DatabendDataType.VARCHAR;
      DatabendSchema.DatabendCompositeDataType databendSchema_DatabendCompositeDataType0 = new DatabendSchema.DatabendCompositeDataType(databendSchema_DatabendDataType0, 0);
      DatabendSchema.DatabendColumn databendSchema_DatabendColumn0 = new DatabendSchema.DatabendColumn(">?<<*.}K", databendSchema_DatabendCompositeDataType0, false, false);
      DatabendBinaryComparisonOperation.DatabendBinaryComparisonOperator databendBinaryComparisonOperation_DatabendBinaryComparisonOperator0 = DatabendBinaryComparisonOperation.DatabendBinaryComparisonOperator.NOT_EQUALS;
      DatabendConstant databendConstant0 = DatabendConstant.createStringConstant(">?<<*.}K");
      DatabendConstant databendConstant1 = databendBinaryComparisonOperation_DatabendBinaryComparisonOperator0.apply(databendConstant0, databendConstant0);
      DatabendColumnValue databendColumnValue0 = new DatabendColumnValue(databendSchema_DatabendColumn0, databendConstant1);
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.UNARY_MINUS;
      DatabendUnaryPrefixOperation databendUnaryPrefixOperation0 = new DatabendUnaryPrefixOperation(databendColumnValue0, databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0);
      DatabendConstant databendConstant2 = databendUnaryPrefixOperation0.getExpectedValue();
      assertNull(databendConstant2);
  }

  @Test(timeout = 4000)
  public void test15()  throws Throwable  {
      DatabendSchema.DatabendDataType databendSchema_DatabendDataType0 = DatabendSchema.DatabendDataType.VARCHAR;
      DatabendSchema.DatabendCompositeDataType databendSchema_DatabendCompositeDataType0 = new DatabendSchema.DatabendCompositeDataType(databendSchema_DatabendDataType0, 0);
      DatabendSchema.DatabendColumn databendSchema_DatabendColumn0 = new DatabendSchema.DatabendColumn(">?<<*.}K", databendSchema_DatabendCompositeDataType0, false, false);
      DatabendBinaryComparisonOperation.DatabendBinaryComparisonOperator databendBinaryComparisonOperation_DatabendBinaryComparisonOperator0 = DatabendBinaryComparisonOperation.DatabendBinaryComparisonOperator.NOT_EQUALS;
      DatabendConstant databendConstant0 = DatabendConstant.createStringConstant(">?<<*.}K");
      DatabendConstant databendConstant1 = databendBinaryComparisonOperation_DatabendBinaryComparisonOperator0.apply(databendConstant0, databendConstant0);
      DatabendColumnValue databendColumnValue0 = new DatabendColumnValue(databendSchema_DatabendColumn0, databendConstant1);
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.NOT;
      DatabendUnaryPrefixOperation databendUnaryPrefixOperation0 = new DatabendUnaryPrefixOperation(databendColumnValue0, databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0);
      DatabendConstant.DatabendBooleanConstant databendConstant_DatabendBooleanConstant0 = (DatabendConstant.DatabendBooleanConstant)databendUnaryPrefixOperation0.getExpectedValue();
      assertTrue(databendConstant_DatabendBooleanConstant0.getValue());
  }

  @Test(timeout = 4000)
  public void test16()  throws Throwable  {
      DatabendConstant.DatabendNullConstant databendConstant_DatabendNullConstant0 = new DatabendConstant.DatabendNullConstant();
      DatabendColumnValue databendColumnValue0 = new DatabendColumnValue((DatabendSchema.DatabendColumn) null, databendConstant_DatabendNullConstant0);
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.NOT;
      DatabendUnaryPrefixOperation databendUnaryPrefixOperation0 = new DatabendUnaryPrefixOperation(databendColumnValue0, databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0);
      DatabendConstant databendConstant0 = databendUnaryPrefixOperation0.getExpectedValue();
      assertFalse(databendConstant0.isInt());
  }

  @Test(timeout = 4000)
  public void test17()  throws Throwable  {
      DatabendSchema.DatabendDataType databendSchema_DatabendDataType0 = DatabendSchema.DatabendDataType.VARCHAR;
      DatabendSchema.DatabendCompositeDataType databendSchema_DatabendCompositeDataType0 = new DatabendSchema.DatabendCompositeDataType(databendSchema_DatabendDataType0, 0);
      DatabendSchema.DatabendColumn databendSchema_DatabendColumn0 = new DatabendSchema.DatabendColumn(">?<<*.}K", databendSchema_DatabendCompositeDataType0, false, false);
      DatabendBinaryComparisonOperation.DatabendBinaryComparisonOperator databendBinaryComparisonOperation_DatabendBinaryComparisonOperator0 = DatabendBinaryComparisonOperation.DatabendBinaryComparisonOperator.EQUALS;
      DatabendConstant databendConstant0 = DatabendConstant.createStringConstant(">?<<*.}K");
      DatabendConstant databendConstant1 = databendBinaryComparisonOperation_DatabendBinaryComparisonOperator0.apply(databendConstant0, databendConstant0);
      DatabendColumnValue databendColumnValue0 = new DatabendColumnValue(databendSchema_DatabendColumn0, databendConstant1);
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.NOT;
      DatabendUnaryPrefixOperation databendUnaryPrefixOperation0 = new DatabendUnaryPrefixOperation(databendColumnValue0, databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0);
      DatabendConstant databendConstant2 = databendUnaryPrefixOperation0.getExpectedValue();
      assertEquals("false", databendConstant2.toString());
  }

  @Test(timeout = 4000)
  public void test18()  throws Throwable  {
      DatabendConstant.DatabendBooleanConstant databendConstant_DatabendBooleanConstant0 = new DatabendConstant.DatabendBooleanConstant(false);
      DatabendUnaryPrefixOperation databendUnaryPrefixOperation0 = new DatabendUnaryPrefixOperation(databendConstant_DatabendBooleanConstant0, (DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator) null);
      DatabendConstant databendConstant0 = databendUnaryPrefixOperation0.getExpectedValue();
      assertNull(databendConstant0);
  }

  @Test(timeout = 4000)
  public void test19()  throws Throwable  {
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.UNARY_MINUS;
      String string0 = databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0.getTextRepresentation();
      assertEquals("-", string0);
  }

  @Test(timeout = 4000)
  public void test20()  throws Throwable  {
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.UNARY_MINUS;
      DatabendSchema.DatabendDataType databendSchema_DatabendDataType0 = databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0.getRandomInputDataTypes();
      assertEquals(DatabendSchema.DatabendDataType.INT, databendSchema_DatabendDataType0);
  }

  @Test(timeout = 4000)
  public void test21()  throws Throwable  {
      DatabendSchema.DatabendColumn databendSchema_DatabendColumn0 = new DatabendSchema.DatabendColumn("CREATE TABLE statements should set couldAffectSchema to true", (DatabendSchema.DatabendCompositeDataType) null, true, true);
      DatabendConstant databendConstant0 = DatabendConstant.createFloatConstant(2790L);
      DatabendColumnValue databendColumnValue0 = DatabendColumnValue.create(databendSchema_DatabendColumn0, databendConstant0);
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.UNARY_PLUS;
      DatabendUnaryPrefixOperation databendUnaryPrefixOperation0 = new DatabendUnaryPrefixOperation(databendColumnValue0, databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0);
      // Undeclared exception!
      try { 
        databendUnaryPrefixOperation0.getExpectedType();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.databend.ast.DatabendColumnValue", e);
      }
  }

  @Test(timeout = 4000)
  public void test22()  throws Throwable  {
      DatabendConstant.DatabendNullConstant databendConstant_DatabendNullConstant0 = new DatabendConstant.DatabendNullConstant();
      DatabendSchema.DatabendCompositeDataType databendSchema_DatabendCompositeDataType0 = DatabendSchema.DatabendCompositeDataType.getRandomWithoutNull();
      DatabendSchema.DatabendColumn databendSchema_DatabendColumn0 = new DatabendSchema.DatabendColumn("", databendSchema_DatabendCompositeDataType0, true, true);
      DatabendColumnValue databendColumnValue0 = new DatabendColumnValue(databendSchema_DatabendColumn0, databendConstant_DatabendNullConstant0);
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.NOT;
      DatabendUnaryPrefixOperation databendUnaryPrefixOperation0 = new DatabendUnaryPrefixOperation(databendColumnValue0, databendUnaryPrefixOperation_DatabendUnaryPrefixOperator0);
      DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator databendUnaryPrefixOperation_DatabendUnaryPrefixOperator1 = DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.UNARY_MINUS;
      DatabendSchema.DatabendDataType databendSchema_DatabendDataType0 = databendUnaryPrefixOperation_DatabendUnaryPrefixOperator1.getExpressionType(databendUnaryPrefixOperation0);
      assertEquals(DatabendSchema.DatabendDataType.BOOLEAN, databendSchema_DatabendDataType0);
  }
}

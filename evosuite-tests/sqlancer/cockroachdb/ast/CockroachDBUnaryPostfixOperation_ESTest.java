/*
 * This file was automatically generated by EvoSuite
 * Mon Apr 14 04:57:34 GMT 2025
 */

package sqlancer.cockroachdb.ast;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import sqlancer.cockroachdb.ast.CockroachDBConstant;
import sqlancer.cockroachdb.ast.CockroachDBUnaryPostfixOperation;
import sqlancer.common.visitor.UnaryOperation;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class CockroachDBUnaryPostfixOperation_ESTest extends CockroachDBUnaryPostfixOperation_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      CockroachDBUnaryPostfixOperation.CockroachDBUnaryPostfixOperator cockroachDBUnaryPostfixOperation_CockroachDBUnaryPostfixOperator0 = CockroachDBUnaryPostfixOperation.CockroachDBUnaryPostfixOperator.IS_NOT_NULL;
      String string0 = cockroachDBUnaryPostfixOperation_CockroachDBUnaryPostfixOperator0.getTextRepresentation();
      assertEquals("IS NOT NULL", string0);
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      CockroachDBUnaryPostfixOperation.CockroachDBUnaryPostfixOperator cockroachDBUnaryPostfixOperation_CockroachDBUnaryPostfixOperator0 = CockroachDBUnaryPostfixOperation.CockroachDBUnaryPostfixOperator.IS_NOT_TRUE;
      CockroachDBConstant.CockroachDBNullConstant cockroachDBConstant_CockroachDBNullConstant0 = new CockroachDBConstant.CockroachDBNullConstant();
      CockroachDBUnaryPostfixOperation cockroachDBUnaryPostfixOperation0 = new CockroachDBUnaryPostfixOperation(cockroachDBConstant_CockroachDBNullConstant0, cockroachDBUnaryPostfixOperation_CockroachDBUnaryPostfixOperator0);
      UnaryOperation.OperatorKind unaryOperation_OperatorKind0 = cockroachDBUnaryPostfixOperation0.getOperatorKind();
      assertEquals(UnaryOperation.OperatorKind.POSTFIX, unaryOperation_OperatorKind0);
  }
}

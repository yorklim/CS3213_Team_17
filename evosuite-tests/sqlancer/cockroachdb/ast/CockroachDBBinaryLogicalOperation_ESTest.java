/*
 * This file was automatically generated by EvoSuite
 * Mon Apr 14 05:10:34 GMT 2025
 */

package sqlancer.cockroachdb.ast;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import sqlancer.cockroachdb.ast.CockroachDBBinaryLogicalOperation;
import sqlancer.cockroachdb.ast.CockroachDBExpression;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class CockroachDBBinaryLogicalOperation_ESTest extends CockroachDBBinaryLogicalOperation_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      CockroachDBBinaryLogicalOperation.CockroachDBBinaryLogicalOperator cockroachDBBinaryLogicalOperation_CockroachDBBinaryLogicalOperator0 = CockroachDBBinaryLogicalOperation.CockroachDBBinaryLogicalOperator.OR;
      CockroachDBBinaryLogicalOperation cockroachDBBinaryLogicalOperation0 = new CockroachDBBinaryLogicalOperation((CockroachDBExpression) null, (CockroachDBExpression) null, cockroachDBBinaryLogicalOperation_CockroachDBBinaryLogicalOperator0);
      assertEquals("OR", cockroachDBBinaryLogicalOperation0.getOperatorRepresentation());
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      CockroachDBBinaryLogicalOperation.CockroachDBBinaryLogicalOperator cockroachDBBinaryLogicalOperation_CockroachDBBinaryLogicalOperator0 = CockroachDBBinaryLogicalOperation.CockroachDBBinaryLogicalOperator.getRandom();
      assertEquals("AND", cockroachDBBinaryLogicalOperation_CockroachDBBinaryLogicalOperator0.getTextRepresentation());
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      CockroachDBBinaryLogicalOperation.CockroachDBBinaryLogicalOperator cockroachDBBinaryLogicalOperation_CockroachDBBinaryLogicalOperator0 = CockroachDBBinaryLogicalOperation.CockroachDBBinaryLogicalOperator.OR;
      String string0 = cockroachDBBinaryLogicalOperation_CockroachDBBinaryLogicalOperator0.getTextRepresentation();
      assertEquals("OR", string0);
  }
}

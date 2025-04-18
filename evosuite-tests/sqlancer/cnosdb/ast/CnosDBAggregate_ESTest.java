/*
 * This file was automatically generated by EvoSuite
 * Sun Apr 13 14:31:30 GMT 2025
 */

package sqlancer.cnosdb.ast;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import sqlancer.cnosdb.CnosDBSchema;
import sqlancer.cnosdb.ast.CnosDBAggregate;
import sqlancer.cnosdb.ast.CnosDBExpression;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class CnosDBAggregate_ESTest extends CnosDBAggregate_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      CnosDBSchema.CnosDBDataType cnosDBSchema_CnosDBDataType0 = CnosDBSchema.CnosDBDataType.DOUBLE;
      CnosDBAggregate.CnosDBAggregateFunction cnosDBAggregate_CnosDBAggregateFunction0 = CnosDBAggregate.CnosDBAggregateFunction.CORR;
      boolean boolean0 = cnosDBAggregate_CnosDBAggregateFunction0.supportsReturnType(cnosDBSchema_CnosDBDataType0);
      assertTrue(boolean0);
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      CnosDBAggregate.CnosDBAggregateFunction cnosDBAggregate_CnosDBAggregateFunction0 = CnosDBAggregate.CnosDBAggregateFunction.APPROX_PERCENTILE_CONT;
      CnosDBSchema.CnosDBDataType cnosDBSchema_CnosDBDataType0 = cnosDBAggregate_CnosDBAggregateFunction0.getRandomReturnType();
      assertEquals(CnosDBSchema.CnosDBDataType.DOUBLE, cnosDBSchema_CnosDBDataType0);
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      CnosDBAggregate.CnosDBAggregateFunction cnosDBAggregate_CnosDBAggregateFunction0 = CnosDBAggregate.CnosDBAggregateFunction.COUNT;
      CnosDBSchema.CnosDBDataType cnosDBSchema_CnosDBDataType0 = CnosDBSchema.CnosDBDataType.STRING;
      CnosDBSchema.CnosDBDataType[] cnosDBSchema_CnosDBDataTypeArray0 = cnosDBAggregate_CnosDBAggregateFunction0.getArgsTypes(cnosDBSchema_CnosDBDataType0);
      assertEquals(1, cnosDBSchema_CnosDBDataTypeArray0.length);
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      CnosDBAggregate.CnosDBAggregateFunction cnosDBAggregate_CnosDBAggregateFunction0 = CnosDBAggregate.CnosDBAggregateFunction.COVAR_SAMP;
      CnosDBSchema.CnosDBDataType cnosDBSchema_CnosDBDataType0 = CnosDBSchema.CnosDBDataType.DOUBLE;
      CnosDBSchema.CnosDBDataType[] cnosDBSchema_CnosDBDataTypeArray0 = cnosDBAggregate_CnosDBAggregateFunction0.getArgsTypes(cnosDBSchema_CnosDBDataType0);
      assertEquals(2, cnosDBSchema_CnosDBDataTypeArray0.length);
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      CnosDBAggregate.CnosDBAggregateFunction cnosDBAggregate_CnosDBAggregateFunction0 = CnosDBAggregate.CnosDBAggregateFunction.COVAR;
      CnosDBSchema.CnosDBDataType cnosDBSchema_CnosDBDataType0 = CnosDBSchema.CnosDBDataType.TIMESTAMP;
      CnosDBSchema.CnosDBDataType[] cnosDBSchema_CnosDBDataTypeArray0 = cnosDBAggregate_CnosDBAggregateFunction0.getArgsTypes(cnosDBSchema_CnosDBDataType0);
      assertEquals(2, cnosDBSchema_CnosDBDataTypeArray0.length);
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      CnosDBSchema.CnosDBDataType cnosDBSchema_CnosDBDataType0 = CnosDBSchema.CnosDBDataType.DOUBLE;
      CnosDBAggregate.CnosDBAggregateFunction cnosDBAggregate_CnosDBAggregateFunction0 = CnosDBAggregate.CnosDBAggregateFunction.COVAR_POP;
      CnosDBSchema.CnosDBDataType[] cnosDBSchema_CnosDBDataTypeArray0 = cnosDBAggregate_CnosDBAggregateFunction0.getArgsTypes(cnosDBSchema_CnosDBDataType0);
      assertEquals(2, cnosDBSchema_CnosDBDataTypeArray0.length);
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      CnosDBAggregate.CnosDBAggregateFunction cnosDBAggregate_CnosDBAggregateFunction0 = CnosDBAggregate.CnosDBAggregateFunction.APPROX_PERCENTILE_CONT;
      LinkedList<CnosDBExpression> linkedList0 = new LinkedList<CnosDBExpression>();
      CnosDBAggregate cnosDBAggregate0 = new CnosDBAggregate(linkedList0, cnosDBAggregate_CnosDBAggregateFunction0);
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      CnosDBSchema.CnosDBDataType cnosDBSchema_CnosDBDataType0 = CnosDBSchema.CnosDBDataType.DOUBLE;
      CnosDBAggregate.CnosDBAggregateFunction cnosDBAggregate_CnosDBAggregateFunction0 = CnosDBAggregate.CnosDBAggregateFunction.CORR;
      CnosDBSchema.CnosDBDataType[] cnosDBSchema_CnosDBDataTypeArray0 = cnosDBAggregate_CnosDBAggregateFunction0.getArgsTypes(cnosDBSchema_CnosDBDataType0);
      assertEquals(2, cnosDBSchema_CnosDBDataTypeArray0.length);
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      CnosDBSchema.CnosDBDataType cnosDBSchema_CnosDBDataType0 = CnosDBSchema.CnosDBDataType.STRING;
      CnosDBAggregate.CnosDBAggregateFunction cnosDBAggregate_CnosDBAggregateFunction0 = CnosDBAggregate.CnosDBAggregateFunction.APPROX_PERCENTILE_CONT_WITH_WEIGHT;
      CnosDBSchema.CnosDBDataType[] cnosDBSchema_CnosDBDataTypeArray0 = cnosDBAggregate_CnosDBAggregateFunction0.getArgsTypes(cnosDBSchema_CnosDBDataType0);
      assertEquals(3, cnosDBSchema_CnosDBDataTypeArray0.length);
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      CnosDBAggregate.CnosDBAggregateFunction cnosDBAggregate_CnosDBAggregateFunction0 = CnosDBAggregate.CnosDBAggregateFunction.APPROX_PERCENTILE_CONT;
      CnosDBSchema.CnosDBDataType cnosDBSchema_CnosDBDataType0 = CnosDBSchema.CnosDBDataType.TIMESTAMP;
      CnosDBSchema.CnosDBDataType[] cnosDBSchema_CnosDBDataTypeArray0 = cnosDBAggregate_CnosDBAggregateFunction0.getArgsTypes(cnosDBSchema_CnosDBDataType0);
      assertEquals(2, cnosDBSchema_CnosDBDataTypeArray0.length);
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      CnosDBAggregate.CnosDBAggregateFunction cnosDBAggregate_CnosDBAggregateFunction0 = CnosDBAggregate.CnosDBAggregateFunction.valueOf("APPROX_DISTINCT");
      CnosDBSchema.CnosDBDataType cnosDBSchema_CnosDBDataType0 = CnosDBSchema.CnosDBDataType.DOUBLE;
      CnosDBSchema.CnosDBDataType[] cnosDBSchema_CnosDBDataTypeArray0 = cnosDBAggregate_CnosDBAggregateFunction0.getArgsTypes(cnosDBSchema_CnosDBDataType0);
      assertEquals(1, cnosDBSchema_CnosDBDataTypeArray0.length);
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      CnosDBSchema.CnosDBDataType cnosDBSchema_CnosDBDataType0 = CnosDBSchema.CnosDBDataType.STRING;
      List<CnosDBAggregate.CnosDBAggregateFunction> list0 = CnosDBAggregate.CnosDBAggregateFunction.getAggregates(cnosDBSchema_CnosDBDataType0);
      assertEquals(2, list0.size());
  }
}

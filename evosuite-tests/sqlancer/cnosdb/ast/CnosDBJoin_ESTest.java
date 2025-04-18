/*
 * This file was automatically generated by EvoSuite
 * Sun Apr 13 14:18:41 GMT 2025
 */

package sqlancer.cnosdb.ast;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import sqlancer.cnosdb.CnosDBSchema;
import sqlancer.cnosdb.ast.CnosDBAggregate;
import sqlancer.cnosdb.ast.CnosDBAlias;
import sqlancer.cnosdb.ast.CnosDBConstant;
import sqlancer.cnosdb.ast.CnosDBExpression;
import sqlancer.cnosdb.ast.CnosDBJoin;
import sqlancer.cnosdb.ast.CnosDBSelect;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class CnosDBJoin_ESTest extends CnosDBJoin_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      CnosDBConstant cnosDBConstant0 = CnosDBConstant.createFalse();
      CnosDBJoin.CnosDBJoinType cnosDBJoin_CnosDBJoinType0 = CnosDBJoin.CnosDBJoinType.INNER;
      CnosDBJoin cnosDBJoin0 = new CnosDBJoin(cnosDBConstant0, cnosDBConstant0, cnosDBJoin_CnosDBJoinType0);
      CnosDBJoin.CnosDBJoinType cnosDBJoin_CnosDBJoinType1 = cnosDBJoin0.getType();
      assertEquals(CnosDBJoin.CnosDBJoinType.INNER, cnosDBJoin_CnosDBJoinType1);
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      CnosDBSelect cnosDBSelect0 = new CnosDBSelect();
      List<CnosDBExpression> list0 = cnosDBSelect0.getOrderByClauses();
      CnosDBAggregate.CnosDBAggregateFunction cnosDBAggregate_CnosDBAggregateFunction0 = CnosDBAggregate.CnosDBAggregateFunction.MIN;
      CnosDBAggregate cnosDBAggregate0 = new CnosDBAggregate(list0, cnosDBAggregate_CnosDBAggregateFunction0);
      CnosDBJoin.CnosDBJoinType cnosDBJoin_CnosDBJoinType0 = CnosDBJoin.CnosDBJoinType.RIGHT;
      CnosDBJoin cnosDBJoin0 = new CnosDBJoin(cnosDBAggregate0, cnosDBSelect0, cnosDBJoin_CnosDBJoinType0);
      CnosDBExpression cnosDBExpression0 = cnosDBJoin0.getTableReference();
      assertSame(cnosDBAggregate0, cnosDBExpression0);
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      CnosDBConstant cnosDBConstant0 = CnosDBConstant.createStringConstant("");
      CnosDBAlias cnosDBAlias0 = new CnosDBAlias(cnosDBConstant0, " as ");
      CnosDBJoin.CnosDBJoinType cnosDBJoin_CnosDBJoinType0 = CnosDBJoin.CnosDBJoinType.FULL;
      CnosDBJoin cnosDBJoin0 = new CnosDBJoin(cnosDBAlias0, cnosDBConstant0, cnosDBJoin_CnosDBJoinType0);
      CnosDBExpression cnosDBExpression0 = cnosDBJoin0.getOnClause();
      assertEquals(CnosDBSchema.CnosDBDataType.STRING, cnosDBExpression0.getExpressionType());
  }

  @Test(timeout = 4000)
  public void test3()  throws Throwable  {
      CnosDBJoin.CnosDBJoinType cnosDBJoin_CnosDBJoinType0 = CnosDBJoin.CnosDBJoinType.getRandom();
      assertEquals(CnosDBJoin.CnosDBJoinType.INNER, cnosDBJoin_CnosDBJoinType0);
  }

  @Test(timeout = 4000)
  public void test4()  throws Throwable  {
      CnosDBJoin.CnosDBJoinType cnosDBJoin_CnosDBJoinType0 = CnosDBJoin.CnosDBJoinType.INNER;
      CnosDBJoin cnosDBJoin0 = new CnosDBJoin((CnosDBExpression) null, (CnosDBExpression) null, cnosDBJoin_CnosDBJoinType0);
      CnosDBExpression cnosDBExpression0 = cnosDBJoin0.getOnClause();
      assertNull(cnosDBExpression0);
  }

  @Test(timeout = 4000)
  public void test5()  throws Throwable  {
      CnosDBConstant.CnosDBNullConstant cnosDBConstant_CnosDBNullConstant0 = new CnosDBConstant.CnosDBNullConstant();
      CnosDBJoin.CnosDBJoinType cnosDBJoin_CnosDBJoinType0 = CnosDBJoin.CnosDBJoinType.LEFT;
      CnosDBJoin cnosDBJoin0 = new CnosDBJoin(cnosDBConstant_CnosDBNullConstant0, cnosDBConstant_CnosDBNullConstant0, cnosDBJoin_CnosDBJoinType0);
      // Undeclared exception!
      try { 
        cnosDBJoin0.getExpressionType();
        fail("Expecting exception: AssertionError");
      
      } catch(AssertionError e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test(timeout = 4000)
  public void test6()  throws Throwable  {
      CnosDBJoin.CnosDBJoinType cnosDBJoin_CnosDBJoinType0 = CnosDBJoin.CnosDBJoinType.LEFT;
      CnosDBJoin cnosDBJoin0 = new CnosDBJoin((CnosDBExpression) null, (CnosDBExpression) null, cnosDBJoin_CnosDBJoinType0);
      CnosDBExpression cnosDBExpression0 = cnosDBJoin0.getTableReference();
      assertNull(cnosDBExpression0);
  }

  @Test(timeout = 4000)
  public void test7()  throws Throwable  {
      CnosDBConstant.CnosDBNullConstant cnosDBConstant_CnosDBNullConstant0 = new CnosDBConstant.CnosDBNullConstant();
      CnosDBJoin cnosDBJoin0 = new CnosDBJoin(cnosDBConstant_CnosDBNullConstant0, cnosDBConstant_CnosDBNullConstant0, (CnosDBJoin.CnosDBJoinType) null);
      cnosDBJoin0.getType();
  }
}

/*
 * This file was automatically generated by EvoSuite
 * Mon Apr 14 05:24:24 GMT 2025
 */

package sqlancer.cockroachdb.ast;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import sqlancer.cockroachdb.ast.CockroachDBAggregate;
import sqlancer.cockroachdb.ast.CockroachDBAlias;
import sqlancer.cockroachdb.ast.CockroachDBCaseOperation;
import sqlancer.cockroachdb.ast.CockroachDBConstant;
import sqlancer.cockroachdb.ast.CockroachDBExpression;
import sqlancer.cockroachdb.ast.CockroachDBJoin;
import sqlancer.cockroachdb.ast.CockroachDBSelect;
import sqlancer.cockroachdb.ast.CockroachDBUnaryArithmeticOperation;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class CockroachDBJoin_ESTest extends CockroachDBJoin_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      CockroachDBConstant.CockroachDBBitConstant cockroachDBConstant_CockroachDBBitConstant0 = new CockroachDBConstant.CockroachDBBitConstant("");
      CockroachDBAlias cockroachDBAlias0 = new CockroachDBAlias(cockroachDBConstant_CockroachDBBitConstant0, "");
      CockroachDBUnaryArithmeticOperation.CockroachDBUnaryAritmeticOperator cockroachDBUnaryArithmeticOperation_CockroachDBUnaryAritmeticOperator0 = CockroachDBUnaryArithmeticOperation.CockroachDBUnaryAritmeticOperator.MINUS;
      CockroachDBUnaryArithmeticOperation cockroachDBUnaryArithmeticOperation0 = new CockroachDBUnaryArithmeticOperation(cockroachDBAlias0, cockroachDBUnaryArithmeticOperation_CockroachDBUnaryAritmeticOperator0);
      CockroachDBJoin.JoinType cockroachDBJoin_JoinType0 = CockroachDBJoin.JoinType.NATURAL;
      CockroachDBJoin cockroachDBJoin0 = CockroachDBJoin.createJoin(cockroachDBUnaryArithmeticOperation0, cockroachDBConstant_CockroachDBBitConstant0, cockroachDBJoin_JoinType0, cockroachDBAlias0);
      assertEquals(CockroachDBJoin.JoinType.NATURAL, cockroachDBJoin0.getJoinType());
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      CockroachDBSelect cockroachDBSelect0 = new CockroachDBSelect();
      CockroachDBJoin.JoinType cockroachDBJoin_JoinType0 = CockroachDBJoin.JoinType.INNER;
      CockroachDBJoin cockroachDBJoin0 = new CockroachDBJoin(cockroachDBSelect0, cockroachDBSelect0, cockroachDBJoin_JoinType0, cockroachDBSelect0);
      CockroachDBSelect cockroachDBSelect1 = new CockroachDBSelect();
      CockroachDBJoin cockroachDBJoin1 = CockroachDBJoin.createJoin(cockroachDBSelect0, cockroachDBSelect1, cockroachDBJoin_JoinType0, cockroachDBJoin0);
      assertNotSame(cockroachDBJoin1, cockroachDBJoin0);
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      CockroachDBSelect cockroachDBSelect0 = new CockroachDBSelect();
      CockroachDBJoin.JoinType cockroachDBJoin_JoinType0 = CockroachDBJoin.JoinType.INNER;
      CockroachDBJoin cockroachDBJoin0 = new CockroachDBJoin(cockroachDBSelect0, cockroachDBSelect0, cockroachDBJoin_JoinType0, cockroachDBSelect0);
      CockroachDBExpression cockroachDBExpression0 = cockroachDBJoin0.getRightTable();
      assertSame(cockroachDBExpression0, cockroachDBSelect0);
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      CockroachDBSelect cockroachDBSelect0 = new CockroachDBSelect();
      CockroachDBJoin.JoinType cockroachDBJoin_JoinType0 = CockroachDBJoin.JoinType.INNER;
      CockroachDBJoin cockroachDBJoin0 = new CockroachDBJoin(cockroachDBSelect0, cockroachDBSelect0, cockroachDBJoin_JoinType0, cockroachDBSelect0);
      CockroachDBExpression cockroachDBExpression0 = cockroachDBJoin0.getLeftTable();
      assertSame(cockroachDBSelect0, cockroachDBExpression0);
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      CockroachDBSelect cockroachDBSelect0 = new CockroachDBSelect();
      List<CockroachDBExpression> list0 = cockroachDBSelect0.getJoinList();
      CockroachDBAggregate.CockroachDBAggregateFunction cockroachDBAggregate_CockroachDBAggregateFunction0 = CockroachDBAggregate.CockroachDBAggregateFunction.AVG;
      CockroachDBAggregate cockroachDBAggregate0 = new CockroachDBAggregate(cockroachDBAggregate_CockroachDBAggregateFunction0, list0);
      CockroachDBCaseOperation cockroachDBCaseOperation0 = new CockroachDBCaseOperation(list0, list0, cockroachDBAggregate0);
      CockroachDBJoin.JoinType[] cockroachDBJoin_JoinTypeArray0 = new CockroachDBJoin.JoinType[13];
      // Undeclared exception!
      try { 
        CockroachDBJoin.createJoin(cockroachDBCaseOperation0, cockroachDBCaseOperation0, cockroachDBJoin_JoinTypeArray0[1], cockroachDBCaseOperation0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.cockroachdb.ast.CockroachDBJoin", e);
      }
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      CockroachDBSelect cockroachDBSelect0 = new CockroachDBSelect();
      CockroachDBJoin.JoinType cockroachDBJoin_JoinType0 = CockroachDBJoin.JoinType.INNER;
      CockroachDBJoin cockroachDBJoin0 = new CockroachDBJoin(cockroachDBSelect0, cockroachDBSelect0, cockroachDBJoin_JoinType0, cockroachDBSelect0);
      CockroachDBJoin.JoinType cockroachDBJoin_JoinType1 = CockroachDBJoin.JoinType.CROSS;
      CockroachDBJoin cockroachDBJoin1 = CockroachDBJoin.createJoin(cockroachDBSelect0, cockroachDBSelect0, cockroachDBJoin_JoinType1, cockroachDBJoin0);
      CockroachDBExpression cockroachDBExpression0 = cockroachDBJoin1.getOnCondition();
      assertNull(cockroachDBExpression0);
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      CockroachDBJoin.JoinType cockroachDBJoin_JoinType0 = CockroachDBJoin.JoinType.getRandom();
      CockroachDBJoin.JoinType[] cockroachDBJoin_JoinTypeArray0 = new CockroachDBJoin.JoinType[6];
      cockroachDBJoin_JoinTypeArray0[0] = cockroachDBJoin_JoinType0;
      CockroachDBJoin.JoinType cockroachDBJoin_JoinType1 = CockroachDBJoin.JoinType.getRandomExcept(cockroachDBJoin_JoinTypeArray0);
      assertEquals(CockroachDBJoin.JoinType.LEFT, cockroachDBJoin_JoinType1);
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      CockroachDBJoin.JoinType cockroachDBJoin_JoinType0 = CockroachDBJoin.JoinType.FULL;
      CockroachDBJoin cockroachDBJoin0 = new CockroachDBJoin((CockroachDBExpression) null, (CockroachDBExpression) null, cockroachDBJoin_JoinType0, (CockroachDBExpression) null);
      cockroachDBJoin0.setOnClause((CockroachDBExpression) cockroachDBJoin0);
      assertEquals(CockroachDBJoin.JoinType.FULL, cockroachDBJoin0.getJoinType());
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      CockroachDBExpression cockroachDBExpression0 = CockroachDBConstant.createTimetz(0L);
      CockroachDBJoin.JoinType[] cockroachDBJoin_JoinTypeArray0 = new CockroachDBJoin.JoinType[0];
      CockroachDBJoin.JoinType cockroachDBJoin_JoinType0 = CockroachDBJoin.JoinType.getRandomExcept(cockroachDBJoin_JoinTypeArray0);
      CockroachDBJoin cockroachDBJoin0 = CockroachDBJoin.createJoin(cockroachDBExpression0, cockroachDBExpression0, cockroachDBJoin_JoinType0, cockroachDBExpression0);
      cockroachDBJoin0.setJoinType((CockroachDBJoin.JoinType) null);
      cockroachDBJoin0.getJoinType();
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      CockroachDBExpression cockroachDBExpression0 = CockroachDBConstant.createTimetz(0L);
      CockroachDBJoin.JoinType[] cockroachDBJoin_JoinTypeArray0 = new CockroachDBJoin.JoinType[0];
      CockroachDBJoin.JoinType cockroachDBJoin_JoinType0 = CockroachDBJoin.JoinType.getRandomExcept(cockroachDBJoin_JoinTypeArray0);
      CockroachDBJoin cockroachDBJoin0 = CockroachDBJoin.createJoin(cockroachDBExpression0, cockroachDBExpression0, cockroachDBJoin_JoinType0, cockroachDBExpression0);
      CockroachDBJoin.JoinType cockroachDBJoin_JoinType1 = cockroachDBJoin0.getJoinType();
      assertSame(cockroachDBJoin_JoinType0, cockroachDBJoin_JoinType1);
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      CockroachDBJoin.JoinType cockroachDBJoin_JoinType0 = CockroachDBJoin.JoinType.FULL;
      CockroachDBJoin cockroachDBJoin0 = new CockroachDBJoin((CockroachDBExpression) null, (CockroachDBExpression) null, cockroachDBJoin_JoinType0, (CockroachDBExpression) null);
      CockroachDBExpression cockroachDBExpression0 = cockroachDBJoin0.getLeftTable();
      assertNull(cockroachDBExpression0);
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      CockroachDBJoin.JoinType cockroachDBJoin_JoinType0 = CockroachDBJoin.JoinType.FULL;
      CockroachDBJoin cockroachDBJoin0 = new CockroachDBJoin((CockroachDBExpression) null, (CockroachDBExpression) null, cockroachDBJoin_JoinType0, (CockroachDBExpression) null);
      CockroachDBExpression cockroachDBExpression0 = cockroachDBJoin0.getRightTable();
      assertNull(cockroachDBExpression0);
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      CockroachDBExpression cockroachDBExpression0 = CockroachDBConstant.createTimetz(0L);
      CockroachDBJoin.JoinType[] cockroachDBJoin_JoinTypeArray0 = new CockroachDBJoin.JoinType[0];
      CockroachDBJoin.JoinType cockroachDBJoin_JoinType0 = CockroachDBJoin.JoinType.getRandomExcept(cockroachDBJoin_JoinTypeArray0);
      CockroachDBJoin cockroachDBJoin0 = CockroachDBJoin.createJoin(cockroachDBExpression0, cockroachDBExpression0, cockroachDBJoin_JoinType0, cockroachDBExpression0);
      CockroachDBExpression cockroachDBExpression1 = cockroachDBJoin0.getOnCondition();
      assertSame(cockroachDBExpression0, cockroachDBExpression1);
  }
}

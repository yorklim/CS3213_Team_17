/*
 * This file was automatically generated by EvoSuite
 * Sun Apr 13 13:31:34 GMT 2025
 */

package sqlancer.clickhouse.ast;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.LinkedList;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import sqlancer.clickhouse.ast.ClickHouseExpression;
import sqlancer.clickhouse.ast.ClickHouseSelect;
import sqlancer.clickhouse.ast.ClickHouseUnaryPrefixOperation;
import sqlancer.common.visitor.UnaryOperation;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class ClickHouseSelect_ESTest extends ClickHouseSelect_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      ClickHouseUnaryPrefixOperation.ClickHouseUnaryPrefixOperator clickHouseUnaryPrefixOperation_ClickHouseUnaryPrefixOperator0 = ClickHouseUnaryPrefixOperation.ClickHouseUnaryPrefixOperator.NOT;
      ClickHouseUnaryPrefixOperation clickHouseUnaryPrefixOperation0 = new ClickHouseUnaryPrefixOperation(clickHouseSelect0, clickHouseUnaryPrefixOperation_ClickHouseUnaryPrefixOperator0);
      clickHouseSelect0.setWhereClause((ClickHouseExpression) clickHouseUnaryPrefixOperation0);
      ClickHouseUnaryPrefixOperation clickHouseUnaryPrefixOperation1 = (ClickHouseUnaryPrefixOperation)clickHouseSelect0.getWhereClause();
      assertEquals(UnaryOperation.OperatorKind.PREFIX, clickHouseUnaryPrefixOperation1.getOperatorKind());
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      clickHouseSelect0.setOrderByClauses((List<ClickHouseExpression>) null);
      List<ClickHouseExpression> list0 = clickHouseSelect0.getOrderByClauses();
      assertNull(list0);
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      LinkedList<ClickHouseExpression> linkedList0 = new LinkedList<ClickHouseExpression>();
      clickHouseSelect0.setOrderByClauses(linkedList0);
      List<ClickHouseExpression> list0 = clickHouseSelect0.getOrderByClauses();
      assertEquals(0, list0.size());
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      ClickHouseUnaryPrefixOperation.ClickHouseUnaryPrefixOperator clickHouseUnaryPrefixOperation_ClickHouseUnaryPrefixOperator0 = ClickHouseUnaryPrefixOperation.ClickHouseUnaryPrefixOperator.MINUS;
      ClickHouseUnaryPrefixOperation clickHouseUnaryPrefixOperation0 = new ClickHouseUnaryPrefixOperation(clickHouseSelect0, clickHouseUnaryPrefixOperation_ClickHouseUnaryPrefixOperator0);
      clickHouseSelect0.setLimitClause((ClickHouseExpression) clickHouseUnaryPrefixOperation0);
      ClickHouseUnaryPrefixOperation clickHouseUnaryPrefixOperation1 = (ClickHouseUnaryPrefixOperation)clickHouseSelect0.getLimitClause();
      assertEquals("-", clickHouseUnaryPrefixOperation1.getOperatorRepresentation());
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      clickHouseSelect0.setJoinClauses((List<ClickHouseExpression.ClickHouseJoin>) null);
      List<ClickHouseExpression.ClickHouseJoin> list0 = clickHouseSelect0.getJoinClauses();
      assertNull(list0);
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      LinkedList<ClickHouseExpression.ClickHouseJoin> linkedList0 = new LinkedList<ClickHouseExpression.ClickHouseJoin>();
      linkedList0.offerFirst((ClickHouseExpression.ClickHouseJoin) null);
      clickHouseSelect0.setJoinClauses(linkedList0);
      List<ClickHouseExpression.ClickHouseJoin> list0 = clickHouseSelect0.getJoinClauses();
      assertEquals(1, list0.size());
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      ClickHouseExpression.ClickHouseExist clickHouseExpression_ClickHouseExist0 = new ClickHouseExpression.ClickHouseExist(clickHouseSelect0);
      clickHouseSelect0.setHavingClause((ClickHouseExpression) clickHouseExpression_ClickHouseExist0);
      ClickHouseExpression clickHouseExpression0 = clickHouseSelect0.getHavingClause();
      assertSame(clickHouseExpression0, clickHouseExpression_ClickHouseExist0);
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      clickHouseSelect0.setGroupByClause((List<ClickHouseExpression>) null);
      List<ClickHouseExpression> list0 = clickHouseSelect0.getGroupByClause();
      assertNull(list0);
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      LinkedList<ClickHouseExpression> linkedList0 = new LinkedList<ClickHouseExpression>();
      clickHouseSelect0.setGroupByClause(linkedList0);
      List<ClickHouseExpression> list0 = clickHouseSelect0.getGroupByClause();
      assertTrue(list0.isEmpty());
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      LinkedList<ClickHouseExpression> linkedList0 = new LinkedList<ClickHouseExpression>();
      linkedList0.add((ClickHouseExpression) clickHouseSelect0);
      clickHouseSelect0.setGroupByClause(linkedList0);
      List<ClickHouseExpression> list0 = clickHouseSelect0.getGroupByClause();
      assertFalse(list0.isEmpty());
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      LinkedList<ClickHouseExpression> linkedList0 = new LinkedList<ClickHouseExpression>();
      clickHouseSelect0.setFromList(linkedList0);
      List<ClickHouseExpression> list0 = clickHouseSelect0.getFromList();
      assertTrue(list0.isEmpty());
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      clickHouseSelect0.setFetchColumns((List<ClickHouseExpression>) null);
      List<ClickHouseExpression> list0 = clickHouseSelect0.getFetchColumns();
      assertNull(list0);
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      LinkedList<ClickHouseExpression> linkedList0 = new LinkedList<ClickHouseExpression>();
      clickHouseSelect0.setFetchColumns(linkedList0);
      List<ClickHouseExpression> list0 = clickHouseSelect0.getFetchColumns();
      assertTrue(list0.isEmpty());
  }

  @Test(timeout = 4000)
  public void test13()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      LinkedList<ClickHouseExpression> linkedList0 = new LinkedList<ClickHouseExpression>();
      linkedList0.add((ClickHouseExpression) clickHouseSelect0);
      clickHouseSelect0.setFetchColumns(linkedList0);
      List<ClickHouseExpression> list0 = clickHouseSelect0.getFetchColumns();
      assertEquals(1, list0.size());
  }

  @Test(timeout = 4000)
  public void test14()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      // Undeclared exception!
      try { 
        clickHouseSelect0.setFromClause((ClickHouseExpression) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("java.util.Objects", e);
      }
  }

  @Test(timeout = 4000)
  public void test15()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      clickHouseSelect0.setFetchColumns((List<ClickHouseExpression>) null);
      // Undeclared exception!
      try { 
        clickHouseSelect0.asString();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.common.visitor.ToStringVisitor", e);
      }
  }

  @Test(timeout = 4000)
  public void test16()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      ClickHouseExpression clickHouseExpression0 = clickHouseSelect0.getHavingClause();
      assertNull(clickHouseExpression0);
  }

  @Test(timeout = 4000)
  public void test17()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      clickHouseSelect0.setOrderByClauses((List<ClickHouseExpression>) null);
      // Undeclared exception!
      try { 
        clickHouseSelect0.getHavingClause();
        fail("Expecting exception: AssertionError");
      
      } catch(AssertionError e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test(timeout = 4000)
  public void test18()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      ClickHouseExpression clickHouseExpression0 = clickHouseSelect0.getWhereClause();
      assertNull(clickHouseExpression0);
  }

  @Test(timeout = 4000)
  public void test19()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      ClickHouseSelect.SelectType clickHouseSelect_SelectType0 = ClickHouseSelect.SelectType.DISTINCT;
      clickHouseSelect0.setFromOptions(clickHouseSelect_SelectType0);
      assertEquals(ClickHouseSelect.SelectType.DISTINCT, clickHouseSelect0.getFromOptions());
  }

  @Test(timeout = 4000)
  public void test20()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      ClickHouseSelect.SelectType clickHouseSelect_SelectType0 = clickHouseSelect0.getFromOptions();
      assertEquals(ClickHouseSelect.SelectType.ALL, clickHouseSelect_SelectType0);
  }

  @Test(timeout = 4000)
  public void test21()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      clickHouseSelect0.setOrderByClauses((List<ClickHouseExpression>) null);
      // Undeclared exception!
      try { 
        clickHouseSelect0.asString();
        fail("Expecting exception: AssertionError");
      
      } catch(AssertionError e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test(timeout = 4000)
  public void test22()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      LinkedList<ClickHouseExpression.ClickHouseJoin> linkedList0 = new LinkedList<ClickHouseExpression.ClickHouseJoin>();
      clickHouseSelect0.setJoinClauses(linkedList0);
      List<ClickHouseExpression.ClickHouseJoin> list0 = clickHouseSelect0.getJoinClauses();
      assertEquals(0, list0.size());
  }

  @Test(timeout = 4000)
  public void test23()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      List<ClickHouseExpression> list0 = clickHouseSelect0.getFromList();
      clickHouseSelect0.setGroupByClause(list0);
      assertEquals(ClickHouseSelect.SelectType.ALL, clickHouseSelect0.getFromOptions());
  }

  @Test(timeout = 4000)
  public void test24()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      ClickHouseUnaryPrefixOperation.ClickHouseUnaryPrefixOperator clickHouseUnaryPrefixOperation_ClickHouseUnaryPrefixOperator0 = ClickHouseUnaryPrefixOperation.ClickHouseUnaryPrefixOperator.MINUS;
      ClickHouseUnaryPrefixOperation clickHouseUnaryPrefixOperation0 = new ClickHouseUnaryPrefixOperation(clickHouseSelect0, clickHouseUnaryPrefixOperation_ClickHouseUnaryPrefixOperator0);
      clickHouseSelect0.setFromClause(clickHouseUnaryPrefixOperation0);
      assertFalse(clickHouseUnaryPrefixOperation0.omitBracketsWhenPrinting());
  }

  @Test(timeout = 4000)
  public void test25()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      ClickHouseExpression clickHouseExpression0 = clickHouseSelect0.getOffsetClause();
      assertNull(clickHouseExpression0);
  }

  @Test(timeout = 4000)
  public void test26()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      clickHouseSelect0.setOffsetClause((ClickHouseExpression) clickHouseSelect0);
      ClickHouseSelect clickHouseSelect1 = (ClickHouseSelect)clickHouseSelect0.getOffsetClause();
      assertEquals(ClickHouseSelect.SelectType.ALL, clickHouseSelect1.getFromOptions());
  }

  @Test(timeout = 4000)
  public void test27()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      LinkedList<ClickHouseExpression> linkedList0 = new LinkedList<ClickHouseExpression>();
      clickHouseSelect0.setOrderByClauses(linkedList0);
      linkedList0.add((ClickHouseExpression) clickHouseSelect0);
      List<ClickHouseExpression> list0 = clickHouseSelect0.getOrderByClauses();
      assertEquals(1, list0.size());
  }

  @Test(timeout = 4000)
  public void test28()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      LinkedList<ClickHouseExpression> linkedList0 = new LinkedList<ClickHouseExpression>();
      clickHouseSelect0.setFromList(linkedList0);
      linkedList0.add((ClickHouseExpression) clickHouseSelect0);
      List<ClickHouseExpression> list0 = clickHouseSelect0.getFromList();
      assertEquals(1, list0.size());
  }

  @Test(timeout = 4000)
  public void test29()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      ClickHouseExpression clickHouseExpression0 = clickHouseSelect0.getLimitClause();
      assertNull(clickHouseExpression0);
  }

  @Test(timeout = 4000)
  public void test30()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      clickHouseSelect0.setHavingClause((ClickHouseExpression) clickHouseSelect0);
      // Undeclared exception!
      try { 
        clickHouseSelect0.asString();
        fail("Expecting exception: StackOverflowError");
      
      } catch(StackOverflowError e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test(timeout = 4000)
  public void test31()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      String string0 = clickHouseSelect0.asString();
      assertEquals("(SELECT )", string0);
  }

  @Test(timeout = 4000)
  public void test32()  throws Throwable  {
      ClickHouseSelect clickHouseSelect0 = new ClickHouseSelect();
      ClickHouseSelect.SelectType clickHouseSelect_SelectType0 = ClickHouseSelect.SelectType.ALL;
      clickHouseSelect0.setSelectType(clickHouseSelect_SelectType0);
      assertEquals(ClickHouseSelect.SelectType.ALL, clickHouseSelect0.getFromOptions());
  }
}

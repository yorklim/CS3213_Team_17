/**
 * Scaffolding file used to store all the setups needed to run 
 * tests automatically generated by EvoSuite
 * Mon Apr 14 05:24:24 GMT 2025
 */

package sqlancer.cockroachdb.ast;

import org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.runtime.sandbox.Sandbox.SandboxMode;

@EvoSuiteClassExclude
public class CockroachDBJoin_ESTest_scaffolding {

  @org.junit.Rule
  public org.evosuite.runtime.vnet.NonFunctionalRequirementRule nfr = new org.evosuite.runtime.vnet.NonFunctionalRequirementRule();

  private static final java.util.Properties defaultProperties = (java.util.Properties) java.lang.System.getProperties().clone(); 

  private org.evosuite.runtime.thread.ThreadStopper threadStopper =  new org.evosuite.runtime.thread.ThreadStopper (org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);


  @BeforeClass
  public static void initEvoSuiteFramework() { 
    org.evosuite.runtime.RuntimeSettings.className = "sqlancer.cockroachdb.ast.CockroachDBJoin"; 
    org.evosuite.runtime.GuiSupport.initialize(); 
    org.evosuite.runtime.RuntimeSettings.maxNumberOfThreads = 100; 
    org.evosuite.runtime.RuntimeSettings.maxNumberOfIterationsPerLoop = 10000; 
    org.evosuite.runtime.RuntimeSettings.mockSystemIn = true; 
    org.evosuite.runtime.RuntimeSettings.sandboxMode = org.evosuite.runtime.sandbox.Sandbox.SandboxMode.RECOMMENDED; 
    org.evosuite.runtime.sandbox.Sandbox.initializeSecurityManagerForSUT(); 
    org.evosuite.runtime.classhandling.JDKClassResetter.init();
    setSystemProperties();
    initializeClasses();
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
  } 

  @AfterClass
  public static void clearEvoSuiteFramework(){ 
    Sandbox.resetDefaultSecurityManager(); 
    java.lang.System.setProperties((java.util.Properties) defaultProperties.clone()); 
  } 

  @Before
  public void initTestCase(){ 
    threadStopper.storeCurrentThreads();
    threadStopper.startRecordingTime();
    org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().initHandler(); 
    org.evosuite.runtime.sandbox.Sandbox.goingToExecuteSUTCode(); 
    setSystemProperties(); 
    org.evosuite.runtime.GuiSupport.setHeadless(); 
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
    org.evosuite.runtime.agent.InstrumentingAgent.activate(); 
  } 

  @After
  public void doneWithTestCase(){ 
    threadStopper.killAndJoinClientThreads();
    org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().safeExecuteAddedHooks(); 
    org.evosuite.runtime.classhandling.JDKClassResetter.reset(); 
    resetClasses(); 
    org.evosuite.runtime.sandbox.Sandbox.doneWithExecutingSUTCode(); 
    org.evosuite.runtime.agent.InstrumentingAgent.deactivate(); 
    org.evosuite.runtime.GuiSupport.restoreHeadlessMode(); 
  } 

  public static void setSystemProperties() {
 
    java.lang.System.setProperties((java.util.Properties) defaultProperties.clone()); 
    java.lang.System.setProperty("user.dir", "E:\\Y2S2\\CS3213\\Sqlancer_original"); 
    java.lang.System.setProperty("java.io.tmpdir", "C:\\Users\\hp\\AppData\\Local\\Temp\\"); 
  }

  private static void initializeClasses() {
    org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(CockroachDBJoin_ESTest_scaffolding.class.getClassLoader() ,
      "sqlancer.common.ast.UnaryOperatorNode",
      "sqlancer.cockroachdb.ast.CockroachDBSelect",
      "sqlancer.Randomly$StringGenerationStrategy",
      "sqlancer.cockroachdb.ast.CockroachDBAggregate$CockroachDBAggregateFunction$1",
      "sqlancer.cockroachdb.ast.CockroachDBAggregate$CockroachDBAggregateFunction$3",
      "sqlancer.cockroachdb.ast.CockroachDBAggregate$CockroachDBAggregateFunction$2",
      "sqlancer.common.visitor.UnaryOperation",
      "sqlancer.cockroachdb.ast.CockroachDBConstant$CockroachDBArrayConstant",
      "sqlancer.cockroachdb.ast.CockroachDBAggregate$CockroachDBAggregateFunction$5",
      "sqlancer.cockroachdb.ast.CockroachDBAggregate$CockroachDBAggregateFunction$4",
      "sqlancer.common.schema.AbstractTable",
      "sqlancer.cockroachdb.ast.CockroachDBConstant$CockroachDBIntConstant",
      "sqlancer.common.ast.UnaryNode",
      "sqlancer.common.ast.newast.Select",
      "sqlancer.cockroachdb.ast.CockroachDBAlias",
      "sqlancer.common.schema.AbstractSchema",
      "sqlancer.common.ast.newast.Join",
      "sqlancer.cockroachdb.CockroachDBSchema$CockroachDBColumn",
      "sqlancer.cockroachdb.ast.CockroachDBConstant",
      "sqlancer.cockroachdb.ast.CockroachDBConstant$CockroachDBTextConstant",
      "sqlancer.common.schema.AbstractRelationalTable",
      "sqlancer.cockroachdb.ast.CockroachDBJoin",
      "sqlancer.cockroachdb.ast.CockroachDBUnaryArithmeticOperation$CockroachDBUnaryAritmeticOperator",
      "sqlancer.cockroachdb.ast.CockroachDBAggregate",
      "sqlancer.cockroachdb.ast.CockroachDBConstant$CockroachDBNullConstant",
      "sqlancer.Randomly",
      "sqlancer.cockroachdb.ast.CockroachDBConstant$CockroachDBBitConstant",
      "sqlancer.cockroachdb.CockroachDBSchema",
      "sqlancer.common.schema.AbstractTableColumn",
      "sqlancer.cockroachdb.CockroachDBSchema$CockroachDBTable",
      "sqlancer.common.ast.newast.Expression",
      "sqlancer.common.ast.BinaryOperatorNode$Operator",
      "sqlancer.Randomly$StringGenerationStrategy$4",
      "sqlancer.Randomly$StringGenerationStrategy$3",
      "sqlancer.Randomly$StringGenerationStrategy$2",
      "sqlancer.cockroachdb.ast.CockroachDBConstant$CockroachDBBooleanConstant",
      "sqlancer.Randomly$StringGenerationStrategy$1",
      "sqlancer.cockroachdb.CockroachDBSchema$CockroachDBDataType",
      "sqlancer.cockroachdb.ast.CockroachDBCaseOperation",
      "sqlancer.cockroachdb.ast.CockroachDBAggregate$CockroachDBAggregateFunction",
      "sqlancer.cockroachdb.ast.CockroachDBUnaryArithmeticOperation",
      "sqlancer.cockroachdb.ast.CockroachDBConstant$CockroachDBDoubleConstant",
      "sqlancer.common.visitor.UnaryOperation$OperatorKind",
      "sqlancer.IgnoreMeException",
      "sqlancer.common.ast.SelectBase",
      "sqlancer.cockroachdb.ast.CockroachDBConstant$CockroachDBIntervalConstant",
      "sqlancer.cockroachdb.ast.CockroachDBConstant$CockroachDBTimeRelatedConstant",
      "sqlancer.cockroachdb.ast.CockroachDBJoin$JoinType",
      "sqlancer.cockroachdb.ast.CockroachDBExpression"
    );
  } 

  private static void resetClasses() {
    org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(CockroachDBJoin_ESTest_scaffolding.class.getClassLoader()); 

    org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
      "sqlancer.cockroachdb.ast.CockroachDBJoin",
      "sqlancer.cockroachdb.ast.CockroachDBJoin$JoinType",
      "sqlancer.cockroachdb.ast.CockroachDBConstant",
      "sqlancer.cockroachdb.ast.CockroachDBConstant$CockroachDBDoubleConstant",
      "sqlancer.GlobalState",
      "sqlancer.SQLGlobalState",
      "sqlancer.cockroachdb.CockroachDBProvider$CockroachDBGlobalState",
      "sqlancer.common.gen.TypedExpressionGenerator",
      "sqlancer.cockroachdb.gen.CockroachDBExpressionGenerator",
      "sqlancer.common.ast.SelectBase",
      "sqlancer.cockroachdb.ast.CockroachDBSelect",
      "sqlancer.Randomly$StringGenerationStrategy$4",
      "sqlancer.Randomly$StringGenerationStrategy",
      "sqlancer.Randomly",
      "sqlancer.cockroachdb.CockroachDBSchema$CockroachDBCompositeDataType",
      "sqlancer.common.ast.UnaryNode",
      "sqlancer.cockroachdb.ast.CockroachDBTypeAnnotation",
      "sqlancer.common.ast.UnaryOperatorNode",
      "sqlancer.cockroachdb.ast.CockroachDBUnaryPostfixOperation",
      "sqlancer.cockroachdb.ast.CockroachDBOrderingTerm",
      "sqlancer.cockroachdb.ast.CockroachDBConstant$CockroachDBIntervalConstant",
      "sqlancer.cockroachdb.ast.CockroachDBCast",
      "sqlancer.common.ast.BinaryNode",
      "sqlancer.cockroachdb.ast.CockroachDBConcatOperation",
      "sqlancer.cockroachdb.ast.CockroachDBRegexOperation",
      "sqlancer.cockroachdb.ast.CockroachDBConstant$CockroachDBBitConstant",
      "sqlancer.cockroachdb.ast.CockroachDBConstant$CockroachDBTimeRelatedConstant",
      "sqlancer.cockroachdb.ast.CockroachDBConstant$CockroachDBBooleanConstant",
      "sqlancer.cockroachdb.ast.CockroachDBNotOperation",
      "sqlancer.cockroachdb.ast.CockroachDBAggregate",
      "sqlancer.cockroachdb.ast.CockroachDBConstant$CockroachDBNullConstant",
      "sqlancer.common.ast.BinaryOperatorNode",
      "sqlancer.cockroachdb.ast.CockroachDBBinaryArithmeticOperation",
      "sqlancer.common.query.Query",
      "sqlancer.common.query.SQLQueryAdapter",
      "sqlancer.common.query.ExpectedErrors",
      "sqlancer.cockroachdb.ast.CockroachDBCaseOperation",
      "sqlancer.cockroachdb.ast.CockroachDBTableReference",
      "sqlancer.common.schema.TableIndex",
      "sqlancer.cockroachdb.ast.CockroachDBIndexReference",
      "sqlancer.cockroachdb.ast.CockroachDBBinaryLogicalOperation",
      "sqlancer.common.visitor.NodeVisitor",
      "sqlancer.common.visitor.ToStringVisitor",
      "sqlancer.cockroachdb.CockroachDBToStringVisitor",
      "sqlancer.cockroachdb.ast.CockroachDBConstant$CockroachDBTextConstant",
      "sqlancer.cockroachdb.ast.CockroachDBAlias",
      "sqlancer.common.visitor.UnaryOperation$OperatorKind",
      "sqlancer.common.schema.AbstractTable",
      "sqlancer.common.schema.AbstractRelationalTable",
      "sqlancer.cockroachdb.CockroachDBSchema$CockroachDBTable",
      "sqlancer.cockroachdb.ast.CockroachDBBinaryComparisonOperator",
      "sqlancer.cockroachdb.ast.CockroachDBBetweenOperation",
      "sqlancer.cockroachdb.ast.CockroachDBUnaryArithmeticOperation",
      "sqlancer.common.schema.AbstractTables",
      "sqlancer.cockroachdb.ast.CockroachDBConstant$CockroachDBIntConstant",
      "sqlancer.common.schema.AbstractTableColumn",
      "sqlancer.cockroachdb.CockroachDBSchema$CockroachDBColumn",
      "sqlancer.cockroachdb.ast.CockroachDBColumnReference",
      "sqlancer.cockroachdb.ast.CockroachDBMultiValuedComparison",
      "sqlancer.common.query.ExpectedErrors$ExpectedErrorsBuilder",
      "sqlancer.cockroachdb.ast.CockroachDBConstant$CockroachDBArrayConstant",
      "sqlancer.cockroachdb.ast.CockroachDBCollate",
      "sqlancer.cockroachdb.ast.CockroachDBInOperation",
      "sqlancer.cockroachdb.ast.CockroachDBFunctionCall",
      "sqlancer.cockroachdb.CockroachDBCommon",
      "sqlancer.postgres.PostgresGlobalState",
      "sqlancer.citus.CitusGlobalState",
      "sqlancer.materialize.MaterializeGlobalState",
      "sqlancer.common.query.SQLQueryResultCheckAdapter",
      "sqlancer.clickhouse.ClickHouseProvider$ClickHouseGlobalState",
      "sqlancer.cockroachdb.CockroachDBBugs"
    );
  }
}

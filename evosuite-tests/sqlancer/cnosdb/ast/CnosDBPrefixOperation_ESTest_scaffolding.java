/**
 * Scaffolding file used to store all the setups needed to run 
 * tests automatically generated by EvoSuite
 * Sun Apr 13 14:14:50 GMT 2025
 */

package sqlancer.cnosdb.ast;

import org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.runtime.sandbox.Sandbox.SandboxMode;

@EvoSuiteClassExclude
public class CnosDBPrefixOperation_ESTest_scaffolding {

  @org.junit.Rule
  public org.evosuite.runtime.vnet.NonFunctionalRequirementRule nfr = new org.evosuite.runtime.vnet.NonFunctionalRequirementRule();

  private static final java.util.Properties defaultProperties = (java.util.Properties) java.lang.System.getProperties().clone(); 

  private org.evosuite.runtime.thread.ThreadStopper threadStopper =  new org.evosuite.runtime.thread.ThreadStopper (org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);


  @BeforeClass
  public static void initEvoSuiteFramework() { 
    org.evosuite.runtime.RuntimeSettings.className = "sqlancer.cnosdb.ast.CnosDBPrefixOperation"; 
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
    org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(CnosDBPrefixOperation_ESTest_scaffolding.class.getClassLoader() ,
      "sqlancer.cnosdb.ast.CnosDBPrefixOperation",
      "sqlancer.cnosdb.ast.CnosDBConstant$StringConstant",
      "sqlancer.cnosdb.ast.CnosDBConstant",
      "sqlancer.cnosdb.ast.CnosDBExpression",
      "sqlancer.cnosdb.ast.CnosDBSelect$CnosDBSubquery",
      "sqlancer.cnosdb.ast.CnosDBSelect$SelectType",
      "sqlancer.cnosdb.ast.CnosDBPrefixOperation$PrefixOperator$2",
      "sqlancer.cnosdb.ast.CnosDBConstant$TimeStampConstant",
      "sqlancer.cnosdb.ast.CnosDBPrefixOperation$PrefixOperator$3",
      "sqlancer.common.schema.AbstractTableColumn",
      "sqlancer.cnosdb.ast.CnosDBConstant$DoubleConstant",
      "sqlancer.cnosdb.ast.CnosDBPrefixOperation$PrefixOperator$1",
      "sqlancer.cnosdb.CnosDBSchema$CnosDBColumn",
      "sqlancer.cnosdb.CnosDBSchema",
      "sqlancer.common.ast.BinaryOperatorNode$Operator",
      "sqlancer.cnosdb.ast.CnosDBSelect",
      "sqlancer.cnosdb.ast.CnosDBPrefixOperation$PrefixOperator",
      "sqlancer.common.schema.AbstractSchema",
      "sqlancer.cnosdb.ast.CnosDBConstant$BooleanConstant",
      "sqlancer.IgnoreMeException",
      "sqlancer.common.ast.SelectBase",
      "sqlancer.cnosdb.ast.CnosDBConstant$CnosDBNullConstant",
      "sqlancer.cnosdb.ast.CnosDBConstant$IntConstant",
      "sqlancer.cnosdb.CnosDBSchema$CnosDBDataType",
      "sqlancer.cnosdb.ast.CnosDBSelect$CnosDBFromTable"
    );
  } 

  private static void resetClasses() {
    org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(CnosDBPrefixOperation_ESTest_scaffolding.class.getClassLoader()); 

    org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
      "sqlancer.cnosdb.ast.CnosDBPrefixOperation",
      "sqlancer.cnosdb.CnosDBSchema$CnosDBDataType",
      "sqlancer.cnosdb.ast.CnosDBPrefixOperation$PrefixOperator",
      "sqlancer.cnosdb.ast.CnosDBConstant",
      "sqlancer.cnosdb.ast.CnosDBConstant$StringConstant",
      "sqlancer.cnosdb.ast.CnosDBConstant$CnosDBNullConstant",
      "sqlancer.cnosdb.ast.CnosDBConstant$BooleanConstant",
      "sqlancer.cnosdb.ast.CnosDBConstant$TimeStampConstant",
      "sqlancer.cnosdb.ast.CnosDBConstant$IntConstant",
      "sqlancer.Randomly$StringGenerationStrategy$4",
      "sqlancer.Randomly$StringGenerationStrategy",
      "sqlancer.Randomly",
      "sqlancer.GlobalState",
      "sqlancer.cnosdb.CnosDBGlobalState",
      "sqlancer.cnosdb.gen.CnosDBExpressionGenerator",
      "sqlancer.cnosdb.ast.CnosDBConstant$DoubleConstant",
      "sqlancer.cnosdb.ast.CnosDBJoin",
      "sqlancer.common.schema.AbstractTable",
      "sqlancer.common.schema.AbstractRelationalTable",
      "sqlancer.common.schema.AbstractTableColumn",
      "sqlancer.cnosdb.CnosDBSchema$CnosDBColumn",
      "sqlancer.cnosdb.ast.CnosDBColumnValue",
      "sqlancer.cnosdb.ast.CnosDBPostfixText",
      "sqlancer.cnosdb.CnosDBCompoundDataType",
      "sqlancer.cnosdb.ast.CnosDBCastOperation",
      "sqlancer.common.ast.SelectBase",
      "sqlancer.cnosdb.ast.CnosDBSelect",
      "sqlancer.cnosdb.ast.CnosDBSelect$CnosDBSubquery",
      "sqlancer.common.ast.BinaryNode",
      "sqlancer.common.ast.BinaryOperatorNode",
      "sqlancer.cnosdb.ast.CnosDBBinaryComparisonOperation",
      "sqlancer.cnosdb.ast.CnosDBLikeOperation",
      "sqlancer.common.ast.FunctionNode",
      "sqlancer.cnosdb.ast.CnosDBAggregate",
      "sqlancer.cnosdb.ast.CnosDBAlias",
      "sqlancer.cnosdb.ast.CnosDBPostfixOperation",
      "sqlancer.cnosdb.CnosDBSchema$CnosDBTable",
      "sqlancer.cnosdb.ast.CnosDBSimilarTo",
      "sqlancer.SQLGlobalState",
      "sqlancer.clickhouse.ClickHouseProvider$ClickHouseGlobalState",
      "sqlancer.cnosdb.ast.CnosDBOrderByTerm",
      "sqlancer.common.query.Query",
      "sqlancer.cnosdb.ast.CnosDBBinaryArithmeticOperation",
      "sqlancer.cnosdb.ast.CnosDBBetweenOperation",
      "sqlancer.tidb.TiDBProvider$TiDBGlobalState",
      "sqlancer.cnosdb.ast.CnosDBBinaryLogicalOperation",
      "sqlancer.cnosdb.ast.CnosDBFunction",
      "sqlancer.IgnoreMeException",
      "sqlancer.cnosdb.ast.CnosDBInOperation",
      "sqlancer.cnosdb.ast.CnosDBConcatOperation",
      "sqlancer.cnosdb.ast.CnosDBSelect$CnosDBFromTable",
      "sqlancer.datafusion.DataFusionProvider$DataFusionGlobalState",
      "sqlancer.datafusion.DataFusionUtil$DataFusionInstanceID",
      "sqlancer.datafusion.DataFusionUtil$DataFusionLogger",
      "sqlancer.cnosdb.CnosDBSchema$CnosDBTimeColumn",
      "sqlancer.oceanbase.OceanBaseGlobalState",
      "sqlancer.common.query.ExpectedErrors",
      "sqlancer.common.query.ExpectedErrors$ExpectedErrorsBuilder",
      "sqlancer.postgres.PostgresGlobalState",
      "sqlancer.citus.CitusGlobalState",
      "sqlancer.common.query.SQLQueryAdapter",
      "sqlancer.common.visitor.UnaryOperation$OperatorKind",
      "sqlancer.cnosdb.ast.CnosDBSelect$SelectType"
    );
  }
}

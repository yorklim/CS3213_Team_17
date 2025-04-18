/**
 * Scaffolding file used to store all the setups needed to run 
 * tests automatically generated by EvoSuite
 * Sun Apr 13 14:27:41 GMT 2025
 */

package sqlancer.cnosdb.oracle.tlp;

import org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.runtime.sandbox.Sandbox.SandboxMode;

@EvoSuiteClassExclude
public class CnosDBTLPWhereOracle_ESTest_scaffolding {

  @org.junit.Rule
  public org.evosuite.runtime.vnet.NonFunctionalRequirementRule nfr = new org.evosuite.runtime.vnet.NonFunctionalRequirementRule();

  private static final java.util.Properties defaultProperties = (java.util.Properties) java.lang.System.getProperties().clone(); 

  private org.evosuite.runtime.thread.ThreadStopper threadStopper =  new org.evosuite.runtime.thread.ThreadStopper (org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);


  @BeforeClass
  public static void initEvoSuiteFramework() { 
    org.evosuite.runtime.RuntimeSettings.className = "sqlancer.cnosdb.oracle.tlp.CnosDBTLPWhereOracle"; 
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
    org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(CnosDBTLPWhereOracle_ESTest_scaffolding.class.getClassLoader() ,
      "sqlancer.cnosdb.ast.CnosDBPrefixOperation",
      "sqlancer.cnosdb.ast.CnosDBSelect$SelectType",
      "sqlancer.common.gen.ExpressionGenerator",
      "sqlancer.cnosdb.ast.CnosDBBetweenOperation",
      "sqlancer.Randomly$StringGenerationStrategy",
      "sqlancer.cnosdb.oracle.tlp.CnosDBTLPBase",
      "sqlancer.cnosdb.ast.CnosDBSimilarTo",
      "sqlancer.Main$StateLogger",
      "sqlancer.cnosdb.CnosDBSchema$CnosDBColumn",
      "sqlancer.common.visitor.UnaryOperation",
      "sqlancer.common.schema.AbstractTable",
      "sqlancer.common.query.ExpectedErrors",
      "sqlancer.GlobalState",
      "sqlancer.cnosdb.oracle.tlp.CnosDBTLPWhereOracle",
      "sqlancer.cnosdb.ast.CnosDBSelect",
      "sqlancer.common.schema.AbstractSchema",
      "sqlancer.common.ast.FunctionNode",
      "sqlancer.cnosdb.ast.CnosDBAggregate",
      "sqlancer.common.visitor.ToStringVisitor",
      "sqlancer.cnosdb.ast.CnosDBCastOperation",
      "sqlancer.cnosdb.ast.CnosDBBinaryLogicalOperation",
      "sqlancer.Main$QueryManager",
      "sqlancer.cnosdb.CnosDBGlobalState",
      "sqlancer.common.visitor.NodeVisitor",
      "sqlancer.common.query.Query",
      "sqlancer.cnosdb.CnosDBSchema$CnosDBDataType",
      "sqlancer.cnosdb.ast.CnosDBColumnValue",
      "sqlancer.cnosdb.ast.CnosDBSelect$CnosDBFromTable",
      "sqlancer.SQLancerDBConnection",
      "sqlancer.MainOptions",
      "sqlancer.Randomly",
      "sqlancer.cnosdb.ast.CnosDBConstant",
      "sqlancer.cnosdb.ast.CnosDBExpression",
      "sqlancer.cnosdb.gen.CnosDBExpressionGenerator",
      "sqlancer.cnosdb.ast.CnosDBSelect$CnosDBSubquery",
      "sqlancer.common.ast.BinaryNode",
      "sqlancer.common.ast.BinaryOperatorNode",
      "sqlancer.common.visitor.BinaryOperation",
      "sqlancer.common.oracle.TestOracle",
      "sqlancer.cnosdb.ast.CnosDBFunction",
      "sqlancer.common.schema.AbstractTableColumn",
      "sqlancer.common.query.SQLancerResultSet",
      "sqlancer.cnosdb.CnosDBSchema$CnosDBTable",
      "sqlancer.cnosdb.CnosDBSchema",
      "sqlancer.Randomly$StringGenerationStrategy$4",
      "sqlancer.cnosdb.CnosDBVisitor",
      "sqlancer.StateToReproduce",
      "sqlancer.Randomly$StringGenerationStrategy$3",
      "sqlancer.Randomly$StringGenerationStrategy$2",
      "sqlancer.cnosdb.CnosDBSchema$CnosDBTimeColumn",
      "sqlancer.cnosdb.CnosDBSchema$CnosDBTagColumn",
      "sqlancer.Randomly$StringGenerationStrategy$1",
      "sqlancer.cnosdb.CnosDBSchema$CnosDBTables",
      "sqlancer.cnosdb.CnosDBToStringVisitor",
      "sqlancer.cnosdb.ast.CnosDBOrderByTerm",
      "sqlancer.cnosdb.ast.CnosDBPostfixText",
      "sqlancer.cnosdb.client.CnosDBConnection",
      "sqlancer.cnosdb.ast.CnosDBPostfixOperation",
      "sqlancer.DBMSSpecificOptions",
      "sqlancer.cnosdb.ast.CnosDBLikeOperation",
      "sqlancer.common.log.Loggable",
      "sqlancer.IgnoreMeException",
      "sqlancer.common.ast.SelectBase",
      "sqlancer.cnosdb.ast.CnosDBInOperation",
      "sqlancer.common.schema.AbstractTables",
      "sqlancer.cnosdb.CnosDBSchema$CnosDBFieldColumn",
      "sqlancer.common.oracle.TernaryLogicPartitioningOracleBase",
      "sqlancer.Reproducer"
    );
  } 

  private static void resetClasses() {
    org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(CnosDBTLPWhereOracle_ESTest_scaffolding.class.getClassLoader()); 

    org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
      "sqlancer.common.oracle.TernaryLogicPartitioningOracleBase",
      "sqlancer.cnosdb.oracle.tlp.CnosDBTLPBase",
      "sqlancer.cnosdb.oracle.tlp.CnosDBTLPWhereOracle",
      "sqlancer.Randomly$StringGenerationStrategy$4",
      "sqlancer.Randomly$StringGenerationStrategy",
      "sqlancer.Randomly",
      "sqlancer.GlobalState",
      "sqlancer.cnosdb.CnosDBGlobalState",
      "sqlancer.common.query.ExpectedErrors",
      "sqlancer.common.visitor.NodeVisitor",
      "sqlancer.common.visitor.ToStringVisitor",
      "sqlancer.cnosdb.CnosDBToStringVisitor",
      "sqlancer.common.query.Query",
      "sqlancer.cnosdb.gen.CnosDBExpressionGenerator",
      "sqlancer.common.schema.AbstractSchema",
      "sqlancer.cnosdb.CnosDBSchema",
      "sqlancer.common.ast.SelectBase",
      "sqlancer.cnosdb.ast.CnosDBSelect",
      "sqlancer.common.schema.AbstractTableColumn",
      "sqlancer.cnosdb.CnosDBSchema$CnosDBColumn",
      "sqlancer.common.schema.AbstractTable",
      "sqlancer.cnosdb.CnosDBSchema$CnosDBTable",
      "sqlancer.cnosdb.ast.CnosDBConstant",
      "sqlancer.cnosdb.ast.CnosDBConstant$DoubleConstant",
      "sqlancer.cnosdb.ast.CnosDBColumnValue",
      "sqlancer.common.ast.BinaryNode",
      "sqlancer.common.ast.BinaryOperatorNode",
      "sqlancer.cnosdb.ast.CnosDBBinaryArithmeticOperation",
      "sqlancer.cnosdb.oracle.CnosDBNoRECBase",
      "sqlancer.cnosdb.oracle.CnosDBNoRECOracle",
      "sqlancer.cnosdb.ast.CnosDBConstant$CnosDBNullConstant",
      "sqlancer.cnosdb.ast.CnosDBConcatOperation",
      "sqlancer.cnosdb.ast.CnosDBJoin",
      "sqlancer.cnosdb.CnosDBSchema$CnosDBTimeColumn",
      "sqlancer.common.schema.AbstractTables",
      "sqlancer.cnosdb.CnosDBSchema$CnosDBTables",
      "sqlancer.cnosdb.client.CnosDBConnection",
      "sqlancer.cnosdb.ast.CnosDBConstant$IntConstant",
      "sqlancer.cnosdb.ast.CnosDBConstant$StringConstant",
      "sqlancer.common.schema.AbstractRelationalTable",
      "sqlancer.cnosdb.ast.CnosDBConstant$TimeStampConstant",
      "sqlancer.cnosdb.ast.CnosDBSelect$CnosDBFromTable",
      "sqlancer.cnosdb.ast.CnosDBConstant$BooleanConstant",
      "sqlancer.cnosdb.ast.CnosDBFunction",
      "sqlancer.cnosdb.ast.CnosDBLikeOperation",
      "sqlancer.cnosdb.ast.CnosDBPrefixOperation",
      "sqlancer.cnosdb.ast.CnosDBSelect$SelectType"
    );
  }
}

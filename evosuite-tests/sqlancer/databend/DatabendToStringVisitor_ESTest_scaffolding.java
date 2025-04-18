/**
 * Scaffolding file used to store all the setups needed to run 
 * tests automatically generated by EvoSuite
 * Mon Apr 14 08:52:38 GMT 2025
 */

package sqlancer.databend;

import org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.runtime.sandbox.Sandbox.SandboxMode;

@EvoSuiteClassExclude
public class DatabendToStringVisitor_ESTest_scaffolding {

  @org.junit.Rule
  public org.evosuite.runtime.vnet.NonFunctionalRequirementRule nfr = new org.evosuite.runtime.vnet.NonFunctionalRequirementRule();

  private static final java.util.Properties defaultProperties = (java.util.Properties) java.lang.System.getProperties().clone(); 

  private org.evosuite.runtime.thread.ThreadStopper threadStopper =  new org.evosuite.runtime.thread.ThreadStopper (org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);


  @BeforeClass
  public static void initEvoSuiteFramework() { 
    org.evosuite.runtime.RuntimeSettings.className = "sqlancer.databend.DatabendToStringVisitor"; 
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
    org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(DatabendToStringVisitor_ESTest_scaffolding.class.getClassLoader() ,
      "sqlancer.databend.DatabendSchema$DatabendTable",
      "sqlancer.common.ast.newast.NewOrderingTerm",
      "sqlancer.common.ast.newast.NewPostfixTextNode",
      "sqlancer.databend.DatabendProvider$DatabendGlobalState",
      "sqlancer.SQLProviderAdapter",
      "sqlancer.databend.DatabendProvider",
      "sqlancer.common.ast.newast.NewFunctionNode",
      "sqlancer.databend.ast.DatabendConstant$DatabendFloatConstant",
      "sqlancer.common.ast.newast.NewCaseOperatorNode",
      "sqlancer.databend.DatabendSchema",
      "sqlancer.common.ast.newast.NewTernaryNode",
      "sqlancer.common.schema.AbstractTable",
      "sqlancer.databend.DatabendSchema$DatabendCompositeDataType",
      "sqlancer.GlobalState",
      "sqlancer.common.ast.newast.Select",
      "sqlancer.databend.ast.DatabendConstant$DatabendDateConstant",
      "sqlancer.databend.DatabendSchema$DatabendRowValue",
      "sqlancer.common.schema.AbstractSchema",
      "sqlancer.common.ast.newast.Join",
      "sqlancer.databend.ast.DatabendCastOperation",
      "sqlancer.databend.ast.DatabendConstant$DatabendStringConstant",
      "sqlancer.DatabaseProvider",
      "sqlancer.databend.ast.DatabendConstant$DatabendBooleanConstant",
      "sqlancer.common.schema.AbstractRelationalTable",
      "sqlancer.databend.ast.DatabendUnaryPrefixOperation$DatabendUnaryPrefixOperator$3",
      "sqlancer.databend.ast.DatabendSelect",
      "sqlancer.databend.ast.DatabendUnaryPrefixOperation$DatabendUnaryPrefixOperator$2",
      "sqlancer.databend.ast.DatabendUnaryPrefixOperation$DatabendUnaryPrefixOperator$1",
      "sqlancer.ProviderAdapter",
      "sqlancer.databend.ast.DatabendTableReference",
      "sqlancer.common.ast.newast.NewUnaryPostfixOperatorNode",
      "sqlancer.common.ast.newast.ColumnReferenceNode",
      "sqlancer.databend.ast.DatabendJoin$OuterType",
      "sqlancer.common.ast.BinaryNode",
      "sqlancer.common.ast.BinaryOperatorNode",
      "sqlancer.common.visitor.BinaryOperation",
      "sqlancer.common.ast.newast.NewToStringVisitor",
      "sqlancer.common.schema.AbstractTableColumn",
      "sqlancer.common.ast.newast.NewAliasNode",
      "sqlancer.databend.ast.DatabendConstant",
      "sqlancer.databend.ast.DatabendConstant$DatabendIntConstant",
      "sqlancer.databend.ast.DatabendJoin$JoinType",
      "sqlancer.common.ast.newast.Expression",
      "sqlancer.SQLGlobalState",
      "sqlancer.common.ast.BinaryOperatorNode$Operator",
      "sqlancer.common.ast.newast.NewBinaryOperatorNode",
      "sqlancer.databend.ast.DatabendJoin",
      "sqlancer.databend.DatabendSchema$DatabendColumn",
      "sqlancer.databend.ast.DatabendUnaryPrefixOperation$DatabendUnaryPrefixOperator",
      "sqlancer.databend.DatabendSchema$DatabendDataType",
      "sqlancer.common.ast.newast.NewBetweenOperatorNode",
      "sqlancer.common.ast.newast.NewInOperatorNode",
      "sqlancer.common.ast.newast.NewUnaryPrefixOperatorNode",
      "sqlancer.IgnoreMeException",
      "sqlancer.databend.ast.DatabendConstant$DatabendNullConstant",
      "sqlancer.common.ast.SelectBase",
      "sqlancer.databend.ast.DatabendConstant$DatabendTimestampConstant",
      "sqlancer.common.schema.AbstractRowValue",
      "sqlancer.common.schema.TableIndex",
      "sqlancer.databend.ast.DatabendExpression",
      "sqlancer.databend.ast.DatabendCastOperation$1",
      "sqlancer.databend.DatabendToStringVisitor",
      "sqlancer.databend.ast.DatabendUnaryPrefixOperation",
      "sqlancer.common.ast.newast.TableReferenceNode"
    );
  } 

  private static void resetClasses() {
    org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(DatabendToStringVisitor_ESTest_scaffolding.class.getClassLoader()); 

    org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
      "sqlancer.common.ast.newast.NewToStringVisitor",
      "sqlancer.databend.DatabendToStringVisitor",
      "sqlancer.databend.ast.DatabendConstant",
      "sqlancer.databend.ast.DatabendConstant$DatabendNullConstant",
      "sqlancer.databend.ast.DatabendConstant$DatabendBooleanConstant",
      "sqlancer.common.ast.newast.NewUnaryPrefixOperatorNode",
      "sqlancer.databend.ast.DatabendUnaryPrefixOperation",
      "sqlancer.common.ast.newast.NewBinaryOperatorNode",
      "sqlancer.databend.ast.DatabendLikeOperation",
      "sqlancer.common.ast.SelectBase",
      "sqlancer.databend.ast.DatabendSelect",
      "sqlancer.common.ast.newast.NewFunctionNode",
      "sqlancer.databend.ast.DatabendFunctionOperation",
      "sqlancer.databend.ast.DatabendBinaryArithmeticOperation",
      "sqlancer.databend.ast.DatabendConstant$DatabendTimestampConstant",
      "sqlancer.databend.ast.DatabendBinaryLogicalOperation",
      "sqlancer.common.ast.newast.NewUnaryPostfixOperatorNode",
      "sqlancer.databend.ast.DatabendUnaryPostfixOperation",
      "sqlancer.databend.ast.DatabendConstant$DatabendStringConstant",
      "sqlancer.common.ast.newast.NewAliasNode",
      "sqlancer.databend.ast.DatabendAlias",
      "sqlancer.common.ast.newast.NewInOperatorNode",
      "sqlancer.databend.ast.DatabendInOperation",
      "sqlancer.Randomly$StringGenerationStrategy$4",
      "sqlancer.Randomly$StringGenerationStrategy",
      "sqlancer.Randomly",
      "sqlancer.databend.ast.DatabendConstant$DatabendDateConstant",
      "sqlancer.common.schema.AbstractTable",
      "sqlancer.common.schema.AbstractRelationalTable",
      "sqlancer.databend.DatabendSchema$DatabendTable",
      "sqlancer.databend.ast.DatabendAggregateOperation",
      "sqlancer.common.ast.newast.NewTernaryNode",
      "sqlancer.databend.ast.DatabendConstant$DatabendFloatConstant",
      "sqlancer.databend.ast.DatabendConstant$DatabendIntConstant",
      "sqlancer.common.ast.newast.TableReferenceNode",
      "sqlancer.databend.ast.DatabendTableReference",
      "sqlancer.databend.ast.DatabendJoin",
      "sqlancer.databend.ast.DatabendJoin$JoinType",
      "sqlancer.common.ast.newast.NewPostfixTextNode",
      "sqlancer.common.ast.newast.NewBetweenOperatorNode",
      "sqlancer.databend.ast.DatabendBetweenOperation",
      "sqlancer.databend.ast.DatabendBinaryComparisonOperation",
      "sqlancer.databend.ast.DatabendPostFixText",
      "sqlancer.databend.DatabendSchema$DatabendCompositeDataType",
      "sqlancer.databend.ast.DatabendCastOperation",
      "sqlancer.databend.ast.DatabendCastOperation$1",
      "sqlancer.h2.H2ExpressionGenerator$H2BinaryArithmeticOperator",
      "sqlancer.GlobalState",
      "sqlancer.SQLGlobalState",
      "sqlancer.databend.DatabendProvider$DatabendGlobalState",
      "sqlancer.common.query.Query",
      "sqlancer.common.query.SQLQueryAdapter",
      "sqlancer.common.query.ExpectedErrors",
      "sqlancer.IgnoreMeException",
      "sqlancer.common.ast.newast.NewOrderingTerm",
      "sqlancer.databend.ast.DatabendOrderByTerm",
      "sqlancer.common.ast.newast.NewCaseOperatorNode",
      "sqlancer.common.schema.AbstractTableColumn",
      "sqlancer.databend.DatabendSchema$DatabendColumn",
      "sqlancer.common.ast.newast.ColumnReferenceNode",
      "sqlancer.databend.ast.DatabendColumnValue",
      "sqlancer.h2.H2ExpressionGenerator$H2BinaryComparisonOperator",
      "sqlancer.h2.H2ExpressionGenerator$H2UnaryPostfixOperator",
      "sqlancer.h2.H2ExpressionGenerator$H2UnaryPrefixOperator",
      "sqlancer.databend.ast.DatabendBinaryOperation",
      "sqlancer.databend.ast.DatabendColumnReference",
      "sqlancer.h2.H2ExpressionGenerator$H2BinaryLogicalOperator",
      "sqlancer.common.query.SQLQueryResultCheckAdapter"
    );
  }
}

/**
 * Scaffolding file used to store all the setups needed to run 
 * tests automatically generated by EvoSuite
 * Mon Apr 14 08:36:31 GMT 2025
 */

package sqlancer.databend.ast;

import org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.runtime.sandbox.Sandbox.SandboxMode;

@EvoSuiteClassExclude
public class DatabendPostFixText_ESTest_scaffolding {

  @org.junit.Rule
  public org.evosuite.runtime.vnet.NonFunctionalRequirementRule nfr = new org.evosuite.runtime.vnet.NonFunctionalRequirementRule();

  private static final java.util.Properties defaultProperties = (java.util.Properties) java.lang.System.getProperties().clone(); 

  private org.evosuite.runtime.thread.ThreadStopper threadStopper =  new org.evosuite.runtime.thread.ThreadStopper (org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);


  @BeforeClass
  public static void initEvoSuiteFramework() { 
    org.evosuite.runtime.RuntimeSettings.className = "sqlancer.databend.ast.DatabendPostFixText"; 
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
    org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(DatabendPostFixText_ESTest_scaffolding.class.getClassLoader() ,
      "sqlancer.databend.ast.DatabendConstant$DatabendDateConstant",
      "sqlancer.databend.ast.DatabendPostFixText",
      "sqlancer.common.schema.AbstractSchema",
      "sqlancer.common.ast.newast.NewPostfixTextNode",
      "sqlancer.databend.ast.DatabendConstant$DatabendStringConstant",
      "sqlancer.databend.ast.DatabendConstant$DatabendFloatConstant",
      "sqlancer.databend.DatabendSchema$DatabendDataType",
      "sqlancer.databend.ast.DatabendConstant$DatabendBooleanConstant",
      "sqlancer.databend.ast.DatabendConstant",
      "sqlancer.databend.ast.DatabendConstant$DatabendNullConstant",
      "sqlancer.databend.DatabendSchema",
      "sqlancer.databend.ast.DatabendConstant$DatabendTimestampConstant",
      "sqlancer.databend.ast.DatabendConstant$DatabendIntConstant",
      "sqlancer.databend.ast.DatabendExpression",
      "sqlancer.common.ast.newast.Expression"
    );
  } 

  private static void resetClasses() {
    org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(DatabendPostFixText_ESTest_scaffolding.class.getClassLoader()); 

    org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
      "sqlancer.common.ast.newast.NewPostfixTextNode",
      "sqlancer.databend.ast.DatabendPostFixText",
      "sqlancer.databend.ast.DatabendConstant",
      "sqlancer.databend.ast.DatabendConstant$DatabendFloatConstant",
      "sqlancer.common.ast.newast.NewBinaryOperatorNode",
      "sqlancer.databend.ast.DatabendBinaryLogicalOperation",
      "sqlancer.databend.ast.DatabendConstant$DatabendStringConstant",
      "sqlancer.common.ast.newast.NewOrderingTerm",
      "sqlancer.databend.ast.DatabendOrderByTerm",
      "sqlancer.databend.ast.DatabendConstant$DatabendDateConstant",
      "sqlancer.common.ast.newast.NewUnaryPrefixOperatorNode",
      "sqlancer.databend.ast.DatabendUnaryPrefixOperation",
      "sqlancer.databend.ast.DatabendConstant$DatabendNullConstant",
      "sqlancer.databend.ast.DatabendConstant$DatabendTimestampConstant",
      "sqlancer.databend.ast.DatabendConstant$DatabendBooleanConstant",
      "sqlancer.databend.ast.DatabendConstant$DatabendIntConstant",
      "sqlancer.common.ast.SelectBase",
      "sqlancer.databend.ast.DatabendSelect",
      "sqlancer.databend.ast.DatabendBinaryComparisonOperation",
      "sqlancer.databend.ast.DatabendLikeOperation",
      "sqlancer.databend.ast.DatabendBinaryArithmeticOperation",
      "sqlancer.common.ast.newast.NewBetweenOperatorNode",
      "sqlancer.databend.ast.DatabendBetweenOperation",
      "sqlancer.common.ast.newast.NewUnaryPostfixOperatorNode",
      "sqlancer.databend.ast.DatabendUnaryPostfixOperation",
      "sqlancer.common.ast.newast.NewInOperatorNode",
      "sqlancer.databend.ast.DatabendInOperation",
      "sqlancer.common.ast.newast.NewAliasNode",
      "sqlancer.databend.ast.DatabendAlias",
      "sqlancer.common.ast.newast.NewFunctionNode",
      "sqlancer.databend.ast.DatabendFunctionOperation",
      "sqlancer.databend.DatabendSchema$DatabendCompositeDataType",
      "sqlancer.common.schema.AbstractTableColumn",
      "sqlancer.databend.DatabendSchema$DatabendColumn",
      "sqlancer.common.ast.newast.ColumnReferenceNode",
      "sqlancer.databend.ast.DatabendColumnReference",
      "sqlancer.databend.ast.DatabendBinaryOperation",
      "sqlancer.databend.ast.DatabendColumnValue",
      "sqlancer.h2.H2ExpressionGenerator$H2BinaryComparisonOperator"
    );
  }
}

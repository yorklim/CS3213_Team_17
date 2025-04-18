/**
 * Scaffolding file used to store all the setups needed to run 
 * tests automatically generated by EvoSuite
 * Mon Apr 14 04:12:11 GMT 2025
 */

package sqlancer.cnosdb;

import org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.runtime.sandbox.Sandbox.SandboxMode;

@EvoSuiteClassExclude
public class CnosDBOptions_ESTest_scaffolding {

  @org.junit.Rule
  public org.evosuite.runtime.vnet.NonFunctionalRequirementRule nfr = new org.evosuite.runtime.vnet.NonFunctionalRequirementRule();

  private static final java.util.Properties defaultProperties = (java.util.Properties) java.lang.System.getProperties().clone(); 

  private org.evosuite.runtime.thread.ThreadStopper threadStopper =  new org.evosuite.runtime.thread.ThreadStopper (org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);


  @BeforeClass
  public static void initEvoSuiteFramework() { 
    org.evosuite.runtime.RuntimeSettings.className = "sqlancer.cnosdb.CnosDBOptions"; 
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
    org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(CnosDBOptions_ESTest_scaffolding.class.getClassLoader() ,
      "sqlancer.DBMSSpecificOptions",
      "sqlancer.cnosdb.CnosDBOptions",
      "sqlancer.common.oracle.TestOracle",
      "sqlancer.GlobalState",
      "sqlancer.cnosdb.CnosDBOracleFactory",
      "sqlancer.cnosdb.CnosDBOracleFactory$3",
      "sqlancer.cnosdb.CnosDBOracleFactory$1",
      "sqlancer.cnosdb.CnosDBGlobalState",
      "sqlancer.cnosdb.CnosDBOracleFactory$2",
      "sqlancer.OracleFactory"
    );
  } 

  private static void resetClasses() {
    org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(CnosDBOptions_ESTest_scaffolding.class.getClassLoader()); 

    org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
      "sqlancer.cnosdb.CnosDBOptions",
      "sqlancer.cnosdb.CnosDBOracleFactory",
      "sqlancer.GlobalState",
      "sqlancer.cnosdb.CnosDBGlobalState",
      "sqlancer.common.query.Query",
      "sqlancer.cnosdb.oracle.CnosDBNoRECBase",
      "sqlancer.cnosdb.oracle.CnosDBNoRECOracle",
      "sqlancer.common.schema.AbstractSchema",
      "sqlancer.cnosdb.CnosDBSchema",
      "sqlancer.common.oracle.TernaryLogicPartitioningOracleBase",
      "sqlancer.cnosdb.oracle.tlp.CnosDBTLPBase",
      "sqlancer.cnosdb.oracle.tlp.CnosDBTLPHavingOracle",
      "sqlancer.common.query.ExpectedErrors",
      "sqlancer.cnosdb.oracle.tlp.CnosDBTLPWhereOracle",
      "sqlancer.cnosdb.oracle.tlp.CnosDBTLPAggregateOracle",
      "sqlancer.common.oracle.CompositeTestOracle"
    );
  }
}

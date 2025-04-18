/**
 * Scaffolding file used to store all the setups needed to run 
 * tests automatically generated by EvoSuite
 * Mon Apr 14 05:21:52 GMT 2025
 */

package sqlancer.cockroachdb.gen;

import org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.runtime.sandbox.Sandbox.SandboxMode;

@EvoSuiteClassExclude
public class CockroachDBCreateStatisticsGenerator_ESTest_scaffolding {

  @org.junit.Rule
  public org.evosuite.runtime.vnet.NonFunctionalRequirementRule nfr = new org.evosuite.runtime.vnet.NonFunctionalRequirementRule();

  private static final java.util.Properties defaultProperties = (java.util.Properties) java.lang.System.getProperties().clone(); 

  private org.evosuite.runtime.thread.ThreadStopper threadStopper =  new org.evosuite.runtime.thread.ThreadStopper (org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);


  @BeforeClass
  public static void initEvoSuiteFramework() { 
    org.evosuite.runtime.RuntimeSettings.className = "sqlancer.cockroachdb.gen.CockroachDBCreateStatisticsGenerator"; 
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
    org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(CockroachDBCreateStatisticsGenerator_ESTest_scaffolding.class.getClassLoader() ,
      "sqlancer.MainOptions",
      "sqlancer.Randomly",
      "sqlancer.cockroachdb.CockroachDBSchema",
      "sqlancer.SQLProviderAdapter",
      "sqlancer.common.query.SQLancerResultSet",
      "sqlancer.cockroachdb.CockroachDBSchema$CockroachDBTable",
      "sqlancer.common.query.SQLQueryAdapter",
      "sqlancer.Main$StateLogger",
      "sqlancer.SQLGlobalState",
      "sqlancer.common.schema.AbstractTable",
      "sqlancer.StateToReproduce",
      "sqlancer.GlobalState",
      "sqlancer.cockroachdb.CockroachDBProvider$Action",
      "sqlancer.cockroachdb.CockroachDBProvider$CockroachDBGlobalState",
      "sqlancer.common.schema.AbstractSchema",
      "sqlancer.SQLConnection",
      "sqlancer.Main",
      "sqlancer.DatabaseProvider",
      "sqlancer.DBMSSpecificOptions",
      "sqlancer.cockroachdb.gen.CockroachDBCreateStatisticsGenerator",
      "sqlancer.common.schema.AbstractRelationalTable",
      "sqlancer.Main$QueryManager",
      "sqlancer.cockroachdb.CockroachDBProvider",
      "sqlancer.common.log.Loggable",
      "sqlancer.IgnoreMeException",
      "sqlancer.ProviderAdapter",
      "sqlancer.common.query.Query",
      "sqlancer.SQLancerDBConnection"
    );
  } 

  private static void resetClasses() {
    org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(CockroachDBCreateStatisticsGenerator_ESTest_scaffolding.class.getClassLoader()); 

    org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
      "sqlancer.cockroachdb.gen.CockroachDBCreateStatisticsGenerator",
      "sqlancer.Randomly$StringGenerationStrategy$4",
      "sqlancer.Randomly$StringGenerationStrategy",
      "sqlancer.Randomly",
      "sqlancer.GlobalState",
      "sqlancer.SQLGlobalState",
      "sqlancer.cockroachdb.CockroachDBProvider$CockroachDBGlobalState",
      "sqlancer.common.schema.AbstractSchema",
      "sqlancer.cockroachdb.CockroachDBSchema",
      "sqlancer.postgres.PostgresGlobalState",
      "sqlancer.citus.CitusGlobalState",
      "sqlancer.common.query.ExpectedErrors",
      "sqlancer.common.query.Query",
      "sqlancer.common.query.SQLQueryAdapter",
      "sqlancer.materialize.MaterializeGlobalState",
      "sqlancer.common.query.SQLQueryResultCheckAdapter",
      "sqlancer.common.query.ExpectedErrors$ExpectedErrorsBuilder",
      "sqlancer.hsqldb.HSQLDBProvider$HSQLDBGlobalState",
      "sqlancer.h2.H2Provider$H2GlobalState",
      "sqlancer.SQLConnection"
    );
  }
}

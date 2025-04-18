/*
 * This file was automatically generated by EvoSuite
 * Mon Apr 14 03:44:33 GMT 2025
 */

package sqlancer.cnosdb.gen;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import sqlancer.cnosdb.gen.CnosDBTableGenerator;
import sqlancer.cnosdb.query.CnosDBOtherQuery;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class CnosDBTableGenerator_ESTest extends CnosDBTableGenerator_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      CnosDBTableGenerator cnosDBTableGenerator0 = new CnosDBTableGenerator("CREATE TABLE");
      CnosDBOtherQuery cnosDBOtherQuery0 = cnosDBTableGenerator0.generate();
      assertEquals("CREATE TABLE CREATE TABLE(f0 BIGINT , TAGS(t0))", cnosDBOtherQuery0.getQueryString());
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      CnosDBOtherQuery cnosDBOtherQuery0 = CnosDBTableGenerator.generate("");
      assertEquals("CREATE TABLE (f0 BIGINT , TAGS(t0))", cnosDBOtherQuery0.getQueryString());
  }
}

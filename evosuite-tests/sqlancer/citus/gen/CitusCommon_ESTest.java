/*
 * This file was automatically generated by EvoSuite
 * Thu Apr 10 15:59:56 GMT 2025
 */

package sqlancer.citus.gen;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import sqlancer.citus.gen.CitusCommon;
import sqlancer.common.query.ExpectedErrors;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class CitusCommon_ESTest extends CitusCommon_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      String[] stringArray0 = new String[9];
      ExpectedErrors expectedErrors0 = ExpectedErrors.from(stringArray0);
      CitusCommon.addCitusErrors(expectedErrors0);
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      List<String> list0 = CitusCommon.getCitusErrors();
      assertFalse(list0.isEmpty());
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      // Undeclared exception!
      try { 
        CitusCommon.addCitusErrors((ExpectedErrors) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("sqlancer.citus.gen.CitusCommon", e);
      }
  }
}

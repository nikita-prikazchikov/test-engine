package testprocess.runner;

import junit.framework.TestCase;
import org.junit.Test;
import testInterface.TestDataException;
import testprocess.exceptions.DriverManagerException;

/**
 * Created by IntelliJ IDEA.
 * User: akugurushev
 * Date: 24.11.11
 * Time: 19:07
 * To change this template use File | Settings | File Templates.
 */
public class TestCaseVersionRunnerTest extends TestCase {
    public void testStatusUpdate() throws Exception {
        TestCaseVersionRunner testCaseVersionRunner = new TestCaseVersionRunner("Firefox");
        testCaseVersionRunner.set_token( "fdadeb36a6b19f156330bb5cc00275fe3d770c1a" );
        testCaseVersionRunner.sendStatusUpdate( "test auto", null, "in_progress");
    }

    @Test
    public void testRun() {
        try {
            TestCaseVersionRunner testCaseVersionRunner = new TestCaseVersionRunner("Firefox");
            testCaseVersionRunner.run(0);
            assertTrue(true);
        } catch (DriverManagerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            assertTrue(false);
        } catch (TestDataException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            assertTrue(false);
        }
    }
}

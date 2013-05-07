package testprocess;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: akugurushev
 * Date: 29.11.11
 * Time: 11:04
 * To change this template use File | Settings | File Templates.
 */
public class ProgramRunnerTest{

    @Test
    public void test_main() {
        try {
            ProgramRunner.main( new String[]{ } );
            Assert.assertTrue(true);
        } catch (Exception ex) {
            Assert.assertTrue(false);
        }
    }
}

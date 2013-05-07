package testprocess.log;

import junit.framework.TestCase;
import testInterface.objects.utils.StepExStatus;
import org.junit.Test;
import selenium.log.Result;
import selenium.log.ResultList;

public class ResultListTest extends TestCase{

    @Test
    public void test1(){
        try{
            ResultList rl1 = new ResultList("node 1");
            rl1.add(new Result(StepExStatus.passed, "test passed"));
            rl1.add(new Result(StepExStatus.failed, "test failed"));
            rl1.add(new Result(StepExStatus.deferred, "test deferred"));
            rl1.add(new Result(StepExStatus.blocked, "test blocked"));
            rl1.add(new Result(StepExStatus.blocked, "test super blocked"));

            ResultList rl2 = rl1.appendList("child list 1");
            rl2.add(new Result(StepExStatus.passed, "test passed"));
            rl2.add(new Result(StepExStatus.failed, "test failed"));
            rl2.add(new Result(StepExStatus.deferred, "test deferred"));
            rl2.add(new Result(StepExStatus.blocked, "test blocked"));
            rl2.add(new Result(StepExStatus.blocked, "test super blocked"));


            rl1.add(new Result(StepExStatus.passed, "test passed"));
            rl1.add(new Result(StepExStatus.failed, "test failed"));
            rl1.add(new Result(StepExStatus.deferred, "test deferred"));
            rl1.add(new Result(StepExStatus.blocked, "test blocked"));
            rl1.add(new Result(StepExStatus.blocked, "test super blocked"));

            rl1.toString();


            assertTrue("Works", true);
        }catch(Exception e) {
            assertTrue(e.getMessage(), false);
        }
    }
}

package testInterface;

import testInterface.objects.Browser;
import testInterface.objects.TestCaseExecutionLog;
import testInterface.objects.TestCaseVersion;
import testInterface.objects.TestStep;
import testInterface.objects.utils.Status;
import testInterface.objects.utils.StepExStatus;

public interface TestDataInterface{

    public void addTestStepExecutionLog ( final TestStep testStep, final TestCaseExecutionLog testCaseExecutionLog, final StepExStatus status, final String log ) throws TestDataException;
    public Browser getBrowser ( String browserName ) throws TestDataException;
    public TestCaseExecutionLog getTestCaseExecutionLog ( Browser browser, int cycle ) throws TestDataException;
    public TestCaseVersion getTestCaseVersion ( int testCaseId ) throws TestDataException;
    public TestCaseExecutionLog startTestProcess ( final Browser browser, final int cycle ) throws TestDataException;
    public void stopProcessForTestCase ( final TestCaseExecutionLog executionLog, final Status status, final StepExStatus executionStatus ) throws TestDataException;
    public void updateTestCaseStatus ( final TestCaseExecutionLog executionLog, final Status status ) throws TestDataException;

}

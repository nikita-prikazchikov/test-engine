package testInterface.objects;

import org.json.JSONException;
import org.json.JSONObject;
import testInterface.objects.utils.Identified;
import testInterface.objects.utils.StepExStatus;

import java.sql.Timestamp;

public class TestStepExecutionLog extends Identified {

    private static final String JSON_STEP = "step";
    private static final String JSON_STEP_FK = "id_test_step_fk";
    private static final String JSON_CASE_LOG = "caseLog";
    private static final String JSON_CASE_LOG_FK = "id_test_case_execution_log_fk";
    private static final String JSON_STATUS = "status";
    private static final String JSON_CREATED = "created";
    private static final String JSON_COMPLETED = "timestamp_completed";
    private static final String JSON_LOG = "log";

    private TestStep testStep;
    private int testStepFk;
    private TestCaseExecutionLog testCaseExecutionLog;
    private int testCaseLogFk;
    private StepExStatus status;
    private String timestampCreated;
    private String timestampCompleted;
    private String log;

    public TestStepExecutionLog() {
    }

    public TestStepExecutionLog(TestStep testStep, TestCaseExecutionLog testCaseExecutionLog, StepExStatus status, String timestampCompleted, String log) {
        this.testStep = testStep;
        this.testCaseExecutionLog = testCaseExecutionLog;
        this.status = status;
        this.timestampCompleted = timestampCompleted;
        this.log = log;
    }

    public TestStepExecutionLog(JSONObject obj) throws JSONException{
        super(obj);
        this.testStep = obj.has(TestStepExecutionLog.JSON_STEP) ? new TestStep(obj.getJSONObject(TestStepExecutionLog.JSON_STEP)) : null;
        this.testCaseExecutionLog = obj.has(TestStepExecutionLog.JSON_CASE_LOG) ? new TestCaseExecutionLog(obj.getJSONObject(TestStepExecutionLog.JSON_CASE_LOG)) : null;
        this.status = obj.has(TestStepExecutionLog.JSON_STATUS) ? StepExStatus.valueOf(obj.getString(TestStepExecutionLog.JSON_STATUS)) : null;
        this.timestampCreated = obj.has(TestStepExecutionLog.JSON_CREATED) ? obj.getString(TestStepExecutionLog.JSON_CREATED) : null;
        this.timestampCompleted = obj.has(TestStepExecutionLog.JSON_COMPLETED) ? obj.getString(TestStepExecutionLog.JSON_COMPLETED) : null;
        this.log = obj.getString(TestStepExecutionLog.JSON_LOG);
    }

    public JSONObject toJSONObject() throws JSONException{
        JSONObject obj = super.toJSONObject();
        obj.put(TestStepExecutionLog.JSON_STEP, this.testStep.toJSONObject());
        obj.put(TestStepExecutionLog.JSON_STEP_FK, this.testStep.getId());
        obj.put(TestStepExecutionLog.JSON_CASE_LOG, this.testCaseExecutionLog.toJSONObject());
        obj.put(TestStepExecutionLog.JSON_CASE_LOG_FK, this.testCaseExecutionLog.getId());
        obj.put(TestStepExecutionLog.JSON_STATUS, this.status.toString());
        obj.put(TestStepExecutionLog.JSON_CREATED,  this.timestampCreated != null ?this.timestampCreated : null);
        obj.put(TestStepExecutionLog.JSON_COMPLETED, this.timestampCompleted != null ? this.timestampCompleted : null);
        obj.put(TestStepExecutionLog.JSON_LOG, this.log);
        return obj;
    }

    public TestStep getTestStep() {
        return testStep;
    }

    public void setTestStep(TestStep testStep) {
        this.testStep = testStep;
    }

    public TestCaseExecutionLog getTestCaseExecutionLog() {
        return testCaseExecutionLog;
    }

    public void setTestCaseExecutionLog(TestCaseExecutionLog testCaseExecutionLog) {
        this.testCaseExecutionLog = testCaseExecutionLog;
    }

    public StepExStatus getStatus() {
        return status;
    }

    public void setStatus(StepExStatus status) {
        this.status = status;
    }

    public String getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(String timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public String getTimestampCompleted() {
        return timestampCompleted;
    }

    public void setTimestampCompleted(String timestampCompleted) {
        this.timestampCompleted = timestampCompleted;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}

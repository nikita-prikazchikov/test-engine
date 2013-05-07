package testInterface.objects;

import org.json.JSONException;
import org.json.JSONObject;
import testInterface.objects.utils.Identified;
import testInterface.objects.utils.Status;

public class TestCaseExecutionLog extends Identified{

    public static final String JSON_STATUS = "status";

    private static final String JSON_ID = "id_test_case_execution_log_pk";

    private static final String JSON_PARENT = "id_test_case_execution_log_fk";

    private static final String JSON_VERSION = "version";

    private static final String JSON_BROWSER = "id_browser_fk";

    private static final String JSON_USER = "id_user_fk";

    private static final String JSON_PRIORITY = "priority";

    private static final String JSON_WORKER = "ip_worker";

    private static final String JSON_EXECUTION_STATUS = "execution_status";

    private static final String JSON_CREATED = "timestamp_created";

    private static final String JSON_STARTED = "timestamp_start";

    private static final String JSON_END = "timestamp_end";

    private Browser browser;

    private String ipWorker;

    private TestCaseExecutionLog parent;

    private Integer priority;

    private Status status;

    private TestCaseVersion testCaseVersion;

    private Object testCycle;

    //    private StepExStatus         executionStatus;
    private String timestampCreated;

    private String timestampEnd;

    private String timestampStart;

    private User user;

    public TestCaseExecutionLog (){
    }

    public TestCaseExecutionLog ( TestCaseVersion testCaseVersion, Browser browser, User user, String ipWorker, Status status ){
        this.testCaseVersion = testCaseVersion;
        this.browser = browser;
        this.user = user;
        this.ipWorker = ipWorker;
        this.status = status;
    }

    public TestCaseExecutionLog ( JSONObject obj ) throws JSONException{
        this.id = obj.has( TestCaseExecutionLog.JSON_ID ) ? obj.getInt( TestCaseExecutionLog.JSON_ID ) : null;
        //this.parent = obj.has(TestCaseExecutionLog.JSON_PARENT) ? new TestCaseExecutionLog(obj.getJSONObject(TestCaseExecutionLog.JSON_PARENT)) : null;
        this.testCaseVersion =
                obj.has( TestCaseExecutionLog.JSON_VERSION ) ? new TestCaseVersion( obj.getJSONObject( TestCaseExecutionLog.JSON_VERSION ) ) : null;
        //this.browser = obj.has(TestCaseExecutionLog.JSON_BROWSER) ? new Browser(obj.getJSONObject(TestCaseExecutionLog.JSON_BROWSER)) : null;
        //this.user = obj.has(TestCaseExecutionLog.JSON_USER) ? new User(obj.getJSONObject(TestCaseExecutionLog.JSON_USER)) : null;
        this.priority = obj.has( TestCaseExecutionLog.JSON_PRIORITY ) ? obj.getInt( TestCaseExecutionLog.JSON_PRIORITY ) : null;
        this.ipWorker = obj.has( TestCaseExecutionLog.JSON_WORKER ) ? obj.getString( TestCaseExecutionLog.JSON_WORKER ) : null;
        this.status = obj.has( TestCaseExecutionLog.JSON_STATUS ) ? Status.valueOf( obj.getString( TestCaseExecutionLog.JSON_STATUS ) ) : null;
//        this.executionStatus = obj.has(TestCaseExecutionLog.JSON_EXECUTION_STATUS) ? StepExStatus.valueOf(obj.getString(TestCaseExecutionLog.JSON_EXECUTION_STATUS)) : null;
        this.timestampCreated = obj.has( TestCaseExecutionLog.JSON_CREATED ) ? obj.getString( TestCaseExecutionLog.JSON_CREATED ) : null;
        this.timestampStart = obj.has( TestCaseExecutionLog.JSON_STARTED ) ? obj.getString( TestCaseExecutionLog.JSON_STARTED ) : null;
        this.timestampEnd = obj.has( TestCaseExecutionLog.JSON_END ) ? obj.getString( TestCaseExecutionLog.JSON_END ) : null;
    }

    public Browser getBrowser (){
        return browser;
    }

    public String getIpWorker (){
        return ipWorker;
    }

    public TestCaseExecutionLog getParent (){
        return parent;
    }

    public Integer getPriority (){
        return priority;
    }

    public Status getStatus (){
        return status;
    }

    public TestCaseVersion getTestCaseVersion (){
        return testCaseVersion;
    }

    public Object getTestCycle (){
        return testCycle;
    }

    public String getTimestampCreated (){
        return timestampCreated;
    }

    public String getTimestampEnd (){
        return timestampEnd;
    }

    public String getTimestampStart (){
        return timestampStart;
    }

    public User getUser (){
        return user;
    }

    public TestCaseExecutionLog setBrowser ( Browser browser ){
        this.browser = browser;
        return this;
    }

    public TestCaseExecutionLog setIpWorker ( String ipWorker ){
        this.ipWorker = ipWorker;
        return this;
    }

    public TestCaseExecutionLog setParent ( TestCaseExecutionLog parent ){
        this.parent = parent;
        return this;
    }

    public TestCaseExecutionLog setPriority ( Integer priority ){
        this.priority = priority;
        return this;
    }

//    public StepExStatus getExecutionStatus (){
//        return executionStatus;
//    }
//
//    public TestCaseExecutionLog setExecutionStatus ( StepExStatus executionStatus ){
//        this.executionStatus = executionStatus;
//        return this;
//    }

    public TestCaseExecutionLog setStatus ( Status status ){
        this.status = status;
        return this;
    }

    public TestCaseExecutionLog setTestCaseVersion ( TestCaseVersion testCaseVersion ){
        this.testCaseVersion = testCaseVersion;
        return this;
    }

    public TestCaseExecutionLog setTestCycle ( Object testCycle ){
        this.testCycle = testCycle;
        return this;
    }

    public TestCaseExecutionLog setTimestampCreated ( String timestampCreated ){
        this.timestampCreated = timestampCreated;
        return this;
    }

    public TestCaseExecutionLog setTimestampEnd ( String timestampEnd ){
        this.timestampEnd = timestampEnd;
        return this;
    }

    public TestCaseExecutionLog setTimestampStart ( String timestampStart ){
        this.timestampStart = timestampStart;
        return this;
    }

    public TestCaseExecutionLog setUser ( User user ){
        this.user = user;
        return this;
    }

    public JSONObject toJSONObject () throws JSONException{
        JSONObject obj = super.toJSONObject();
        obj.put( TestCaseExecutionLog.JSON_PARENT, this.parent != null ? this.parent.toJSONObject() : null );
        obj.put( TestCaseExecutionLog.JSON_VERSION, this.testCaseVersion != null ? this.testCaseVersion.toJSONObject() : null );
        obj.put( TestCaseExecutionLog.JSON_BROWSER, this.browser != null ? this.browser.toJSONObject() : null );
        obj.put( TestCaseExecutionLog.JSON_USER, this.user != null ? this.user.toJSONObject() : null );
        obj.put( TestCaseExecutionLog.JSON_PRIORITY, this.priority );
        obj.put( TestCaseExecutionLog.JSON_WORKER, this.ipWorker );
        obj.put( TestCaseExecutionLog.JSON_STATUS, this.status.toString() );
//        obj.put(TestCaseExecutionLog.JSON_EXECUTION_STATUS, this.executionStatus.toString());
        obj.put( TestCaseExecutionLog.JSON_CREATED, this.timestampCreated );
        obj.put( TestCaseExecutionLog.JSON_STARTED, this.timestampStart );
        obj.put( TestCaseExecutionLog.JSON_END, this.timestampEnd );
        return obj;
    }
}

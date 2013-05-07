package testInterface.objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import testInterface.objects.utils.Named;
import testInterface.objects.utils.Priority;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class TestCaseVersion extends Named{

    private static final String JSON_ID = "id_test_case_version_pk";

    private static final String JSON_TEST_CASE = "testcase";

    private static final String JSON_PRIORITY = "priority";

    private static final String JSON_NAME = "name";

    private static final String JSON_DESCRIPTION = "description";

    private static final String JSON_PRECONDITIONS = "preconditions";

    private static final String JSON_POSTCONDITIONS = "postconditions";

    private static final String JSON_NOTES = "notes";

    private static final String JSON_USER = "user";

    private static final String JSON_UPDATED = "timestamp_updated";

    private static final String JSON_COMMENT = "comment";

    private static final String JSON_LOGS = "logs";

    private static final String JSON_STEPS = "steps";

    private String comment;

    private String description;

    private String notes;

    private String postconditions;

    private String preconditions;

    private Priority priority;

    private TestCase testCase;

    private List<TestCaseExecutionLog> testCaseExecutionLogs = new LinkedList<TestCaseExecutionLog>();

    private List<TestStep> testSteps = new LinkedList<TestStep>();

    private Timestamp timestampUpdated;

    private User user;


    public TestCaseVersion (){
    }

    public TestCaseVersion ( TestCase testCase, Priority priority, String name, String description, String preconditions, String postconditions, String notes, User user, String comment ){
        this.testCase = testCase;
        this.priority = priority;
        this.name = name;
        this.description = description;
        this.preconditions = preconditions;
        this.postconditions = postconditions;
        this.notes = notes;
        this.user = user;
        this.comment = comment;
    }

    public TestCaseVersion ( JSONObject obj ) throws JSONException{
        this.id = obj.has( TestCaseVersion.JSON_ID ) ? obj.getInt( TestCaseVersion.JSON_ID ) : null;
        this.name = obj.has( TestCaseVersion.JSON_NAME ) ? obj.getString( TestCaseVersion.JSON_NAME ) : null;
        this.testCase = obj.has( TestCaseVersion.JSON_TEST_CASE ) ? new TestCase( obj.getJSONObject( TestCaseVersion.JSON_TEST_CASE ) ) : null;
        this.priority = obj.has( TestCaseVersion.JSON_PRIORITY ) ? Priority.valueOf( obj.getString( TestCaseVersion.JSON_PRIORITY ) ) : null;
        this.description = obj.has( TestCaseVersion.JSON_DESCRIPTION ) ? obj.getString( TestCaseVersion.JSON_DESCRIPTION ) : null;
        this.preconditions = obj.has( TestCaseVersion.JSON_PRECONDITIONS ) ? obj.getString( TestCaseVersion.JSON_PRECONDITIONS ) : null;
        this.postconditions = obj.has( TestCaseVersion.JSON_POSTCONDITIONS ) ? obj.getString( TestCaseVersion.JSON_POSTCONDITIONS ) : null;
        this.notes = obj.has( TestCaseVersion.JSON_NOTES ) ? obj.getString( TestCaseVersion.JSON_NOTES ) : null;
        this.user = obj.has( TestCaseVersion.JSON_USER ) ? new User( obj.getJSONObject( TestCaseVersion.JSON_USER ) ) : null;
        this.timestampUpdated = obj.has( TestCaseVersion.JSON_UPDATED ) ? Timestamp.valueOf( obj.getString( TestCaseVersion.JSON_UPDATED ) ) : null;
        this.comment = obj.has( TestCaseVersion.JSON_COMMENT ) ? obj.getString( TestCaseVersion.JSON_COMMENT ) : null;

        if ( obj.has( TestCaseVersion.JSON_LOGS ) ){
            JSONArray arr = obj.getJSONArray( TestCaseVersion.JSON_LOGS );
            for ( int i = 0; i < arr.length(); i++ ){
                JSONObject o = arr.optJSONObject( i );
                this.testCaseExecutionLogs.add( new TestCaseExecutionLog( o ) );
            }
        }

        if ( obj.has( TestCaseVersion.JSON_STEPS ) ){
            JSONArray arr = obj.getJSONArray( TestCaseVersion.JSON_STEPS );
            for ( int i = 0; i < arr.length(); i++ ){
                JSONObject o = arr.optJSONObject( i );
                this.testSteps.add( new TestStep( o ) );
            }
        }
    }

    public String getComment (){
        return comment;
    }

    public String getDescription (){
        return description;
    }

    public String getNotes (){
        return notes;
    }

    public String getPostconditions (){
        return postconditions;
    }

    public String getPreconditions (){
        return preconditions;
    }

    public Priority getPriority (){
        return priority;
    }

    public TestCase getTestCase (){
        return testCase;
    }

    public List<TestCaseExecutionLog> getTestCaseExecutionLogs (){
        return testCaseExecutionLogs;
    }

    public List<TestStep> getTestSteps (){
        return testSteps;
    }

    public Timestamp getTimestampUpdated (){
        return timestampUpdated;
    }

    public User getUser (){
        return user;
    }

    public void setComment ( String comment ){
        this.comment = comment;
    }

    public void setDescription ( String description ){
        this.description = description;
    }

    public void setNotes ( String notes ){
        this.notes = notes;
    }

    public void setPostconditions ( String postconditions ){
        this.postconditions = postconditions;
    }

    public void setPreconditions ( String preconditions ){
        this.preconditions = preconditions;
    }

    public void setPriority ( Priority priority ){
        this.priority = priority;
    }

    public void setTestCase ( TestCase testCase ){
        this.testCase = testCase;
    }

    public void setTestCaseExecutionLogs ( List<TestCaseExecutionLog> testCaseExecutionLogs ){
        this.testCaseExecutionLogs = testCaseExecutionLogs;
    }

    public void setTestSteps ( List<TestStep> testSteps ){
        this.testSteps = testSteps;
    }

    public void setTimestampUpdated ( Timestamp timestampUpdated ){
        this.timestampUpdated = timestampUpdated;
    }

    public void setUser ( User user ){
        this.user = user;
    }

    public JSONObject toJSONObject () throws JSONException{
        JSONObject obj = super.toJSONObject();
        obj.put( TestCaseVersion.JSON_TEST_CASE, this.testCase != null ? this.testCase.toJSONObject() : null );
        obj.put( TestCaseVersion.JSON_PRIORITY, this.priority.toString() );
        obj.put( TestCaseVersion.JSON_DESCRIPTION, this.description );
        obj.put( TestCaseVersion.JSON_PRECONDITIONS, this.preconditions );
        obj.put( TestCaseVersion.JSON_POSTCONDITIONS, this.postconditions );
        obj.put( TestCaseVersion.JSON_NOTES, this.notes );
        obj.put( TestCaseVersion.JSON_USER, this.user != null ? this.user.toJSONObject() : null );
        obj.put( TestCaseVersion.JSON_UPDATED, this.timestampUpdated.getTime() );
        obj.put( TestCaseVersion.JSON_COMMENT, this.comment );

        if ( this.testCaseExecutionLogs.size() > 0 ){
            JSONArray arr = new JSONArray();
            for ( TestCaseExecutionLog v : this.testCaseExecutionLogs ){
                arr.put( v.toJSONObject() );
            }
            obj.put( TestCaseVersion.JSON_LOGS, arr );
        }

        if ( this.testSteps.size() > 0 ){
            JSONArray arr = new JSONArray();
            for ( TestStep v : this.testSteps ){
                arr.put( v.toJSONObject() );
            }
            obj.put( TestCaseVersion.JSON_STEPS, arr );
        }

        return obj;
    }
}


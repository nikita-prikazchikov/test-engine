package testInterface.objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import testInterface.objects.utils.Identified;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class TestCase extends Identified{

    private static final String JSON_VERSION = "version";

    private static final String JSON_USER = "user";

    private static final String JSON_CREATED = "created";

    private static final String JSON_INDEX = "index";

    private static final String JSON_ACTIVE = "active";

    private static final String JSON_AUTOMATED = "automated";

    private static final String JSON_PRIORITY = "priority";

    private static final String JSON_VERSIONS = "versions";

    private Boolean active;

    private Boolean automated;

    private Integer execPriority;

    private Integer index;

    private TestCaseVersion testCaseVersion;

    private List<TestCaseVersion> testCaseVersions = new LinkedList<TestCaseVersion>();

    private Timestamp timestamp_created;

    private User user;

    public TestCase (){
    }

    public TestCase ( TestCaseVersion testCaseVersion, User user, Integer index, Boolean active ){
        this.testCaseVersion = testCaseVersion;
        this.user = user;
        this.index = index;
        this.active = active;
    }

    public TestCase ( JSONObject obj ) throws JSONException{
        super( obj );
        this.testCaseVersion = obj.has( TestCase.JSON_VERSION ) ? new TestCaseVersion( obj.optJSONObject( TestCase.JSON_VERSION ) ) : null;
        this.user = obj.has( TestCase.JSON_USER ) ? new User( obj.getJSONObject( TestCase.JSON_USER ) ) : null;
        this.timestamp_created = obj.has( TestCase.JSON_CREATED ) ? new Timestamp( obj.getLong( TestCase.JSON_CREATED ) ) : null;
        this.index = obj.has( TestCase.JSON_INDEX ) ? obj.getInt( TestCase.JSON_INDEX ) : null;
        this.active = obj.has( TestCase.JSON_ACTIVE ) ? obj.getBoolean( TestCase.JSON_ACTIVE ) : null;
        this.automated = obj.has( TestCase.JSON_AUTOMATED ) ? obj.getBoolean( TestCase.JSON_AUTOMATED ) : null;
        this.execPriority = obj.has( TestCase.JSON_PRIORITY ) ? obj.getInt( TestCase.JSON_PRIORITY ) : null;

        if ( obj.has( TestCase.JSON_VERSIONS ) ){
            JSONArray arr = obj.getJSONArray( TestCase.JSON_VERSIONS );
            for ( int i = 0; i < arr.length(); i++ ){
                JSONObject o = arr.getJSONObject( i );
                this.testCaseVersions.add( new TestCaseVersion( o ) );
            }
        }
    }

    public Boolean getActive (){
        return active;
    }

    public Boolean getAutomated (){
        return automated;
    }

    public Integer getExecPriority (){
        return execPriority;
    }

    public Integer getIndex (){
        return index;
    }

    public TestCaseVersion getTestCaseVersion (){
        return testCaseVersion;
    }

    public List<TestCaseVersion> getTestCaseVersions (){
        return testCaseVersions;
    }

    public Timestamp getTimestamp_created (){
        return timestamp_created;
    }

    public User getUser (){
        return user;
    }

    public void setActive ( Boolean active ){
        this.active = active;
    }

    public void setAutomated ( Boolean automated ){
        this.automated = automated;
    }

    public void setExecPriority ( Integer execPriority ){
        this.execPriority = execPriority;
    }

    public void setIndex ( Integer index ){
        this.index = index;
    }

    public void setTestCaseVersion ( TestCaseVersion testCaseVersion ){
        this.testCaseVersion = testCaseVersion;
    }

    public void setTestCaseVersions ( List<TestCaseVersion> testCaseVersions ){
        this.testCaseVersions = testCaseVersions;
    }

    public void setTimestamp_created ( Timestamp timestamp_created ){
        this.timestamp_created = timestamp_created;
    }

    public void setUser ( User user ){
        this.user = user;
    }

    public JSONObject toJSONObject () throws JSONException{
        JSONObject obj = super.toJSONObject();
        obj.put( TestCase.JSON_VERSION, this.testCaseVersion.toJSONObject() );
        obj.put( TestCase.JSON_USER, this.user.toJSONObject() );
        obj.put( TestCase.JSON_CREATED, this.timestamp_created.getTime() );
        obj.put( TestCase.JSON_INDEX, this.index );
        obj.put( TestCase.JSON_ACTIVE, this.active );
        obj.put( TestCase.JSON_AUTOMATED, this.automated );
        obj.put( TestCase.JSON_PRIORITY, this.execPriority );

        if ( this.testCaseVersions.size() > 0 ){
            JSONArray arr = new JSONArray();
            for ( TestCaseVersion v : this.testCaseVersions ){
                arr.put( v.toJSONObject() );
            }
            obj.put( TestCase.JSON_VERSIONS, arr );
        }

        return obj;
    }
}

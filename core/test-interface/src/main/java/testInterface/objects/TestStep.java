package testInterface.objects;

import org.json.JSONException;
import org.json.JSONObject;
import testInterface.objects.utils.Named;

import java.sql.Timestamp;

public class TestStep extends Named{

    private static final String JSON_ID = "id_test_step_pk";

    private static final String JSON_NAME = "name";

    private static final String JSON_USER = "user";

    private static final String JSON_CREATED = "timestamp_created";

    private static final String JSON_BLOCKING = "blocking";

    private static final String JSON_SCRIPT = "script";

    private boolean blocking;

    private String script;

    private Timestamp timestamp_created;

    private User user;

    public TestStep (){
    }

    public TestStep ( User user, String name, String script ){
        this.user = user;
        this.name = name;
        this.script = script;
    }

    public TestStep ( JSONObject obj ) throws JSONException{
        this.id = obj.has( TestStep.JSON_ID ) ? obj.getInt( TestStep.JSON_ID ) : null;
        this.name = obj.has( TestStep.JSON_NAME ) ? obj.getString( TestStep.JSON_NAME ) : null;
        this.user = obj.has( TestStep.JSON_USER ) ? new User( obj.getJSONObject( TestStep.JSON_USER ) ) : null;
        this.timestamp_created = obj.has( TestStep.JSON_CREATED ) ? Timestamp.valueOf( obj.getString( TestStep.JSON_CREATED ) ) : null;
        this.blocking = obj.has( TestStep.JSON_BLOCKING ) ? obj.getInt( TestStep.JSON_BLOCKING ) == 1 : null;
        this.script = obj.getString( TestStep.JSON_SCRIPT );
    }

    public boolean getBlocking (){
        return this.blocking;
    }

    public String getScript (){
        return script;
    }

    public Timestamp getTimestamp_created (){
        return timestamp_created;
    }

    public User getUser (){
        return user;
    }

    public void setBlocking ( boolean blocking ){
        this.blocking = blocking;
    }

    public void setScript ( String script ){
        this.script = script.trim();
    }

    public void setTimestamp_created ( Timestamp timestamp_created ){
        this.timestamp_created = timestamp_created;
    }

    public void setUser ( User user ){
        this.user = user;
    }

    public JSONObject toJSONObject () throws JSONException{
        JSONObject obj = super.toJSONObject();
        obj.put( TestStep.JSON_USER, this.user != null ? this.user.toJSONObject() : null );
        obj.put( TestStep.JSON_CREATED, this.timestamp_created.getTime() );
        obj.put( TestStep.JSON_BLOCKING, this.blocking );
        obj.put( TestStep.JSON_SCRIPT, this.script );
        return obj;
    }
}

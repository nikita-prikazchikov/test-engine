package testInterface.objects;

import org.json.JSONException;
import org.json.JSONObject;
import testInterface.objects.utils.Named;

public class Browser extends Named{

    private static final String JSON_ID = "id_browser_pk";

    private static final String JSON_NAME = "name";

    public Browser (){
    }

    public Browser ( JSONObject obj ) throws JSONException{
        this.id = obj.has( Browser.JSON_ID ) ? obj.getInt( Browser.JSON_ID ) : null;
        this.name = obj.has( Browser.JSON_NAME ) ? obj.getString( Browser.JSON_NAME ) : null;
    }
}

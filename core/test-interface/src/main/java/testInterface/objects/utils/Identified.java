package testInterface.objects.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by IntelliJ IDEA.
 * User: kibberpunk
 * Date: 20.11.11
 * Time: 13:22
 * To change this template use File | Settings | File Templates.
 */
public class Identified {

    private static final String JSON_ID = "id";

    protected Integer id;

    public Identified(JSONObject obj) throws JSONException{
        this.id = obj.has(Identified.JSON_ID) ? obj.getInt(Identified.JSON_ID) : null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Identified() {
    }

    public JSONObject toJSONObject() throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put(Identified.JSON_ID, this.id);
        return obj;
    }
}

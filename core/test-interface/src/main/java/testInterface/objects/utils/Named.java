package testInterface.objects.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: nfedotov
 * Date: 01.11.12
 * Time: 10:37
 * To change this template use File | Settings | File Templates.
 */
public class Named extends Identified {

    private static final String JSON_NAME = "name";

    protected String name;

    public Named() {
    }

    public Named(JSONObject obj) throws JSONException {
        super(obj);
        this.name = obj.getString(Named.JSON_NAME);
    }

    public Named(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JSONObject toJSONObject() throws JSONException{
        JSONObject obj = super.toJSONObject();
        obj.put(Named.JSON_NAME, this.name);
        return obj;
    }
}

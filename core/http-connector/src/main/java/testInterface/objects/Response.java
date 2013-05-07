package testInterface.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class Response{

    private static final String JSON_SUCCESS = "success";

    private static final String JSON_MESSAGE = "message";

    private static final String JSON_CODE = "code";

    public static final Integer JSON_CODE_DONE = 0;
    public static final Integer JSON_CODE_BUSY = 1;
    public static final Integer JSON_CODE_WAIT = 10;

    protected Boolean success;

    protected String message;

    protected Integer code;

    public Response (){
    }

    public Response ( JSONObject obj ) throws JSONException{
        this.success = !obj.has( Response.JSON_SUCCESS ) || obj.getBoolean( Response.JSON_SUCCESS );
        this.message = obj.has( Response.JSON_MESSAGE ) ? obj.getString( Response.JSON_MESSAGE ) : null;
        this.code = obj.has( Response.JSON_CODE ) ? obj.getInt( Response.JSON_CODE ) : null;
    }

    public Boolean getSuccess (){
        return success;
    }

    public void setSuccess ( Boolean success ){
        this.success = success;
    }

    public String getMessage (){
        return message;
    }

    public void setMessage ( String message ){
        this.message = message;
    }

    public Integer getCode (){
        return code;
    }

    public void setCode ( Integer code ){
        this.code = code;
    }

    public JSONObject toJSONObject () throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put( Response.JSON_SUCCESS, this.success );
        obj.put( Response.JSON_MESSAGE, this.message );
        obj.put( Response.JSON_CODE, this.code );
        return obj;
    }
}

package testInterface.objects;

import org.json.JSONException;
import org.json.JSONObject;
import testInterface.objects.utils.Named;

public class User extends Named{

    public User (){
    }

    public User ( JSONObject obj ) throws JSONException{
        super( obj );
    }

}

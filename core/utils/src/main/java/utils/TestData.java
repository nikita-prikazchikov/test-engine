package utils;

import java.util.HashMap;

public class TestData{

    private static HashMap<String, String> map;

    static{
        map = new HashMap<String, String>();
    }

    public static void clear(){
        map.clear();
    }

    public static void put ( String key, String value ){
        map.put( key, value );
    }

    public static String get ( String key ){
        if ( map.containsKey( key )){
            return map.get( key );
        }
        return String.format( "Unknown stored data [%s]", key );
    }

}

package utils;

import java.util.HashMap;

public class Storage{

    private static HashMap<String, DataHashMap<AbstractData>> objects;

    static{
        objects = new HashMap<String, DataHashMap<AbstractData>>();
    }

    public static void clear (){
        objects.clear();
    }

    public static AbstractData get ( String key ){
        for ( DataHashMap<AbstractData> map : objects.values() ){
            if ( map.containsKey( key ) ){
                return map.get( key );
            }
        }
        return null;
    }

    public static AbstractData get ( String key, Class objectClass ){
        if ( objects.containsKey( objectClass.getName() ) && objects.get( objectClass.getName() ).containsKey( key ) ){
            return objects.get( objectClass.getName() ).get( key );
        }
        return null;
    }

    public static DataHashMap<AbstractData> get ( Class objectClass ){
        if ( !objects.containsKey( objectClass.getName() ) ){
            objects.put( objectClass.getName(), new DataHashMap<AbstractData>() );
        }
        return objects.get( objectClass.getName() );
    }

    public static void put ( String key, AbstractData value ){
        Storage.get( value.getClass() ).add( key, value );
    }

}

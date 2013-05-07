package utils;

import java.util.HashMap;

public class DataHashMap<V> extends HashMap<String, V>{

    private String currentKey;

    private String keyDefault;

    private String prefix;

    public DataHashMap (){

    }

    public DataHashMap ( V defaultObject, String prefix ){
        this.prefix = prefix;
        this.add( defaultObject );
        this.keyDefault = this.getCurrentKey();
    }

    public DataHashMap ( V defaultObject ){
        this.prefix = "default";
        this.add( defaultObject );
        this.keyDefault = this.getCurrentKey();
    }

    public void add ( String key, V value ){
        this.setCurrentKey( key );
        this.put( key, value );
    }

    public void add ( V value ){
        this.setCurrentKey( String.format( "%s_%d", this.prefix, this.size() ) );
        this.put( this.getCurrentKey(), value );
    }

    public V get ( String key ){
        return super.get( key );
    }

    public V getCurrent (){
        return this.get( this.getCurrentKey() );
    }

    public String getCurrentKey (){
        return this.currentKey;
    }

    public void setCurrentKey ( String key ){
        this.currentKey = key;
    }

    public void setKeyOfCurrent ( String key ){
        V value = this.getCurrent();
        this.remove( this.getCurrentKey() );
        this.add( key, value );
    }

    public void useDefault (){
        this.setCurrentKey( this.keyDefault );
    }
}

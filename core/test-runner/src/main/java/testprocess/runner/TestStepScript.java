package testprocess.runner;

import testprocess.exceptions.TestStepScriptException;
import utils.RndEngine;
import utils.TestData;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Creator: akugurushev
 * Date: 22.11.11
 * Time: 17:11
 * Supported: nprikazchikov
 */
public class TestStepScript{

    private String className;
    private List<ChainElement> methodList = new LinkedList<ChainElement>();

//    private String methodName;
//    private String actionName;

//    private List<AbstractMap.SimpleEntry<Class, Object>> parameters       = new LinkedList<AbstractMap.SimpleEntry<Class, Object>>();
//    private List<AbstractMap.SimpleEntry<Class, Object>> actionParameters = new LinkedList<AbstractMap.SimpleEntry<Class, Object>>();

    public static TestStepScript buildScript ( String textScript ) throws TestStepScriptException{
        if ( null == textScript ){
            throw new TestStepScriptException( new NullPointerException() );
        }
        try {
            TestStepScript script = new TestStepScript();
            Pattern pattern = Pattern.compile( "^((?:\\w+\\.)*\\w+)((?:\\.(?:\\w+)\\s*\\((?:| |,|null|true|false|\\d+(\\.\\d+)?|\\\".*?\\\")*\\))+)$" );
            Matcher matcher = pattern.matcher( textScript.trim() );
            if ( matcher.find() ){
                script.className = matcher.group( 1 );
                String callableMethods = matcher.group( 2 );
                ChainElementIterator iterator = new ChainElementIterator( callableMethods );
                do {
                    ChainElement element = iterator.getNext();
                    if ( !element.iExist() ){
                        break;
                    }
                    script.methodList.add( element );
                }
                while ( true );
            }
            else {
                throw new TestStepScriptException();
            }
            return script;
        }
        catch ( Exception ex ){
            throw new TestStepScriptException( ex );
        }
    }

    public String getClassName (){
        return className;
    }

    public List<ChainElement> getMethodList (){
        return methodList;
    }

    @Override
    public String toString (){
        StringBuilder builder = new StringBuilder();
        builder.append("======= Start step data =======\n");
        builder.append( "Class: " ).append( className ).append( "\n" );
        for ( ChainElement entry : this.methodList ){
            builder.append( entry.toString() ).append( "" );
        }
        builder.append("======= End step data =======\n");
        return builder.toString();
    }
}

class ChainElementIterator{
    private static Pattern pattern = Pattern.compile( "(?:.(\\w+)\\s*\\(((?:| |,|null|true|false|\\d+(\\.\\d+)?|\".*?\")*)\\))" );
    private String content;

    public ChainElementIterator ( String content ){
        this.content = content;
    }

    public ChainElement getNext () throws TestStepScriptException{

        ChainElement element = new ChainElement();
        try {
            if ( !this.content.trim().equals( "" ) ){
                Matcher matcher = pattern.matcher( content );
                if ( matcher.find() ){
                    this.content = this.content.substring( matcher.group( 0 ).length() );
                    element.setMethodName( matcher.group( 1 ) );
                    String arguments = matcher.group( 2 );
                    if ( !arguments.equals( "" ) ){
                        ItemIterator iterator = new ItemIterator( arguments );
                        do {
                            Item item = iterator.getNext();
                            if ( null == item ){
                                break;
                            }
                            if ( item.isArgument() || item.isComma() ){
                                if ( item.isArgument() ){
                                    element.addParameter( item.getEntry() );
                                }
                            }
                            else {
                                throw new TestStepScriptException();
                            }
                        }
                        while ( true );
                    }
                }
                else {
                    throw new TestStepScriptException( "bad script:  " + content );
                }
            }
        }
        catch ( Exception ex ){
            throw new TestStepScriptException( ex );
        }
        return element;
    }
}

class ChainElement{
    private String methodName;
    private List<AbstractMap.SimpleEntry<Class, Object>> parameterList = new LinkedList<AbstractMap.SimpleEntry<Class, Object>>();

    public ChainElement (){
    }

    public boolean iExist (){
        return this.methodName != null;
    }

    public String getMethodName (){
        return methodName;
    }

    public void setMethodName ( String methodName ){
        this.methodName = methodName;
    }

    public List<AbstractMap.SimpleEntry<Class, Object>> getParameterList (){
        return parameterList;
    }

    public void addParameter ( AbstractMap.SimpleEntry<Class, Object> item ){
        this.parameterList.add( item );
    }

    public Class[] getClasses (){
        if ( parameterList.isEmpty() ){
            return new Class[ 0 ];
        }
        else {
            Class[] classes = new Class[ parameterList.size() ];
            for ( int i = 0; i < classes.length; i++ ){
                classes[ i ] = parameterList.get( i ).getKey();
            }
            return classes;
        }
    }

    public Object[] getParameters (){
        if ( parameterList.isEmpty() ){
            return new Object[ 0 ];
        }
        else {
            Object[] objects = new Object[ parameterList.size() ];
            for ( int i = 0; i < objects.length; i++ ){
                objects[ i ] = parameterList.get( i ).getValue();
            }
            return objects;
        }
    }

    @Override
    public String toString (){
        StringBuilder builder = new StringBuilder();
        builder.append( String.format( "\tMethod name: \"%s\"\n", this.methodName ) );
//        builder.append( "[" );
        for ( Map.Entry<Class, Object> entry : this.parameterList ){
            builder.append("\t\t[").append( entry.toString() ).append( "]\n" );
        }
//        builder.append( "" );
        return builder.toString();
    }
}

class ItemIterator{

    private static Pattern pattern    = Pattern.compile( "^(\\s*((true|false)|(\"[^\"]*\")|([+-]?\\d+\\.\\d+)|([+-]?\\d+)|(null)|(,))\\s*)" );
    private static Pattern rndPattern = Pattern.compile( "\\$rnd\\.(.+?)(?:\\.(-?\\d+)\\.(\\d+)?([^;]+)?);" );
    private static Pattern storedDataPattern = Pattern.compile( "\\$data\\.([-: \\w]+);" );
    private String content;

    public ItemIterator ( String content ){
        this.content = content;
    }

    public Item getNext () throws TestStepScriptException{
        try {
            Matcher matcher = pattern.matcher( content );
            if ( matcher.find() ){
                String group = matcher.group( 1 );
                if ( matcher.group( 8 ) != null ){
                    this.content = this.content.substring( 1 ).trim();
                    return new Item( ItemType.token_comma );
                }
                else {
                    this.content = this.content.substring( group.length() );
                    if ( matcher.group( 3 ) != null ){
                        Item item;
                        item = new Item( ItemType.token_boolean,
                                group,
                                new AbstractMap.
                                        SimpleEntry<Class, Object>( Boolean.class, Boolean.valueOf( group.trim() ) ) );
                        return item;
                    }
                    else if ( matcher.group( 4 ) != null ){
                        return new Item( ItemType.token_string,
                                group,
                                new AbstractMap.
                                        SimpleEntry<Class, Object>( String.class, this.prepareStringParameter( group ) ) );
                    }
                    else if ( matcher.group( 5 ) != null ){
                        return new Item( ItemType.token_double,
                                group,
                                new AbstractMap.
                                        SimpleEntry<Class, Object>( Double.class, Double.valueOf( group.trim() ) ) );
                    }
                    else if ( matcher.group( 6 ) != null ){
                        return new Item( ItemType.token_integer,
                                group,
                                new AbstractMap.
                                        SimpleEntry<Class, Object>( Integer.class, Integer.valueOf( group.trim() ) ) );
                    }
                    else if ( matcher.group( 7 ) != null ){
                        return new Item( ItemType.token_null_string,
                                group,
                                new AbstractMap.
                                        SimpleEntry<Class, Object>( String.class, null ) );
                    }
                }
            }
            if ( content.trim().isEmpty() ){
                return null;
            }
            else {
                throw new TestStepScriptException( "bad script:  " + content );
            }
        }
        catch ( Exception ex ){
            throw new TestStepScriptException( ex );
        }
    }

    private String prepareStringParameter( String str ){
        return this.processRandomString( this.processDataString( str ) ).replaceAll( "\"", "" ).replaceAll( "(?<!\\\\)'", "\"" ).replaceAll( "\\\\'", "'" ).trim();
    }

    private String processDataString ( String str ){
        Matcher matcher;
        String res = str;
        for ( matcher = storedDataPattern.matcher( res ); matcher.find(); matcher = storedDataPattern.matcher( res ) ){
            res = matcher.replaceAll( TestData.get( matcher.group( 1 ) ) );
        }
        return res;
    }


    private String processRandomString ( String str ){
        Matcher matcher;
        String res = str;
        for ( matcher = rndPattern.matcher( res ); matcher.find(); matcher = rndPattern.matcher( res ) ){
            if ( null != matcher.group( 4 ) ){
                res = matcher.replaceFirst( new RndEngine( matcher.group( 1 ), Integer.parseInt( matcher.group( 2 ) ), matcher.group( 4 ) ).generate() );
            }
            else if ( null != matcher.group( 2 ) ){
                res = matcher.replaceFirst( new RndEngine( matcher.group( 1 ), Integer.parseInt( matcher.group( 2 ) ), Integer.parseInt( matcher.group( 3 ) ) ).generate() );
            }
            else if ( null != matcher.group( 1 ) ){
                res = matcher.replaceFirst( new RndEngine( matcher.group( 1 ) ).generate() );
            }
        }
        return res;
    }
}

class Item{

    private ItemType itemType;
    private String   content;
    AbstractMap.SimpleEntry<Class, Object> entry;

    protected Item ( ItemType itemType ){
        this.itemType = itemType;
    }

    public Item ( ItemType itemType, String content, AbstractMap.SimpleEntry<Class, Object> entry ){
        this.itemType = itemType;
        this.content = content;
        this.entry = entry;
    }

    public Item ( ItemType itemType, String content ){
        this.itemType = itemType;
        this.content = content;
    }

    public ItemType getItemType (){
        return itemType;
    }

    public String getContent (){
        return content;
    }

    public AbstractMap.SimpleEntry<Class, Object> getEntry (){
        return entry;
    }

    public boolean isComma (){
        return this.getItemType() == ItemType.token_comma;
    }

    public boolean isArgument (){
        return this.getItemType() == ItemType.token_string ||
                this.getItemType() == ItemType.token_double ||
                this.getItemType() == ItemType.token_integer ||
                this.getItemType() == ItemType.token_boolean ||
                this.getItemType() == ItemType.token_null_string;
    }

    public String toString (){
        return String.format( "{\r\n\t'type':'%s',\r\n\t'content':'%s'\r\n}\r\n", this.getItemType(), this.getContent() );
    }
}

enum ItemType{
    token_comma,
    token_string,
    token_integer,
    token_double,
    token_boolean,
    token_null_string
}
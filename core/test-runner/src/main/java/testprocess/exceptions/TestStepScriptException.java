package testprocess.exceptions;

/**
 *
 */
public class TestStepScriptException extends Exception{

    public TestStepScriptException (){
    }

    public TestStepScriptException ( String message ){
        super( message );
    }

    public TestStepScriptException ( String message, Throwable cause ){
        super( message, cause );
    }

    public TestStepScriptException ( Throwable cause ){
        super( cause );
    }
}

package selenium.exceptions;

/**
 * Exception for illegal access to elements action in Element.
 * E.g. setValue for Button
 */
public class IllegalElementActionException extends TestExecutionRuntimeException{

    public IllegalElementActionException (){
        super();
    }

    public IllegalElementActionException ( String message ){
        super( message );
    }

}

package utils.exceptions;

public class StartParameterException extends Exception{

    public StartParameterException (){
    }

    public StartParameterException ( String message ){
        super( message );
    }

    public StartParameterException ( String message, Throwable cause ){
        super( message, cause );
    }

    public StartParameterException ( Throwable cause ){
        super( cause );
    }
}

package testprocess.exceptions;

public class DriverManagerException extends Exception{

    public DriverManagerException (){
    }

    public DriverManagerException ( String message ){
        super( message );
    }

    public DriverManagerException ( String message, Throwable cause ){
        super( message, cause );
    }

    public DriverManagerException ( Throwable cause ){
        super( cause );
    }
}

package selenium.exceptions;

public class SeleniumFacilitiesException extends Exception{

    public SeleniumFacilitiesException (){
        super();
    }

    public SeleniumFacilitiesException ( Throwable ex ){
        super( ex );
    }

    public SeleniumFacilitiesException ( String message ){
        super( message );
    }

    public SeleniumFacilitiesException ( String message, Throwable ex ){
        super( message, ex );
    }

}

package selenium.support.exceptions;

import selenium.exceptions.SeleniumFacilitiesException;

public class ElementInitialisationException extends SeleniumFacilitiesException{

    private final static String MSG = "Element initialisation exception";

    public ElementInitialisationException (){
        super( ElementInitialisationException.MSG );
    }

    public ElementInitialisationException ( Throwable ex ){
        super( ElementInitialisationException.MSG, ex );
    }
}

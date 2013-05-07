package selenium.support.exceptions;

import selenium.exceptions.SeleniumFacilitiesException;

public class NoElementFindByAnnotationException extends SeleniumFacilitiesException{

    public NoElementFindByAnnotationException (){
        super( "There is nothing about how to find the element" );
    }
}

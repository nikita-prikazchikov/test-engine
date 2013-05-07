package selenium.support.exceptions;

import selenium.exceptions.SeleniumFacilitiesException;

public class NoLabelFindByAnnotationException extends SeleniumFacilitiesException{

    public NoLabelFindByAnnotationException (){
        super( "There is nothing about how to find the element's label" );
    }
}

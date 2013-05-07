package selenium.elements.basic;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import selenium.Element;
import selenium.elements.AbstractElement;
import selenium.exceptions.IllegalElementActionException;
import selenium.exceptions.TestExecutionRuntimeException;
import selenium.log.iResult;

public class Button extends AbstractElement{

    private static final String STR_ELEMENT_NAME = "button";

    public Button (){
        super();
        this.setElementName( STR_ELEMENT_NAME );
    }

    public Button ( WebElement element, String name ){
        super( element, name );
        this.setElementName( STR_ELEMENT_NAME );
    }

    public Button ( SearchContext parent, By by, String name ){
        super( parent, by, name );
        this.setElementName( STR_ELEMENT_NAME );
    }

    public Button ( Element parent, By by, String name ){
        super( parent, by, name );
        this.setElementName( STR_ELEMENT_NAME );
    }

    public Button ( By by, String name ){
        super( by, name );
        this.setElementName( STR_ELEMENT_NAME );
    }

    public iResult setValue ( String value ) throws TestExecutionRuntimeException{
        throw new IllegalElementActionException( String.format( "Unable to set value for %s", this.getElementName() ) );
    }
}

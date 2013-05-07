package selenium.elements.basic;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import selenium.Element;
import selenium.elements.AbstractElement;
import selenium.exceptions.IllegalElementActionException;
import selenium.exceptions.TestExecutionRuntimeException;
import selenium.log.iResult;

public class Text extends AbstractElement{

    private static final String STR_ELEMENT_NAME = "text (input)";

    public Text (){
        super();
        this.setElementName( STR_ELEMENT_NAME );
    }

    public Text ( WebElement element, String name ){
        super( element, name );
        this.setElementName( STR_ELEMENT_NAME );
    }

    public Text ( SearchContext parent, By by, String name ){
        super( parent, by, name );
        this.setElementName( STR_ELEMENT_NAME );
    }

    public Text ( Element parent, By by, String name ){
        super( parent, by, name );
        this.setElementName( STR_ELEMENT_NAME );
    }

    public Text ( By by, String name ){
        super( by, name );
        this.setElementName( STR_ELEMENT_NAME );
    }
}

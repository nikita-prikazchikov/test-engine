package selenium.elements.basic;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import selenium.Element;
import selenium.elements.AbstractElement;
import selenium.exceptions.IllegalElementActionException;
import selenium.exceptions.TestExecutionRuntimeException;
import selenium.log.ResultList;
import selenium.log.iResult;
import utils.TestData;

public class Select extends AbstractElement{

    private static final String STR_ELEMENT_NAME = "select";

    public Select (){
        super();
        this.setElementName( STR_ELEMENT_NAME );
    }

    public Select ( WebElement element, String name ){
        super( element, name );
        this.setElementName( STR_ELEMENT_NAME );
    }

    public Select ( SearchContext parent, By by, String name ){
        super( parent, by, name );
        this.setElementName( STR_ELEMENT_NAME );
    }

    public Select ( Element parent, By by, String name ){
        super( parent, by, name );
        this.setElementName( STR_ELEMENT_NAME );
    }

    public Select ( By by, String name ){
        super( by, name );
        this.setElementName( STR_ELEMENT_NAME );
    }

    @Override
    public String getValue () throws TestExecutionRuntimeException{
        return this.getElement().findElement( By.cssSelector( "option:checked" ) ).getText();
    }

    @Override
    public iResult setValue ( String value ) throws TestExecutionRuntimeException{

        TestData.put( this.getName(), value );

        ResultList rl = new ResultList( String.format( "Set element [%s] value: [%s]", this.getName(), value ) );
        HtmlElement option = new HtmlElement(
                this.getElement().findElement( By.xpath( String.format( "//option[contains(text(),'%s')]", value ) ) ),
                String.format( "option [%s]", value ) );
        rl.push( option.click() );
        rl.push( this.verifyValue( value ), true );
        return rl;
    }
}

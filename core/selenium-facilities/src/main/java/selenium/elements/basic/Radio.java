package selenium.elements.basic;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import selenium.Element;
import selenium.elements.AbstractElement;
import selenium.exceptions.ElementNotFoundException;
import selenium.exceptions.IllegalElementActionException;
import selenium.exceptions.TestExecutionRuntimeException;
import selenium.log.Result;
import selenium.log.ResultList;
import selenium.log.iResult;
import testInterface.objects.utils.StepExStatus;
import utils.TestData;

public class Radio extends AbstractElement{

    public static final String VALUE_ON = "on";

    public static final String VALUE_OFF = "off";

    private static final String STR_ELEMENT_NAME = "checkbox";

    private static final String STR_MSG_SET_CHECKBOX = "Set checkbox [%s] to: [%s]";

    private static final String STR_MSG_CHECKBOX_ALREADY_SET = "The checkbox [%s] already set to: [%s]";

    public Radio (){
        super();
        this.setElementName( STR_ELEMENT_NAME );
    }

    public Radio ( WebElement element, String name ){
        super( element, name );
        this.setElementName( STR_ELEMENT_NAME );

    }

    public Radio ( SearchContext parent, By by, String name ){
        super( parent, by, name );
        this.setElementName( STR_ELEMENT_NAME );

    }

    public Radio ( By by, String name ){
        super( by, name );
        this.setElementName( STR_ELEMENT_NAME );

    }

    public Radio ( Element parent, By by, String name ){
        super( parent, by, name );
        this.setElementName( STR_ELEMENT_NAME );
    }

    public String getValue () throws ElementNotFoundException{
        return this.getElement().isSelected() ? VALUE_ON : VALUE_OFF;
    }

    public ResultList setValue ( String value ) throws TestExecutionRuntimeException{

        if ( !( value.equals( VALUE_OFF ) || value.equals( VALUE_ON ) ) ){
            throw new IllegalElementActionException( String.format( "Unexpected checkbox value. Please use %s or %s like constants from checkbox class", VALUE_ON, VALUE_OFF ) );
        }
        TestData.put( this.getName(), value );
        ResultList result = new ResultList( String.format( Radio.STR_MSG_SET_CHECKBOX, this._name, value ) );
        try {
            if ( !this.getValue().equals( value ) ){
                result.push( this.click(), true );
            }
            else {
                result.push( new Result( StepExStatus.passed, String.format( Radio.STR_MSG_CHECKBOX_ALREADY_SET, this._name, value ) ), true );
            }
        }
        catch ( Exception ex ){
            result.push( ex, true );
        }
        return result;
    }

    public iResult setValue ( Boolean value ) throws TestExecutionRuntimeException{
        return this.setValue( value ? VALUE_ON : VALUE_OFF );
    }

    public iResult verifyValue ( Boolean value ) throws TestExecutionRuntimeException{
        return this.verifyValue( value ? VALUE_ON : VALUE_OFF );
    }
}

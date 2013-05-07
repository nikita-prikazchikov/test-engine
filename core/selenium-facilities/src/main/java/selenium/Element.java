package selenium;

import org.openqa.selenium.WebElement;
import selenium.exceptions.ElementNotFoundException;
import selenium.exceptions.IllegalElementActionException;
import selenium.exceptions.SeleniumFacilitiesException;
import selenium.log.iResult;
import selenium.exceptions.TestExecutionRuntimeException;

/**
 * Created by IntelliJ IDEA.
 * User: nprikazchikov
 * Date: 01.02.12
 * Time: 17:12
 * To change this template use File | Settings | File Templates.
 */
public interface Element extends ContextObject {
    public boolean isFound();
    public Element find(Object... params);
    public WebElement getElement() throws ElementNotFoundException;
    public String getName();
    public String getValue() throws TestExecutionRuntimeException;
    public iResult setValue( String value ) throws TestExecutionRuntimeException;
    public iResult setValue( Boolean value ) throws TestExecutionRuntimeException;
    public iResult setValue( String... values ) throws TestExecutionRuntimeException;
    /**
     * Verify value of element. Expected value should be received from internal data
     * @return iResult
     * @throws TestExecutionRuntimeException
     */
    public iResult verifyValue() throws TestExecutionRuntimeException;
    public iResult verifyValue( Boolean value ) throws TestExecutionRuntimeException;
    public iResult verifyValue( String value ) throws TestExecutionRuntimeException;
    public iResult click () throws TestExecutionRuntimeException;
    public iResult doubleClickDomElement () throws TestExecutionRuntimeException;
    public iResult contextClick () throws TestExecutionRuntimeException;
    public iResult clickDomElement ();
    public iResult clickDomElementAndWait();
    public iResult clickDomElementAndWait( long timeout );
    public iResult clickAndWait() throws TestExecutionRuntimeException;
    public iResult clickAndWait( long timeout ) throws TestExecutionRuntimeException;
    public boolean isValid () throws TestExecutionRuntimeException;
    public iResult isReadonly ( Boolean expected ) throws TestExecutionRuntimeException;
    public iResult verifyVisibility(boolean expected) throws TestExecutionRuntimeException;
    public iResult verifyIsMandatory(Boolean expected) throws TestExecutionRuntimeException;
}

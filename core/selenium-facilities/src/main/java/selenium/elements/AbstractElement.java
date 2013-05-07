package selenium.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import selenium.Element;
import selenium.bots.AbstractBot;
import selenium.bots.ActionBot;
import selenium.bots.VerifyBot;
import selenium.bots.WaitBot;
import selenium.bots.interfaces.ActionBotInterface;
import selenium.bots.interfaces.VerifyBotInterface;
import selenium.bots.interfaces.WaitBotInterface;
import selenium.exceptions.ElementNotFoundException;
import selenium.exceptions.IllegalElementActionException;
import selenium.exceptions.SeleniumFacilitiesException;
import selenium.exceptions.TestExecutionRuntimeException;
import selenium.log.Result;
import selenium.log.ResultList;
import selenium.log.iResult;
import selenium.support.exceptions.NoElementFindByAnnotationException;
import selenium.support.pagefactory.ElementAnnotations;
import testInterface.objects.utils.StepExStatus;
import utils.TestData;

public abstract class AbstractElement implements Element{

    protected ActionBotInterface _actionBot;

    protected ElementAnnotations _annotations;

    protected By _by;

    protected WebElement _element;

    protected String _elementName;

    protected String _name;

    protected SearchContext _parent;

    protected VerifyBotInterface _verifyBot;

    protected WaitBotInterface _waitBot;

    public AbstractElement (){
        this._actionBot = new ActionBot();
        this._waitBot = new WaitBot();
        this._verifyBot = new VerifyBot();
        this.setElementName( "base element" );
    }

    public AbstractElement ( String name ){
        this();

        this._name = name;
    }

    public AbstractElement ( WebElement element, String name ){
        this();

        this._name = name;
        this._element = element;
    }

    public AbstractElement ( SearchContext parent, By by, String name ){
        this();

        this._name = name;
        this._parent = parent;
        this._by = by;
        this.find();
    }

    public AbstractElement ( Element parent, By by, String name ){
        this();

        this._name = name;
        try {
            this._parent = parent.getElement();
            this._by = by;
            this.find();
        }
        catch ( Exception ex ){
            ex.printStackTrace();
        }
    }

    public AbstractElement ( By by, String name ){
        this();

        this._name = name;
        try {
            this._parent = AbstractBot.getDriver();
            this._by = by;
            this.find();
        }
        catch ( Exception ex ){
            ex.printStackTrace();
        }
    }

    /**
     * Check if the element is readonly. Element is readonly if class value equals to 'leaf_inputbox_readonly'
     *
     * @return True / False
     * @throws selenium.exceptions.ElementNotFoundException
     *
     */
    protected boolean _isReadonly () throws TestExecutionRuntimeException{
        throw new IllegalElementActionException( String.format( "Unable to check is valid for %s", this.getElementName() ) );

    }

    public ActionBotInterface getActionBot (){
        return _actionBot;
    }

    public String getByToString (){
        return this._by.toString();
    }

    public String getElementName (){
        return _elementName;
    }

    public VerifyBotInterface getVerifyBot (){
        return _verifyBot;
    }

    public WaitBotInterface getWaitBot (){
        return _waitBot;
    }

    /**
     * Check is the element was found.
     *
     * @return True - element is not null, False - element is null.
     */
    public boolean isFound (){
        return this._element != null;
    }

    /**
     * Look for WebElement if parent WebElement / SearchContext and By were set. And set element field.
     *
     * @return True if a WebElement is found or False if it is not found.
     */
    public Element find ( Object... params ){
        if ( this._by != null && this._parent != null ){
            this._element = this.getActionBot().findElement( this._parent, this._by );
        }
        else if ( this._by == null && this._parent != null && this._annotations != null ){
            By by = null;
            try {
                if ( this.getName().contains( "%" ) ){
                    try {
                        this.setName( String.format( this.getName(), params ) );
                    }
                    catch ( Exception ignored ){
                    }
                }
                this._by = this._annotations.buildBy( params );
                return this.find();
            }
            catch ( NoElementFindByAnnotationException ex ){
                ex.printStackTrace();
            }
        }
        return this;
    }

    /**
     * Returns WebElement object. If WebElement is null (was not found) it throws an ElementNotFoundException exception.
     *
     * @return WebElement object
     * @throws selenium.exceptions.ElementNotFoundException
     *
     */
    public WebElement getElement () throws ElementNotFoundException{
        if ( !this.isFound() ){
            throw new ElementNotFoundException( this._name );
        }
        return this._element;
    }

    /**
     * Returns element' name
     *
     * @return
     */
    public String getName (){
        return this._name;
    }

    public String getValue () throws TestExecutionRuntimeException{
        return this.getElement().getAttribute( "value" );
    }

    public iResult setValue ( String value ) throws TestExecutionRuntimeException{
        TestData.put( this.getName(), value );
        return new ResultList( String.format( "Set element [%s] value: [%s]", this.getName(), value ) )
                .push( this._actionBot.setValue( this.getElement(), this._name, value ), true )
                .push( this.verifyValue( value ), true );
    }

    public iResult setValue ( Boolean value ) throws TestExecutionRuntimeException{
        throw new IllegalElementActionException( String.format( "Unable to set boolean value for %s", this.getElementName() ) );
    }

    public iResult setValue ( String... values ) throws TestExecutionRuntimeException{
        throw new IllegalElementActionException( String.format( "Unable to set value list for %s", this.getElementName() ) );
    }

    public iResult verifyValue () throws TestExecutionRuntimeException{
        return this.verifyValue( TestData.get( this.getName() ) );
    }

    public iResult verifyValue ( Boolean value ) throws TestExecutionRuntimeException{
        throw new IllegalElementActionException( String.format( "Unable to verify Boolean value for %s", this.getElementName() ) );
    }

    /**
     * Verifies value of the WebElement. It uses Element.getValue() method to get value.
     *
     * @param value String
     * @return Result object
     */
    public iResult verifyValue ( String value ) throws TestExecutionRuntimeException{
        return this._verifyBot.verifyEqual( value, this.getValue(), String.format( "Element [%s]", this._name ) );
    }

    /**
     * Fires native click method of the WebElement object. Override this if it is make sense.
     *
     * @return Result object
     */
    public iResult click () throws TestExecutionRuntimeException{
        return this._actionBot.click( this.getElement(), this.getElementName(), this.getName() );
    }

    public iResult doubleClickDomElement () throws TestExecutionRuntimeException{
        try {
            return this._actionBot.doubleClickDOMElement( this.getElement(), this._name );
        }
        catch ( ElementNotFoundException ex ){
            return new Result( StepExStatus.failed, ex.getMessage() );
        }
    }

    public iResult contextClick () throws TestExecutionRuntimeException{
        try {
            return this._actionBot.contextClickDOMElement( this.getElement(), this._name );
        }
        catch ( ElementNotFoundException ex ){
            return new Result( StepExStatus.failed, ex.getMessage() );
        }
    }

    public iResult clickDomElement (){
        try {
            return this._actionBot.clickDOMElement( this.getElement(), this._name );
        }
        catch ( ElementNotFoundException ex ){
            return new Result( StepExStatus.failed, ex.getMessage() );
        }
    }

    public iResult clickDomElementAndWait (){
        return this.clickDomElementAndWait( 0 );
    }

    public iResult clickDomElementAndWait ( long timeout ){
        try {
            return this._actionBot.clickDOMElementAndWait( this.getElement(), this._name );
        }
        catch ( ElementNotFoundException ex ){
            return new Result( StepExStatus.failed, ex.getMessage() );
        }
    }

    /**
     * Clicks and wait for loading mask disappears.
     *
     * @return Result object.
     */
    public iResult clickAndWait () throws TestExecutionRuntimeException{
        return this.clickAndWait( 0 );
    }

    public iResult clickAndWait ( long timeout ) throws TestExecutionRuntimeException{
        iResult r = this.click();
        this._waitBot.waitForTime( timeout );
        this._actionBot.waitLoading();
        return r;
    }

    public boolean isValid () throws TestExecutionRuntimeException{
        return true;
    }

    /**
     * Verifies if the element is readonly.
     *
     * @param expected True = is readonly, False = is not readonly
     * @return Result object
     */
    public iResult isReadonly ( Boolean expected ) throws TestExecutionRuntimeException{
        return new Result(
                this._isReadonly() == expected ? StepExStatus.passed : StepExStatus.failed,
                String.format( "Verify if the element '%s' is readonly", this._name )
        );
    }

    /**
     * Verify if the WebElement is visible.
     *
     * @param expected True = is visible, False = is not visible.
     * @return Result object.
     */
    public iResult verifyVisibility ( boolean expected ){
        WebElement e = null;
        try {
            e = this.getElement();
        }
        catch ( ElementNotFoundException ex ){
            e = null;
        }
        return this._verifyBot.verifyWebElementVisibility( e, expected, this._name );
    }

    public iResult verifyIsMandatory ( Boolean expected ) throws IllegalElementActionException{
        throw new IllegalElementActionException( String.format( "Unable to define mandatory option for %s", this.getElementName() ) );
    }

    public iResult isIllegalValue ( Boolean expected ) throws SeleniumFacilitiesException{
        throw new IllegalElementActionException( String.format( "Unable to define illegal value for %s", this.getElementName() ) );
    }

    public void setActionBot ( ActionBotInterface _actionBot ){
        this._actionBot = _actionBot;
    }

    public void setElementName ( String _elementName ){
        this._elementName = _elementName;
    }

    public void setName ( String _name ){
        this._name = _name;
    }

    public void setVerifyBot ( VerifyBotInterface _verifyBot ){
        this._verifyBot = _verifyBot;
    }

    public void setWaitBot ( WaitBotInterface _waitBot ){
        this._waitBot = _waitBot;
    }
}

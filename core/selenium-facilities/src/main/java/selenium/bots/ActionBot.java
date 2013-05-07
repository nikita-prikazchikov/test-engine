package selenium.bots;

import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import selenium.bots.interfaces.ActionBotInterface;
import selenium.exceptions.IllegalElementActionException;
import selenium.exceptions.TestExecutionRuntimeException;
import selenium.exceptions.WebDriverNullException;
import selenium.log.Result;
import selenium.log.ResultList;
import selenium.log.iResult;
import testInterface.objects.utils.StepExStatus;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

public class ActionBot extends AbstractBot implements ActionBotInterface{

    private static final String STR_MSG_TO_URL = "Navigate to [%s]";

    private static final String STR_MSG_CLICK = "Click [%s]";

    private static final String STR_MSG_CLICK_TYPE = "Click %s [%s]";

    private static final String STR_MSG_CLICK_RANDOM_ELEMENT = "Click random element [%s]";

    private static final String STR_MSG_ENTER_VALUE = "Enter value '%s': [%s]";

    private static final String STR_MSG_SEND_KEYS = "Send keys '%s': [%s]";

    private static final String STR_MSG_CLEAR_ELEMENT = "Clear element '%s'";

    private static final String STR_MSG_CLEAR_ELEMENT_CTRL_A_DELETE = "Clear element '%s' by pressing {CTRL}+A -> {DELETE}.";

    private static final String STR_MSG_IS_SELECTED_CHECKBOX = "Checkbox '%s' has value: [%s]";

    private static final String STR_MSG_DRAG_AND_DROP = "Drag and drop [%s] to [%s]";

    private static final String STR_MSG_DRAG_AND_DROP_BY = "Drag and drop [%s] to X: [%s], Y: [%s]";

    private static final String STR_MSG_ROBOT_DRAG_AND_DROP = "Drag and drop from [%s, %s] to [%s, %s]";

    private static final String STR_MSG_ROBOT_CLICK_ELEMENT = "Click element [%s]";

    private static final String STR_MSG_ROBOT_CLICK_AT = "Click at [%s, %s]";

    private static final String STR_MSG_ROBOT_DOUBLE_CLICK_AT = "Double click at [%s, %s]";

    private static final String STR_MSG_ROBOT_CONTEXT_CLICK_AT = "Context click at [%s, %s]";

    private static final String STR_MSG_ROBOT_MOVE_MOUSE_TO = "Move mouse to [%s, %s]";

    private static final String STR_MSG_CLICK_IN_THE_MIDDLE = "Click in the middle of the [%s] element";

    private static final String STR_MSG_DOUBLE_CLICK_IN_THE_MIDDLE = "Double click in the middle of the [%s] element";

    private static final String STR_MSG_CONTEXT_CLICK_IN_THE_MIDDLE = "Right click (context click) in the middle of the [%s] element";

    /**
     * Clear element.
     *
     * @param element
     * @return
     */
    private StepExStatus _clear ( WebElement element ){
        StepExStatus status = StepExStatus.failed;
        try {
            if ( this.isElementDisplayed( element ) ){
                element.clear();
                status = StepExStatus.passed;
            }
        }
        catch ( Exception ex ){
            status = StepExStatus.failed;
            ex.printStackTrace();
        }
        return status;
    }

    private StepExStatus _clear_Ctrl_A_Delete ( WebElement element ){
        StepExStatus status = StepExStatus.failed;
        try {
            if ( this.isElementDisplayed( element ) ){
                this._clickDOMElement( element );
                element.sendKeys( Keys.CONTROL, "a" );
                element.sendKeys( Keys.DELETE );
                status = StepExStatus.passed;
            }
        }
        catch ( Exception ex ){
            status = StepExStatus.failed;
            ex.printStackTrace();
        }
        return status;
    }

    /**
     * Clicks control.
     *
     * @param element
     * @return
     */
    private StepExStatus _click ( WebElement element ){
        StepExStatus status = StepExStatus.failed;
        try {
            if ( this.isElementDisplayed( element ) ){
                element.click();
                status = StepExStatus.passed;
            }
        }
        catch ( Exception ex ){
            status = StepExStatus.failed;
            ex.printStackTrace();
        }
        return status;
    }

    /**
     * Clicks in the middle of the given element.
     *
     * @param element
     * @return
     */
    private StepExStatus _clickDOMElement ( WebElement element ){
        StepExStatus status = StepExStatus.failed;
        try {
            if ( this.isElementDisplayed( element ) ){
                AbstractBot.getActionsObject().click( element ).perform();
                status = StepExStatus.passed;
            }
        }
        catch ( Exception ex ){
            status = StepExStatus.failed;
            ex.printStackTrace();
        }
        return status;
    }

    /**
     * Clicks in the middle of the given element to open context menu.
     *
     * @param element
     * @return
     */
    private StepExStatus _contextClickDOMElement ( WebElement element ){
        StepExStatus status = StepExStatus.failed;
        try {
            if ( this.isElementDisplayed( element ) ){
                AbstractBot.getActionsObject().contextClick( element ).perform();
                status = StepExStatus.passed;
            }
        }
        catch ( Exception ex ){
            status = StepExStatus.failed;
            ex.printStackTrace();
        }
        return status;
    }

    /**
     * Performs a double-click at middle of the given element.
     *
     * @param element
     * @return
     */
    private StepExStatus _doubleClickDOMElement ( WebElement element ){
        StepExStatus status = StepExStatus.failed;
        try {
            if ( this.isElementDisplayed( element ) ){
                AbstractBot.getActionsObject().doubleClick( element ).perform();
                status = StepExStatus.passed;
            }
        }
        catch ( Exception ex ){
            status = StepExStatus.failed;
            ex.printStackTrace();
        }
        return status;
    }

    private StepExStatus _dragAndDrop ( WebElement source, WebElement target ){
        StepExStatus status = StepExStatus.failed;
        try {
            if ( this.isElementDisplayed( source ) && this.isElementDisplayed( target ) ){
                AbstractBot.getActionsObject().dragAndDrop( source, target ).perform();
                status = StepExStatus.passed;
            }
        }
        catch ( Exception ex ){
            status = StepExStatus.failed;
            ex.printStackTrace();
        }
        return status;
    }

    private StepExStatus _dragAndDropBy ( WebElement source, int xOffset, int yOffset ){
        StepExStatus status = StepExStatus.failed;
        try {
            if ( this.isElementDisplayed( source ) ){
                AbstractBot.getActionsObject().dragAndDropBy( source, xOffset, yOffset ).perform();
                status = StepExStatus.passed;
            }
        }
        catch ( Exception ex ){
            status = StepExStatus.failed;
            ex.printStackTrace();
        }
        return status;
    }

    /**
     * Populates value.
     *
     * @param element
     * @param value
     * @return
     */
    private StepExStatus _enterValue ( WebElement element, String value ){
        StepExStatus status = StepExStatus.failed;
        try {
            if ( this.isElementDisplayed( element ) ){
                element.clear();
                element.sendKeys( value );
                status = StepExStatus.passed;
            }
        }
        catch ( Exception ex ){
            status = StepExStatus.failed;
            ex.printStackTrace();
        }
        return status;
    }

    /**
     * Checks if a controls is selected.
     *
     * @param element
     * @return <0 - Not Selected, 0 - Undefined, >0 Selected
     */
    private int _isSelected ( WebElement element ){
        int result = 0;
        try {
            if ( this.isElementDisplayed( element ) ){
                if ( element.isSelected() ){
                    result = 1;
                }
                else if ( !element.isSelected() ){
                    result = -1;
                }
                else {
                    result = 0;
                }
            }
        }
        catch ( Exception ex ){
            result = 0;
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * Send keys.
     *
     * @param element
     * @param value
     * @return
     */
    private StepExStatus _sendKeys ( WebElement element, CharSequence... value ){
        StepExStatus status = StepExStatus.failed;
        try {
            if ( this.isElementDisplayed( element ) ){
                element.sendKeys( value );
                status = StepExStatus.passed;
            }
        }
        catch ( Exception ex ){
            status = StepExStatus.failed;
            ex.printStackTrace();
        }
        return status;
    }

    protected Result robotClick ( Point point ){
        Result result = new Result( String.format( ActionBot.STR_MSG_ROBOT_CLICK_AT, point.getX(), point.getY() ) );
        try {
            Robot robot = new Robot();
            robot.mouseMove( point.getX(), point.getY() );
            robot.mousePress( InputEvent.BUTTON1_MASK );
            robot.mouseRelease( InputEvent.BUTTON1_MASK );

            result.setStatus( StepExStatus.passed );
        }
        catch ( Exception ex ){
            result.setStatus( StepExStatus.failed );
            ex.printStackTrace();
        }
        return result;
    }

    public Result alertAccept (){

        StepExStatus status = StepExStatus.failed;
        String text;
        try {
            this.waitForTime( 500 );
            Alert alert = AbstractBot.getDriver().switchTo().alert();
            text = "Accept " + alert.getText();
            alert.accept();
            this.waitLoading();
            status = StepExStatus.passed;
            ActionBot.switchToDefaultContent();
        }
        catch ( Exception ex ){
            status = StepExStatus.failed;
            ex.printStackTrace();
            text = "Unable to accept request";
        }
        return new Result( status, text );
    }

    public Result alertDismiss (){

        StepExStatus status = StepExStatus.failed;
        String text;
        try {
            this.waitForTime( 500 );
            Alert alert = AbstractBot.getDriver().switchTo().alert();
            text = "Dismiss " + alert.getText();
            alert.dismiss();
            WaitBot bot = new WaitBot();
            bot.waitForTime( 500 );
            bot.waitLoading();
            status = StepExStatus.passed;
            ActionBot.switchToDefaultContent();
        }
        catch ( Exception ex ){
            status = StepExStatus.failed;
            ex.printStackTrace();
            text = "Unable to dismiss request";
        }
        return new Result( status, text );
    }

    public Result clear ( WebElement element, String name ){
        return new Result( this._clear( element ), String.format( ActionBot.STR_MSG_CLEAR_ELEMENT, name ) );
    }

    public Result clear_Ctrl_A_Delete ( WebElement element, String name ){
        return new Result( this._clear_Ctrl_A_Delete( element ), String.format( ActionBot.STR_MSG_CLEAR_ELEMENT_CTRL_A_DELETE, name ) );
    }

    public Result click ( WebElement element, String name ){
        return new Result( this._click( element ), String.format( ActionBot.STR_MSG_CLICK, name ) );
    }

    public Result click ( WebElement element, String elementType, String name ){
        return new Result( this._click( element ), String.format( ActionBot.STR_MSG_CLICK_TYPE, elementType, name ) );
    }

    public Result click ( SearchContext parent, By bySelector, String name ){
        return this.click( this.findElement( parent, bySelector ), name );
    }

    public Result clickAndWait ( WebElement element, String name ){
        Result res = this.click( element, name );
        this.waitLoading();
        return res;
    }

    public Result clickAndWait ( SearchContext parent, By bySelector, String name ){
        Result res = this.click( this.findElement( parent, bySelector ), name );
        this.waitLoading();
        return res;
    }

    public Result clickAndWait ( By bySelector, String name ){
        Result res = this.click( this.findElement( bySelector ), name );
        this.waitLoading();
        return res;
    }

    public Result clickDOMElement ( WebElement element, String name ){
        return new Result( this._clickDOMElement( element ), String.format( ActionBot.STR_MSG_CLICK_IN_THE_MIDDLE, name ) );
    }

    public Result clickDOMElement ( SearchContext parent, By bySelector, String name ){
        return this.clickDOMElement( this.findElement( parent, bySelector ), name );
    }

    public Result clickDOMElementAndWait ( WebElement element, String name ){
        Result res = this.clickDOMElement( element, name );
        this.waitLoading();
        return res;
    }

    public Result clickDOMElementAndWait ( SearchContext parent, By bySelector, String name ){
        Result res = this.clickDOMElement( parent, bySelector, name );
        this.waitLoading();
        return res;
    }

    public iResult clickRandomElement ( List<WebElement> elements, String name ) throws TestExecutionRuntimeException{
        ResultList result = new ResultList( String.format( ActionBot.STR_MSG_CLICK_RANDOM_ELEMENT, name ) );
        if ( elements.size() > 0 ){
            WebElement weRandom = elements.get( new Random().nextInt( elements.size() ) );    // choose random element
            // click
            result.push(
                    new Result(
                            this._click( weRandom ),
                            String.format( ActionBot.STR_MSG_CLICK, weRandom.getText() ) )
            );
        }
        else {
            result.push( new Result( StepExStatus.failed, "There are no available elements" ) );
        }
        return result;
    }

    public Result contextClickDOMElement ( WebElement element, String name ){
        return new Result( this._contextClickDOMElement( element ), String.format( ActionBot.STR_MSG_CONTEXT_CLICK_IN_THE_MIDDLE, name ) );
    }

    public Result doubleClickDOMElement ( WebElement element, String name ){
        return new Result( this._doubleClickDOMElement( element ), String.format( ActionBot.STR_MSG_DOUBLE_CLICK_IN_THE_MIDDLE, name ) );
    }

    public Result doubleClickDOMElementAndWait ( WebElement element, String name ){
        Result res = new Result( this._doubleClickDOMElement( element ), String.format( ActionBot.STR_MSG_DOUBLE_CLICK_IN_THE_MIDDLE, name ) );
        this.waitLoading();
        return res;
    }

    public Result dragAndDrop ( WebElement source, WebElement target, String strSourceName, String strTargetName ){
        return new Result( this._dragAndDrop( source, target ), String.format( ActionBot.STR_MSG_DRAG_AND_DROP, strSourceName, strTargetName ) );
    }

    public Result dragAndDropBy ( WebElement source, Point pointOffset, String strTargetName, String strSourceName ){
        return new Result( this._dragAndDropBy( source, pointOffset.getX(), pointOffset.getY() ), String.format( ActionBot.STR_MSG_DRAG_AND_DROP_BY, strSourceName, pointOffset.getX(), pointOffset.getY() ) );
    }

    public boolean isSelectedCheckbox ( WebElement element, String name ) throws IllegalElementActionException{
        int selected = this._isSelected( element );
        if ( selected == 0 ){
            throw new IllegalElementActionException( String.format( ActionBot.STR_MSG_IS_SELECTED_CHECKBOX, name, "!undefined!" ) );
        }
        return ( selected > 0 );
    }

    public Result navigate ( String strUrl ) throws WebDriverNullException{
        System.out.println( String.format( "ActionBot: Navigate to [%s]", strUrl ) );
        AbstractBot.getDriver().get( strUrl );
        return new Result( StepExStatus.passed, String.format( ActionBot.STR_MSG_TO_URL, strUrl ) );
    }

    public iResult perform_CtrlA_Delete (){

        StepExStatus status = StepExStatus.failed;

        try {
            Robot robot = new Robot();

            robot.keyPress( KeyEvent.VK_CONTROL );
            robot.keyPress( KeyEvent.VK_A );
            this.waitForTime( 50 );
            robot.keyRelease( KeyEvent.VK_A );
            robot.keyRelease( KeyEvent.VK_CONTROL );
            // hit enter button
            robot.keyPress( KeyEvent.VK_DELETE );
            robot.keyRelease( KeyEvent.VK_DELETE );
            status = StepExStatus.passed;
        }
        catch ( Exception ex ){
            status = StepExStatus.failed;
            ex.printStackTrace();
        }
        return new Result( status, "Send Ctrl+A, Delete keys" );
    }

    public iResult robotClick ( WebElement element, String name ) throws TestExecutionRuntimeException{
        ResultList result = new ResultList( String.format( ActionBot.STR_MSG_ROBOT_CLICK_ELEMENT, name ) );
        try {
            if ( !this.isElementDisplayed( element ) ){
                throw new Exception( "Element is not displayed" );
            }

            //selenium.Window w = AbstractBot.getWindow();
            Point loc = element.getLocation();
            Dimension dim = element.getSize();
            Point pageLocation = AbstractBot.getPageLocation();
            Point p = new Point(
                    (int) ( pageLocation.getX() + loc.getX() + dim.getWidth() / 2 ),
                    (int) ( pageLocation.getY() + loc.getY() + dim.getHeight() / 2 )
            );
            result.push( this.robotClick( p ) );
        }
        catch ( Exception ex ){
            result.push( ex );
        }
        return result;
    }

    public iResult robotClick ( SearchContext parent, By by, String name ) throws TestExecutionRuntimeException{
        return this.robotClick( this.findElement( parent, by ), name );
    }

    public Result robotContextClick ( Point point ){
        Result result = new Result( String.format( ActionBot.STR_MSG_ROBOT_CONTEXT_CLICK_AT, point.getX(), point.getY() ) );
        try {
            Robot robot = new Robot();
            robot.mouseMove( point.getX(), point.getY() );
            robot.mousePress( InputEvent.BUTTON3_MASK );
            robot.mouseRelease( InputEvent.BUTTON3_MASK );

            result.setStatus( StepExStatus.passed );
        }
        catch ( Exception ex ){
            result.setStatus( StepExStatus.failed );
            ex.printStackTrace();
        }
        return result;
    }

    public Result robotDoubleClick ( Point point ){
        Result result = new Result( String.format( ActionBot.STR_MSG_ROBOT_DOUBLE_CLICK_AT, point.getX(), point.getY() ) );
        try {
            Robot robot = new Robot();
            robot.mouseMove( point.getX(), point.getY() );
            robot.mousePress( InputEvent.BUTTON1_MASK );
            robot.mouseRelease( InputEvent.BUTTON1_MASK );
            robot.mousePress( InputEvent.BUTTON1_MASK );
            robot.mouseRelease( InputEvent.BUTTON1_MASK );

            result.setStatus( StepExStatus.passed );
        }
        catch ( Exception ex ){
            result.setStatus( StepExStatus.failed );
            ex.printStackTrace();
        }
        return result;
    }

    public Result robotDragAndDrop ( Point from, Point to ){
        Result result = new Result( String.format( ActionBot.STR_MSG_ROBOT_DRAG_AND_DROP, from.getX(), from.getY(), to.getX(), to.getY() ) );
        try {
            Robot robot = new Robot();
            robot.setAutoDelay( 100 );

            robot.mouseMove( from.getX(), from.getY() );
            robot.mousePress( InputEvent.BUTTON1_MASK );
            robot.mouseMove( to.getX(), to.getY() );
            robot.mouseRelease( InputEvent.BUTTON1_MASK );

            result.setStatus( StepExStatus.passed );
        }
        catch ( Exception ex ){
            result.setStatus( StepExStatus.failed );
            ex.printStackTrace();
        }
        return result;
    }

    public Result robotHitButton ( int keyCode ){
        StepExStatus status;
        try {
            Robot robot = new Robot();
            robot.keyPress( keyCode );
            this.waitForTime( 50 );
            robot.keyRelease( keyCode );
            status = StepExStatus.passed;
        }
        catch ( Exception ex ){
            status = StepExStatus.failed;
            ex.printStackTrace();
        }
        return new Result( status, String.format( "Hit {%s} button", KeyEvent.getKeyText( keyCode ) ) );
    }

    public Result robotMoveMouseTo ( Point point ){
        Result result = new Result( String.format( ActionBot.STR_MSG_ROBOT_MOVE_MOUSE_TO, point.getX(), point.getY() ) );
        try {
            Robot robot = new Robot();
            robot.mouseMove( point.getX(), point.getY() );

            result.setStatus( StepExStatus.passed );
        }
        catch ( Exception ex ){
            result.setStatus( StepExStatus.failed );
            ex.printStackTrace();
        }
        return result;
    }

    public Result setValue ( WebElement element, String name, String value ){
        return new Result( this._enterValue( element, value ), String.format( ActionBot.STR_MSG_ENTER_VALUE, name, value ) );
    }

    public Result setValue ( SearchContext parent, By bySelector, String name, String value ){
        return this.setValue( this.findElement( parent, bySelector ), name, value );
    }

    public String stringArrayToString ( String... values ){
        StringBuilder sb = new StringBuilder();
        sb.append( "[ " );
        for ( int i = 0; i < values.length; i++ ){
            String str = values[ i ];
            if ( i != 0 ){
                sb.append( ", " );
            }
            sb.append( str );
        }
        sb.append( " ]" );
        return sb.toString();
    }

    public Result waitForTime ( int milliseconds ){
        WaitBot bot = new WaitBot();
        bot.waitForTime( milliseconds );
        return new Result( StepExStatus.passed, String.format( "Waiting for %s milliseconds done", milliseconds ) );
    }

    public Result waitLoading (){
        return this.waitLoading( 300 );
    }

    public Result waitLoading ( int milliseconds ){
        WaitBot bot = new WaitBot();
        bot.waitLoading( milliseconds );
        return new Result( StepExStatus.passed, "Waiting for loading done" );
    }

    public Result sendKeys ( WebElement element, String name, String value ){
        return new Result( this._sendKeys( element, value ), String.format( ActionBot.STR_MSG_SEND_KEYS, name, value ) );
    }

    public Result sendKeysToDOMElement ( WebElement element, String name, CharSequence... value ){
        StepExStatus status = StepExStatus.failed;
        try {
            boolean displayed = this.isElementDisplayed( element );
            if ( displayed ){
                AbstractBot.getActionsObject().sendKeys( element, value ).perform();
                status = StepExStatus.passed;
            }
        }
        catch ( Exception ex ){
            status = StepExStatus.failed;
            ex.printStackTrace();
        }
        return new Result( status, String.format( "Send keys to element [%s]", name ) );
    }
}

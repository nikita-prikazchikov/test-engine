package selenium.bots.interfaces;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import selenium.exceptions.IllegalElementActionException;
import selenium.exceptions.TestExecutionRuntimeException;
import selenium.exceptions.WebDriverNullException;
import selenium.log.Result;
import selenium.log.iResult;

import java.util.List;

public interface ActionBotInterface extends AbstractBotInterface{

    public Result alertAccept ();
    public Result alertDismiss ();
    public Result clear ( WebElement element, String name );
    public Result clear_Ctrl_A_Delete ( WebElement element, String name );
    public Result click ( WebElement element, String name );
    public Result click ( WebElement element, String type, String name );
    public Result click ( SearchContext parent, By bySelector, String name );
    public Result clickAndWait ( WebElement element, String name );
    public Result clickAndWait ( SearchContext parent, By bySelector, String name );
    public Result clickAndWait ( By bySelector, String name );
    public Result clickDOMElement ( WebElement element, String name );
    public Result clickDOMElement ( SearchContext parent, By bySelector, String name );
    public Result clickDOMElementAndWait ( WebElement element, String name );
    public Result clickDOMElementAndWait ( SearchContext parent, By bySelector, String name );
    public iResult clickRandomElement ( List<WebElement> elements, String name ) throws TestExecutionRuntimeException;
    public Result contextClickDOMElement ( WebElement element, String name );
    public Result doubleClickDOMElement ( WebElement element, String name );
    public Result doubleClickDOMElementAndWait ( WebElement element, String name );
    public Result dragAndDrop ( WebElement source, WebElement target, String strSourceName, String strTargetName );
    public Result dragAndDropBy ( WebElement source, Point pointOffset, String strTargetName, String strSourceName );
    public boolean isSelectedCheckbox ( WebElement element, String name ) throws IllegalElementActionException;
    public Result navigate ( String strUrl ) throws WebDriverNullException;
    public iResult perform_CtrlA_Delete ();
    public iResult robotClick ( WebElement element, String name ) throws TestExecutionRuntimeException;
    public iResult robotClick ( SearchContext parent, By by, String name ) throws TestExecutionRuntimeException;
    public Result robotContextClick ( Point point );
    public Result robotDoubleClick ( Point point );
    public Result robotDragAndDrop ( Point from, Point to );
    public Result robotHitButton ( int keyCode );
    public Result robotMoveMouseTo ( Point point );
    public Result sendKeys ( WebElement element, String name, String value );
    public Result sendKeysToDOMElement ( WebElement element, String name, CharSequence... value );
    public Result setValue ( WebElement element, String name, String value );
    public Result setValue ( SearchContext parent, By bySelector, String name, String value );
    public String stringArrayToString ( String... values );
    public Result waitForTime ( int milliseconds );
    public Result waitLoading ();
    public Result waitLoading ( int milliseconds );
}

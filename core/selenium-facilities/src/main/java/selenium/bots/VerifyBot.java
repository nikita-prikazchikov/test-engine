package selenium.bots;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import selenium.bots.interfaces.VerifyBotInterface;
import selenium.log.Result;
import testInterface.objects.utils.StepExStatus;

public class VerifyBot extends AbstractBot implements VerifyBotInterface{

    private static final String STR_MSG_EQUAL = "Value '%s'%scorrect. Expected: [%s], current: [%s]";

    private static final String STR_MSG_CONTAINS = "Value '%s'%scorrect. Expected: [%s], current: [%s]";

    private static final String STR_MSG_VISIBILITY = "Element '%s'%svisible";

    private static final String STR_MSG_ELEMENT_IS_NULL = "Element is NULL. Maybe it has not been found on page";

    public VerifyBot (){
    }

    protected Result _verifyWebElementVisibility ( boolean expected, boolean actual, String name ){
        StepExStatus status = ( expected == actual ? StepExStatus.passed : StepExStatus.failed );
        String msg = String.format( VerifyBot.STR_MSG_VISIBILITY, name, ( actual ? " " : " is not " ) );
        return new Result( status, msg );
    }

    protected String not ( boolean r ){
        return r ? " " : " not ";
    }

    public Result verifyContains ( String expected, String actual, String name ){
        boolean status = actual.contains( expected );
        return new Result(
                status ? StepExStatus.passed : StepExStatus.failed,
                String.format( VerifyBot.STR_MSG_CONTAINS, name, this.not( status ), expected, actual )
        );
    }

    public Result verifyEqual ( int expected, int actual, String name ){
        return this.verifyEqual( String.valueOf( expected ), String.valueOf( actual ), name );
    }

    public Result verifyEqual ( String expected, String actual, String name ){
        boolean status = actual.equals( expected );
        return new Result(
                status ? StepExStatus.passed : StepExStatus.failed,
                String.format( VerifyBot.STR_MSG_EQUAL, name, this.not( status ), expected, actual )
        );
    }

    /**
     * @param element
     * @param displayed True - should be visible, False - should not be visible.
     * @param name
     * @return
     */
    public Result verifyWebElementVisibility ( WebElement element, boolean displayed, String name ){
        return this._verifyWebElementVisibility( displayed, this.isElementDisplayed( element ), name );
    }

    public Result verifyWebElementVisibility ( SearchContext parent, By byElementSelector, boolean displayed, String name ){
        return this._verifyWebElementVisibility( displayed, this.isElementDisplayed( parent, byElementSelector ), name );
    }

    public Result verifyWebElementVisibility ( By byElementSelector, boolean displayed, String name ){
        return this._verifyWebElementVisibility( displayed, this.isElementDisplayed( byElementSelector ), name );
    }

}

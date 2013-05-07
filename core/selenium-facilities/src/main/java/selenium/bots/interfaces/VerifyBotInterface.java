package selenium.bots.interfaces;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import selenium.log.Result;

public interface VerifyBotInterface extends AbstractBotInterface{

    public Result verifyContains ( String expected, String actual, String name );
    public Result verifyEqual ( int expected, int actual, String name );
    public Result verifyEqual ( String expected, String actual, String name );
    /**
     * @param element
     * @param displayed True - should be visible, False - should not be visible.
     * @param name
     * @return
     */
    public Result verifyWebElementVisibility ( WebElement element, boolean displayed, String name );
    public Result verifyWebElementVisibility ( SearchContext parent, By byElementSelector, boolean displayed, String name );
    public Result verifyWebElementVisibility ( By byElementSelector, boolean displayed, String name );

}

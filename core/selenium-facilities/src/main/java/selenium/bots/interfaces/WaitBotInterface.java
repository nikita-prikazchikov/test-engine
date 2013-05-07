package selenium.bots.interfaces;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import selenium.exceptions.WebDriverNullException;
import selenium.log.Result;

import java.util.List;

public interface WaitBotInterface extends AbstractBotInterface{

    public long getTimeout ();
    /**
     * @param timeout Seconds
     */
    public WaitBotInterface setTimeout ( long timeout );
    public Result waitForTime ( long miliseconds );
    public WebElement waitForWebElement ( final SearchContext parent, final By bySelector );
    public WebElement waitForWebElement ( final By bySelector );
    public boolean waitForWebElement_Collapse ( final SearchContext parent, final By bySelector );
    public boolean waitForWebElement_Collapse ( final By bySelector );
    public Boolean waitForWebElement_Disappears ( final WebElement element );
    public Boolean waitForWebElement_Disappears ( final SearchContext parent, final By bySelector );
    public Boolean waitForWebElement_Disappears ( final By bySelector );
    public Boolean waitForWebElement_Displays ( final WebElement element );
    public Boolean waitForWebElement_Displays ( final SearchContext parent, final By bySelector );
    public Boolean waitForWebElement_Displays ( final By bySelector );
    public Boolean waitForWebElement_StopMoving ( final WebElement element );
    public Boolean waitForWebElement_StopMoving ( final SearchContext parent, final By bySelector ) throws WebDriverNullException;
    public Boolean waitForWebElement_StopMoving ( final By bySelector );
    public Boolean waitForWebElement_StopResizing ( final WebElement element );
    public Boolean waitForWebElement_StopResizing ( final SearchContext parent, final By bySelector );
    public Boolean waitForWebElement_StopResizing ( final By bySelector );
    public List<WebElement> waitForWebElements ( final SearchContext parent, final By bySelector, String name );
    public List<WebElement> waitForWebElements ( final By bySelector, String name );
    public Boolean waitLoading ();
    public Boolean waitLoading ( int milliseconds );
}

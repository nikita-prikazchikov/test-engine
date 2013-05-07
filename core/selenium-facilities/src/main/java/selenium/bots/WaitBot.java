package selenium.bots;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.bots.interfaces.WaitBotInterface;
import selenium.exceptions.WebDriverNullException;
import selenium.log.Result;
import testInterface.objects.utils.StepExStatus;

import java.util.List;

public class WaitBot extends AbstractBot implements WaitBotInterface{

    private static final String strMsgWaitForTime = "Wait for [%s] milliseconds";

    private long _timeout;

    /**
     * @param timeout Seconds
     */
    public WaitBot ( long timeout ){
        this._timeout = timeout;
    }

    public WaitBot (){
        this( 10 );
    }

    public long getTimeout (){
        return this._timeout;
    }

    /**
     * @param timeout Seconds
     */
    public WaitBot setTimeout ( long timeout ){
        this._timeout = timeout;
        return this;
    }

    public Result waitForTime ( long milliseconds ){
        try {
            Thread.sleep( milliseconds );
        }
        catch ( Exception ex ){
            ex.printStackTrace();
        }
        return new Result( StepExStatus.passed, String.format( WaitBot.strMsgWaitForTime, milliseconds ) );
    }

    public WebElement waitForWebElement ( final SearchContext parent, final By bySelector ){
        WebElement element = null;
        try {
//            AbstractBot.switchToDefaultContent();
            final WaitBot that = this;
            element = ( new WebDriverWait( AbstractBot.getDriver(), this.getTimeout() ) )
                    .until( new ExpectedCondition<WebElement>(){
                        public WebElement apply ( WebDriver d ){
                            return that.findElement( parent, bySelector );
                        }
                    } );
        }
        catch ( TimeoutException ex ){
            element = null;
        }
        catch ( WebDriverNullException ex ){
            ex.printStackTrace();
        }
        return element;
    }

    public WebElement waitForWebElement ( final By bySelector ){
        try {
            return this.waitForWebElement( AbstractBot.getDriver(), bySelector );
        }
        catch ( WebDriverNullException ex ){
            ex.printStackTrace();
        }
        return null;
    }

    public boolean waitForWebElement_Collapse ( final SearchContext parent, final By bySelector ){
        boolean result = false;
        try {
//            AbstractBot.switchToDefaultContent();
            final WaitBot that = this;
            AbstractBot.resetImplicitlyWait();
            result = ( new WebDriverWait( AbstractBot.getDriver(), this.getTimeout() ) )
                    .until( new ExpectedCondition<Boolean>(){
                        public Boolean apply ( WebDriver d ){
                            return !that.isElementExists( parent, bySelector );  // ! - only false result is accepted
                        }
                    } );
            AbstractBot.setImplicitlyWait();
        }
        catch ( TimeoutException ex ){
            result = false;
        }
        catch ( WebDriverNullException ex ){
            ex.printStackTrace();
        }
        return result;
    }

    public boolean waitForWebElement_Collapse ( final By bySelector ){
        try {
            return this.waitForWebElement_Collapse( AbstractBot.getDriver(), bySelector );
        }
        catch ( WebDriverNullException ex ){
            ex.printStackTrace();
        }
        return false;
    }

    public Boolean waitForWebElement_Disappears ( final WebElement element ){
        Boolean result = false;
        try {
//            AbstractBot.switchToDefaultContent();
            final WaitBot that = this;
            result = ( new WebDriverWait( AbstractBot.getDriver(), this.getTimeout() ) )
                    .until( new ExpectedCondition<Boolean>(){
                        public Boolean apply ( WebDriver d ){
                            return !that.isElementDisplayed( element );
                        }
                    } );
        }
        catch ( TimeoutException ex ){
            result = false;
        }
        catch ( WebDriverNullException ex ){
            ex.printStackTrace();
        }
        return result;
    }

    public Boolean waitForWebElement_Disappears ( final SearchContext parent, final By bySelector ){
        Boolean result = false;
        try {
//            AbstractBot.switchToDefaultContent();
            final WaitBot that = this;
            AbstractBot.resetImplicitlyWait();
            result = ( new WebDriverWait( AbstractBot.getDriver(), this.getTimeout() ) )
                    .until( new ExpectedCondition<Boolean>(){
                        public Boolean apply ( WebDriver d ){
                            return !that.isElementDisplayed( parent, bySelector );
                        }
                    } );
            AbstractBot.setImplicitlyWait();
        }
        catch ( TimeoutException ex ){
            result = false;
        }
        catch ( WebDriverNullException ex ){
            ex.printStackTrace();
        }
        return result;
    }

    public Boolean waitForWebElement_Disappears ( final By bySelector ){
        try {
            return this.waitForWebElement_Disappears( AbstractBot.getDriver(), bySelector );
        }
        catch ( WebDriverNullException ex ){
            ex.printStackTrace();
        }
        return false;
    }

    public Boolean waitForWebElement_Displays ( final WebElement element ){
        Boolean result = false;
        try {
//            AbstractBot.switchToDefaultContent();
            final WaitBot that = this;
            result = ( new WebDriverWait( AbstractBot.getDriver(), this.getTimeout() ) )
                    .until( new ExpectedCondition<Boolean>(){
                        public Boolean apply ( WebDriver d ){
                            return that.isElementDisplayed( element );
                        }
                    } );
        }
        catch ( TimeoutException ex ){
            result = false;
        }
        catch ( WebDriverNullException ex ){
            ex.printStackTrace();
        }
        return result;
    }

    public Boolean waitForWebElement_Displays ( final SearchContext parent, final By bySelector ){
        Boolean result = false;
        try {
//            AbstractBot.switchToDefaultContent();
            final WaitBot that = this;
            AbstractBot.resetImplicitlyWait();
            result = ( new WebDriverWait( AbstractBot.getDriver(), this.getTimeout() ) )
                    .until( new ExpectedCondition<Boolean>(){
                        public Boolean apply ( WebDriver d ){
                            return that.isElementDisplayed( parent, bySelector );
                        }
                    } );
            AbstractBot.setImplicitlyWait();
        }
        catch ( TimeoutException ex ){
            result = false;
        }
        catch ( WebDriverNullException ex ){
            ex.printStackTrace();
        }
        return result;
    }

    public Boolean waitForWebElement_Displays ( final By bySelector ){
        try {
            return this.waitForWebElement_Displays( AbstractBot.getDriver(), bySelector );
        }
        catch ( WebDriverNullException ex ){
            ex.printStackTrace();
        }
        return false;
    }

    public Boolean waitForWebElement_StopMoving ( final WebElement element ){
        Boolean result = false;
        try {
//            AbstractBot.switchToDefaultContent();
            final WaitBot that = this;
            result = ( new WebDriverWait( AbstractBot.getDriver(), this.getTimeout() ) )
                    .until( new ExpectedCondition<Boolean>(){
                        public Boolean apply ( WebDriver d ){

                            Point p = element.getLocation();
                            that.waitForTime( 200 ); // wait for one second
                            Point c = element.getLocation();

                            return ( p.getX() == c.getX() ) && ( p.getY() == c.getY() );
                        }
                    } );
        }
        catch ( TimeoutException ex ){
            result = false;
        }
        catch ( WebDriverNullException ex ){
            ex.printStackTrace();
        }
        return result;
    }

    public Boolean waitForWebElement_StopMoving ( final SearchContext parent, final By bySelector ) throws WebDriverNullException{
        WebElement elementTemp = this.findElement( parent, bySelector );    // look for the element
        return waitForWebElement_StopMoving( elementTemp );
    }

    public Boolean waitForWebElement_StopMoving ( final By bySelector ){
        try {
            return this.waitForWebElement_StopMoving( AbstractBot.getDriver(), bySelector );
        }
        catch ( WebDriverNullException ex ){
            ex.printStackTrace();
        }
        return false;
    }

    public Boolean waitForWebElement_StopResizing ( final WebElement element ){
        Boolean result = false;
        try {
//            AbstractBot.switchToDefaultContent();
            final WaitBot that = this;
            result = ( new WebDriverWait( AbstractBot.getDriver(), this.getTimeout() ) )
                    .until( new ExpectedCondition<Boolean>(){
                        public Boolean apply ( WebDriver d ){

                            Dimension p = element.getSize();
                            that.waitForTime( 200 );   // wait for one second
                            Dimension c = element.getSize();

                            return ( p.getWidth() == c.getWidth() ) && ( p.getHeight() == c.getHeight() );
                        }
                    } );
        }
        catch ( TimeoutException ex ){
            result = false;
        }
        catch ( Exception ex ){
            ex.printStackTrace();
        }
        return result;
    }

    public Boolean waitForWebElement_StopResizing ( final SearchContext parent, final By bySelector ){
        WebElement elementTemp = this.findElement( parent, bySelector );    // look for the element
        return waitForWebElement_StopResizing( elementTemp );
    }

    public Boolean waitForWebElement_StopResizing ( final By bySelector ){
        try {
            return waitForWebElement_StopResizing( AbstractBot.getDriver(), bySelector );
        }
        catch ( WebDriverNullException ex ){
            ex.printStackTrace();
        }
        return false;
    }

    public List<WebElement> waitForWebElements ( final SearchContext parent, final By bySelector, String name ){
        List<WebElement> element = null;
        try {
//            AbstractBot.switchToDefaultContent();
            final WaitBot that = this;
            element = ( new WebDriverWait( AbstractBot.getDriver(), this.getTimeout() ) )
                    .until( new ExpectedCondition<List<WebElement>>(){
                        public List<WebElement> apply ( WebDriver d ){
                            return that.findElements( parent, bySelector );
                        }
                    } );
        }
        catch ( TimeoutException ex ){
            element = null;
        }
        catch ( WebDriverNullException ex ){
            ex.printStackTrace();
        }
        return element;
    }

    public List<WebElement> waitForWebElements ( final By bySelector, String name ){
        try {
            return this.waitForWebElements( AbstractBot.getDriver(), bySelector, name );
        }
        catch ( WebDriverNullException ex ){
            ex.printStackTrace();
        }
        return null;
    }

    public Boolean waitLoading (){
        return this.waitLoading( 300 );
    }

    public Boolean waitLoading ( int milliseconds ){
        return this.waitForTime( milliseconds ).getStatus() == StepExStatus.passed;
    }
}

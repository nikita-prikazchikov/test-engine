package selenium.bots;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;
import selenium.Element;
import selenium.bots.interfaces.AbstractBotInterface;
import selenium.exceptions.ElementNotFoundException;
import selenium.exceptions.WebDriverNullException;
import selenium.log.Result;
import selenium.log.Screenshot;
import testInterface.objects.utils.StepExStatus;
import utils.TestEngineDataHolder;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AbstractBot implements AbstractBotInterface{

    public static final Point FIREFOX_PAGE_LOCATION = new Point( 0, 0 );   // location of page on screen. Firefox is maximized

    public static final Point CHROME_PAGE_LOCATION = new Point( 0, 0 );   // location of page on screen. Chrome is maximized

    public static final Point FIREFOX_PAGE_LOCATION_LINUX = new Point( 0, 0 );   // location of page on screen. Firefox is maximized

    private static final long DEFAULT_WEBDRIVER_IMPLICITLY_WAIT_TIME = 5;

    private static final String JAVASCRIPT_WINDOW = "" +
            "return {" +
            "screenX : window.screenX," +
            "screenY : window.screenY," +
            "outerHeight : window.outerHeight," +
            "outerWidth : window.outerWidth," +
            "innerHeight : window.innerHeight," +
            "innerWidth : window.innerWidth," +
            "}";

    private static final TimeUnit DEFAULT_WEBDRIVER_IMPLICITLY_WAIT_UNIT = TimeUnit.SECONDS;

    private static Actions _actionsObject;

    private static WebDriver _driver;

    /**
     * Workaround for the issue http://code.google.com/p/selenium/issues/detail?id=1438
     *
     * @throws WebDriverNullException
     */
    protected static void switchToDefaultContent () throws WebDriverNullException{
        AbstractBot.getDriver().switchTo().defaultContent();
    }

    public static Object executeJavaScript ( String script ){
        try {
            JavascriptExecutor executor = (JavascriptExecutor) AbstractBot.getDriver();
            return executor.executeScript( script );
        }
        catch ( Exception ex ){
            ex.printStackTrace();
        }
        return null;
    }

    public static Actions getActionsObject () throws WebDriverNullException{
        if ( AbstractBot._actionsObject == null ){
            AbstractBot._actionsObject = new Actions( AbstractBot.getDriver() );
        }
        return AbstractBot._actionsObject;
    }

    public static WebDriver getDriver () throws WebDriverNullException{
        if ( AbstractBot._driver == null ){
            throw new WebDriverNullException();
        }
        return AbstractBot._driver;
    }

    public static Point getPageLocation (){
        String browser = TestEngineDataHolder.getData().getBrowser().toLowerCase();
        String os = System.getProperty( "os.name" ).toLowerCase();

        // Windows
        if ( os.contains( "win" ) ){
            if ( browser.equals( "firefox" ) ){
                return AbstractBot.FIREFOX_PAGE_LOCATION;
            }

            if ( browser.equals( "chrome" ) ){
                return AbstractBot.CHROME_PAGE_LOCATION;
            }
        }

        // Linux
        if ( os.contains( "linux" ) ){
            if ( browser.equals( "firefox" ) ){
                return AbstractBot.FIREFOX_PAGE_LOCATION_LINUX;
            }
        }
        return null;
    }

    public static selenium.Window getWindow (){
        selenium.Window window = null;
        Object obj = AbstractBot.executeJavaScript( AbstractBot.JAVASCRIPT_WINDOW );
        if ( obj != null ){
            Map<String, Long> params = (Map<String, Long>) obj;
            window = new selenium.Window(
                    params.get( "screenX" ),
                    params.get( "screenY" ),
                    params.get( "outerHeight" ),
                    params.get( "outerWidth" ),
                    params.get( "innerHeight" ),
                    params.get( "innerWidth" )
            );
        }
        return window;
    }

    /**
     * Maximize window using hot keys
     */
    public static void maximizeWindow (){
        try {
            String os = System.getProperty( "os.name" ).toLowerCase();
            Robot rob = new Robot();

            // Windows
            if ( os.contains( "win" ) ){
                rob.keyPress( KeyEvent.VK_WINDOWS );
                rob.keyPress( KeyEvent.VK_UP );
                rob.keyRelease( KeyEvent.VK_WINDOWS );
                rob.keyRelease( KeyEvent.VK_UP );
                rob.keyPress( KeyEvent.VK_F11 );
                rob.keyRelease( KeyEvent.VK_F11 );
            }

            // Linux
            if ( os.contains( "linux" ) ){
                rob.keyPress( KeyEvent.VK_ALT );
                rob.keyPress( KeyEvent.VK_F5 );
                rob.keyRelease( KeyEvent.VK_F5 );
                rob.keyRelease( KeyEvent.VK_ALT );
                rob.keyPress( KeyEvent.VK_F11 );
                rob.keyRelease( KeyEvent.VK_F11 );
            }

            // Mac
            if ( os.contains( "mac" ) ){

            }
        }
        catch ( Exception ex ){
            ex.printStackTrace();
        }
    }

    public static boolean resetImplicitlyWait (){
        return AbstractBot.setImplicitlyWait( 0, TimeUnit.SECONDS );
    }

    public static void setActionsObject ( Actions actions ){
        AbstractBot._actionsObject = actions;
    }

    public static void setDriver ( WebDriver driver ){
        AbstractBot._driver = driver;
        if ( null == driver ){
            AbstractBot.setActionsObject( null );
        }
        else {
            AbstractBot.setImplicitlyWait();
        }
    }

    public static boolean setImplicitlyWait ( long time, TimeUnit unit ){
        try {
            AbstractBot.getDriver().manage().timeouts().implicitlyWait( time, unit );
            return true;
        }
        catch ( Exception ex ){
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean setImplicitlyWait (){
        return AbstractBot.setImplicitlyWait( AbstractBot.DEFAULT_WEBDRIVER_IMPLICITLY_WAIT_TIME, AbstractBot.DEFAULT_WEBDRIVER_IMPLICITLY_WAIT_UNIT );
    }

    private File _takeScreenshot (){
        File fileScreenshot = null;
        try {
            fileScreenshot = ( (TakesScreenshot) AbstractBot.getDriver() ).getScreenshotAs( OutputType.FILE );
        }
        catch ( WebDriverNullException ex ){
            ex.printStackTrace();
        }
        return fileScreenshot;
    }

    public WebElement findElement ( SearchContext parent, By bySelector ){
        WebElement element = null;
        if ( parent != null ){
            try {
//                switchToDefaultContent();
                element = parent.findElement( bySelector );
            }
            catch ( NoSuchElementException ex ){
                element = null;
//                ex.printStackTrace();
            }
            catch ( StaleElementReferenceException ex ){
                element = null;
                ex.printStackTrace();
            }
            catch ( Exception ex ){
                element = null;
                ex.printStackTrace();
            }
        }
        return element;
    }

    public WebElement findElement ( By bySelector ){
        try {
            return this.findElement( AbstractBot.getDriver(), bySelector );
        }
        catch ( WebDriverNullException ex ){
            ex.printStackTrace();
        }
        return null;
    }

    public List<WebElement> findElements ( SearchContext parent, By bySelector ){
        List<WebElement> elements = new ArrayList<WebElement>();
        if ( parent != null ){
            try {
//                switchToDefaultContent();
                elements = parent.findElements( bySelector );
            }
            catch ( NoSuchElementException ex ){
                elements = null;
                ex.printStackTrace();
            }
            catch ( StaleElementReferenceException ex ){
                elements = null;
                ex.printStackTrace();
            }
            catch ( Exception ex ){
                elements = null;
                ex.printStackTrace();
            }
        }
        return elements;
    }

    public List<WebElement> findElements ( By bySelector ){
        try {
            return this.findElements( AbstractBot.getDriver(), bySelector );
        }
        catch ( WebDriverNullException ex ){
            ex.printStackTrace();
        }
        return null;
    }

    public List<WebElement> findElementsNoWait ( SearchContext parent, By bySelector ){
        List<WebElement> elements = new ArrayList<WebElement>();
        AbstractBot.resetImplicitlyWait();
        elements = this.findElements( parent, bySelector );
        AbstractBot.setImplicitlyWait();
        return elements;
    }

    public List<WebElement> findElementsNoWait ( By bySelector ){
        List<WebElement> elements = new ArrayList<WebElement>();
        AbstractBot.resetImplicitlyWait();
        elements = this.findElements( bySelector );
        AbstractBot.setImplicitlyWait();
        return elements;
    }

    public boolean isElementDisplayed ( Element element ){
        try {
            return this.isElementDisplayed( element.getElement() );
        }
        catch ( ElementNotFoundException e ){
            return false;
        }
    }

    public boolean isElementDisplayed ( WebElement element ){
        boolean valid = false;
        if ( element != null ){
            try {
                valid = element.isDisplayed();
            }
            catch ( NoSuchElementException ex ){
                valid = false;
                //ex.printStackTrace();
            }
            catch ( StaleElementReferenceException ex ){
                valid = false;
                ex.printStackTrace();
            }
        }
        return valid;
    }

    public boolean isElementDisplayed ( SearchContext parent, By bySelector ){
        AbstractBot.resetImplicitlyWait();
        boolean r = isElementDisplayed( this.findElement( parent, bySelector ) );
        AbstractBot.setImplicitlyWait();
        return r;
    }

    public boolean isElementDisplayed ( By bySelector ){
        AbstractBot.resetImplicitlyWait();
        boolean r = isElementDisplayed( this.findElement( bySelector ) );
        AbstractBot.setImplicitlyWait();
        return r;
    }

    public boolean isElementExists ( SearchContext parent, By bySelector ){
        AbstractBot.resetImplicitlyWait();
        boolean r = ( this.findElement( parent, bySelector ) != null );
        AbstractBot.setImplicitlyWait();
        return r;
    }

    public boolean isElementExists ( By bySelector ){
        boolean r = false;
        try {
            AbstractBot.resetImplicitlyWait();
            r = ( this.findElement( AbstractBot.getDriver(), bySelector ) != null );
        }
        catch ( WebDriverNullException ex ){
            ex.printStackTrace();
        }
        finally{
            AbstractBot.setImplicitlyWait();
        }
        return r;
    }

    public Screenshot makeScreenshot ( String strComment ){
        Screenshot screenshot = null;
        try {
            File filePicture = this._takeScreenshot();

            WebDAVBot wd = new WebDAVBot();
            String url = wd.send( filePicture, String.format( Locale.US, "%1$tY/%1$tB/%1$te/%2$s", new Date( filePicture.lastModified() ), filePicture.getName() ) );

            filePicture.delete();

            screenshot = new Screenshot( strComment, url );
        }
        catch ( Exception ex ){
            screenshot = null;
            ex.printStackTrace();
        }
        return screenshot;
    }

    public Result takeScreenshot ( String strComment ){
        Result result = new Result( strComment );
        try {
            Screenshot scrn = this.makeScreenshot( strComment );
            result.setStatus( StepExStatus.passed ).setComment( strComment ).setScreenshot( scrn );
        }
        catch ( Exception ex ){
            result.setStatus( StepExStatus.failed );
            ex.printStackTrace();
        }
        return result;
    }
}

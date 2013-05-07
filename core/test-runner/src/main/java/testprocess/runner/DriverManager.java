package testprocess.runner;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import testInterface.TestDataException;
import testInterface.TestDataInterface;
import testInterface.context.ContextHolder;
import testInterface.http.TestData;
import testInterface.objects.Browser;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import selenium.bots.AbstractBot;
import testprocess.exceptions.DriverManagerException;

import java.io.File;
import java.util.Set;

public class DriverManager {

    private static Logger logger = Logger.getLogger( DriverManager.class );

    private ContextHolder context;

    protected TestDataInterface databaseManager;

    public static final  String FIREFOX_NAME    = "Firefox";

    public static final  String CHROME_NAME     = "Chrome";

    public static final  String IE_NAME         = "InternetExplorer";

    public static final  String SAFARI_NAME     = "Safari";

    public static final  String HTMLUNIT_NAME   = "HtmlUnitDriver";

    private WebDriver driver;

    private Browser   browser;

    public DriverManager ( String browserName ) throws TestDataException, DriverManagerException{
        this.databaseManager = null;
        this.context = ContextHolder.getInstance();
        this.databaseManager = new TestData();

        this.setBrowser( browserName );
    }

    public DriverManager () throws TestDataException{
        super();
    }

    public WebDriver getWebDriver (){
        return driver;
    }

    public void setBrowser ( String browserName ) throws DriverManagerException, TestDataException{
        logger.info( "Get browser for name = '" + browserName + "'" );
        browser = this.databaseManager.getBrowser( browserName );
        if ( null == browser ){
            throw new DriverManagerException( "Not found browser for name = '" + browserName );
        }
    }

    public Browser getBrowser (){
        return browser;
    }

    public void closeBrowser (){
        try {
            if ( null != this.driver ){
                logger.info( "Closing current browser" );
                this.driver.quit();
                AbstractBot.setDriver( null );
            }
        }
        catch ( Exception ex ){
            ex.printStackTrace();
        }
    }

    public void startBrowser () throws DriverManagerException{

        if ( browser != null ){
            if ( browser.getName().equalsIgnoreCase( FIREFOX_NAME ) ){
                this.startBrowserFirefox();
            }
            else if ( browser.getName().equalsIgnoreCase( CHROME_NAME ) ){
                this.startBrowserChrome();
            }
            else if ( browser.getName().equalsIgnoreCase( IE_NAME ) ){
                this.startBrowserIE();
            }
            else if ( browser.getName().equalsIgnoreCase( SAFARI_NAME ) ){
                this.startBrowserSafari();
            }
            else if ( browser.getName().equalsIgnoreCase( HTMLUNIT_NAME ) ){
                this.startBrowserHTMLUnit();
            }
            if ( driver != null && !browser.getName().equalsIgnoreCase( HTMLUNIT_NAME ) ){
                try {
                    logger.info( "Configure browser" );
                    AbstractBot.maximizeWindow();
                }
                catch ( Exception ex ){
                    throw new DriverManagerException( "Configure browser process failed" );
                }
            }
        }
        else {
            throw new DriverManagerException( "Browser for execution is not set" );
        }
    }

    private void startBrowserChrome () throws DriverManagerException{
        logger.info( "Starting " + CHROME_NAME + " browser" );
        try {
            String chromeDriverPath = (String) this.context.getBean( "ChromeDriverPath" );

            String chromeDriverLogPath = (String) this.context.getBean( "ChromeDriverLogPath" );
            ChromeDriverService service = new ChromeDriverService.Builder()
                    .usingDriverExecutable( new File( chromeDriverPath ) )
                    .usingAnyFreePort()
                    .withLogFile( new File( chromeDriverLogPath ) )
                    .build();
            service.start();
            driver = new ChromeDriver( service );
        }
        catch ( Exception ex ){
            throw new DriverManagerException( "Driver not started", ex );
        }
        logger.info( CHROME_NAME + " start successfully" );
    }

    private void startBrowserFirefox () throws DriverManagerException{
        logger.info( "Starting " + FIREFOX_NAME + " browser" );
        try {
            driver = new FirefoxDriver();
            this.maximize();
        }
        catch ( Exception ex ){
            throw new DriverManagerException( "Driver not started", ex );
        }
        logger.info( FIREFOX_NAME + " start successfully" );
    }

    private void startBrowserHTMLUnit () throws DriverManagerException{
        logger.info( "Starting " + HTMLUNIT_NAME + " browser" );
        try {
            HtmlUnitDriver htmlUnitDriver = new HtmlUnitDriver( BrowserVersion.FIREFOX_3_6 );
            htmlUnitDriver.setJavascriptEnabled( true );
            driver = htmlUnitDriver;
        }
        catch ( Exception ex ){
            throw new DriverManagerException( "Driver not started", ex );
        }
        logger.info( HTMLUNIT_NAME + " start successfully" );
    }

    private void startBrowserIE () throws DriverManagerException{
        logger.info( "Starting " + IE_NAME + " browser" );
        try {
            DesiredCapabilities capabilities =
                    DesiredCapabilities.internetExplorer();
            capabilities.setCapability(
                    InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
                    true );
            driver = new InternetExplorerDriver( capabilities );
        }
        catch ( Exception ex ){
            throw new DriverManagerException( "Driver not started", ex );
        }
        logger.info( IE_NAME + " start successfully" );
    }

    private void startBrowserSafari () throws DriverManagerException{
//                try {
//                    RemoteControlConfiguration rcc = new RemoteControlConfiguration();
//                    rcc.setInteractive( true );
//                    rcc.setSingleWindow( true );
//                    rcc.setTimeoutInSeconds( 10 );
//                    SeleniumServer server = null;
//                    server = new SeleniumServer( rcc );
//                    server.start();
//                    Selenium sel = new DefaultSelenium( "localhost", 4444, "*safari", "http://ya.ru" );
//                    CommandExecutor executor = new SeleneseCommandExecutor( sel );
//                    DesiredCapabilities dc = new DesiredCapabilities( "safari", "5", Platform.WINDOWS );
//                    driver = new RemoteWebDriver( executor, dc );
//                }
//                catch ( Exception e ){
//                    DriverManagerException exception = new DriverManagerException( "Configure browser process failed" );
//                    throw exception;
//                }
        throw new DriverManagerException( "Safari is not supported now" );
   }

    protected void resetDriver () throws DriverManagerException{
        try {
            driver.manage().deleteAllCookies();
        }
        catch ( Exception ex ){
            throw new DriverManagerException( "Reset " + driver.getTitle() + " Browser failed" );
        }
    }

    public void maximize (){
        Set<String> handles = driver.getWindowHandles();
        String script = "if (window.screen){var win = window.open(window.location); win.moveTo(0,0);win.resizeTo(window.screen.availWidth,window.screen.availHeight);};";
        ( (JavascriptExecutor) driver ).executeScript( script );
        Set<String> newHandles = driver.getWindowHandles();
        newHandles.removeAll( handles );
        driver.close();
        driver.switchTo().window( newHandles.iterator().next() );
    }
}

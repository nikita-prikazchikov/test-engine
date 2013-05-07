package selenium.bots.interfaces;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import selenium.Element;
import selenium.log.Result;
import selenium.log.Screenshot;

import java.util.List;

public interface AbstractBotInterface{

    public WebElement findElement ( SearchContext parent, By bySelector );
    public WebElement findElement ( By bySelector );
    public List<WebElement> findElements ( SearchContext parent, By bySelector );
    public List<WebElement> findElements ( By bySelector );
    public List<WebElement> findElementsNoWait ( SearchContext parent, By bySelector );
    public List<WebElement> findElementsNoWait ( By bySelector );
    public boolean isElementDisplayed ( Element element );
    public boolean isElementDisplayed ( WebElement element );
    public boolean isElementDisplayed ( SearchContext parent, By bySelector );
    public boolean isElementDisplayed ( By bySelector );
    public boolean isElementExists ( SearchContext parent, By bySelector );
    public boolean isElementExists ( By bySelector );
    public Screenshot makeScreenshot ( String strComment );
    public Result takeScreenshot ( String strComment );

}

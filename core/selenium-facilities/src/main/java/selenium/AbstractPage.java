package selenium;

import org.openqa.selenium.WebDriver;
import selenium.bots.AbstractBot;
import selenium.bots.ActionBot;
import selenium.bots.VerifyBot;
import selenium.bots.WaitBot;
import selenium.bots.interfaces.ActionBotInterface;
import selenium.bots.interfaces.VerifyBotInterface;
import selenium.bots.interfaces.WaitBotInterface;
import selenium.exceptions.WebDriverNullException;

public abstract class AbstractPage{

    protected ActionBotInterface _actionBot;

    protected String _name;

    protected VerifyBotInterface _verifyBot;

    protected WaitBotInterface _waitBot;


    public AbstractPage (){
        this._actionBot = new ActionBot();
        this._waitBot = new WaitBot();
        this._verifyBot = new VerifyBot();
    }

    public AbstractPage ( String name ){
        this();
        this._name = name;
    }

    public ActionBotInterface getActionBot (){
        return _actionBot;
    }

    public VerifyBotInterface getVerifyBot (){
        return _verifyBot;
    }

    public WaitBotInterface getWaitBot (){
        return _waitBot;
    }

    public void setActionBot ( ActionBotInterface _actionBot ){
        this._actionBot = _actionBot;
    }

    public void setVerifyBot ( VerifyBotInterface _verifyBot ){
        this._verifyBot = _verifyBot;
    }

    public void setWaitBot ( WaitBotInterface _waitBot ){
        this._waitBot = _waitBot;
    }

    public int getWindowsCount () throws WebDriverNullException{
        return AbstractBot.getDriver().getWindowHandles().size();
    }

    public boolean switchToNextWindow () throws WebDriverNullException{
        boolean res = false;
        String curWindowHandler = AbstractBot.getDriver().getWindowHandle();
        Object[] handles = AbstractBot.getDriver().getWindowHandles().toArray();
        for ( int i = 0; i < handles.length; i++ ){
            if ( handles[ i ].equals( curWindowHandler ) && i < handles.length - 1 ){
                WebDriver d = AbstractBot.getDriver().switchTo().window( (String) handles[ i + 1 ] );
                AbstractBot.setDriver( d );
                res = true;
                break;
            }
        }
        return res;
    }

    public boolean switchToPreviousWindow () throws WebDriverNullException{
        boolean res = false;
        String curWindowHandler = AbstractBot.getDriver().getWindowHandle();
        Object[] handles = AbstractBot.getDriver().getWindowHandles().toArray();
        for ( int i = handles.length - 1; i > -1; i-- ){
            if ( handles[ i ].equals( curWindowHandler ) && i > 0 ){
                WebDriver d = AbstractBot.getDriver().switchTo().window( (String) handles[ i - 1 ] );
                AbstractBot.setDriver( d );
                res = true;
                break;
            }
        }
        return res;
    }

    /**
     * @param count Count of windows which existed before action
     * @return boolean
     * @throws WebDriverNullException
     */
    public boolean waitForNewWindow ( int count ) throws WebDriverNullException{

        int newCount = 0;
        int iteration = 60;
        boolean res = false;

        while ( iteration != 0 && !res ){
            newCount = AbstractBot.getDriver().getWindowHandles().size();
            res = newCount > count;
            if ( !res ){
                this._waitBot.waitForTime( 1000 );
            }
            iteration--;
        }
        return res;
    }

}

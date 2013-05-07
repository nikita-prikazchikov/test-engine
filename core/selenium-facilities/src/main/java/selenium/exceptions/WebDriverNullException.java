package selenium.exceptions;

import selenium.exceptions.SeleniumFacilitiesException;

public class WebDriverNullException extends SeleniumFacilitiesException{

    public WebDriverNullException(){
        super("WebDriver instance is null");
    }
}

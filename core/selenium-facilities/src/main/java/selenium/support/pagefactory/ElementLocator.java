package selenium.support.pagefactory;

import selenium.Element;
import selenium.support.exceptions.ElementInitialisationException;

public interface ElementLocator {
    Element findElement() throws ElementInitialisationException;
}

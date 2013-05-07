package selenium.support.pagefactory;

import selenium.Element;
import selenium.support.pagefactory.internal.LocatingElementHandler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Default decorator for use with PageFactory. Will decorate 1) all of the
 * WebElement fields and 2) List<WebElement> fields that have @FindBy or
 * @FindBys annotation with a proxy that locates the elements using the passed
 * in ElementLocatorFactory.
 */
public class DefaultFieldDecorator implements FieldDecorator {

    protected ElementLocatorFactory factory;

    public DefaultFieldDecorator(ElementLocatorFactory factory) {
        this.factory = factory;
    }

    public Object decorate(ClassLoader loader, Field field) {
        if (!Element.class.isAssignableFrom(field.getType())){
            return null;
        }

        ElementLocator locator = factory.createLocator(field);
        if(locator == null){
            return null;
        }
        return proxyForLocator(loader, locator);
    }

    protected Element proxyForLocator(ClassLoader loader, ElementLocator locator) {
        InvocationHandler handler = new LocatingElementHandler(locator);
        return (Element) Proxy.newProxyInstance( loader, new Class[] {Element.class}, handler);
    }

}
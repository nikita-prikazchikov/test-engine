package selenium.support.pagefactory;

import org.openqa.selenium.*;
import selenium.Element;
import selenium.support.exceptions.ElementInitialisationException;
import selenium.support.exceptions.NoElementFindByAnnotationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.MissingFormatArgumentException;

/**
 * The default element locator, which will lazily locate an element or an element list on a page. This class is
 * designed for use with the {@link org.openqa.selenium.support.PageFactory} and understands the
 * annotations {@link org.openqa.selenium.support.FindBy} and {@link org.openqa.selenium.support.CacheLookup}.
 */
public class DefaultElementLocator implements ElementLocator {

    private final static String FIELDNAME_NAME = "_name";
    private final static String FIELDNAME_PARENT = "_parent";
    private final static String FIELDNAME_ELEMENT = "_element";
    private final static String FIELDNAME_BY = "_by";
    private final static String FIELDNAME_LABEL_BY = "_byLabel";
    private final static String FIELDNAME_ANNOTATIONS = "_annotations";

    private final SearchContext searchContext;
    private final boolean shouldCache;
    private By by;
    private final String name;
    private final Class<?> _class;
    private Element cachedElement;
    private ElementAnnotations _annotations;

    /**
     * Creates a new element locator.
     *
     * @param searchContext The context to use when finding the element
     * @param field The field on the Page Object that will hold the located value
     */
    public DefaultElementLocator(SearchContext searchContext, Field field) throws NoElementFindByAnnotationException{
        ElementAnnotations annotations = new ElementAnnotations(field);

        this.searchContext = searchContext;
        this.shouldCache = annotations.isLookupCached();
        this.name = annotations.getElementName();
        this._class = annotations.getElementClass();
        this._annotations = annotations;

        try{
            this.by = annotations.buildBy();
        } catch (MissingFormatArgumentException ex) {}
    }

    /**
     * Find the element.
     */
    public Element findElement() throws ElementInitialisationException {
        if (cachedElement != null && shouldCache) {
            return cachedElement;
        }

        WebElement webElement = this.findWebElement(this.searchContext, this.by);
        Element element = this.createElementObject(this._class);
        try{
            Field field = ReflectionHelper.getDeclaredField(this._class, DefaultElementLocator.FIELDNAME_NAME);
            field.setAccessible(true);
            field.set(element, this.name);

            field = ReflectionHelper.getDeclaredField(this._class, DefaultElementLocator.FIELDNAME_PARENT);
            field.setAccessible(true);
            field.set(element, this.searchContext);

            field = ReflectionHelper.getDeclaredField(this._class, DefaultElementLocator.FIELDNAME_BY);
            field.setAccessible(true);
            field.set(element, this.by);

            field = ReflectionHelper.getDeclaredField(this._class, DefaultElementLocator.FIELDNAME_ANNOTATIONS);
            field.setAccessible(true);
            field.set(element, this._annotations);

            field = ReflectionHelper.getDeclaredField(this._class, DefaultElementLocator.FIELDNAME_ELEMENT);
            field.setAccessible(true);
            field.set(element, webElement);
        } catch (Exception ex){
            throw new ElementInitialisationException(ex);
        }

        if (shouldCache) {
            cachedElement = element;
        }

        return element;
    }

    private WebElement findWebElement(SearchContext searchContext, By by){
        WebElement webElement = null;
        try{
            if(searchContext != null && by != null){
                webElement = searchContext.findElement(by);
            }
        } catch (NoSuchElementException ex){
            webElement = null;
        } catch (StaleElementReferenceException ex){
            webElement = null;
        }
        return webElement;
    }

    private Element createElementObject(Class<?> _class) throws ElementInitialisationException{
        Element element = null;
        try{
            Constructor constructor = _class.getConstructor(null);
            element = (Element)constructor.newInstance();
        } catch (NoSuchMethodException ex){
            throw new ElementInitialisationException(ex);
        } catch (IllegalAccessException ex){
            throw new ElementInitialisationException(ex);
        } catch (InvocationTargetException ex){
            throw new ElementInitialisationException(ex);
        } catch (InstantiationException ex){
            throw new ElementInitialisationException(ex);
        } catch (IllegalArgumentException ex) {
            throw new ElementInitialisationException(ex);
        }
        return element;
    }
}

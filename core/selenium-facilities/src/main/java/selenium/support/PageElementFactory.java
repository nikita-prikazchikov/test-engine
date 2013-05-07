package selenium.support;

import org.openqa.selenium.*;
import selenium.bots.AbstractBot;
import selenium.support.exceptions.ElementInitialisationException;
import selenium.support.exceptions.NoElementFindByAnnotationException;
import selenium.exceptions.ElementNotFoundException;
import selenium.exceptions.WebDriverNullException;
import selenium.support.pagefactory.*;

import java.lang.reflect.Field;

public class PageElementFactory {

    private final static String ELEMENT_NAME_FIELD = "_name";
    private final static String ELEMENT_ELEMENT_FIELD = "_element";

    /**
     * As
     * {@link org.openqa.selenium.support.PageFactory#initElements(org.openqa.selenium.WebDriver, Class)}
     * but will only replace the fields of an already instantiated Page Object.
     *
     * @param driver The driver that will be used to look up the elements
     * @param page The object with WebElement and List<WebElement> fields that should be proxied.
     */
    public static void initElements(WebDriver driver, Object page) throws WebDriverNullException, NoElementFindByAnnotationException, ElementInitialisationException {
        PageElementFactory.initElements( driver, page, null );
    }

    public static void initElements ( Object page ) throws WebDriverNullException, NoElementFindByAnnotationException, ElementInitialisationException{
        initElements( AbstractBot.getDriver(), page );
    }

    public static void initElements(WebDriver driver, Object page, SearchContext context) throws WebDriverNullException, NoElementFindByAnnotationException, ElementInitialisationException {
        final WebDriver driverRef = driver;
        final SearchContext searchContext;

        if ( null == context ){
            WebElement webPage = PageElementFactory.initPageElement(driverRef, page );
            searchContext = webPage != null ? webPage : driver;
        }
        else {
            searchContext = context;
        }

        PageElementFactory.initElements(new DefaultElementLocatorFactory(searchContext), page);
    }

    public static void initElements ( Object page, SearchContext context ) throws WebDriverNullException, NoElementFindByAnnotationException, ElementInitialisationException{
        initElements( AbstractBot.getDriver(), page, context );
    }

    /**
     * Similar to the other "initElements" methods, but takes an {@link ElementLocatorFactory} which
     * is used for providing the mechanism for fniding elements. If the ElementLocatorFactory returns
     * null then the field won't be decorated.
     *
     * @param factory The factory to use
     * @param page The object to decorate the fields of
     */
    private static void initElements(ElementLocatorFactory factory, Object page) {
        final ElementLocatorFactory factoryRef = factory;
        PageElementFactory.initElements(new DefaultFieldDecorator(factoryRef), page);
    }

    /**
     * Similar to the other "initElements" methods, but takes an {@link FieldDecorator} which is used
     * for decorating each of the fields.
     *
     * @param decorator the decorator to use
     * @param page The object to decorate the fields of
     */
    private static void initElements(FieldDecorator decorator, Object page) {
        Class<?> proxyIn = page.getClass();
        while (proxyIn != Object.class) {
            PageElementFactory.proxyFields(decorator, page, proxyIn);
            proxyIn = proxyIn.getSuperclass();
        }
    }

    private static void proxyFields(FieldDecorator decorator, Object page, Class<?> proxyIn) {
        Field[] fields = proxyIn.getDeclaredFields();
        for (Field field : fields) {
            Object value = decorator.decorate(page.getClass().getClassLoader(), field);
            if (value != null) {
                try {
                    field.setAccessible(true);
                    field.set(page, value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static WebElement initPageElement(WebDriver driver, Object element ) throws WebDriverNullException, NoElementFindByAnnotationException, ElementInitialisationException {
        SearchContext searchContext = null;
        WebElement webElement = null;
        Class<?> _class = element.getClass();
        if(_class.isAnnotationPresent(ElementParent.class)){
            try{
                searchContext = PageElementFactory.getParentElement(driver, _class);
            } catch (ElementNotFoundException ex){
                ex.printStackTrace();   // Print stack trace to help debug the test
            }
        } else {
            searchContext = driver;
        }
        if(_class.isAnnotationPresent(ElementFindBy.class)){
            webElement = PageElementFactory.initPageElement(searchContext, element);
        }
        return webElement;
    }

    public static WebElement initPageElement(SearchContext searchContext, Object element) throws NoElementFindByAnnotationException, ElementInitialisationException{
        WebElement webElement = null;
        Class<?> _class = element.getClass();
        ElementAnnotations pageElementAnnotations = new ElementAnnotations(_class);
        By by = pageElementAnnotations.buildBy();
        String elementName = pageElementAnnotations.getElementName();
        try{
            if(searchContext != null){  // if parent element was not found leave a webElement null
                webElement = searchContext.findElement(by);
            }
        } catch (NoSuchElementException ex){
            webElement = null;
        }
        try{
            // Set _element field
            Field field = ReflectionHelper.getDeclaredField(_class, PageElementFactory.ELEMENT_ELEMENT_FIELD);
            field.setAccessible(true);
            field.set(element, webElement);

            // Set _name field
            field = ReflectionHelper.getDeclaredField(_class, PageElementFactory.ELEMENT_NAME_FIELD);
            field.setAccessible(true);
            field.set(element, elementName);
        } catch (IllegalAccessException ex){
            throw new ElementInitialisationException(ex);
        }
        return webElement;
    }
    /*private static Element initParentPageElement(Class<?> _class){
        Element parent = null;
        ElementParent elementParent = _class.getAnnotation(ElementParent.class);
        try{
            Constructor elementConstructor = elementParent.parent().getConstructor(null);
            parent = (Element)elementConstructor.newInstance(null);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return parent;
    }*/

    private static WebElement getParentElement(SearchContext searchContext, Class<?> _class) throws NoElementFindByAnnotationException, ElementNotFoundException{
        SearchContext parentSearchContext = searchContext;
        ElementParent elementParent = _class.getAnnotation(ElementParent.class);

        // if the element class has parent class too look for him
        if(elementParent.parent().isAnnotationPresent(ElementParent.class)){
            parentSearchContext = getParentElement(parentSearchContext, elementParent.parent());
        }

        // find parent element
        WebElement parent = null;
        ElementAnnotations elementAnnotations = new ElementAnnotations(elementParent.parent());
        By by = elementAnnotations.buildBy();
        try{
            parent = searchContext.findElement(by);
        } catch (NoSuchElementException ex){
            // Throw exception.
            // it is only here we throw exception if could not find an element!
            throw new ElementNotFoundException(String.format("Parent element [%s]", elementAnnotations.getElementName()));
        }
        return parent;
    }
}

package selenium.support.pagefactory;

import org.openqa.selenium.SearchContext;
import selenium.support.exceptions.NoElementFindByAnnotationException;

import java.lang.reflect.Field;

public final class DefaultElementLocatorFactory implements ElementLocatorFactory {
    private final SearchContext searchContext;

    public DefaultElementLocatorFactory(SearchContext searchContext) {
        this.searchContext = searchContext;
    }

    public ElementLocator createLocator(Field field) {
        try{
            return new DefaultElementLocator(searchContext, field);
        } catch (NoElementFindByAnnotationException ex){
            return null;
        }
    }
}
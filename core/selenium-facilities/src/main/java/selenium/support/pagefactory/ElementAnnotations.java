package selenium.support.pagefactory;

import org.openqa.selenium.By;
import selenium.support.ElementFindBy;
import selenium.support.How;
import selenium.support.exceptions.NoElementFindByAnnotationException;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class ElementAnnotations{

    private ElementFindBy elementFindBy;

    private boolean isLookupCached = false;

    public ElementAnnotations ( Class<?> _class ){
        this.elementFindBy = _class != null && _class.isAnnotationPresent( ElementFindBy.class ) ? _class.getAnnotation( ElementFindBy.class ) : null;
    }

    public ElementAnnotations ( Field field ){
        this.elementFindBy = field != null && field.isAnnotationPresent( ElementFindBy.class ) ? field.getAnnotation( ElementFindBy.class ) : null;
    }

    private void assertValidFindBy ( ElementFindBy elementFindBy ){
        if ( elementFindBy.how() != null ){
            if ( elementFindBy.using() == null ){
                throw new IllegalArgumentException(
                        "If you set the 'how' property, you must also set 'using'" );
            }
        }

        Set<String> finders = new HashSet<String>();
        if ( !"".equals( elementFindBy.using() ) ){
            finders.add( "how: " + elementFindBy.using() );
        }
        if ( !"".equals( elementFindBy.className() ) ){
            finders.add( "class name:" + elementFindBy.className() );
        }
        if ( !"".equals( elementFindBy.css() ) ){
            finders.add( "css:" + elementFindBy.css() );
        }
        if ( !"".equals( elementFindBy.id() ) ){
            finders.add( "id: " + elementFindBy.id() );
        }
        if ( !"".equals( elementFindBy.linkText() ) ){
            finders.add( "link text: " + elementFindBy.linkText() );
        }
        if ( !"".equals( elementFindBy.name() ) ){
            finders.add( "name: " + elementFindBy.name() );
        }
        if ( !"".equals( elementFindBy.partialLinkText() ) ){
            finders.add( "partial link text: " + elementFindBy.partialLinkText() );
        }
        if ( !"".equals( elementFindBy.tagName() ) ){
            finders.add( "tag name: " + elementFindBy.tagName() );
        }
        if ( !"".equals( elementFindBy.xpath() ) ){
            finders.add( "xpath: " + elementFindBy.xpath() );
        }

        // A zero count is okay: it means to look by name or id.
        if ( finders.size() > 1 ){
            throw new IllegalArgumentException(
                    String.format( "You must specify at most one location strategy. Number found: %d (%s)",
                                   finders.size(), finders.toString() ) );
        }
    }

    private By buildByFromFindBy ( ElementFindBy elementFindBy, Object... params ){
        this.assertValidFindBy( elementFindBy );

        By by = this.buildByFromShortFindBy( elementFindBy, params );
        if ( by == null ){
            by = this.buildByFromLongFindBy( elementFindBy, params );
        }
        return by;
    }

    private By buildByFromLongFindBy ( ElementFindBy findBy, Object... params ){
        How how = findBy.how();
        String using = params.length == 0 ? findBy.using() : String.format( findBy.using(), params );

        switch ( how ){
            case CLASS_NAME:
                return By.className( using );

            case CSS:
                return By.cssSelector( using );

            case ID:
                return By.id( using );

            case LINK_TEXT:
                return By.linkText( using );

            case NAME:
                return By.name( using );

            case PARTIAL_LINK_TEXT:
                return By.partialLinkText( using );

            case TAG_NAME:
                return By.tagName( using );

            case XPATH:
                return By.xpath( using );

            default:
                // Note that this shouldn't happen (eg, the above matches all
                // possible values for the How enum)
                throw new IllegalArgumentException( "Cannot determine how to locate element " );
        }
    }

    private By buildByFromShortFindBy ( ElementFindBy findBy, Object... params ){
        if ( !"".equals( findBy.className() ) ){
            return By.className( String.format( findBy.className(), params ) );
        }

        if ( !"".equals( findBy.css() ) ){
            return By.cssSelector( String.format( findBy.css(), params ) );
        }

        if ( !"".equals( findBy.id() ) ){
            return By.id( String.format( findBy.id(), params ) );
        }

        if ( !"".equals( findBy.linkText() ) ){
            return By.linkText( String.format( findBy.linkText(), params ) );
        }

        if ( !"".equals( findBy.name() ) ){
            return By.name( String.format( findBy.name(), params ) );
        }

        if ( !"".equals( findBy.partialLinkText() ) ){
            return By.partialLinkText( String.format( findBy.partialLinkText(), params ) );
        }

        if ( !"".equals( findBy.tagName() ) ){
            return By.tagName( String.format( findBy.tagName(), params ) );
        }

        if ( !"".equals( findBy.xpath() ) ){
            return By.xpath( String.format( findBy.xpath(), params ) );
        }

        // Fall through
        return null;
    }

    private ElementFindBy getElementFindByAnnotation () throws NoElementFindByAnnotationException{
        if ( this.elementFindBy == null ){
            throw new NoElementFindByAnnotationException();
        }
        return this.elementFindBy;
    }

    public By buildBy ( Object... params ) throws NoElementFindByAnnotationException{
        ElementFindBy findBy = this.getElementFindByAnnotation();
        return buildByFromFindBy( findBy, params );
    }

    public Class<?> getElementClass () throws NoElementFindByAnnotationException{
        return this.getElementFindByAnnotation().elementClass();
    }

    public String getElementName () throws NoElementFindByAnnotationException{
        return this.getElementFindByAnnotation().elementName();
    }

    public boolean isLookupCached (){
        return this.isLookupCached;
    }
}

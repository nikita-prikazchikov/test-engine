package selenium.support;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This is similar to FindBy interface of Selenium, but it has to more fields to simplify logging and implement element classification
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ElementFindBy {
    How how() default How.ID;

    String using() default "";

    String id() default "";

    String name() default "";

    String className() default "";

    String css() default "";

    String tagName() default "";

    String linkText() default "";

    String partialLinkText() default "";

    String xpath() default "";

    Class elementClass() default Object.class;

    String elementName() default "";
}

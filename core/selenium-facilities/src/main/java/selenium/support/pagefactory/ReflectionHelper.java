package selenium.support.pagefactory;

import java.lang.reflect.Field;

public class ReflectionHelper {

    /**
     * Looks for declared field of Class and all supers.
     * @param _class Class.
     * @param fieldName Field name.
     * @return Field or null.
     */
    public static Field getDeclaredField(Class<?> _class, String fieldName){
        Field field = null;
        try{
            field = _class.getDeclaredField(fieldName);
        } catch (NoSuchFieldException ex){
            field = ReflectionHelper.getDeclaredField(_class.getSuperclass(), fieldName);
        }
        return field;
    }
}

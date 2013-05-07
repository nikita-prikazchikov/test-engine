package selenium.exceptions;

public class ElementNotFoundException extends TestExecutionRuntimeException{
    
    private static final String STR_MESSAGE = "Element [%s] not found";
    
    public ElementNotFoundException(String name){
        super(String.format(ElementNotFoundException.STR_MESSAGE, name));
    }
}

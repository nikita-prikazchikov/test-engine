package testInterface;

public class TestDataException extends Exception{

    public TestDataException (){
    }

    public TestDataException ( String message ){
        super( message );
    }

    public TestDataException ( String message, Throwable cause ){
        super( message, cause );
    }

    public TestDataException ( Throwable cause ){
        super( cause );
    }
}

package testprocess.exceptions;

/**
 * User: nprikazchikov
 */
public class TestCaseBlockedException extends Exception {

    public TestCaseBlockedException () {
    }

    public TestCaseBlockedException ( String message ) {
        super(message);
    }

    public TestCaseBlockedException ( String message, Throwable cause ) {
        super(message, cause);
    }

    public TestCaseBlockedException ( Throwable cause ) {
        super(cause);
    }
}

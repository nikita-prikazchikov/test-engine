package testprocess.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: nprikazchikov
 * Date: 25.10.12
 * Time: 16:20
 */
public class TestCaseExecutionException extends Exception{

    public TestCaseExecutionException (){
    }

    public TestCaseExecutionException ( String message ){
        super( message );
    }

    public TestCaseExecutionException ( String message, Throwable cause ){
        super( message, cause );
    }

    public TestCaseExecutionException ( Throwable cause ){
        super( cause );
    }

    @Override
    public String toString (){
        StringBuilder string = new StringBuilder();
        string.append( "<pre>" );
        string.append( super.toString() );
        StackTraceElement[] arr = this.getStackTrace();

        for ( StackTraceElement anArr : arr ){
            string.append( "\r\n\tat " )
                  .append( anArr.toString() );
        }
        Throwable cause = getCause();
        if ( cause != null ){
            string.append( this.getCauseStackTrace( cause ) );
        }
        string.append( "</pre>" );
        return string.toString();
    }

    public String getCauseStackTrace ( Throwable cause ){
        StringBuilder str = new StringBuilder();
        str.append( "\r\n\r\nCaused by: " )
           .append( cause );

        StackTraceElement[] arr = cause.getStackTrace();
        for ( StackTraceElement anArr : arr ){
            str.append( "\r\n\tat " )
               .append( anArr.toString() );
        }
        Throwable ourCause = cause.getCause();
           if ( ourCause != null ){
               str.append( this.getCauseStackTrace( ourCause ) );
           }
        return str.toString();
    }

}

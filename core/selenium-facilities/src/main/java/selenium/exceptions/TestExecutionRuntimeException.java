package selenium.exceptions;

import selenium.exceptions.SeleniumFacilitiesException;
import selenium.log.Result;
import selenium.log.ResultList;
import selenium.log.iResult;
import testInterface.objects.utils.StepExStatus;

/**
 * Exception which identifies blocking result of execution during long test steps. Throws from push method of ResultList
 */
public class TestExecutionRuntimeException extends SeleniumFacilitiesException{

    protected iResult lastResult;

    public TestExecutionRuntimeException (){
        super( "No result provided" );
        this.setLastResult( new Result( StepExStatus.failed, "No result provided" ) );
    }

    public TestExecutionRuntimeException ( String message ){
        super( message );
        this.lastResult = new Result( StepExStatus.failed, message );
    }

    public TestExecutionRuntimeException ( iResult result ){
        this.setLastResult( result );
    }

    public TestExecutionRuntimeException ( Result result ){
        super( result.getComment() );
        this.setLastResult( result );
    }

    public TestExecutionRuntimeException ( ResultList result ){
        super( result.getLast().getComment() );
        this.setLastResult( result );
    }

    public iResult getLastResult (){
        return lastResult;
    }

    public void setLastResult ( iResult lastResult ){
        this.lastResult = lastResult;
    }

    @Override
    public String toString (){
        if ( null != this.lastResult ){
            return this.lastResult.toString();
        }
        else {
            return null;
        }
    }
}

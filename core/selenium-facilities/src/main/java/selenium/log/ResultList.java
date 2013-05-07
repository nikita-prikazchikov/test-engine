package selenium.log;

import java.util.ArrayList;

import org.springframework.web.util.HtmlUtils;
import selenium.exceptions.TestExecutionRuntimeException;
import testInterface.objects.utils.StepExStatus;

public class ResultList extends ArrayList<iResult> implements iResult{

    private String     _comment;
    private ResultList _parentResultList;

    public ResultList ( String comment ){
        this._comment = HtmlUtils.htmlEscape( comment );
        this._parentResultList = null;
    }

    public ResultList setParentResultList ( ResultList parentResultList ){
        this._parentResultList = parentResultList;
        return this;
    }

    public ResultList getParentResultList (){
        return this._parentResultList;
    }

    public iResult getLast (){
        iResult result = null;
        if ( this.size() > 0 ){
            result = this.get( this.size() - 1 );
        }
        return result;
    }

    public String getComment (){
        return this._comment;
    }

    public StepExStatus getStatus (){
        StepExStatus res = null;
        if ( this.size() > 0 ){
            res = this.get( 0 ).getStatus();
            for ( int i = 1; i < this.size(); i++ ){
                iResult r = this.get( i );
                res = res.compareTo( r.getStatus() ) < 0 ? r.getStatus() : res;
            }
        }
        return res;
    }

    public boolean isPassed (){
        return this.getStatus() == StepExStatus.passed;
    }

    public ResultList appendList ( String comment ){
        ResultList listNew = new ResultList( comment ).setParentResultList( this ); // create new result list and set parent list for it.
        this.add( listNew );
        return listNew;
    }

    public ResultList popList (){
        if ( this.getParentResultList() != null ){
            return this.getParentResultList();
        }
        return this;
    }

    public ResultList push ( iResult result ) throws TestExecutionRuntimeException{
        return this.push( result, false );
    }

    public ResultList push ( iResult result, boolean notBlocking ) throws TestExecutionRuntimeException{
        this.add( result );
        if ( !notBlocking ){
            if ( result.getStatus() != StepExStatus.passed ){
                throw new TestExecutionRuntimeException( this );
            }
        }
        return this;
    }

    public ResultList push ( Exception ex ) throws TestExecutionRuntimeException{
        return this.push( ex, false );
    }

    public ResultList push ( Exception ex, boolean notBlocking ) throws TestExecutionRuntimeException{
        if ( ex instanceof TestExecutionRuntimeException ){
            if ( !( (TestExecutionRuntimeException) ex ).getLastResult().equals( this ) ){
                this.push( ( (TestExecutionRuntimeException) ex ).getLastResult(), true );
            }
        }
        else {
            ex.printStackTrace();
            this.push( new Result( StepExStatus.failed, ex.toString() ), notBlocking );
        }
        return this;
    }

    public ResultList info ( String message ) throws TestExecutionRuntimeException{
        return this.push( new Result( StepExStatus.passed, message ), false );
    }

    @Override
    public String toString (){
        StringBuilder str = new StringBuilder();

        str.append( '{' );
        str.append( "\"status\":\"" ).append( this.getStatus() ).append( '"' ).append( ',' );   // Status value
        str.append( "\"comment\":\"" ).append( this.getComment() ).append( '"' ).append( ',' ); // Comment value

        // children results
        str.append( "\"children\":[" );
        for ( int i = 0; i < this.size(); i++ ){
            str.append( this.get( i ).toString() ).append( ',' );
        }
        str.deleteCharAt( str.length() - 1 ).append( ']' ); // remove ',' at the end and close array
        str.append( '}' );
        return str.toString();
    }

    public String toHTML (){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "<ul>" );
        stringBuilder.append( String.format( "<span>%s</span> - <span>%s</span>", this.getStatus().toString(), this.getComment() ) );
        for ( int i = 0; i < this.size(); i++ ){
            stringBuilder.append( "<li>" );
            stringBuilder.append( this.get( i ).toHTML() );
            stringBuilder.append( "</li>" );
        }
        stringBuilder.append( "</ul>" );
        return stringBuilder.toString();
    }
}

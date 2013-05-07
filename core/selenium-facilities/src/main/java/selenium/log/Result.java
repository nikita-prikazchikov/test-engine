package selenium.log;

import org.springframework.web.util.HtmlUtils;
import selenium.bots.ActionBot;
import testInterface.objects.utils.StepExStatus;

public class Result implements iResult{

    private static Boolean NO_SCREENSHOT = false;

    private String _comment;

    private Screenshot _screenshot;

    private StepExStatus _status;

    public Result (){
        this( StepExStatus.not_executed, "" );
    }

    public Result ( String comment ){
        this( StepExStatus.not_executed, comment );
    }

    public Result ( StepExStatus status, String comment ){
        this.setStatus( status );
        this.setComment( comment );
    }

    public Result ( StepExStatus status, Screenshot screenshot ){
        this( status, "" );
        this._screenshot = screenshot;
    }

    public Result ( boolean status, String comment ){
        this( status ? StepExStatus.passed : StepExStatus.failed, comment );
    }

    public static void setNoScreenshot ( Boolean noScreenshot ){
        Result.NO_SCREENSHOT = noScreenshot;
    }

    public Screenshot getScreenshot (){
        return _screenshot;
    }

    public StepExStatus getStatus (){
        return this._status;
    }

    public boolean isPassed (){
        return this.getStatus() == StepExStatus.passed;
    }

    public String getComment (){
        return this._comment;
    }

    public String toHTML (){
        return String.format( "<span>%s</span> - <span>%s</span>", this.getStatus().toString(), this.getComment() );
    }

    public Result setComment ( String value ){
        this._comment = HtmlUtils.htmlEscape( value );
        return this;
    }

    public void setScreenshot ( Screenshot _screenshot ){
        this._screenshot = _screenshot;
    }

    public Result setStatus ( StepExStatus status ){
        return this.setStatus( status, NO_SCREENSHOT );
    }

    public Result setStatus ( StepExStatus status, boolean noScreenshot ){
        this._status = status;

        if ( this._status == StepExStatus.failed && !noScreenshot ){    // post screenshot
            Screenshot screenshot = new ActionBot().makeScreenshot( "Failed result" );
            this.setScreenshot( screenshot );
        }
        return this;
    }

    @Override
    public String toString (){
        return String.format(
                "{\"status\":\"%s\",\"comment\":\"%s\", \"images\":[%s]}",
                this.getStatus(), this.getComment(),
                ( null == this.getScreenshot() ? "" : this.getScreenshot().toString() )
        );
    }
}

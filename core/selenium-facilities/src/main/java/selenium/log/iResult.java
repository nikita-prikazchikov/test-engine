package selenium.log;

import testInterface.objects.utils.StepExStatus;

public interface iResult {

    public StepExStatus getStatus();
    public boolean isPassed();
    public String getComment();
    public String toHTML();
}

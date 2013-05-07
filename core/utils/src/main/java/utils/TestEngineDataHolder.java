package utils;


/**
 *
 */
public class TestEngineDataHolder implements Data{

    private static Data data = new TestEngineDataHolder();

    private String browser;

    private String testCaseId;

    private Integer testCycleId;

    private String token;

    private String url;

    private String workspacePath;

    public static TestEngineDataHolder getData (){
        return (TestEngineDataHolder) data;
    }

    public String getBaseUrl (){
        return url;
    }

    public String getBrowser (){
        return browser;
    }

    public String getTestCaseId (){
        return testCaseId;
    }

    public Integer getTestCycleId (){
        return testCycleId;
    }

    public String getToken (){
        return token;
    }

    public String getWorkspacePath (){
        return workspacePath;
    }

    public void setBrowser ( String browser ){
        this.browser = browser;
    }

    public void setTestCaseId ( String testCaseId ){
        this.testCaseId = testCaseId;
    }

    public void setTestCycleId ( Integer testCycleId ){
        this.testCycleId = testCycleId;
    }

    public void setToken ( String token ){
        this.token = token;
    }

    public void setUrl ( String url ){
        this.url = url;
    }

    public void setWorkspacePath ( String workspacePath ){
        this.workspacePath = workspacePath;
    }
}

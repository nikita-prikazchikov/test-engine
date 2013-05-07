package testprocess.runner;

import org.apache.log4j.Logger;
import selenium.ContextObject;
import selenium.exceptions.ElementNotFoundException;
import selenium.exceptions.SeleniumFacilitiesException;
import selenium.bots.AbstractBot;
import selenium.log.Result;
import selenium.log.ResultList;
import selenium.log.iResult;
import selenium.exceptions.TestExecutionRuntimeException;
import testInterface.TestDataException;
import testInterface.context.ContextHolder;
import testInterface.objects.TestCaseExecutionLog;
import testInterface.objects.TestCaseVersion;
import testInterface.objects.TestStep;
import testInterface.objects.utils.Status;
import testInterface.objects.utils.StepExStatus;
import testprocess.exceptions.DriverManagerException;
import testprocess.exceptions.TestCaseBlockedException;
import testprocess.exceptions.TestCaseExecutionException;
import utils.Storage;
import utils.TestData;
import utils.TestEngineDataHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestCaseVersionRunner extends DriverManager{

    private static final Logger logger = Logger.getLogger( TestCaseVersionRunner.class );

    private static final long SLEEP_TIME    = 1000;
    private static final long WAIT_FOR_TIME = 1000 * 60 * 5;

    private String neytiriUrl;
    private String statusUpdateUrl;
    private String statusIdLog;
    private static final String STATUS_IN_PROGRESS = "in_progress";
    private static final String STATUS_SUCCESS     = "success";

    private String       _token         = "";
    private StepExStatus testCaseStatus = StepExStatus.not_executed;

    public TestCaseVersionRunner ( final String browserName ) throws DriverManagerException, TestDataException{
        super( browserName ); // TODO remove browserName parameter since each test will run on it is own browser

        ContextHolder c = ContextHolder.getInstance();
        this.neytiriUrl = ( (String) c.getBean( "NeytiriURL" ) ).replaceAll( "/+$", "" );
        this.statusUpdateUrl = "/" + ( (String) c.getBean( "NeytiriUpdateURL" ) ).replaceAll( "^/+", "" );
        this.statusIdLog = ( (String) c.getBean( "NeytiriStatusIdLog" ) ).replaceAll( "^/+", "" );
    }

    public void run ( int cycle ) throws DriverManagerException{

        logger.info( "--------------------------------------------------------------------------------------------" );
        logger.info( "Start run process" );
        logger.info( "Sleep time  [" + SLEEP_TIME + "]" );
        logger.info( "Worker browser [" + getBrowser().getName() + ']' );

        this.set_token( TestEngineDataHolder.getData().getToken() );

        this.startBrowser();

        int waitTime = 0;
        while ( waitTime < WAIT_FOR_TIME ){
            TestCaseExecutionLog testCaseExecutionLog = null;
            try {
                testCaseExecutionLog = this.databaseManager.startTestProcess( getBrowser(), cycle );
            }
            catch ( TestDataException e ){
                logger.error( "Not started test process", e );
            }
            if ( testCaseExecutionLog != null ){
                this.sendStatusUpdate(
                        String.format(
                                "Start execution test case: [%s] with id [%s]"
                                , testCaseExecutionLog.getTestCaseVersion().getName()
                                , testCaseExecutionLog.getId()
                        )
                        , testCaseExecutionLog.getId()
                        , STATUS_IN_PROGRESS
                );

                // Initiate test data foreach test case execution
                TestData.clear();
                Storage.clear();

                try {
                    testCaseVersionProcess( testCaseExecutionLog );
                }
                catch ( DriverManagerException e ){
                    logger.error( "testCaseVersionProcess failed", e );
                }

                this.sendStatusUpdate(
                        String.format(
                                "Test case execution done: [%s]"
                                , testCaseExecutionLog.getTestCaseVersion().getName()
                        )
                        , null
                        , STATUS_IN_PROGRESS
                );
                waitTime = 0;
                if ( testCaseExecutionLog.getStatus() != Status.passed ){
                    this.closeBrowser();
                    this.startBrowser();
                }
            }
            else {
                if ( cycle > 0 ){
                    this.sendStatusUpdate(
                            String.format(
                                    "Test case execution on browser %s done."
                                    , getBrowser().getName()
                            )
                            , null
                            , STATUS_SUCCESS
                    );
                    this.closeBrowser();
                    return;     // desirable cycle has been performed so there is no need to wait others
                }
                try {
                    Thread.sleep( SLEEP_TIME );
                    waitTime += SLEEP_TIME;
                }
                catch ( InterruptedException e ){
                    logger.error( e );
                }
            }
        }
    }

    public void runTestCase ( int testCaseId ) throws DriverManagerException{

        logger.info( "--------------------------------------------------------------------------------------------" );
        logger.info( String.format("Start case #%s", testCaseId) );
        logger.info( "Worker browser [" + getBrowser().getName() + ']' );

        this.startBrowser();

        TestCaseVersion testCaseVersion = null;
        try {
            testCaseVersion = this.databaseManager.getTestCaseVersion( testCaseId );
        }
        catch ( TestDataException e ){
            logger.error( "Not started test case", e );
        }
        if ( testCaseVersion != null ){
            // Initiate test data foreach test case execution
            TestData.clear();
            Storage.clear();

            try {
                testCaseVersionProcess( testCaseVersion );
            }
            catch ( DriverManagerException e ){
                logger.error( "testCaseVersionProcess failed", e );
            }
            catch ( TestExecutionRuntimeException e ){
                logger.error( e.toString() );
            }
        }
        else {
            logger.error( "Test case was not found" );
        }
    }

    protected iResult testCaseVersionProcess ( final TestCaseExecutionLog testCaseExecutionLog ) throws DriverManagerException{

        logger.info( "Test case execution: START" );

        this.setTestCaseStatus( StepExStatus.not_executed );

        int stepId = 0;
        int time = 0;

        TestCaseVersion testCaseVersion = testCaseExecutionLog.getTestCaseVersion();
        ResultList resultLog = new ResultList( "Test case log" );
        iResult testStepLog;

        if ( testCaseVersion != null ){
            logger.info( "reset browser" );
            resetDriver();
            AbstractBot.setDriver( getWebDriver() );
            List<TestStep> testStepList = testCaseVersion.getTestSteps();

            try {

                boolean testCaseInvalid = false;
                TestCaseVersionVerifier testCaseVersionVerifier = new TestCaseVersionVerifier();
                for ( stepId = 0; stepId < testStepList.size(); stepId++ ){
                    iResult r = testCaseVersionVerifier.verifyTestStep( testStepList.get( stepId ) );
                    if(!r.isPassed()) {
                        testCaseInvalid = true;
                        try {
                            // report error log for test step
                            this.databaseManager.addTestStepExecutionLog( testStepList.get( stepId ), testCaseExecutionLog, StepExStatus.blocked, r.toString() );
                        } catch ( TestDataException e1 ){
                            logger.error( e1 );
                        }
                    }
                }
                if(testCaseInvalid)
                    throw new TestExecutionRuntimeException();  // stop execution.

                for ( stepId = 0; stepId < testStepList.size(); stepId++ ){
                    TestStep testStep = testStepList.get( stepId );
                    logger.info( "Get test step [" + testStep.getName() + "]" );
                    TestStepScript testStepScript = null;
                    testStepLog = null;

                    try {
                        testStepScript = TestStepScript.buildScript( testStep.getScript() );
                    }
                    catch ( Exception e ){
                        this._reportTestStepExecutionError( testStepList, stepId, testCaseExecutionLog, e.toString() );
                    }

                    if ( testStepScript != null ){
                        logger.info( "script : \n" + testStepScript );
                        try {
                            try {
                                testStepLog = this.runTestStep( testStepScript );
                            }
                            catch ( TestExecutionRuntimeException e ){
                                testStepLog = e.getLastResult();
                                e.printStackTrace();
                            }

                            if ( testStepLog.getStatus() != StepExStatus.passed ){
                                this._reportTestStepExecutionError( testStepList, stepId, testCaseExecutionLog, testStepLog.toString() );
                            }
                            else {
                                this.databaseManager.addTestStepExecutionLog( testStep, testCaseExecutionLog, testStepLog.getStatus(), testStepLog.toString() );
                                this.updateTestCaseExecutionStatus( testStepLog.getStatus() );
                            }
                        }
                        catch ( TestCaseExecutionException e ){
                            this._reportTestStepExecutionError( testStepList, stepId, testCaseExecutionLog, e.toString() );
                        }
                        catch ( TestDataException e ){
                            logger.error( "process for step " + testStep.toString() + " failed", e );
                        }
                        if ( null != testStepLog ){
                            resultLog.push( testStepLog, true );
                        }
                    }
                }
            }
            catch ( TestCaseBlockedException e ){
                logger.info( e.toString() );
                for ( stepId++; stepId < testStepList.size(); stepId++ ){
                    TestStep ts = testStepList.get( stepId );
                    try {
                        this.databaseManager.addTestStepExecutionLog( ts, testCaseExecutionLog, StepExStatus.blocked, "" );
                    }
                    catch ( TestDataException e1 ){
                        logger.error( e1 );
                    }
                }
            }
            catch ( TestExecutionRuntimeException ignored ){
            }
        }
        try {
            this.databaseManager.stopProcessForTestCase(
                    testCaseExecutionLog,
                    ( this.getTestCaseStatus() == StepExStatus.passed
                      ? Status.passed
                      : Status.failed ),
                    this.getTestCaseStatus() );

            logger.info( "Test case execution: DONE" );
            logger.info( "========================================================================================================================" );
        }
        catch ( TestDataException e ){
            logger.error( e );
        }
        return resultLog;
    }

    protected iResult testCaseVersionProcess ( final TestCaseVersion testCaseVersion ) throws DriverManagerException, TestExecutionRuntimeException{

        logger.info( "Test case version execution: START" );

        Date start = new Date(),
                end;

        int stepId = 0;
        iResult localResult;

        ResultList resultLog = new ResultList( "Test log" );

        if ( testCaseVersion != null ){
            iResult verifcationResult = new TestCaseVersionVerifier().verifyTestCaseVersion( testCaseVersion );
            if(!verifcationResult.isPassed()) {
                logger.info( "Script error :" + verifcationResult.toString() );
                logger.info( "========================================================================================================================" );
                return verifcationResult;
            }

            logger.info( "reset browser" );
            resetDriver();
            AbstractBot.setDriver( getWebDriver() );
            List<TestStep> testStepList = testCaseVersion.getTestSteps();

            for ( stepId = 0; stepId < testStepList.size(); stepId++ ){
                TestStep testStep = testStepList.get( stepId );
                logger.info( String.format( "Get test step #%s [%s]", stepId, testStep.getName() ) );
                localResult = null;

                TestStepScript testStepScript = null;
                try {
                    testStepScript = TestStepScript.buildScript( testStep.getScript() );
                }
                catch ( Exception e ){
                    e.printStackTrace();
                }

                if ( testStepScript != null ){
                    logger.info( "script : \n" + testStepScript );
                    try {
                        localResult = this.runTestStep( testStepScript );
                    }
                    catch ( TestCaseExecutionException e ){
                        e.printStackTrace();
                    }
                    catch ( TestExecutionRuntimeException e ){
                        iResult result = e.getLastResult();
                        if ( result instanceof Result ){
                            resultLog.push( result );
                        }
                        else if ( result instanceof ResultList ){
                            localResult = (ResultList) result;
                        }
                    }
                    if ( null != localResult ){
                        logger.info( "Script return :" + localResult.toString() );
                        resultLog.push( localResult, true );
                    }
                }
            }
        }
        end = new Date();
        long executionTime = end.getTime() - start.getTime();

        logger.info( String.format( "Test case execution time: [%s:%s]",
                                    TimeUnit.MILLISECONDS.toMinutes( executionTime ),
                                    TimeUnit.MILLISECONDS.toSeconds( executionTime ) -
                                            TimeUnit.MINUTES.toSeconds( TimeUnit.MILLISECONDS.toMinutes( executionTime ) ) ) );
        logger.info( String.format( "Test case execution status: [%s]", resultLog.getStatus() ) );
        logger.info( "Test case execution: DONE" );
        logger.info( "========================================================================================================================" );
        return resultLog;
    }

    private iResult runTestStep ( TestStepScript testStepScript ) throws TestCaseExecutionException, TestExecutionRuntimeException{

        ResultList testStepLog = new ResultList( "Test step log" );
        try {

            Class xClass = Class.forName( testStepScript.getClassName() );
            List<ChainElement> methods = testStepScript.getMethodList();

            Constructor constructor = xClass.getConstructor();
            Object context = constructor.newInstance();

            for ( ChainElement method : methods ){
                Class[] classes = method.getClasses();

                Method testMethod = ( classes.length == 0 )
                                    ? ( context.getClass().getMethod( method.getMethodName() ) )
                                    : ( context.getClass().getMethod( method.getMethodName(), classes ) );

                Object[] parameters = method.getParameters();

                Object resultObject;
                resultObject = ( parameters.length == 0 )
                               ? ( testMethod.invoke( context ) )
                               : ( testMethod.invoke( context, parameters ) );

                if ( resultObject instanceof ContextObject ){
                    context = resultObject;
                }
                else if ( resultObject != null && resultObject instanceof iResult ){
                    if ( methods.size() == 1 ){
                        logger.info( String.format( "Status [%s]", ( (iResult) resultObject ).getStatus() ) );
                        if ( resultObject instanceof ResultList ){
                            testStepLog = (ResultList) resultObject;
                        }
                        else if ( resultObject instanceof Result ){
                            return (Result) resultObject;
                        }
                    }
                    else {
                        testStepLog.push( (iResult) resultObject, true );
                    }
                }
                else {
                    testStepLog.push( new Result( StepExStatus.failed, "Unexpected or NULL result received" ) );
                }
            }
        }
        catch ( SeleniumFacilitiesException e ){
            testStepLog.push( e, true );
        }
        catch ( InvocationTargetException e ){
            if ( e.getTargetException() instanceof TestExecutionRuntimeException ){
                return ( (TestExecutionRuntimeException) e.getTargetException() ).getLastResult();
            }
            if ( e.getTargetException() instanceof ElementNotFoundException ){
                testStepLog.push( (ElementNotFoundException) e.getTargetException(), true );
            }
            if ( e.getTargetException() instanceof SeleniumFacilitiesException ){
                testStepLog.push( (SeleniumFacilitiesException) e.getTargetException(), true );
                logger.info( e.getTargetException().toString() );
            }
            else {
                testStepLog.push( e, true );
                throw new TestCaseExecutionException( e );
            }
        }
        catch ( Exception e ){
            testStepLog.push( e, true );
            throw new TestCaseExecutionException( e );
        }
        return testStepLog;
    }

    private void _reportTestStepExecutionError ( List<TestStep> testStepList, int testStepId, TestCaseExecutionLog testCaseExecutionLog, String log ) throws TestCaseBlockedException{
        TestStep testStep = testStepList.get( testStepId );
        try {
            logger.info( "Error in processing current test step" );

            this.databaseManager.addTestStepExecutionLog( testStep, testCaseExecutionLog, StepExStatus.failed, log );

            this.updateTestCaseExecutionStatus( StepExStatus.failed );

            if ( testStep.getBlocking() ){
                throw new TestCaseBlockedException( "The step is blocking a test execution. All next steps are going to be set as blocked" );
            }
        }
        catch ( TestDataException e ){
            logger.error( "process for step " + testStep.toString() + " failed", e );
        }
    }

    protected void sendStatusUpdate ( String message, Integer id, String status ){

        BufferedReader in = null;

        if ( !this.get_token().equals( "" ) ){
            try {
                String _url;
                if ( null != id ){
                    _url = String.format( this.neytiriUrl + statusUpdateUrl + statusIdLog, this.get_token(), URLEncoder.encode( status, "UTF-8" ), URLEncoder.encode( message, "UTF-8" ), id );
                }
                else {
                    _url = String.format( this.neytiriUrl + statusUpdateUrl, this.get_token(), status, URLEncoder.encode( message, "UTF-8" ) );
                }
                logger.info( "URL : " + _url );


                URL url = new URL( _url );
                URLConnection connection = url.openConnection();
                HttpURLConnection httpConnection = (HttpURLConnection) connection;

                if ( httpConnection.getResponseCode() != HttpURLConnection.HTTP_OK ){
                    in = new BufferedReader(
                            new InputStreamReader(
                                    connection.getInputStream() ) );

                    String decodedString;
                    while ( ( decodedString = in.readLine() ) != null ){
                        System.out.println( decodedString );
                    }
                }
            }
            catch ( IOException e ){
                if ( null != in ){
                    try {
                        in.close();
                    }
                    catch ( IOException ignored ){
                    }
                }
                e.printStackTrace();
            }
        }
    }

    //================================================================================================================================================

    public String get_token (){
        return _token;
    }

    public void set_token ( String _token ){
        this._token = _token;
    }

    public StepExStatus getTestCaseStatus (){
        return testCaseStatus;
    }

    public TestCaseVersionRunner setTestCaseStatus ( StepExStatus testCaseStatus ){
        this.testCaseStatus = testCaseStatus;
        return this;
    }

    public void updateTestCaseExecutionStatus ( StepExStatus status ){

        switch ( status ){
            case failed:
                this.setTestCaseStatus( StepExStatus.failed );
                break;
            case passed:
                if ( this.getTestCaseStatus() != StepExStatus.failed ){
                    this.setTestCaseStatus( StepExStatus.passed );
                }
                break;
        }
    }
}

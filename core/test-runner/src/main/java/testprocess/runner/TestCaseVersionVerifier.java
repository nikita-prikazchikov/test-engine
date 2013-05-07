package testprocess.runner;

import selenium.log.Result;
import selenium.log.ResultList;
import selenium.log.iResult;
import selenium.exceptions.TestExecutionRuntimeException;
import testInterface.TestDataException;
import testInterface.objects.TestCaseVersion;
import testInterface.objects.TestStep;
import testInterface.objects.utils.StepExStatus;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

public class TestCaseVersionVerifier /*extends DriverManager */{

//    public TestCaseVersionVerifier ( final String browserName ) throws DriverManagerException, TestDataException{
//        super( browserName );
//    }

    public boolean verifyTestCycle ( int testCycleId ) throws TestDataException{
//        TestCaseExecutionLog testCaseExecutionLog = null;
//        do{
//            testCaseExecutionLog = this.databaseManager.getTestCaseExecutionLog( this.getBrowser(), testCycleId );
//        } while ( testCaseExecutionLog != null )
        return true; // TODO
    }

//    public iResult verifyTestCase ( int testCaseId ) throws TestExecutionRuntimeException, TestDataException {
//        TestCaseVersion testCaseVersion = this.databaseManager.getTestCaseVersion( testCaseId );
//        return this.verifyTestCaseVersion( testCaseVersion );
//    }

    public iResult verifyTestCaseVersion ( TestCaseVersion testCaseVersion ) throws TestExecutionRuntimeException {
        ResultList result = new ResultList( String.format( "Verify test case version [%s]", testCaseVersion.getName() ) );
        try {
            List<TestStep> testStepList = testCaseVersion.getTestSteps();
            for ( int stepIndex = 0; stepIndex < testStepList.size(); stepIndex++ ){
                TestStep testStep = testStepList.get( stepIndex );
                iResult r = this.verifyTestStep( testStep );
                result.push( r );
            }
        } catch ( Exception ex ) {
            result.push( ex );
        }
        return result;
    }

    public iResult verifyTestStep ( TestStep testStep ) throws TestExecutionRuntimeException {
        TestStepScript testStepScript = null;
        ResultList result = new ResultList( "Verify test step" );

        Result r = new Result( String.format( "Build test step script [%s]", testStep.getScript()) );
        try {
            testStepScript = TestStepScript.buildScript( testStep.getScript() );
            r.setStatus( StepExStatus.passed, true );
        } catch ( Exception e ){
            r.setStatus( StepExStatus.failed, true );
            r.setComment( "Parsing error: " + r.getComment() );
        }
        result.push( r, true );

        if(r.isPassed()) {
            result.push( this.verifyTestStepScript( testStepScript), true );
        }

        return result;
    }

    public iResult verifyTestStepScript ( TestStepScript testStepScript ) throws TestExecutionRuntimeException {
        ResultList result = new ResultList( "Verify test step script" );
        Result r = null;
        try {
            Class xClass = null;

            r = new Result( "Create class instance." );
            try{
                xClass = Class.forName( testStepScript.getClassName() );
                Constructor constructor = xClass.getConstructor();  // verify there is default constructor
                r.setStatus( StepExStatus.passed );
            } catch ( Exception ex ) {
                r.setStatus( StepExStatus.failed, true );
                r.setComment( r.getComment() + " " + ex.getMessage());
            }
            result.push( r );

            if(r.isPassed()) {
                Class context = xClass;
                List<ChainElement> methods = testStepScript.getMethodList();
                for ( ChainElement method : methods ){
                    Class[] classes = method.getClasses();

                    // verify there is method
                    r = new Result( String.format( "Verify class [%s] has method [%s]", context.toString(), method.getMethodName() ) );
                    try{
                        Method testMethod = ( classes.length == 0 )
                                            ? ( context.getMethod( method.getMethodName() ) )
                                            : ( context.getMethod( method.getMethodName(), classes ) );
                        context = testMethod.getReturnType();   // update current context
                        r.setStatus( StepExStatus.passed );
                    } catch ( NoSuchMethodException ex ) {
                        r.setStatus( StepExStatus.failed, true );
                    }

                    result.push( r, true );
                    if(!r.isPassed()) {
                        break;
                    }
                }
            }
        } catch ( Exception ex ) {
            result.push( ex, true );
        }
        return result;
    }

}

package testprocess;

import org.apache.log4j.Logger;
import testInterface.TestDataException;
import testprocess.exceptions.DriverManagerException;
import testprocess.runner.TestCaseVersionRunner;
import utils.TestEngineDataHolder;
import utils.exceptions.StartParameterException;

public class ProgramRunner{

    private static Logger logger = Logger.getLogger( ProgramRunner.class );

    public static Logger getLogger (){
        return logger;
    }

    public static void main ( String[] args ){

        logger.info( "Run program" );

        try {
            setProperties();

            String browserName = TestEngineDataHolder.getData().getBrowser();
            String testCaseId = TestEngineDataHolder.getData().getTestCaseId();

            TestCaseVersionRunner runner = new TestCaseVersionRunner( browserName );

            if ( null != testCaseId ){
                String[] testCaseIds = testCaseId.split( "," );
                for ( String id : testCaseIds ){
                    runner.runTestCase( Integer.parseInt( id ) );
                    // Close browser if there is more than one test case for execution.
                    // This prevent from closing browser on error or test case execution done. Helpful for debugging
                    if ( testCaseIds.length > 1 ){
                        runner.closeBrowser();
                    }
                }
            }
            else {
                runner.run( TestEngineDataHolder.getData().getTestCycleId() );
            }
        }
        catch ( DriverManagerException e ){
            logger.fatal( e.getMessage(), e );
        }
        catch ( TestDataException e ){
            logger.fatal( e.getMessage(), e );
        }
        catch ( StartParameterException e ){
            logger.fatal( e.getMessage(), e );
        }
    }

    private static void setProperties () throws StartParameterException{

        String error = "Missing required parameter(s) [%s]";

        String property;
        TestEngineDataHolder data = TestEngineDataHolder.getData();

        property = System.getProperty( "project.url" );
        if ( null == property ){
            throw new StartParameterException( String.format( error, "project.url" ) );
        }
        else {
            logger.info( String.format( "Project base url: [%s]", property ) );
        }
        data.setUrl( property );

        property = System.getProperty( "browser" );
        if ( null == property ){
            throw new StartParameterException( String.format( error, "browser" ) );
        }
        else {
            logger.info( String.format( "Browser: [%s]", property ) );
        }
        data.setBrowser( property );

        property = System.getProperty( "workspace.path" );
        if ( null != property ){
            logger.info( String.format( "Workspace directory id: [%s]", property ) );
        }
        data.setWorkspacePath( property );

        Integer propertyInt = Integer.getInteger( "cycleId", 0 );
        if ( null != property ){
            logger.info( String.format( "Cycle id: [%s]", property ) );
        }

        data.setTestCycleId( propertyInt );

        property = System.getProperty( "testCaseId" );
        if ( null != property ){
            logger.info( String.format( "Test cases id(s): [%s]", property ) );
        }
        data.setTestCaseId( property );

        property = System.getProperty( "token", "" );
        if ( !property.equals( "" ) ){
            logger.info( String.format( "Token: [%s]", property ) );
        }
        data.setToken( property );

        if ( null == data.getTestCaseId() && null == data.getTestCycleId() ){
            throw new StartParameterException( String.format( error, "cycleId or testCaseId" ) );
        }
    }
}

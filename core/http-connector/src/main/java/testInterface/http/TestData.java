package testInterface.http;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import testInterface.TestDataException;
import testInterface.TestDataInterface;
import testInterface.context.ContextHolder;
import testInterface.objects.*;
import testInterface.objects.utils.Status;
import testInterface.objects.utils.StepExStatus;
import utils.Utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestData implements TestDataInterface{

    private ContextHolder context;
    private String cookieSessionId;

    public TestData () {
        this.context = ContextHolder.getInstance();
    }

    private String getServiceUrl() throws MalformedURLException{
        URL url = new URL((String)this.context.getBean("TestServiceUrl"));
        String strUrl = url.getProtocol() + "://" + url.getHost() + ( url.getPort() != -1 ? ":" + url.getPort() : "") + "/";
        return strUrl;
    }
    private String getLogin() {
        return (String)this.context.getBean("TestServiceLogin");
    }
    private String getPassword() {
        return (String)this.context.getBean("TestServicePassword");
    }

    private void httpLogin() throws Exception{
        String url = String.format("%ssite/login?login=%s&password=%s", this.getServiceUrl(), this.getLogin(), this.getPassword());
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        HttpResponse response = client.execute(request);
        Header[] headers = response.getHeaders("Set-Cookie");

        Pattern pattern = Pattern.compile("PHPSESSID=[\\w\\d]+");
        Matcher m = pattern.matcher(headers[headers.length - 1].getValue());
        m.find();

        this.cookieSessionId = String.valueOf(m.group(0));
    }
    private String httpGet(String url) throws Exception{
        StringBuilder str = new StringBuilder();

        if(this.cookieSessionId == null)
            this.httpLogin();

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        request.setHeader("Cookie", this.cookieSessionId);

        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        String line = "";
        while ((line = rd.readLine()) != null) {
            str.append(line);
        }

        return str.toString();
    }
    private String httpPost(String url, JSONObject obj) throws Exception{
        StringBuilder str = new StringBuilder();

        if(this.cookieSessionId == null)
            this.httpLogin();

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.setHeader("Cookie", this.cookieSessionId);
        post.setEntity(new StringEntity(obj.toString()));

        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        String line = "";
        while ((line = rd.readLine()) != null) {
            str.append(line);
        }

        return str.toString();
    }
    private String httpPut(String url, JSONObject obj) throws Exception{
        StringBuilder str = new StringBuilder();

        if(this.cookieSessionId == null)
            this.httpLogin();

        HttpClient client = new DefaultHttpClient();
        HttpPut put = new HttpPut(url);
        put.setHeader("Cookie", this.cookieSessionId);
        put.setEntity(new StringEntity(obj.toString()));

        HttpResponse response = client.execute(put);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        String line = "";
        while ((line = rd.readLine()) != null) {
            str.append(line);
        }

        return str.toString();
    }

    public void addTestStepExecutionLog(TestStep testStep, TestCaseExecutionLog testCaseExecutionLog, StepExStatus status, String log) throws TestDataException{
        TestStepExecutionLog testStepExecutionLog = new TestStepExecutionLog( testStep, testCaseExecutionLog, status, Utils.getCurrentTimestamp(), log );
        try{
            String url = String.format("%ssteplog", this.getServiceUrl());
            this.httpPost(url, testStepExecutionLog.toJSONObject());

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new TestDataException();
        }
    }

    public Browser getBrowser(String browserName) throws TestDataException{
        Browser b = null;
        String url;
        try{
            url = String.format("%sbrowsers/get?name=%s", this.getServiceUrl(), browserName);
            String str = this.httpGet(url);
            JSONObject obj = new JSONObject(str);

            b = new Browser(obj);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new TestDataException();
        }

        return b;
    }
    public TestCaseExecutionLog getTestCaseExecutionLog(Browser browser, int cycle) throws TestDataException{
        String str = "";
        TestCaseExecutionLog log = null;
        try {
            String url = String.format( "%scaselog/?browserId=%s&cycleId=%s", this.getServiceUrl(), browser.getId(), cycle );
            Response response = null;
            do {
                str = this.httpGet( url );
                JSONObject obj = new JSONObject( str );
                if ( obj.length() != 0 ){
                    response = new Response( obj );
                    if ( !response.getSuccess() ){
                        if ( response.getCode().equals( Response.JSON_CODE_WAIT ) ){
                            Thread.sleep( 5000 );
                            continue;
                        }
                        if ( response.getCode().equals( Response.JSON_CODE_DONE ) ){
                            log = null;
                            break;
                        }
                    }
                    log = new TestCaseExecutionLog( obj );
                } else {
                    throw new TestDataException( "Empty result" );
                }
            } while ( !response.getSuccess() );
        } catch ( JSONException e ) {
            System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv JSON vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
            System.out.println(str);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ JSON ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            throw new TestDataException();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new TestDataException();
        }
        return log;
    }

    public TestCaseExecutionLog startTestProcess(Browser browser, int cycle) throws TestDataException{

        TestCaseExecutionLog log = null;
        Response response = null;
        String url;
        Date startDate = new Date();
        JSONObject obj = null;

        try {
            do {
                log = this.getTestCaseExecutionLog( browser, cycle );

                log.setStatus( Status.in_progress );
                log.setIpWorker( Utils.getIpAdress() );
                log.setTimestampStart( Utils.getCurrentTimestamp() );

                url = String.format( "%scaselog/%s", this.getServiceUrl(), log.getId() );
                obj = new JSONObject( this.httpPut( url, log.toJSONObject() ) );
                response = new Response( obj );
                if ( !response.getSuccess() ){
                    if (response.getCode().equals( Response.JSON_CODE_BUSY )  ){
                        System.out.println( "Test case is already in use. Get new one." );
                    }
                    else {
                        throw new TestDataException( response.getMessage() );
                    }
                }
            }
            while ( !response.getSuccess() );
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new TestDataException();
        }
        System.out.println( ">>>>> Fetching test case execution log time: " + (new Date().getTime() - startDate.getTime()) + " ms" );
        return log;
    }


    public void stopProcessForTestCase(TestCaseExecutionLog log, Status status, StepExStatus executionStatus) throws TestDataException{
        try {
            log.setStatus(status);
            log.setTimestampEnd(Utils.getCurrentTimestamp());

            String url = String.format("%scaselog/%s", this.getServiceUrl(), log.getId());
            this.httpPut(url, log.toJSONObject());
        } catch (Exception e) {
            e.printStackTrace();
            throw new TestDataException();
        }
    }

    public void updateTestCaseStatus(TestCaseExecutionLog log, Status status) throws TestDataException{
        try{
            log.setStatus(status);

            String url = String.format("%scaselog/%s", this.getServiceUrl(), log.getId());
            this.httpPut(url, log.toJSONObject());
        } catch (Exception e) {
            e.printStackTrace();
            throw new TestDataException();
        }
    }

    public TestCaseVersion getTestCaseVersion(int testCaseId) throws TestDataException{
        TestCaseVersion v = null;
        String url;
        Date startDate = new Date();
        try{
            url = String.format("%scase/%s", this.getServiceUrl(), testCaseId);
            String str = this.httpGet(url);
            JSONObject obj = new JSONObject(str);

            v = new TestCaseVersion(obj.getJSONObject("version"));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new TestDataException();
        }
        System.out.println( ">>>>> Fetching test case execution log time: " + (new Date().getTime() - startDate.getTime()) + " ms" );

        return v;
    }

}

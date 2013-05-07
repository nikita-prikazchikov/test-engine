package utils.mail;

import junit.framework.TestCase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestGmail extends TestCase{

    public void testSelectMessage () throws Exception{
        Gmail c = new Gmail( "agroupautotest", "agroup1234" );
        c.connect();
        c.openFolder( "inbox" );
        System.out.println( String.format( "Count {%d}", c.getMessageCount() ) );
        System.out.println( String.format( "New Count {%d}", c.getNewMessageCount() ) );

        Calendar calendar = Calendar.getInstance();
        String dayAndHour = new SimpleDateFormat( "dd HH" ).format( calendar.getTime() );
        String subject = "HRB";

        c.disconnect();
    }
}
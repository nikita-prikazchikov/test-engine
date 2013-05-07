package utils.mail;

import com.sun.mail.pop3.POP3SSLStore;

import javax.mail.*;
import javax.mail.internet.ContentType;
import javax.mail.internet.ParseException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class Gmail{

    static String indentStr = "                                               ";

    static int level = 0;

    private Folder _folder;

    private String _password;

    private Session _session = null;

    private Store _store = null;

    private String _username;

    public Gmail (){
    }

    public Gmail ( String username, String password ){
        this.setUserPass( username, password );
    }

    public static void dumpEnvelope ( Message m ) throws Exception{
        pr( " " );
        Address[] a;
        // FROM
        if ( ( a = m.getFrom() ) != null ){
            for ( int j = 0; j < a.length; j++ )
                pr( "FROM: " + a[ j ].toString() );
        }

        // TO
        if ( ( a = m.getRecipients( Message.RecipientType.TO ) ) != null ){
            for ( int j = 0; j < a.length; j++ ){
                pr( "TO: " + a[ j ].toString() );
            }
        }

        // SUBJECT
        pr( "SUBJECT: " + m.getSubject() );

        // DATE
        Date d = m.getSentDate();
        pr( "SendDate: " +
                    ( d != null ? d.toString() : "UNKNOWN" ) );


    }

    public static void dumpPart ( Part p ) throws Exception{
        if ( p instanceof Message ){
            dumpEnvelope( (Message) p );
        }

        System.out.println( p.getDescription() );

        String ct = p.getContentType();
        try {
            pr( "CONTENT-TYPE: " + ( new ContentType( ct ) ).toString() );
        }
        catch ( ParseException pex ){
            pr( "BAD CONTENT-TYPE: " + ct );
        }

        /*
         * Using isMimeType to determine the content type avoids
         * fetching the actual content data until we need it.
         */
        if ( p.isMimeType( "text/plain" ) ){
            pr( "This is plain text" );
            pr( "---------------------------" );
            System.out.println( (String) p.getContent() );
        }
        else {

            // just a separator
            pr( "---------------------------" );

        }
    }

    /**
     * Print a, possibly indented, string.
     */
    public static void pr ( String s ){

        System.out.print( indentStr.substring( 0, level * 2 ) );
        System.out.println( s );
    }

    private boolean isRequired ( Message message, String subject, Date dateAndTime, Date currentTime ) throws MessagingException{
        try {
            return ( message.getSubject().contains( subject ) && Math.abs( dateAndTime.getTime() - message.getSentDate().getTime() ) <= 60000 );
        }
        catch ( Exception e ){
            e.printStackTrace();
        }
        return false;
    }

    public void closeFolder () throws Exception{
        _folder.close( false );
    }

    public void connect () throws Exception{

        String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        Properties pop3Props = new Properties();

        pop3Props.setProperty( "mail.pop3.socketFactory.class", SSL_FACTORY );
        pop3Props.setProperty( "mail.pop3.socketFactory.fallback", "false" );
        pop3Props.setProperty( "mail.pop3.port", "995" );
        pop3Props.setProperty( "mail.pop3.socketFactory.port", "995" );

        URLName url = new URLName( "pop3", "pop.gmail.com", 995, "",
                                   _username, _password );

        _session = Session.getInstance( pop3Props, null );
        _store = new POP3SSLStore( _session, url );
        _store.connect();

    }

    public void disconnect () throws Exception{
        _store.close();
    }

    public String getBody ( Message message ) throws MessagingException, IOException{
        Multipart mp = (Multipart) message.getContent();
        return (String) mp.getBodyPart( 0 ).getContent();
    }

    public int getMessageCount () throws Exception{
        return _folder.getMessageCount();
    }

    public int getNewMessageCount () throws Exception{
        return _folder.getNewMessageCount();
    }

    public void openFolder ( String folderName ) throws Exception{

        // Open the Folder
        _folder = _store.getDefaultFolder();

        _folder = _folder.getFolder( folderName );

        if ( _folder == null ){
            throw new Exception( "Invalid _folder" );
        }

        // try to open read/write and if that fails try read-only
        try {
            _folder.open( Folder.READ_WRITE );

        }
        catch ( MessagingException ex ){
            _folder.open( Folder.READ_ONLY );
        }
    }

    public void printAllMessageEnvelopes () throws Exception{

        // Attributes & Flags for all messages ..
        Message[] msgs = _folder.getMessages();

        // Use a suitable FetchProfile
        FetchProfile fp = new FetchProfile();
        fp.add( FetchProfile.Item.ENVELOPE );
        _folder.fetch( msgs, fp );

        for ( int i = 0; i < msgs.length; i++ ){
            System.out.println( "--------------------------" );
            System.out.println( "MESSAGE #" + ( i + 1 ) + ":" );
            dumpEnvelope( msgs[ i ] );

        }

    }

    public void printAllMessages () throws Exception{

        // Attributes & Flags for all messages ..
        Message[] msgs = _folder.getMessages();

        // Use a suitable FetchProfile
        FetchProfile fp = new FetchProfile();
        fp.add( FetchProfile.Item.ENVELOPE );
        _folder.fetch( msgs, fp );

        for ( int i = 0; i < msgs.length; i++ ){
            System.out.println( "--------------------------" );
            System.out.println( "MESSAGE #" + ( i + 1 ) + ":" );
            dumpPart( msgs[ i ] );
        }


    }

    public void printMessage ( int messageNo ) throws Exception{
        System.out.println( "Getting message number: " + messageNo );

        Message m = null;

        try {
            m = _folder.getMessage( messageNo );
            dumpPart( m );
            Multipart mp = (Multipart) m.getContent();
            System.out.println( mp.getCount() );
            System.out.println( mp.getBodyPart( 0 ).getContent() );
        }
        catch ( IndexOutOfBoundsException iex ){
            System.out.println( "Message number out of range" );
        }
    }

    public void printMessage ( Message m ) throws Exception{
        dumpPart( m );
    }

    public Message selectMessageBySubjectAndDate ( String subject, Date dateAndTime ) throws MessagingException{
        int messageCount = _folder.getUnreadMessageCount();
        Message message;

        int to;

        if ( messageCount > 20 ){
            to = messageCount - 20;
        }
        else {
            to = 0;
        }

        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        for ( int i = messageCount; i > to; i-- ){
            message = _folder.getMessage( i );
            if ( isRequired( message, subject, dateAndTime, currentTime ) ){
                return message;
            }
        }
        return null;
    }

    public void setUserPass ( String username, String password ){
        this._username = username;
        this._password = password;
    }


}

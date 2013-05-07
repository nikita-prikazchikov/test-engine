package selenium.bots;

import com.googlecode.sardine.Sardine;
import com.googlecode.sardine.SardineFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import utils.TestEngineDataHolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebDAVBot{

    private final Sardine _sardine;

    private String _strPassword;

    private String _strURL;

    private String _strUser;

    public WebDAVBot (){
        String packageDir = TestEngineDataHolder.getData().getWorkspacePath();
        String path = String.format( "%s%s%s", packageDir, File.separator, "connector-context.xml" );
        BeanFactory beanFactory = new XmlBeanFactory( new FileSystemResource( path ) );

        this._strURL = (String) beanFactory.getBean( "webDavStorageUrl" );
        this._strUser = (String) beanFactory.getBean( "webDavStorageLogin" );
        this._strPassword = (String) beanFactory.getBean( "webDavStoragePassword" );

        this._sardine = SardineFactory.begin( this._strUser, this._strPassword );
    }

    /**
     * If directory does not exist it creates new one.
     *
     * @param strURLProstfix Relative Path of a directory
     * @throws IOException
     */
    private void _prepareFolder ( String strURLProstfix ) throws IOException{
        Pattern pattern = Pattern.compile( "(\\w+/)" );
        Matcher matcher = pattern.matcher( strURLProstfix );

        StringBuilder strPath = new StringBuilder();
        while ( matcher.find() ){
            strPath.append( matcher.group( 0 ) );
            String strUrlCur = this._strURL + strPath.toString();
            if ( !this._sardine.exists( strUrlCur ) ){
                this._sardine.createDirectory( strUrlCur );
            }
        }
    }

    public String send ( byte[] data, String strURLPostfix ) throws IOException{
        String url = this._strURL + strURLPostfix;
        this._prepareFolder( strURLPostfix );
        this._sardine.put( url, data );
        return url;
    }

    public String send ( File file, String strURLPostfix ) throws IOException{
        String url = this._strURL + strURLPostfix;
        this._prepareFolder( strURLPostfix );
        this._sardine.put( url, new FileInputStream( file ) );
        return url;
    }
}

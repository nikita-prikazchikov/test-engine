package utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;

public class Utils{

    public static String getCurrentTimestamp (){
        return new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss", Locale.US ).format( new Date() );
    }

    public static String getIpAdress (){
        try {
            Enumeration<NetworkInterface> interfaceEnumeration = interfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            while ( interfaceEnumeration.hasMoreElements() ){
                NetworkInterface iAnInterface = interfaceEnumeration.nextElement();
                if ( !iAnInterface.isLoopback() && iAnInterface.isUp() && !iAnInterface.isPointToPoint() ){
                    Enumeration<InetAddress> addressEnumeration = iAnInterface.getInetAddresses();
                    while ( addressEnumeration.hasMoreElements() ){
                        InetAddress address = addressEnumeration.nextElement();
                        if ( !address.isLinkLocalAddress() ){
                            return address.getHostAddress();
                        }
                    }
                }
            }
        }
        catch ( SocketException e ){
            return null;
        }
        return null;
    }

    public static void main ( String[] args ){
        System.out.println( getIpAdress() );
    }

    public static Date parseTimestamp ( String timestamp ){
        Date date = new Date();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss", Locale.US );
            date = dateFormat.parse( timestamp );
        }
        catch ( ParseException e ){
            e.printStackTrace();
        }
        return date;
    }
}

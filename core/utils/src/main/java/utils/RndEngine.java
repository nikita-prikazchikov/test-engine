package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by
 * User: nprikazchikov
 * Date: 07.02.12
 * Time: 13:25
 */
public class RndEngine{

    private int maxLength;

    private int minLength;

    private int offset;

    private String pattern;

    private StringTypes type;

    public RndEngine ( String pattern ){
        this.setPattern( pattern );
        this.setType( StringTypes.free );
    }

    public RndEngine ( StringTypes type, int minLen, int maxLen ){
        this.setType( type );
        this.setMinLength( minLen );
        this.setMaxLength( maxLen );
        this.generatePattern();
    }

    public RndEngine ( String type, int minLen, int maxLen ){
        for ( int i = 0; i < StringTypes.values().length; i++ ){
            if ( StringTypes.valueOf( type ) != null ){
                this.setType( StringTypes.valueOf( type ) );
                break;
            }
        }
        if ( null == this.getType() ){
            this.setType( StringTypes.free );
        }
        this.setMinLength( minLen );
        this.setMaxLength( maxLen );
        this.generatePattern();
    }

    public RndEngine ( String type, int offset, String pattern ){
        for ( int i = 0; i < StringTypes.values().length; i++ ){
            if ( StringTypes.valueOf( type ) != null ){
                this.setType( StringTypes.valueOf( type ) );
                break;
            }
        }
        if ( null == this.getType() ){
            this.setType( StringTypes.free );
        }
        this.setOffset( offset );
        this.setPattern( pattern );
        this.generatePattern();
    }

    private String _generateDate (){
        Date date = new Date();
        date.setTime( date.getTime() + 24 * 3600 * 1000 * this.getOffset() );
        SimpleDateFormat sdf = new SimpleDateFormat( this.getPattern(), Locale.US );

        return sdf.format( date );
    }

    private String _generateDatePattern (){
        StringBuilder pattern = new StringBuilder();

        return pattern.toString();
    }

    private String _generateString (){
        StringBuilder res = new StringBuilder();
        StringBuilder AlphaSet = new StringBuilder( "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz" );
        StringBuilder NumSet = new StringBuilder( "0123456789" );
        StringBuilder SpecialCharSet = new StringBuilder( "!#$%&')(*+,-./:;<=>?@[\\]^_`{|}~" );
        char curChar;
        char ch;

        for ( int i = 0; i < this.getPattern().length(); i++ ){

            curChar = pattern.charAt( i );
            switch ( curChar ){
                case 'c':
                    ch = AlphaSet.charAt( (int) Math.floor( Math.random() * ( AlphaSet.length() ) ) );
                    break;
                case 'd':
                    ch = NumSet.charAt( (int) Math.floor( Math.random() * ( NumSet.length() ) ) );
                    break;
                case 's':
                    ch = SpecialCharSet.charAt( (int) Math.floor( Math.random() * ( SpecialCharSet.length() ) ) );
                    break;
                default:
                    ch = curChar;
            }
            res.append( ch );
        }
        return res.toString();
    }

    protected String _generateAlphaNumPattern ( int length ){
        StringBuilder pattern = new StringBuilder( length );
        for ( int i = 0; i < length; i++ ){
            int r = (int) Math.floor( Math.random() * 2 );
            pattern.append( r == 1 ? 'd' : 'c' );
        }
        return pattern.toString();
    }

    protected String _generateAlphaPattern ( int length ){
        StringBuilder pattern = new StringBuilder( length );
        for ( int i = 0; i < length; i++ ){
            pattern.append( 'c' );
        }
        return pattern.toString();
    }

    protected String _generateAlphaSpacePattern ( int length ){
        StringBuilder pattern = new StringBuilder( length );
        boolean space = true;
        char ch;
        int r;
        for ( int i = 0; i < length; i++ ){
            if ( space || i == length - 1 ){
                r = 0;
            }
            else {
                r = (int) Math.floor( Math.random() * 100 );
            }
            ch = r < 80 ? 'c' : ' ';

            space = ch == ' ';
            pattern.append( ch );
        }
        return pattern.toString();
    }

    protected String _generateEmailPattern ( int length ){
        if ( length < 6 ){
            length = 6;
        }
        StringBuilder pattern = new StringBuilder( length );
        int r;
        boolean ret = true;
        char ch;
        for ( int i = 0; i < length; i++ ){
            int remainedCh = length - i;
            if ( ( i == 0 ) || ( remainedCh > 9 ) ){
                r = (int) Math.floor( Math.random() * 2 );
                ch = ( r == 1 ) ? 'd' : 'c';
            }
            else {
                if ( ret ){
                    ch = '@';
                    ret = false;
                }
                else {
                    if ( remainedCh == 3 ){
                        ch = '.';
                    }
                    else {
                        ch = 'c';
                    }
                }
            }
            pattern.append( ch );
        }
        return pattern.toString();
    }

    protected String _generateFreePattern ( int length ){
        StringBuilder pattern = new StringBuilder( length );
        for ( int i = 0; i < length; i++ ){
            int r = (int) Math.floor( Math.random() * 3 );
            pattern.append( r == 1 ? 'c' : r == 2 ? "d" : 's' );
        }
        return pattern.toString();
    }

    protected String _generateNumPattern ( int length ){
        StringBuilder pattern = new StringBuilder( length );
        for ( int i = 0; i < length; i++ ){
            pattern.append( 'd' );
        }
        return pattern.toString();
    }

    protected void generatePattern (){
        int length = (int) ( this.getMinLength() + Math.round( Math.random() * ( this.getMaxLength() - this.getMinLength() ) ) );
        switch ( this.getType() ){
            case alpha:
            case alpha_lower:
            case alpha_upper:
                this.setPattern( this._generateAlphaPattern( length ) );
                break;
            case num:
                this.setPattern( this._generateNumPattern( length ) );
                break;
            case alphanum:
                this.setPattern( this._generateAlphaNumPattern( length ) );
                break;
            case alphaspace:
                this.setPattern( this._generateAlphaSpacePattern( length ) );
                break;
            case email:
                this.setPattern( this._generateEmailPattern( length ) );
                break;
            case free:
                this.setPattern( this._generateFreePattern( length ) );
                break;
            case date:
                break;
        }
    }

    public String generate (){
        switch ( this.getType() ){
            case alpha:
            case alphanum:
            case alphaspace:
            case num:
            case free:
            case email:
                return this._generateString();
            case alpha_lower:
                return this._generateString().toLowerCase();
            case alpha_upper:
                return this._generateString().toUpperCase();
            case date:
                return this._generateDate();
            default:
                return "";

        }
    }

    //================================================================================================

    public int getMaxLength (){
        return maxLength;
    }

    public int getMinLength (){
        return minLength;
    }

    public int getOffset (){
        return offset;
    }

    public String getPattern (){
        return pattern;
    }

    public StringTypes getType (){
        return type;
    }

    public void setMaxLength ( int maxLength ){
        this.maxLength = maxLength;
    }

    public void setMinLength ( int minLength ){
        this.minLength = minLength;
    }

    public void setOffset ( int offset ){
        this.offset = offset;
    }

    public void setPattern ( String pattern ){
        this.pattern = pattern;
    }

    public void setType ( StringTypes type ){
        this.type = type;
    }
}


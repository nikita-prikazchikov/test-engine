package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: kcherchenko
 * Date: 10.12.12
 * Time: 16:44
 * To change this template use File | Settings | File Templates.
 */
public class StringParsing{

    public static int getNumber(String regExp, int num, String value){
        int result = -1;
        try{
            Pattern pattern = Pattern.compile(regExp);
            Matcher m = pattern.matcher(value);
            m.find();
            result = Integer.valueOf(m.group(num));
        } catch (Exception ex){ ex.printStackTrace(); }
        return result;
    }

    public static int getNumber(String regExp, String value){
        return getNumber(regExp, 1, value);
    }

    public static int  getNumberInSquareBrackets(String value) {
        return getNumber("\\[(\\d+)\\]", value);
    }
}

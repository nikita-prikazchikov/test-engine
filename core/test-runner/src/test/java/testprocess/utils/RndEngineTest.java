package testprocess.utils;

import org.junit.Test;
import utils.RndEngine;
import utils.StringTypes;

/**
 * Created by IntelliJ IDEA.
 * User: nprikazchikov
 * Date: 07.02.12
 * Time: 13:56
 * To change this template use File | Settings | File Templates.
 */
public class RndEngineTest {
    @Test
    public void AlphaStringTest(){

        System.out.println( new RndEngine( StringTypes.alpha, 5, 30 ).generate());
        System.out.println( new RndEngine( StringTypes.alpha, 5, 30 ).generate());
        System.out.println( new RndEngine( StringTypes.alpha, 5, 30 ).generate());
        System.out.println( new RndEngine( StringTypes.alpha, 5, 30 ).generate());
        System.out.println( new RndEngine( StringTypes.alpha, 5, 30 ).generate());

        System.out.println( "==================================================" );

        System.out.println( new RndEngine( "alpha", 5, 30 ).generate());
        System.out.println( new RndEngine( "alpha", 5, 30 ).generate());
        System.out.println( new RndEngine( "alpha", 5, 30 ).generate());
        System.out.println( new RndEngine( "alpha", 5, 30 ).generate());
        System.out.println( new RndEngine( "alpha", 5, 30 ).generate());

        System.out.println( "==================================================" );

        System.out.println( new RndEngine( StringTypes.alphaspace, 50, 800 ).generate());
        System.out.println( new RndEngine( StringTypes.alphaspace, 50, 800 ).generate());
        System.out.println( new RndEngine( StringTypes.alphaspace, 50, 80 ).generate());
        System.out.println( new RndEngine( StringTypes.alphaspace, 50, 80 ).generate());
        System.out.println( new RndEngine( StringTypes.alphaspace, 50, 80 ).generate());

        System.out.println( "==================================================" );

    }

    @Test
    public void NumStringTest(){
        System.out.println( new RndEngine( "num", 5, 30 ).generate());
        System.out.println( new RndEngine( "num", 5, 30 ).generate());
        System.out.println( new RndEngine( "num", 5, 30 ).generate());
        System.out.println( new RndEngine( "num", 5, 30 ).generate());
        System.out.println( new RndEngine( "num", 5, 30 ).generate());

        System.out.println( "==================================================" );

        System.out.println( new RndEngine( StringTypes.num, 5, 30 ).generate());
        System.out.println( new RndEngine( StringTypes.num, 5, 30 ).generate());
        System.out.println( new RndEngine( StringTypes.num, 5, 30 ).generate());
        System.out.println( new RndEngine( StringTypes.num, 5, 30 ).generate());
        System.out.println( new RndEngine( StringTypes.num, 5, 30 ).generate());

        System.out.println( "==================================================" );
    }

    @Test
    public void AlphaNumStringTest(){
        System.out.println( new RndEngine( "alphanum", 5, 30 ).generate());
        System.out.println( new RndEngine( "alphanum", 5, 30 ).generate());
        System.out.println( new RndEngine( "alphanum", 5, 30 ).generate());
        System.out.println( new RndEngine( "alphanum", 5, 30 ).generate());
        System.out.println( new RndEngine( "alphanum", 5, 30 ).generate());

        System.out.println( "==================================================" );

        System.out.println( new RndEngine( StringTypes.alphanum, 5, 30 ).generate());
        System.out.println( new RndEngine( StringTypes.alphanum, 5, 30 ).generate());
        System.out.println( new RndEngine( StringTypes.alphanum, 5, 30 ).generate());
        System.out.println( new RndEngine( StringTypes.alphanum, 5, 30 ).generate());
        System.out.println( new RndEngine( StringTypes.alphanum, 5, 30 ).generate());

        System.out.println( "==================================================" );
    }

    @Test
    public void FreeStringTest(){
        System.out.println( new RndEngine( "free", 5, 30 ).generate());
        System.out.println( new RndEngine( "free", 5, 30 ).generate());
        System.out.println( new RndEngine( "free", 5, 30 ).generate());
        System.out.println( new RndEngine( "free", 5, 30 ).generate());
        System.out.println( new RndEngine( "free", 5, 30 ).generate());

        System.out.println( "==================================================" );

        System.out.println( new RndEngine( StringTypes.free, 5, 30 ).generate());
        System.out.println( new RndEngine( StringTypes.free, 5, 30 ).generate());
        System.out.println( new RndEngine( StringTypes.free, 5, 30 ).generate());
        System.out.println( new RndEngine( StringTypes.free, 5, 30 ).generate());
        System.out.println( new RndEngine( StringTypes.free, 5, 30 ).generate());

        System.out.println( "==================================================" );
    }

    @Test
    public void EmailStringTest(){
        System.out.println( new RndEngine( "email", 7, 30 ).generate());
        System.out.println( new RndEngine( "email", 7, 30 ).generate());
        System.out.println( new RndEngine( "email", 7, 30 ).generate());
        System.out.println( new RndEngine( "email", 7, 30 ).generate());
        System.out.println( new RndEngine( "email", 7, 30 ).generate());

        System.out.println( "==================================================" );

        System.out.println( new RndEngine( StringTypes.email, 1, 3 ).generate());
        System.out.println( new RndEngine( StringTypes.email, 1, 50 ).generate());
        System.out.println( new RndEngine( StringTypes.email, 1, 50 ).generate());
        System.out.println( new RndEngine( StringTypes.email, 1, 30 ).generate());
        System.out.println( new RndEngine( StringTypes.email, 1, 30 ).generate());

        System.out.println( "==================================================" );
    }

    @Test
    public void CustomStringTest(){
        System.out.println( new RndEngine( "cccccccddddddcccccc23424atcccc" ).generate());
        System.out.println( "==================================================" );
    }

    @Test
    public void DateStringTest(){
        System.out.println( new RndEngine( StringTypes.date.toString(), -3, "dd/MM/yyyy" ).generate());
        System.out.println( "==================================================" );
    }

}

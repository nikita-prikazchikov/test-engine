package testprocess.runner;

import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: nprikazchikov
 * Date: 09.02.12
 * Time: 11:36
 * To change this template use File | Settings | File Templates.
 */
public class ItemIteratorTest {
    @Test
    public void testNext() throws Exception {

        ItemIterator o = new ItemIterator( "\"$rnd.date.0.MMMM yyyy;\"" );

        System.out.println(o.getNext());

    }
}

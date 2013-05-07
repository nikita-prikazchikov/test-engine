package testprocess.runner;

import org.junit.Assert;
import org.junit.Test;
import testprocess.exceptions.TestStepScriptException;

/**
 * Created by IntelliJ IDEA.
 * User: akugurushev
 * Date: 24.11.11
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 */
public class TestStepScriptTest{

    @Test
    public void testScriptCreated (){
        String script = "Li.o.d3d.d.d34.d.d.d.dick.rerfefpendffrer(\"dddddd\", 222.2)";
        try {
            TestStepScript.buildScript( script );
            Assert.assertTrue( true );
        }
        catch ( TestStepScriptException e ){
            Assert.assertTrue( false );
        }
    }

    @Test
    public void testScriptCreated1 (){

        String script = "Li.o.d3d.d.d34...d.d.d.dick.rerfefpendffrer(\"dddddd\", 222.2)";
        try {
            TestStepScript.buildScript( script );
            Assert.assertTrue( false );
        }
        catch ( TestStepScriptException e ){
            Assert.assertTrue( true );
        }
    }

    @Test
    public void testScriptCreated2 (){
        String script = ".Li.o.d3d.d.d34.d.d.d.dick.rerfefpendffrer(\"dddddd\", 222.2)";
        try {
            TestStepScript.buildScript( script );
            Assert.assertTrue( false );
        }
        catch ( TestStepScriptException e ){
            Assert.assertTrue( true );
        }
    }

    @Test
    public void testScriptCreated3 (){
        String script = "rerfefpendffrer(\"dddddd\", 222.2)";
        try {
            TestStepScript.buildScript( script );
            Assert.assertTrue( false );
        }
        catch ( TestStepScriptException e ){
            Assert.assertTrue( true );
        }
    }

    @Test
    public void testScriptCreated4 (){
        String script = "hrb.functionality.absences.personal.EditView.populate(\"$rnd.num.7.10;\", \"20/$rnd.date.0.MM/yyyy;\", \"Annual Leave\", \"20/$rnd.date.0.MM/yyyy;\", \"22/$rnd.date.0.MM/yyyy;\", \"Leave comment $rnd.alphaspace.7.10;\")";
        try {
            TestStepScript.buildScript( script );
            Assert.assertTrue( true );
        }
        catch ( TestStepScriptException e ){
            Assert.assertTrue( false );
        }
    }

    @Test
    public void testScriptCreated5 (){
        String script = "(\"dddddd\", 222.2)";
        try {
            TestStepScript.buildScript( script );
            Assert.assertTrue( false );
        }
        catch ( TestStepScriptException e ){
            Assert.assertTrue( true );
        }
    }

    @Test
    public void testScriptWitAction (){
        String script = "hrb.custom.Custom.getTextElement( \"work..TextBox..purpose\", \"Purpose\" ).setValue (\"$rnd.alpha.15.30;\").setValue( \"test\" ).verifyValue( \"$data.Purpose;\" ).populateTransportation(1, \"$rnd.alpha.10.15;\", \"20/12/2010\", \"Receipt\", \"$rnd.alpha.5.10;\", \"15.00\",\"EUR\")";
        try {
            System.out.print( TestStepScript.buildScript( script ) );
            Assert.assertTrue( true );
        }
        catch ( TestStepScriptException e ){
            Assert.assertTrue( false );
        }
    }
}

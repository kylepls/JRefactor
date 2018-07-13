package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.JImport;

public class TestJImport {

    @Test
    public void test() {
        String test = "import test;";
        JImport anImport = Parser.parse(test, JImport.class);
        Verify.that(anImport.getArea()).isNotNull();
        Verify.that(anImport.isOnDemand()).isFalse();
        Verify.that(anImport.isStaticImport()).isFalse();
    }
    
    @Test
    public void testStatic() {
        String test = "import static test;";
        JImport anImport = Parser.parse(test, JImport.class);
        Verify.that(anImport.isStaticImport()).isTrue();        
    }
    
    @Test
    public void testDemand() {
        String test = "import static test.*;";
        JImport anImport = Parser.parse(test, JImport.class);
        Verify.that(anImport.isOnDemand()).isTrue();                
    }
    
    public void testPackage() {
        String test = "import static test.test;";
        JImport anImport = Parser.parse(test, JImport.class);
        Verify.that(anImport.getArea()).isNotNull();                        
    }
}
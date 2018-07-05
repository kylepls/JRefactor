package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JMethodHeader;

public class TestJMethodHeader {

    @Test
    public void test() {
        String test = "void test()";
        JMethodHeader header = Parser.parse(test, JMethodHeader.class);
        Verify.that(header.getParameters()).isEmpty();
        Verify.that(header.getReturns()).isNotNull();
        Verify.that(header.getThrowsTypes()).isEmpty();
        Verify.that(header.getTypeParameters()).isEmpty();
    }
    
    @Test
    public void testParameters() {
        String test = "void test(int i, int j, int k)";
        JMethodHeader header = Parser.parse(test, JMethodHeader.class);
        Verify.that(header.getParameters()).sizeIs(3);
    }
    
    @Test
    public void testThrows() {
        String test = "void test() throws A, B, C";
        JMethodHeader header = Parser.parse(test, JMethodHeader.class);
        Verify.that(header.getThrowsTypes()).sizeIs(3);        
    }
    
    @Test
    public void testType() {
        String test = "<A, B, C> void test()";
        JMethodHeader header = Parser.parse(test, JMethodHeader.class);
        Verify.that(header.getTypeParameters()).sizeIs(3);                
    }
}
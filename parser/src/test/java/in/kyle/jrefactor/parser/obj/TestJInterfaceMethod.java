package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.unit.bodymember.typemember.interfacemember.JInterfaceMethod;

public class TestJInterfaceMethod {
    
    @Test
    public void test() {
        String test = "void test();";
        JInterfaceMethod method = Parser.parse(test, JInterfaceMethod.class);
        Verify.that(method.getHeader()).isNotNull();
        Verify.that(method.getBody()).isNotPresent();
    }
    
    @Test
    public void testDefault() {
        String test = "default void test() {}";
        JInterfaceMethod method = Parser.parse(test, JInterfaceMethod.class);
        Verify.that(method.getHeader()).isNotNull();
        Verify.that(method.getBody()).isPresent();        
    }
}
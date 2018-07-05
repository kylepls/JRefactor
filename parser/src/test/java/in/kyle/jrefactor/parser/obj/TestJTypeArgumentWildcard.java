package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.reference.typeargument.JTypeArgumentWildcard;

public class TestJTypeArgumentWildcard {
    
    @Test
    public void test() {
        String test = "?";
        JTypeArgumentWildcard typeArgument = Parser.parse(test, JTypeArgumentWildcard.class);
        Verify.that(typeArgument.getType()).isNotPresent();
        Verify.that(typeArgument.getReference()).isNotPresent();
    }
    
    @Test
    public void testExtends() {
        String test = "? extends String";
        JTypeArgumentWildcard typeArgument = Parser.parse(test, JTypeArgumentWildcard.class);
        Verify.that(typeArgument.getType()).isPresent();
        Verify.that(typeArgument.getReference()).isPresent();
    }
    
    @Test
    public void testSuper() {
        String test = "? super String";
        JTypeArgumentWildcard typeArgument = Parser.parse(test, JTypeArgumentWildcard.class);
        Verify.that(typeArgument.getType()).isPresent();
        Verify.that(typeArgument.getReference()).isPresent();
    }
}
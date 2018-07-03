package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.JConstructorDeclaration;

public class TestJConstructorDeclaration {
    
    @Test
    public void test() {
        String test = "Test() {}";
        JConstructorDeclaration declaration = Parser.parse(test, JConstructorDeclaration.class);
        Verify.that(declaration.getIdentifier()).isNotNull();
        Verify.that(declaration.getParameters()).isEmpty();
        Verify.that(declaration.getThrowsTypes()).isEmpty();
        Verify.that(declaration.getBody()).isNotNull();
    }
    
    @Test
    public void testParameter() {
        String test = "Test(int i) {}";
        JConstructorDeclaration declaration = Parser.parse(test, JConstructorDeclaration.class);
        Verify.that(declaration.getIdentifier()).isNotNull();
        Verify.that(declaration.getParameters()).sizeIs(1);
        Verify.that(declaration.getThrowsTypes()).isEmpty();
        Verify.that(declaration.getBody()).isNotNull();
    }
    
    @Test
    public void testThrows() {
        String test = "Test() throws RuntimeException {}";
        JConstructorDeclaration declaration = Parser.parse(test, JConstructorDeclaration.class);
        Verify.that(declaration.getIdentifier()).isNotNull();
        Verify.that(declaration.getParameters()).isEmpty();
        Verify.that(declaration.getThrowsTypes()).sizeIs(1);
        Verify.that(declaration.getBody()).isNotNull();        
    }
}
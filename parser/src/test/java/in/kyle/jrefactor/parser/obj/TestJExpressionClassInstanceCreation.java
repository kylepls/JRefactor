package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.JExpressionClassInstanceCreation;

public class TestJExpressionClassInstanceCreation {
    
    @Test
    public void test() {
        String testString = "new Clazz()";
        JExpressionClassInstanceCreation expression =
                Parser.parse(testString, JExpressionClassInstanceCreation.class);
        
        Verify.that(expression.getType()).isNotNull();
        Verify.that(expression.getTypeArguments()).isNotNull();
        Verify.that(expression.getArguments()).isEmpty();
        Verify.that(expression.getBody()).isNotPresent();
    }
    
    @Test
    public void testParameter() {
        String testString = "new Clazz(1, 2, 3)";
        JExpressionClassInstanceCreation expression =
                Parser.parse(testString, JExpressionClassInstanceCreation.class);
        Verify.that(expression.getArguments()).sizeIs(3);
    }
    
    @Test
    public void testTypeParameter() {
        String testString = "new Clazz<T, V, R>()";
        JExpressionClassInstanceCreation expression =
                Parser.parse(testString, JExpressionClassInstanceCreation.class);
        Verify.that(expression.getTypeArguments()).sizeIs(3);
    }
    
    @Test
    public void testBody() {
        String testString = "new Clazz() {}";
        JExpressionClassInstanceCreation expression =
                Parser.parse(testString, JExpressionClassInstanceCreation.class);
        Verify.that(expression.getBody()).isPresent();        
    }
}
package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.expressionunary.JUnaryCast;

public class TestJUnaryCast {
    
    @Test
    public void test() {
        String test = "(int) i";
        JUnaryCast cast = Parser.parse(test, JUnaryCast.class);
        Verify.that(cast.getBounds()).sizeIs(1);
        Verify.that(cast.getExpression()).isNotNull();
    }
    
    @Test
    public void testBounds() {
        String test = "(Read & Write) i";
        JUnaryCast cast = Parser.parse(test, JUnaryCast.class);
        Verify.that(cast.getBounds()).sizeIs(2);
        Verify.that(cast.getExpression()).isNotNull();        
    }
}

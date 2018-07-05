package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralBoolean;

public class TestJLiteralBoolean {
    
    @Test
    public void testTrue() {
        String test = "true";
        JLiteralBoolean literal = Parser.parse(test, JLiteralBoolean.class);
        Verify.that(literal.getValue()).isEqual(true);
    }
    
    @Test
    public void testFalse() {
        String test = "false";
        JLiteralBoolean literal = Parser.parse(test, JLiteralBoolean.class);
        Verify.that(literal.getValue()).isEqual(false);        
    }
}

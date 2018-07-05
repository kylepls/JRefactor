package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralString;

public class TestJLiteralString {
    
    @Test
    public void test() {
        String test = "\"test\"";
        JLiteralString literal = Parser.parse(test, JLiteralString.class);
        Verify.that(literal.getValue()).isEqual("test");
    }
}

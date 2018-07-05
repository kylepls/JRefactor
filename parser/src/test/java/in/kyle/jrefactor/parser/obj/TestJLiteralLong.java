package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.JLiteralLong;

public class TestJLiteralLong {
    
    @Test
    public void test() {
        String test = "1L";
        JLiteralLong literal = Parser.parse(test, JLiteralLong.class);
        Verify.that(literal.getValue()).isEqual(1L);
    }
}

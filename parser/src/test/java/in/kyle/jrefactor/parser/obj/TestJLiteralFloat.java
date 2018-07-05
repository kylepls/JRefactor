package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.literalfloating
        .JLiteralFloat;

public class TestJLiteralFloat {
    
    @Test
    public void test() {
        String test = "1F";
        JLiteralFloat literal = Parser.parse(test, JLiteralFloat.class);
        Verify.that(literal.getValue()).isEqual(1F);
    }
}

package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.literalfloating
        .JLiteralDouble;

public class TestJLiteralDouble {
    
    @Test
    public void testD() {
        String test = "1D";
        JLiteralDouble literal = Parser.parse(test,  JLiteralDouble.class);
        Verify.that(literal.getValue()).isEqual(1);                
    }
}

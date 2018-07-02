package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.expression.JExpressionParenthesis;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.JLiteralInteger;
import in.kyle.jrefactor.writer.Write;

public class TestJExpressionParenthesis {
    
    @Test
    public void test() {
        JExpressionParenthesis parenthesis = new JExpressionParenthesis(new JLiteralInteger(1));
        Verify.that(Write.object(parenthesis)).isEqual("(1)");
    }
}

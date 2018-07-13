package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.expression.JExpressionLeftRight;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.JLiteralInteger;
import in.kyle.jrefactor.writer.Write;

public class TestJExpressionLeftRight {
    @Test
    public void testOperator() {
        Verify.that(Write.object(JExpressionLeftRight.JLeftRightOperatorConditional.CONDITIONAL_OR))
                .isEqual("||");
    }
    
    @Test
    public void test() {
        JExpressionLeftRight expression = new JExpressionLeftRight();
        expression.setOperator(JExpressionLeftRight.JLeftRightOperatorMath.ADD);
        expression.setLeft(new JLiteralInteger(1));
        expression.setRight(new JLiteralInteger(1));
        Verify.that(Write.object(expression)).isEqual("1 + 1");
    }
}

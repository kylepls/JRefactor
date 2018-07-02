package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.expression.JExpressionName;
import in.kyle.jrefactor.tree.obj.expression.JExpressionUnary;
import in.kyle.jrefactor.writer.Write;

import static in.kyle.jrefactor.tree.obj.expression.JExpressionUnary.JOperator.POST_DECREMENT;
import static in.kyle.jrefactor.tree.obj.expression.JExpressionUnary.JOperator.POST_INCREMENT;
import static in.kyle.jrefactor.tree.obj.expression.JExpressionUnary.JOperator.PRE_DECREMENT;
import static in.kyle.jrefactor.tree.obj.expression.JExpressionUnary.JOperator.PRE_INCREMENT;

public class TestJExpressionUnary {
    
    @Test
    public void test() {
        JExpressionName i = new JExpressionName(new JIdentifier("i"));
        JExpressionUnary preDec = new JExpressionUnary(PRE_DECREMENT, i);
        Verify.that(Write.object(preDec)).isEqual("--i");
        JExpressionUnary postDec = new JExpressionUnary(POST_DECREMENT, i);
        Verify.that(Write.object(postDec)).isEqual("i--");
        JExpressionUnary preInc = new JExpressionUnary(PRE_INCREMENT, i);
        Verify.that(Write.object(preInc)).isEqual("++i");
        JExpressionUnary postInc = new JExpressionUnary(POST_INCREMENT, i);
        Verify.that(Write.object(postInc)).isEqual("i++");
    }
}

package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.expression.JExpressionName;
import in.kyle.jrefactor.tree.obj.expression.JExpressionUnary;
import in.kyle.jrefactor.tree.obj.expression.expressionunary.JUnaryPrePost;
import in.kyle.jrefactor.writer.Write;

import static in.kyle.jrefactor.tree.obj.expression.expressionunary.JUnaryPrePost.JUnaryOperator
        .POST_DECREMENT;
import static in.kyle.jrefactor.tree.obj.expression.expressionunary.JUnaryPrePost.JUnaryOperator
        .POST_INCREMENT;
import static in.kyle.jrefactor.tree.obj.expression.expressionunary.JUnaryPrePost.JUnaryOperator
        .PRE_DECREMENT;
import static in.kyle.jrefactor.tree.obj.expression.expressionunary.JUnaryPrePost.JUnaryOperator
        .PRE_INCREMENT;

public class TestJUnaryPrePost {
    
    @Test
    public void test() {
        JExpressionName i = new JExpressionName(new JIdentifier("i"));
        JExpressionUnary preDec = new JUnaryPrePost(i, PRE_DECREMENT);
        Verify.that(Write.object(preDec)).isEqual("--i");
        JExpressionUnary postDec = new JUnaryPrePost(i, POST_DECREMENT);
        Verify.that(Write.object(postDec)).isEqual("i--");
        JExpressionUnary preInc = new JUnaryPrePost(i, PRE_INCREMENT);
        Verify.that(Write.object(preInc)).isEqual("++i");
        JExpressionUnary postInc = new JUnaryPrePost(i, POST_INCREMENT);
        Verify.that(Write.object(postInc)).isEqual("i++");
    }
}

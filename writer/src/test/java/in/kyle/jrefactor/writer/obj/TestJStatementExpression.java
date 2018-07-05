package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.expression.JExpressionName;
import in.kyle.jrefactor.tree.obj.expression.JExpressionUnary;
import in.kyle.jrefactor.tree.obj.expression.expressionunary.JUnaryPrePost;
import in.kyle.jrefactor.tree.obj.statement.JStatementExpression;
import in.kyle.jrefactor.writer.Write;

import static in.kyle.jrefactor.tree.obj.expression.expressionunary.JUnaryPrePost.JUnaryOperator
        .POST_INCREMENT;

public class TestJStatementExpression {
    
    @Test
    public void test() {
        JStatementExpression expression = new JStatementExpression();
        JExpressionName i = new JExpressionName(new JIdentifier("i"));
        JExpressionUnary unary = new JUnaryPrePost(i, POST_INCREMENT);
        expression.setExpression(unary);
        Verify.that(Write.object(expression)).isEqual("i++;");
    }
}

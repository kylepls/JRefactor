package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.expression.JExpressionName;
import in.kyle.jrefactor.tree.obj.expression.JExpressionUnary;
import in.kyle.jrefactor.tree.obj.statement.JStatementExpression;
import in.kyle.jrefactor.writer.Write;

import static in.kyle.jrefactor.tree.obj.expression.JExpressionUnary.JOperator.POST_INCREMENT;

public class TestJStatementExpression {
    
    @Test
    public void test() {
        JStatementExpression expression = new JStatementExpression();
        JExpressionName i = new JExpressionName(new JIdentifier("i"));
        JExpressionUnary unary = new JExpressionUnary(POST_INCREMENT, i);
        expression.setExpression(unary);
        Verify.that(Write.object(expression)).isEqual("i++;");
    }
}

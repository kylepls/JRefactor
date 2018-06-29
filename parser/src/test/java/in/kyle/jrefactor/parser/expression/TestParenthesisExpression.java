package in.kyle.jrefactor.parser.expression;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.JExpressionLiteral;
import in.kyle.jrefactor.tree.obj.expression.JExpressionParenthesis;

public class TestParenthesisExpression {
    
    @Test
    public void testParenthesis() {
        String javaString = "(1)";
        JExpressionParenthesis expression = Parser.parse(javaString, JExpressionParenthesis.class);
        Verify.that(expression.getExpression()).isInstanceOf(JExpressionLiteral.class);
    }
}

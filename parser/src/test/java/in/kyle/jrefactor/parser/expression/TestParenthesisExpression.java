package in.kyle.jrefactor.parser.expression;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.expression.JParenthesisExpression;
import in.kyle.jrefactor.tree.expression.literal.JLiteral;

public class TestParenthesisExpression {
    
    @Test
    public void testParenthesis() {
        String javaString = "(1)";
        JParenthesisExpression expression = Parser.parse(javaString, JParenthesisExpression.class);
        Verify.that(expression.getValue()).isInstanceOf(JLiteral.class);
    }
}

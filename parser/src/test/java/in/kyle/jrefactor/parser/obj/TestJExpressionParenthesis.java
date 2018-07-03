package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.JExpressionParenthesis;

public class TestJExpressionParenthesis {
    
    @Test
    public void test() {
        String javaString = "(1)";
        JExpressionParenthesis expression = Parser.parse(javaString, JExpressionParenthesis.class);
        Verify.that(expression.getExpression()).isNotNull();
    }
}
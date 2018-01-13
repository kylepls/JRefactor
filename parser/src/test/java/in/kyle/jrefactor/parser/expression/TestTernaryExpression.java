package in.kyle.jrefactor.parser.expression;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.expression.JTernaryExpression;
import in.kyle.jrefactor.tree.expression.literal.JBooleanLiteral;

public class TestTernaryExpression {
    
    @Test
    public void testExpression() {
        String javaString = "true ? true : false";
        JTernaryExpression expression = Parser.parse(javaString, JTernaryExpression.class);
        Verify.that(expression.getCondition()).isInstanceOf(JBooleanLiteral.class);
        Verify.that(expression.getLeft()).isInstanceOf(JBooleanLiteral.class);
        Verify.that(expression.getRight()).isInstanceOf(JBooleanLiteral.class);
    }
}

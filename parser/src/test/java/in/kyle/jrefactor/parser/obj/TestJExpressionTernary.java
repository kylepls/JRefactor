package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.JExpressionTernary;

public class TestJExpressionTernary {
    
    @Test
    public void testExpression() {
        String javaString = "true ? true : false;";
        JExpressionTernary expression = Parser.parse(javaString, JExpressionTernary.class);
        Verify.that(expression.getCondition()).isNotNull();
        Verify.that(expression.getLeft()).isNotNull();
        Verify.that(expression.getRight()).isNotNull();
    }
}
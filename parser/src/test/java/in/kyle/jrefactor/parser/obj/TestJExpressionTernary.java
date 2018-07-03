package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.JExpressionTernary;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralBoolean;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralCharacter;

public class TestJExpressionTernary {
    
    @Test
    public void testExpression() {
        String javaString = "true ? true : false";
        JExpressionTernary expression = Parser.parse(javaString, JExpressionTernary.class);
        Verify.that(expression.getCondition()).isInstanceOf(JLiteralBoolean.class);
        Verify.that(expression.getLeft()).isInstanceOf(JLiteralBoolean.class);
        Verify.that(expression.getRight()).isInstanceOf(JLiteralBoolean.class);
    }
    
    @Test
    public void testConditional() {
        String testString = "false ? 'a' : 'b'";
        JExpressionTernary expression = Parser.parse(testString, JExpressionTernary.class);
        Verify.that(expression.getCondition()).isNotNull();
        Verify.that(expression.getLeft()).isNotNull();
        Verify.that(expression.getRight()).isNotNull();
    
        Verify.that(expression.getCondition()).isInstanceOf(JLiteralBoolean.class);
        Verify.that(expression.getLeft()).isInstanceOf(JLiteralCharacter.class);
        Verify.that(expression.getRight()).isInstanceOf(JLiteralCharacter.class);
    }
}
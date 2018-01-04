package in.kyle.parser.expression;

import org.junit.Test;

import java.io.IOException;

import in.kyle.antlr.gen.Java8Parser;
import in.kyle.api.verify.Verify;
import in.kyle.parser.Parser;
import in.kyle.parser.expression.literal.JBooleanLiteral;
import in.kyle.parser.expression.literal.JCharacterLiteral;

public class TestTernary {
    
    @Test
    public void testConditional() throws IOException {
        String testString = "false ? 'a' : 'b'";
        JTernaryExpression expression = Parser.parse(testString, Java8Parser::conditionalExpression);
        Verify.that(expression.getCondition()).isNotNull();
        Verify.that(expression.getLeft()).isNotNull();
        Verify.that(expression.getRight()).isNotNull();
        
        Verify.that(expression.getCondition()).isInstanceOf(JBooleanLiteral.class);
        Verify.that(expression.getLeft()).isInstanceOf(JCharacterLiteral.class);
        Verify.that(expression.getRight()).isInstanceOf(JCharacterLiteral.class);
    }
}

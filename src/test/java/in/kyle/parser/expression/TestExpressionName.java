package in.kyle.parser.expression;

import org.junit.Test;

import java.io.IOException;

import in.kyle.antlr.gen.Java8Parser;
import in.kyle.api.verify.Verify;
import in.kyle.parser.Parser;
import in.kyle.parser.unit.JIdentifier;

public class TestExpressionName {
    
    @Test
    public void testExpressionName() throws IOException {
        String testString = "a.b.c";
        JExpressionName expression = Parser.parse(testString, Java8Parser::expressionName);
        Verify.that(expression.getIdentifier()).isEqual(new JIdentifier("a.b.c"));
    }
}

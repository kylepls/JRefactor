package in.kyle.jrefactor.parser.expression;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.parser.antlr.gen.Java8Parser;
import in.kyle.jrefactor.parser.unit.JIdentifier;

public class TestExpressionName {
    
    @Test
    public void testExpressionName() {
        String testString = "a.b.c";
        JExpressionName expression = Parser.parse(testString, Java8Parser::expressionName);
        Verify.that(expression.getIdentifier()).isEqual(new JIdentifier("a.b.c"));
    }
}

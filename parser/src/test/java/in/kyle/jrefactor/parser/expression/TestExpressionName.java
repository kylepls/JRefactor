package in.kyle.jrefactor.parser.expression;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.expression.JExpressionName;
import in.kyle.jrefactor.tree.unit.JIdentifier;

public class TestExpressionName {
    
    @Test
    public void testExpressionName() {
        String testString = "a.b.c";
        JExpressionName expression = Parser.parse(testString, JExpressionName.class);
        Verify.that(expression.getIdentifier()).isEqual(new JIdentifier("a.b.c"));
    }
}

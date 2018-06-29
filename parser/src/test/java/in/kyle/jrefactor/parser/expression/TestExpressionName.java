package in.kyle.jrefactor.parser.expression;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.expression.JExpressionName;

public class TestExpressionName {
    
    @Test
    public void testExpressionName() {
        String testString = "a.b.c";
        JExpressionName expression = Parser.parse(testString, JExpressionName.class);
        Verify.that(expression.getIdentifier()).isEqual(new JIdentifier("a.b.c"));
    }
}

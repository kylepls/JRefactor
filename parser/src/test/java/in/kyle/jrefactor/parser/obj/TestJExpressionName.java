package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import java.util.Optional;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.tree.obj.expression.JExpressionName;

public class TestJExpressionName {
    
    @Test
    public void test() {
        String testString = "a.b.c;";
        JExpressionName expression = Parser.parse(testString, JExpressionName.class);
        Verify.that(expression.getIdentifier()).isEqual(new JIdentifier("c"));
        Verify.that(expression.getArea())
                .isEqual(Optional.of(JPropertyLookup.builder().addAreas("a", "b").build()));
    }
}
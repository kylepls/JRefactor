package in.kyle.jrefactor.parser.expression;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.expression.JClassInstanceCreationExpression;
import in.kyle.jrefactor.tree.unit.JTypeName;

public class TestClassInstanceCreation {
    
    @Test
    public void testInstanceCreation() {
        String testString = "new pakage.Clazz<>(parameter) {}";
        JClassInstanceCreationExpression expression =
                Parser.parse(testString, JClassInstanceCreationExpression.class);
        
        Verify.that(expression.getType()).isEqual(new JTypeName("pakage.Clazz"));
        
        Verify.that(expression.getTypeArguments()).isNotNull();
        Verify.that(expression.getArguments()).isNotNull();
        Verify.that(expression.getArguments()).sizeIs(1);
        
        Verify.that(expression.getBody()).isNotNull();
    }
}

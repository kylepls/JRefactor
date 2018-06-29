package in.kyle.jrefactor.parser.expression;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.expression.JExpressionClassInstanceCreation;

public class TestClassInstanceCreation {
    
    @Test
    public void testInstanceCreation() {
        String testString = "new pakage.Clazz<>(parameter) {}";
        JExpressionClassInstanceCreation expression =
                Parser.parse(testString, JExpressionClassInstanceCreation.class);
        
        Verify.that(expression.getType()).isEqual(new JTypeName("pakage.Clazz"));
        
        Verify.that(expression.getTypeArguments()).isNotNull();
        Verify.that(expression.getArguments()).isNotNull();
        Verify.that(expression.getArguments()).sizeIs(1);
        
        Verify.that(expression.getBody()).isNotNull();
    }
}

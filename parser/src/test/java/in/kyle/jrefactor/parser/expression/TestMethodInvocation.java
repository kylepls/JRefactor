package in.kyle.jrefactor.parser.expression;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.JExpressionMethodInvocation;

public class TestMethodInvocation {
    
    @Test
    public void testSimpleMethod() {
        String javaString = "method();";
        JExpressionMethodInvocation invocation = Parser.parse(javaString, JExpressionMethodInvocation.class);
        Verify.that(invocation.getTypeArguments()).isEmpty();
        Verify.that(invocation.getArguments()).isEmpty();
        Verify.that(invocation.getMethodName()).isNotNull();
    }
    
    @Test
    public void testTypeMethodInvocation() {
        String javaString = "super.<T>method();";
        JExpressionMethodInvocation invocation = Parser.parse(javaString, JExpressionMethodInvocation.class);
        Verify.that(invocation.getTypeArguments()).sizeIs(1);
        Verify.that(invocation.getArguments()).isEmpty();
        Verify.that(invocation.getMethodName()).isNotNull();
    }
    
    @Test
    public void testMethodAreaInvocation() {
        String javaString = "super.method();";
        JExpressionMethodInvocation invocation = Parser.parse(javaString, JExpressionMethodInvocation.class);
        Verify.that(invocation.getTypeArguments()).isEmpty();
        Verify.that(invocation.getArguments()).isEmpty();
        Verify.that(invocation.getMethodName()).isNotNull();
        Verify.that(invocation.getMethodArea()).isPresent();
        Verify.that(invocation.getMethodArea()).get().isEqual("super.");
    }
}

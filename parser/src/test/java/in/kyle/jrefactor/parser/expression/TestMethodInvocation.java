package in.kyle.jrefactor.parser.expression;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.expression.JMethodInvocation;

public class TestMethodInvocation {
    
    @Test
    public void testSimpleMethod() {
        String javaString = "method();";
        JMethodInvocation invocation = Parser.parse(javaString, JMethodInvocation.class);
        Verify.that(invocation.getTypeArguments()).isEmpty();
        Verify.that(invocation.getArguments()).isEmpty();
        Verify.that(invocation.getIdentifier()).isNotNull();
    }
    
    @Test
    public void testTypeMethodInvocation() {
        String javaString = "super.<T>method();";
        JMethodInvocation invocation = Parser.parse(javaString, JMethodInvocation.class);
        Verify.that(invocation.getTypeArguments()).sizeIs(1);
        Verify.that(invocation.getArguments()).isEmpty();
        Verify.that(invocation.getIdentifier()).isNotNull();
    }
    
    @Test
    public void testMethodAreaInvocation() {
        String javaString = "super.method();";
        JMethodInvocation invocation = Parser.parse(javaString, JMethodInvocation.class);
        Verify.that(invocation.getTypeArguments()).isEmpty();
        Verify.that(invocation.getArguments()).isEmpty();
        Verify.that(invocation.getIdentifier()).isNotNull();
        Verify.that(invocation.getMethodArea().isPresent()).isTrue();
        Verify.that(invocation.getMethodArea().get()).isEqual("super.");
    }
}

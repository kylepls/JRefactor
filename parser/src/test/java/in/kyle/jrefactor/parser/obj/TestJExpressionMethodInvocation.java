package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.JExpressionMethodInvocation;

public class TestJExpressionMethodInvocation {
    
    @Test
    public void test() {
        String javaString = "method()";
        JExpressionMethodInvocation
                invocation = Parser.parse(javaString, JExpressionMethodInvocation.class);
        Verify.that(invocation.getTypeArguments()).isEmpty();
        Verify.that(invocation.getArguments()).isEmpty();
        Verify.that(invocation.getMethodName()).isNotNull();
    }
    
    @Test
    public void testTypeMethodInvocation() {
        String javaString = "<T>method()";
        JExpressionMethodInvocation invocation = Parser.parse(javaString, JExpressionMethodInvocation.class);
        Verify.that(invocation.getTypeArguments()).sizeIs(1);
    }
    
    @Test
    public void testMethodAreaInvocation() {
        String javaString = "super.method();";
        // TODO: 7/3/2018 For some reason the semicolon is needed here 
        JExpressionMethodInvocation invocation = Parser.parse(javaString, JExpressionMethodInvocation.class);
        Verify.that(invocation.getMethodArea()).isPresent();
    }
    
    @Test
    public void testMethodParameter() {
        String javaString = "method(1, 2, 3)";
        JExpressionMethodInvocation invocation = Parser.parse(javaString, JExpressionMethodInvocation.class);
        Verify.that(invocation.getArguments()).sizeIs(3);        
    }
}
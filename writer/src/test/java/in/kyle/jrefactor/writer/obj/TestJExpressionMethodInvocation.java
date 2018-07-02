package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import java.util.Optional;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.expression.JExpressionMethodInvocation;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.JLiteralInteger;
import in.kyle.jrefactor.tree.obj.reference.typeargument.JTypeArgumentReference;
import in.kyle.jrefactor.writer.Write;

public class TestJExpressionMethodInvocation {
    
    @Test
    public void test() {
        JExpressionMethodInvocation invocation = new JExpressionMethodInvocation();
        invocation.setMethodName(new JIdentifier("test"));
        Verify.that(Write.object(invocation)).isEqual("test()");
    }
    
    @Test
    public void testMethodArea() {
        JExpressionMethodInvocation invocation = new JExpressionMethodInvocation();
        invocation.setMethodName(new JIdentifier("test"));
        JPropertyLookup lookup = new JPropertyLookup();
        lookup.addArea("test");
        invocation.setMethodArea(Optional.of(lookup));
        Verify.that(Write.object(invocation)).isEqual("test.test()");
    }
    
    @Test
    public void testArguments() {
        JExpressionMethodInvocation invocation = new JExpressionMethodInvocation();
        invocation.setMethodName(new JIdentifier("test"));
        invocation.addArgument(new JLiteralInteger(1));
        Verify.that(Write.object(invocation)).isEqual("test(1)");
    }
    
    @Test
    public void testTypes() {
        JExpressionMethodInvocation invocation = new JExpressionMethodInvocation();
        invocation.setMethodName(new JIdentifier("test"));
        invocation.addTypeArgument(new JTypeArgumentReference(new JTypeName("T")));
        Verify.that(Write.object(invocation)).isEqual("test<T>()");
    }
}

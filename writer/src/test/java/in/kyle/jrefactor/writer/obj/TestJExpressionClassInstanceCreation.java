package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import java.util.Optional;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.expression.JExpressionClassInstanceCreation;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.JLiteralInteger;
import in.kyle.jrefactor.tree.obj.reference.typeargument.JTypeArgumentReference;
import in.kyle.jrefactor.tree.obj.unit.typebody.JClassBody;
import in.kyle.jrefactor.writer.Write;

public class TestJExpressionClassInstanceCreation {
    
    @Test
    public void test() {
        JExpressionClassInstanceCreation creation = new JExpressionClassInstanceCreation();
        creation.setType(new JTypeName("Integer"));
        Verify.that(Write.object(creation)).isEqual("new Integer()");
    }
    
    @Test
    public void testTypeArguments() {
        JExpressionClassInstanceCreation creation = new JExpressionClassInstanceCreation();
        creation.addTypeArgument(new JTypeArgumentReference(new JTypeName("T")));
        creation.setType(new JTypeName("Integer"));
        Verify.that(Write.object(creation)).isEqual("new Integer<T>()");
    }
    
    @Test
    public void testArguments() {
        JExpressionClassInstanceCreation creation = new JExpressionClassInstanceCreation();
        creation.addArgument(new JLiteralInteger(1));
        creation.setType(new JTypeName("Integer"));
        Verify.that(Write.object(creation)).isEqual("new Integer(1)");
    }
    
    @Test
    public void testBody() {
        JExpressionClassInstanceCreation creation = new JExpressionClassInstanceCreation();
        creation.setType(new JTypeName("Integer"));
        creation.setBody(Optional.of(new JClassBody()));
        Verify.that(Write.object(creation)).isEqual("new Integer() {\n}");
    }
}

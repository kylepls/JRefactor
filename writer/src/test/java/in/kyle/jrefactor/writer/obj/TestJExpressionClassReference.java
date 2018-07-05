package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.expression.JExpressionClassReference;
import in.kyle.jrefactor.writer.Write;

public class TestJExpressionClassReference {
    
    @Test
    public void test() {
        JExpressionClassReference expression = new JExpressionClassReference(new JTypeName("Test"));
        Verify.that(Write.object(expression)).isEqual("Test.class");
    }
}

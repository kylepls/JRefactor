package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.reference.JExpressionTypeReference;
import in.kyle.jrefactor.writer.Write;

public class TestJExpressionTypeReference {
    
    @Test
    public void test() {
        JExpressionTypeReference reference = new JExpressionTypeReference(new JTypeName("Test"));
        Verify.that(Write.object(reference)).isEqual("Test");
    }
}

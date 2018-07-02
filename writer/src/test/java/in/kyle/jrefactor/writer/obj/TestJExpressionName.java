package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.expression.JExpressionName;
import in.kyle.jrefactor.writer.Write;

public class TestJExpressionName {
    
    @Test
    public void test() {
        JExpressionName name = new JExpressionName(new JIdentifier("test"));
        Verify.that(Write.object(name)).isEqual("test");
    }
}

package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.reference.typeargument.JTypeArgumentReference;
import in.kyle.jrefactor.writer.Write;

public class TestJTypeArgumentReference {
    @Test
    public void test() {
        JTypeArgumentReference reference = new JTypeArgumentReference(new JTypeName("Test"));
        Verify.that(Write.object(reference)).isEqual("Test");
    }
}

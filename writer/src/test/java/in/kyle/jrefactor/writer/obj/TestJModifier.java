package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.JModifier;
import in.kyle.jrefactor.writer.Write;

public class TestJModifier {
    
    @Test
    public void test() {
        Verify.that(Write.object(JModifier.ABSTRACT)).isEqual("abstract");
    }
}

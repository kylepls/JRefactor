package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.writer.Write;

public class TestJIdentifier {
    
    @Test
    public void test() {
        JIdentifier identifier = new JIdentifier();
        identifier.setName("id");
        Verify.that(Write.object(identifier)).isEqual("id");
    }
}

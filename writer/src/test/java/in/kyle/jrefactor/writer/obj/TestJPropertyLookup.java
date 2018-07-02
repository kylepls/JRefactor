package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.writer.Write;

public class TestJPropertyLookup {
    
    @Test
    public void test() {
        JPropertyLookup lookup = new JPropertyLookup();
        lookup.addArea("test");
        lookup.addArea("test2");
        lookup.addArea("test3");
        Verify.that(Write.object(lookup)).isEqual("test.test2.test3");
    }
}

package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.writer.EzWriter;

public class TestJTypeName {
    
    @Test
    public void test() {
        JTypeName typeName = new JTypeName("String");
        EzWriter writer = new EzWriter();
        String write = writer.write(typeName);
        Verify.that(write).isEqual("String");
    }
}

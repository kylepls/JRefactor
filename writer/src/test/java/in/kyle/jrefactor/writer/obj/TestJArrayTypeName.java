package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.typename.JArrayTypeName;
import in.kyle.jrefactor.writer.Write;

public class TestJArrayTypeName {
    
    @Test
    public void test() {
        JArrayTypeName typeName = new JArrayTypeName();
        typeName.setType("String");
        Verify.that(Write.object(typeName)).isEqual("String");
    }
    
    @Test
    public void testDimensions() {
        JArrayTypeName typeName = new JArrayTypeName();
        typeName.setType("String");
        typeName.setDimensions(2);
        Verify.that(Write.object(typeName)).isEqual("String[][]");        
    }
}

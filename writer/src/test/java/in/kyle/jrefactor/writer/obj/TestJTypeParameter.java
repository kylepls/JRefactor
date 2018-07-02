package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JTypeParameter;
import in.kyle.jrefactor.writer.Write;

public class TestJTypeParameter {
    
    @Test
    public void test() {
        JTypeParameter parameter = new JTypeParameter();
        parameter.setIdentifier(new JIdentifier("T"));
    }
    
    @Test
    public void testBounds() {
        JTypeParameter parameter = new JTypeParameter();
        parameter.setIdentifier(new JIdentifier("T"));
        parameter.addBound(new JTypeName("String"));
        parameter.addBound(new JTypeName("Function"));
        Verify.that(Write.object(parameter)).isEqual("T extends String & Function");
    }
}

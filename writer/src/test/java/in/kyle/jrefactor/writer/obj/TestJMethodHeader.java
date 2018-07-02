package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.JParameter;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JMethodHeader;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JTypeParameter;
import in.kyle.jrefactor.writer.Write;

public class TestJMethodHeader {
    
    @Test
    public void test() {
        JMethodHeader header = new JMethodHeader();
        header.setIdentifier(new JIdentifier("test"));
        header.setReturns(new JTypeName("void"));
        Verify.that(Write.object(header)).isEqual("void test()");
    }
    
    @Test
    public void testParameters() {
        JMethodHeader header = new JMethodHeader();
        header.setIdentifier(new JIdentifier("test"));
        header.setReturns(new JTypeName("void"));
        header.addParameter(new JParameter(new JTypeName("int"), new JIdentifier("i")));
        header.addParameter(new JParameter(new JTypeName("int"), new JIdentifier("j")));
        Verify.that(Write.object(header)).isEqual("void test(int i, int j)");
    }
    
    @Test
    public void testThrows() {
        JMethodHeader header = new JMethodHeader();
        header.setIdentifier(new JIdentifier("test"));
        header.setReturns(new JTypeName("void"));
        header.addThrowsType(new JTypeName("Exception"));
        Verify.that(Write.object(header)).isEqual("void test() throws Exception");
    }
    
    @Test
    public void testTypeParameter() {
        JMethodHeader header = new JMethodHeader();
        header.setIdentifier(new JIdentifier("test"));
        header.setReturns(new JTypeName("void"));
        header.addTypeParameter(new JTypeParameter(new JIdentifier("T")));
        Verify.that(Write.object(header)).isEqual("<T> void test()");
    }
}

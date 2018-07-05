package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import java.util.Optional;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.block.JStatementBlock;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JMethodHeader;
import in.kyle.jrefactor.tree.obj.unit.bodymember.typemember.interfacemember.JInterfaceMethod;
import in.kyle.jrefactor.writer.Write;

public class TestJInterfaceMethod {
    
    @Test
    public void test() {
        JInterfaceMethod method = new JInterfaceMethod();
        JMethodHeader header = new JMethodHeader();
        header.setReturns(new JTypeName("void"));
        header.setIdentifier(new JIdentifier("test"));
        method.setHeader(header);
        Verify.that(Write.object(method)).isEqual("void test();");
    }
    
    @Test
    public void testBody() {
        JInterfaceMethod method = new JInterfaceMethod();
        JMethodHeader header = new JMethodHeader();
        header.setReturns(new JTypeName("void"));
        header.setIdentifier(new JIdentifier("test"));
        method.setHeader(header);
        method.setBody(Optional.of(new JStatementBlock()));
        Verify.that(Write.object(method)).isEqual("default void test() {}");
    }
}

package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JMethodHeader;
import in.kyle.jrefactor.tree.obj.statement.JBlock;
import in.kyle.jrefactor.tree.obj.unit.bodymember.typemember.enummember.classmember.JMethod;
import in.kyle.jrefactor.writer.Write;

public class TestJMethod {
    
    @Test
    public void test() {
        JMethod method = new JMethod();
        JMethodHeader header = new JMethodHeader(new JIdentifier("test"), new JTypeName("void"));
        method.setHeader(header);
        method.setBody(new JBlock());
        Verify.that(Write.object(method)).isEqual("void test() {}");
    }
}

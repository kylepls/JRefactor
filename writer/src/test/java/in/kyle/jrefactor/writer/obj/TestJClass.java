package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import java.util.Optional;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JTypeParameter;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.type.superinterfacetype
        .typeparametertype.JClass;
import in.kyle.jrefactor.tree.obj.unit.typebody.JClassBody;
import in.kyle.jrefactor.writer.Write;

public class TestJClass {
    @Test
    public void test() {
        JClass clazz = new JClass();
        clazz.setIdentifier(new JIdentifier("Test"));
        clazz.setBody(new JClassBody());
        Verify.that(Write.object(clazz)).isEqual("class Test {}");
    }
    
    @Test
    public void testSuperType() {
        JClass clazz = new JClass();
        clazz.setIdentifier(new JIdentifier("Test"));
        clazz.setBody(new JClassBody());
        clazz.setSuperType(Optional.of(new JTypeName("Extend")));
        Verify.that(Write.object(clazz)).isEqual("class Test extends Extend {}");
    }
    
    @Test
    public void testTypeParameter() {
        JClass clazz = new JClass();
        clazz.setIdentifier(new JIdentifier("Test"));
        clazz.setBody(new JClassBody());
        clazz.addTypeParameter(new JTypeParameter(new JIdentifier("T")));
        Verify.that(Write.object(clazz)).isEqual("class Test<T> {}");
    }
}

package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.type.superinterfacetype.typeparametertype.JClass;

public class TestJClass {
    
    @Test
    public void testSuper() {
        String test = "class Test extends A {}";
        JClass clazz = Parser.parse(test, JClass.class);
        Verify.that(clazz.getSuperType()).isPresent();
    }
}

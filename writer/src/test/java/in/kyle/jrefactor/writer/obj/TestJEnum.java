package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.block.typebody.JEnumBody;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.type.superinterfacetype.JEnum;
import in.kyle.jrefactor.writer.Write;

public class TestJEnum {
    @Test
    public void test() {
        JEnum anEnum = new JEnum();
        anEnum.setIdentifier(new JIdentifier("Test"));
        anEnum.setBody(new JEnumBody());
        Verify.that(Write.object(anEnum)).isEqual("enum Test {}");
    }
}

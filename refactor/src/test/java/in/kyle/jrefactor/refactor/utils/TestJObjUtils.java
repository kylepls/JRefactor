package in.kyle.jrefactor.refactor.utils;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.refactor.util.JObjUtils;
import in.kyle.jrefactor.refactor.util.obj.JTypeNames;
import in.kyle.jrefactor.tree.JObj;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.block.typebody.JClassBody;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.JField;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JType;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.type.superinterfacetype.typeparametertype.JClass;

public class TestJObjUtils {
    
    @Test
    public void testFindParent() {
        JField field = JField.builder().type(JTypeNames.BOOLEAN).build();
        JType type = JClass.builder()
                .body(JClassBody.builder().addElements(field).build())
                .identifier(new JIdentifier("Test"))
                .build();
    
        JObj parent = JObjUtils.findParent(type, field);
        parent = JObjUtils.findParent(type, parent);
        Verify.that(parent).isSame(type);
    }
}

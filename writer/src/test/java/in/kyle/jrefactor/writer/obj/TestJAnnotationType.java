package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.type.JAnnotationType;
import in.kyle.jrefactor.tree.obj.unit.typebody.JAnnotationBody;
import in.kyle.jrefactor.writer.Write;

public class TestJAnnotationType {
    @Test
    public void test() {
        JAnnotationType type = new JAnnotationType();
        type.setIdentifier(new JIdentifier("Test"));
        type.setBody(new JAnnotationBody());
        Verify.that(Write.object(type)).isEqual("@interface Test {}");
    }
}

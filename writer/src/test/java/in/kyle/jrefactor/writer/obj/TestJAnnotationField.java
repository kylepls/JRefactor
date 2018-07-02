package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import java.util.Optional;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralString;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JAnnotationField;
import in.kyle.jrefactor.writer.Write;

public class TestJAnnotationField {
    
    @Test
    public void test() {
        JAnnotationField field = new JAnnotationField();
        field.setType(new JTypeName("String"));
        field.setIdentifier(new JIdentifier("test"));
        Verify.that(Write.object(field)).isEqual("String test();");
    }
    
    @Test
    public void testDefault() {
        JAnnotationField field = new JAnnotationField();
        field.setType(new JTypeName("String"));
        field.setIdentifier(new JIdentifier("test"));
        field.setDefaultValue(Optional.of(new JLiteralString("value")));
        Verify.that(Write.object(field)).isEqual("String test() default \"value\";");
    }
}

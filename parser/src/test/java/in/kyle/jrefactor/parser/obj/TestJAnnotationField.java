package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.JAnnotationValue;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralString;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JAnnotationField;

public class TestJAnnotationField {
    
    @Test
    public void test() {
        String test = "String string()";
        JAnnotationField field = Parser.parse(test, JAnnotationField.class);
        Verify.that(field.getType().getType()).isEqual("String");
        Verify.that(field.getIdentifier().getName()).isEqual("string");
        Verify.that(field.getDefaultValue()).isNotPresent();
        Verify.that(field.getAnnotations()).isEmpty();
        Verify.that(field.getModifiers()).isEmpty();
    }
    
    @Test
    public void testDefaultValue() {
        String test = "String string() default \"test\"";
        JAnnotationField field = Parser.parse(test, JAnnotationField.class);
        Verify.that(field.getDefaultValue()).isPresent();
        JAnnotationValue value = field.getDefaultValue().get();
        Verify.that(value).isInstanceOf(JLiteralString.class);
    }
}
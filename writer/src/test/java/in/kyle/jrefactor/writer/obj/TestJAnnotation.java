package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JAnnotation;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.annotationvalue.JValuePair;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralString;
import in.kyle.jrefactor.writer.Write;

public class TestJAnnotation {
    
    @Test
    public void test() {
        JAnnotation annotation = new JAnnotation(new JTypeName("Data"));
        Verify.that(Write.object(annotation)).isEqual("@Data");
    }
    
    @Test
    public void testParamsSingle() {
        JAnnotation annotation = new JAnnotation(new JTypeName("Data"));
        annotation.addValue(new JLiteralString("value"));
        Verify.that(Write.object(annotation)).isEqual("@Data(\"value\")");
    }
    
    @Test
    public void testParamsMulti() {
        JAnnotation annotation = new JAnnotation(new JTypeName("Data"));
        annotation.addValue(new JValuePair(new JIdentifier("id"), new JLiteralString("value")));
        annotation.addValue(new JValuePair(new JIdentifier("name"), new JLiteralString("joe")));
        Verify.that(Write.object(annotation)).isEqual("@Data(id = \"value\", name = \"joe\")");
    }
}

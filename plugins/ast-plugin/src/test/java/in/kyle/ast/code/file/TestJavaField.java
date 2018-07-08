package in.kyle.ast.code.file;

import org.junit.Test;

import in.kyle.api.verify.Verify;

public class TestJavaField {
    
    @Test
    public void testSimpleRender() {
        JavaField field = new JavaField("String", null, "str", null);
        String javaString = field.write();
        Verify.that(javaString).isEqual("String str");
    }
    
    @Test
    public void testGenericRender() {
        JavaField field = new JavaField("List", "String", "list", null);
        String javaString = field.write();
        Verify.that(javaString).isEqual("List<String> list");
    }
    
    @Test
    public void testDefaultValueRender() {
        JavaField field = new JavaField("String", null, "str", "\"value\"");
        String javaString = field.write();
        Verify.that(javaString).isEqual("String str = \"value\"");
    }
}

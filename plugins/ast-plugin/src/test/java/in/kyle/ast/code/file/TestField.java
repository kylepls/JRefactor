package in.kyle.ast.code.file;

import org.junit.Test;

import in.kyle.api.verify.Verify;

public class TestField {
    
    @Test
    public void testSimpleRender() {
        Field field = new Field("String", null, "str", null);
        String javaString = field.write();
        Verify.that(javaString).isEqual("String str");
    }
    
    @Test
    public void testGenericRender() {
        Field field = new Field("List", "String", "list", null);
        String javaString = field.write();
        Verify.that(javaString).isEqual("List<String> list");
    }
    
    @Test
    public void testDefaultValueRender() {
        Field field = new Field("String", null, "str", "\"value\"");
        String javaString = field.write();
        Verify.that(javaString).isEqual("String str = \"value\"");
    }
}

package in.kyle.ast.code.file;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.ast.util.StringTemplate;

public class TestJavaField {
    
    @Test
    public void testSimpleRender() {
        JavaField field = new JavaField("String", null, "str", null);
        String javaString = writeField(field);
        Verify.that(javaString).isEqual("private String str;");
    }
    
    @Test
    public void testGenericRender() {
        JavaField field = new JavaField("List", "String", "list", null);
        String javaString = writeField(field);
        Verify.that(javaString).isEqual("private List<String> list;");
    }
    
    @Test
    public void testDefaultValueRender() {
        JavaField field = new JavaField("String", null, "str", "\"value\"");
        String javaString = writeField(field);
        Verify.that(javaString).isEqual("private String str = \"value\";");
    }
    
    private String writeField(JavaField field) {
        return StringTemplate.render("file/writeFileField", field);
    }
}

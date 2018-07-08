package in.kyle.ast.code;

import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

import in.kyle.api.verify.Verify;
import in.kyle.ast.code.file.JavaEnumElement;
import in.kyle.ast.code.file.JavaField;
import in.kyle.ast.code.file.JavaFile;
import in.kyle.ast.code.file.JavaFileType;
import in.kyle.ast.util.FileUtils;

public class TestJavaFile {
    @Test
    public void testSimpleFile() throws IOException {
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        String result = file.write();
        matchesFile(result, "testSimpleFile");
    }
    
    @Test
    public void testSimpleEnum() throws IOException {
        JavaFile file = new JavaFile("Test", JavaFileType.ENUM);
        JavaEnumElement one = new JavaEnumElement("ONE");
        one.getValues().add("\"one\"");
        JavaEnumElement two = new JavaEnumElement("TWO");
        two.getValues().add("\"two\"");
        JavaEnumElement three = new JavaEnumElement("THREE");
        three.getValues().add("\"three\"");
        file.getEnumElements().add(one);
        file.getEnumElements().add(two);
        file.getEnumElements().add(three);
        file.addEnumVariable("value");
        
        String result = file.write();
        matchesFile(result, "testSimpleEnum");
    }
    
    @Test
    public void testSimpleFileField() throws IOException {
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        JavaField field = new JavaField("String", null, "string", null);
        file.getFields().add(field);
        String result = file.write();
        matchesFile(result, "testSimpleFileField");
    }
    
    @Test
    public void testSimpleInnerClass() throws IOException {
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        
        JavaFile inner = new JavaFile("Test2", JavaFileType.ENUM);
        inner.getEnumElements().add(new JavaEnumElement("ONE"));
        file.addInnerClass(inner);
        
        String result = file.write();
        matchesFile(result, "testSimpleFileInnerClass");
    }
    
    @Test
    public void testSimpleFileMethod() throws IOException {
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        file.addBodyElement("public void test() {}");
        String result = file.write();
        matchesFile(result, "testSimpleFileMethod");
    }
    
    @Test
    public void testSimpleFilePackage() throws IOException {
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        file.setPackageName("in.kyle.test");
        
        String result = file.write();
        matchesFile(result, "testSimpleFilePackage");
    }
    
    @Test
    public void testGetRewritableTypes() {
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        file.setGenericSuper("A");
        JavaField field = new JavaField("B", "C", "name", null);
        file.getFields().add(field);
        file.addIsType("D");
        
        Map<Consumer<String>, String> rewritableTypes = file.getRewritableTypes();
        Verify.that(rewritableTypes).containsValue("A");
        Verify.that(rewritableTypes).containsValue("B");
        Verify.that(rewritableTypes).containsValue("C");
        Verify.that(rewritableTypes).containsValue("D");
        
        rewritableTypes.keySet().forEach(c -> c.accept("Z"));
        Verify.that(file.getGenericSuper()).isEqual("Z");
        Verify.that(field.getType()).isEqual("Z");
        Verify.that(field.getGeneric()).isEqual("Z");
        Verify.that(file.getIsTypes()).sizeIs(1).contains("Z");
    }
    
    private void matchesFile(String text, String name) throws IOException {
        FileUtils.matchesFile(text, name);
    }
}

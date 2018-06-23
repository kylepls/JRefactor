package in.kyle.ast.code;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import in.kyle.api.verify.Verify;
import in.kyle.ast.code.file.EnumElement;
import in.kyle.ast.code.file.Field;

public class TestJavaFile {
    @Test
    public void testSimpleFile() throws IOException {
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        String result = file.write();
        matchesFile(result, "simpleFile");
    }
    
    @Test
    public void testSimpleEnum() throws IOException {
        JavaFile file = new JavaFile("Test", JavaFileType.ENUM);
        EnumElement one = new EnumElement("ONE");
        one.getValues().add("\"one\"");
        EnumElement two = new EnumElement("TWO");
        two.getValues().add("\"two\"");
        EnumElement three = new EnumElement("THREE");
        three.getValues().add("\"three\"");
        file.getEnumElements().add(one);
        file.getEnumElements().add(two);
        file.getEnumElements().add(three);
        
        String result = file.write();
        matchesFile(result, "simpleFileEnum");
    }
    
    @Test
    public void testSimpleFileField() throws IOException {
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        Field field = new Field("String", null, "string", null);
        file.getFields().add(field);
        String result = file.write();
        matchesFile(result, "simpleFileField");
    }
    
    @Test
    public void testSimpleFileImport() throws IOException {
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        file.getImports().add("java.util.List");
        
        String result = file.write();
        matchesFile(result, "simpleFileImport");
    }
    
    @Test
    public void testSimpleInnerClass() throws IOException {
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        
        JavaFile inner = new JavaFile("Test2", JavaFileType.ENUM);
        inner.getEnumElements().add(new EnumElement("ONE"));
        file.addInnerClass(inner);
        
        String result = file.write();
        matchesFile(result, "simpleFileInnerClass");
    }
    
    @Test
    public void testSimpleFileMethod() throws IOException {
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        file.getMethods().add("public void test() {}");
        String result = file.write();
        matchesFile(result, "simpleFileMethod");
    }
    
    @Test
    public void testSimpleFilePackage() throws IOException {
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        file.setPackageName("in.kyle.test");
        
        String result = file.write();
        matchesFile(result, "simpleFilePackage");
    }
    
    @Test
    public void testGetRewritableTypes() {
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        file.setGenericSuper("A");
        Field field = new Field("B", "C", "name", null);
        file.getFields().add(field);
        file.getImplementingTypes().add("D");
        
        Map<Consumer<String>, String> rewritableTypes = file.getRewritableTypes();
        Verify.that(rewritableTypes).containsValue("A");
        Verify.that(rewritableTypes).containsValue("B");
        Verify.that(rewritableTypes).containsValue("C");
        Verify.that(rewritableTypes).containsValue("D");
        
        rewritableTypes.keySet().forEach(c -> c.accept("Z"));
        Verify.that(file.getGenericSuper()).isEqual("Z");
        Verify.that(field.getType()).isEqual("Z");
        Verify.that(field.getGeneric()).isEqual("Z");
        Verify.that(file.getImplementingTypes()).sizeIs(1).contains("Z");
    }
    
    private void matchesFile(String text, String name) throws IOException {
        List<String> oldText = readFile(name + ".java");
        List<String> newText = Arrays.asList(text.split("\n"));
        oldText.replaceAll(String::trim);
        newText.replaceAll(String::trim);
        
        String oldTextString = oldText.stream().collect(Collectors.joining("\n"));
        String newTextString = newText.stream().collect(Collectors.joining("\n"));
        
        Verify.that(oldTextString).diffEqual(newTextString);
    }
    
    private List<String> readFile(String path) throws IOException {
        return IOUtils.readLines(TestJavaFile.class.getResourceAsStream("/" + path), "UTF-8");
    }
}

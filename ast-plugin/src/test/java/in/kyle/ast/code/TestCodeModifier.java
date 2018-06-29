package in.kyle.ast.code;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

import in.kyle.api.verify.Verify;
import in.kyle.ast.code.file.EnumElement;
import in.kyle.ast.code.file.Field;
import in.kyle.ast.util.FileUtils;

public class TestCodeModifier {
    
    private CodeModifier modifier;
    
    @Before
    public void setup() {
        modifier = new CodeModifier();
    }
    
    @Test
    public void testSetFilePrefix() {
        modifier.setFilePrefix("Q");
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        modifier.updateFileName(file);
        Verify.that(file.getName()).isEqual("QTest");
    }
    
    @Test
    public void testPackagePrefix() {
        modifier.setPackagePrefix("in.kyle.test");
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        modifier.updatePackageName(file);
        Verify.that(file.getPackageName()).isEqual("in.kyle.test");
    }
    
    @Test
    public void testPackagePrefixPreappend() {
        modifier.setPackagePrefix("in.kyle.test");
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        file.setPackageName("qqq");
        modifier.updatePackageName(file);
        Verify.that(file.getPackageName()).isEqual("in.kyle.test.qqq");
    }
    
    @Test
    public void testImportMappingsContains() {
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        file.setPackageName("qqq");
        modifier.preProcess(file);
        Verify.that(modifier.getImportMappings())
              .containsKeyValue("Test", Collections.singleton("qqq.Test"));
    }
    
    @Test
    public void testUpdateTypeNames() {
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        file.getImplementingTypes().add("AType");
        file.setGenericSuper("AType");
        Field field = new Field("AType", "AType", "test", null);
        file.getFields().add(field);
        
        modifier.getOldFiles().put("AType", "ANewType");
        modifier.updateTypeNames(file);
        
        Verify.that(file.getImplementingTypes()).sizeIs(1).contains("ANewType");
        Verify.that(file.getGenericSuper()).isEqual("ANewType");
        Verify.that(field.getType()).isEqual("ANewType");
        Verify.that(field.getGeneric()).isEqual("ANewType");
    }
    
    @Test
    public void testTypeUpdateImportMapping() {
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        file.getImplementingTypes().add("AType");
        
        modifier.getOldFiles().put("AType", "ANewType");
        modifier.getImportMappings().put("ANewType", Collections.singleton("Test"));
        modifier.preProcess(file);
        modifier.process(file);
        
        Verify.that(file.getImplementingTypes()).sizeIs(1).contains("ANewType");
        Verify.that(file.getImports()).sizeIs(1).contains("Test");
    }
    
    @Test
    public void testAddSimpleConstructor() throws IOException {
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        modifier.addConstructors(file);
        
        Verify.that(file.getConstructors()).sizeIs(1);
        FileUtils.matchesFile(file.getConstructors().get(0), "constructors/simpleConstructor");
    }
    
    @Test
    public void testAddConstructorField() throws IOException {
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        Field field = new Field("Q", "A", "p", null);
        file.addField(field);
        modifier.addConstructors(file);
        
        Verify.that(file.getConstructors()).sizeIs(2);
        FileUtils.matchesFile(file.getConstructors().stream().collect(Collectors.joining("\n\n")),
                              "constructors/constructorField");
    }
    
    @Test
    public void testAddConstructorSuperField() throws IOException {
        JavaFile superType = new JavaFile("Super", JavaFileType.CLASS);
        Field field = new Field("A", "b");
        superType.addField(field);
        
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        file.setSuperType(superType);
        modifier.addConstructors(file);
        
        Verify.that(file.getConstructors()).sizeIs(2);
        FileUtils.matchesFile(file.getConstructors().stream().collect(Collectors.joining("\n\n")),
                              "constructors/constructorSuper");
    }
    
    @Test
    public void testConstructorGenericsTransposition() throws IOException {
        JavaFile superType = new JavaFile("Super", JavaFileType.CLASS);
        superType.setGenericDefine("T");
        Field field = new Field("T", "value");
        superType.addField(field);
        
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        file.setGenericSuper("Long");
        file.setSuperType(superType);
        modifier.addConstructors(file);
        
        Verify.that(file.getConstructors()).sizeIs(2);
        FileUtils.matchesFile(file.getConstructors().stream().collect(Collectors.joining("\n\n")),
                              "constructors/genericTransposition");
    }
    
    @Test
    public void testConstructorEnum() throws IOException {
        EnumElement element = new EnumElement("Test");
        element.addValue("string");
        
        JavaFile file = new JavaFile("Test", JavaFileType.ENUM);
        file.addEnumElement(element);
        file.addEnumVariable("value");
        
        modifier.addConstructors(file);
        
        Verify.that(file.getConstructors()).sizeIs(1);
        FileUtils.matchesFile(file.getConstructors().get(0), "constructors/enumElement");
    }
    
    @Test
    public void testConstructorEmptyEnum() throws IOException {
        EnumElement element = new EnumElement("Test");
        JavaFile file = new JavaFile("Test", JavaFileType.ENUM);
        file.addEnumElement(element);
        
        modifier.addConstructors(file);
        
        Verify.that(file.getConstructors()).sizeIs(1);
        FileUtils.matchesFile(file.getConstructors().get(0), "constructors/emptyEnum");
    }
    
    @Test
    public void testReplaceVariable() {
        modifier.addVariable("test", "HELLO WORLD");
        JavaFile file = new JavaFile("Test", JavaFileType.ENUM);
        file.addMethod("test");
        modifier.process(file);
        Verify.that(file.getMethods()).sizeIs(1);
        Verify.that(file.getMethods()).allMatch(string -> string.equals("HELLO WORLD"));
    }
}

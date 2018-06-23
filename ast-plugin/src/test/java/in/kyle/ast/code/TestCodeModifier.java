package in.kyle.ast.code;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import in.kyle.api.verify.Verify;
import in.kyle.ast.code.file.Field;

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
        Verify.that(modifier.getImportMappings()).containsKeyValue("Test", Collections.singleton("qqq.Test"));
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
}

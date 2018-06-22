package in.kyle.ast.code.file;

import org.junit.Before;
import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.ast.code.JavaFile;
import in.kyle.ast.code.JavaFileType;

public class TestFileHeader {
    
    private JavaFileHeader header;
    
    @Before
    public void setup() {
        header = new JavaFileHeader();
        header.setName("Test");
        header.setType(JavaFileType.CLASS);
    }
    
    @Test
    public void testSimpleHeader() {
        String result = header.write();
        Verify.that(result).isEqual("public class Test");
    }
    
    @Test
    public void testGenericDefine() {
        header.setGenericDefine("T");
        String result = header.write();
        Verify.that(result).isEqual("public class Test<T>");
    }
    
    @Test
    public void testExtendsType() {
        JavaFile superType = new JavaFile("Test2");
        superType.setType(JavaFileType.CLASS);
        header.setSuperType(superType);
        header.setGenericSuper("String");
        String result = header.write();
        Verify.that(result).isEqual("public class Test extends Test2<String>");
    }
    
    @Test
    public void testImplementingType() {
        JavaFile superType = new JavaFile("Test2");
        superType.setType(JavaFileType.INTERFACE);
        header.setSuperType(superType);
        header.setGenericSuper("String");
        String result = header.write();
        Verify.that(result).isEqual("public class Test implements Test2<String>");
    }
    
    @Test
    public void testMultipleImplements() {
        JavaFile superType = new JavaFile("Test2");
        superType.setType(JavaFileType.INTERFACE);
        header.setSuperType(superType);
        header.setGenericSuper("String");
        header.addImplementingType("Test3");
        header.addImplementingType("Test4");
        String result = header.write();
        Verify.that(result).isEqual("public class Test implements Test2<String>, Test4, Test3");
    }
}

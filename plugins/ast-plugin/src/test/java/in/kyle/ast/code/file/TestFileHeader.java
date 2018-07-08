package in.kyle.ast.code.file;

import org.junit.Before;
import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.ast.util.StringTemplate;

public class TestFileHeader {
    
    private JavaFileHeader header;
    
    @Before
    public void setup() {
        header = new JavaFileHeader("Test", JavaFileType.CLASS);
    }
    
    @Test
    public void testSimpleHeader() {
        String result = writeHeader(header);
        Verify.that(result).isEqual("public class Test");
    }
    
    @Test
    public void testGenericDefine() {
        header.setGenericDefine("T");
        String result = writeHeader(header);
        Verify.that(result).isEqual("public class Test<T>");
    }
    
    @Test
    public void testExtendsType() {
        JavaFile superType = new JavaFile("Test2", JavaFileType.CLASS);
        header.setSuperType(superType);
        header.setGenericSuper("String");
        String result = writeHeader(header);
        Verify.that(result).isEqual("public class Test extends Test2<String>");
    }
    
    @Test
    public void testImplementingType() {
        header.addIsType("Test2");
        header.setGenericSuper("String");
        String result = writeHeader(header);
        Verify.that(result).isEqual("public class Test implements Test2");
    }
    
    @Test
    public void testMultipleImplements() {
        header.getIsTypes().add("Test3");
        header.getIsTypes().add("Test4");
        String result = writeHeader(header);
        Verify.that(result).isEqual("public class Test implements Test4, Test3");
    }
    
    @Test
    public void testInterfaceExtends() {
        header.setType(JavaFileType.INTERFACE);
        header.getIsTypes().add("Q");
        String result = writeHeader(header);
        Verify.that(result).isEqual("public interface Test extends Q");
    }
    
    private String writeHeader(JavaFileHeader header) {
        return StringTemplate.render("header/writeHeader", header);
    }
}

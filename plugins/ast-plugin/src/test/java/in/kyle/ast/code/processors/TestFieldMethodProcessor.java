package in.kyle.ast.code.processors;

import org.junit.Test;

import java.io.IOException;

import in.kyle.api.verify.Verify;
import in.kyle.ast.code.file.JavaField;
import in.kyle.ast.code.file.JavaFile;
import in.kyle.ast.code.file.JavaFileType;
import in.kyle.ast.util.ResourceUtils;

public class TestFieldMethodProcessor {
    
    private final FieldMethodProcessor processor = new FieldMethodProcessor();
    
    @Test
    public void test() throws IOException {
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        file.addField(new JavaField("java.util.List", "String", "strings", null));
        processor.process(file);
        
        Verify.that(file.getBodyElements()).sizeIs(1);
        String test = ResourceUtils.loadResource("processors/testCollectionMethods.java");
        Verify.that(test).diffEqual(file.write());
    }
}

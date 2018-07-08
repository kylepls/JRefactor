package in.kyle.ast.code.processors;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.ast.code.file.JavaFile;

import static in.kyle.ast.code.file.JavaFileType.ABSTRACT_CLASS;
import static in.kyle.ast.code.file.JavaFileType.CLASS;
import static in.kyle.ast.code.file.JavaFileType.ENUM;

public class TestChildMethodsProcessor {
    
    private final ChildMethodsProcessor processor = new ChildMethodsProcessor();
    
    @Test
    public void testAddClass() {
        JavaFile file = new JavaFile("Test", CLASS);
        processor.process(file);
        Verify.that(file.getBodyElements()).sizeIs(1);
    }
    
    @Test
    public void testAddAbstractClass() {
        JavaFile file = new JavaFile("Test", ABSTRACT_CLASS);
        processor.process(file);
        Verify.that(file.getBodyElements()).sizeIs(1);        
    }
    
    @Test
    public void testAddEnum() {
        JavaFile file = new JavaFile("Test", ENUM);
        processor.process(file);
        Verify.that(file.getBodyElements()).sizeIs(1);                
    }
}

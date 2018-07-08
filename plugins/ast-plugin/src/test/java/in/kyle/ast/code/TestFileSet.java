package in.kyle.ast.code;

import org.junit.Test;

import java.io.File;

import in.kyle.api.verify.Verify;
import in.kyle.ast.code.file.JavaFile;
import in.kyle.ast.code.file.JavaFileType;

public class TestFileSet {
    
    @Test
    public void testCreateRelPathNoPackage() {
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        String relPath = FileSet.createRelPath(file);
        Verify.that(relPath).isEqual("Test.java");
    }
    
    @Test
    public void testCreateRelPath() {
        JavaFile file = new JavaFile("Test", JavaFileType.CLASS);
        file.setPackageName("in.kyle.test");
        String relPath = FileSet.createRelPath(file);
        String fileString = "in.kyle.test.".replace(".", File.separator) + "Test.java";
        Verify.that(relPath).isEqual(fileString);
    }
}

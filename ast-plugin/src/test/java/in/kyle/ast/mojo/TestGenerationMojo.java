package in.kyle.ast.mojo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import io.takari.maven.testing.TestMavenRuntime;
import io.takari.maven.testing.TestResources;

public class TestGenerationMojo {
    
    @Rule
    public final TestResources resources = new TestResources();
    
    @Rule
    public final TestMavenRuntime maven = new TestMavenRuntime();
    
    private File baseDir;
    private File targetDir;
    
    @Before
    public void setup() throws IOException {
        baseDir = resources.getBasedir("test");
        targetDir = new File(baseDir, "target/generated-sources/ast");
    }
    
    @Test
    public void testMojo() throws Exception {
        maven.executeMojo(baseDir, "generate-ast");
        assertFilesPresent(targetDir, "AnEnum.java");
    }
    
    private void assertFilesPresent(File directory, String... paths) {
        for (String path : paths) {
            File file = new File(directory, path);
            if (!file.exists()) {
                throw new AssertionError("File " + file.getAbsolutePath() + " does not exist");
            }
        }
    }
}

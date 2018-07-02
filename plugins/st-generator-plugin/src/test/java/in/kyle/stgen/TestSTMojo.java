package in.kyle.stgen;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import in.kyle.api.verify.Verify;
import io.takari.maven.testing.TestMavenRuntime;
import io.takari.maven.testing.TestResources;

public class TestSTMojo {
        
    @Rule
    public final TestResources resources = new TestResources();
    
    @Rule
    public final TestMavenRuntime maven = new TestMavenRuntime();
    
    private File baseDir;
    private File targetDir;
    
    @Before
    public void setup() throws IOException {
        baseDir = resources.getBasedir("test");
        targetDir = new File(baseDir, "target/generated-sources/template");
    }
    
    @Test
    public void testMojo() throws Exception {
        maven.executeMojo(baseDir, "generate-template-sources");
        assertFilesPresent(targetDir, "output.properties");
        File outputFile = new File(targetDir, "output.properties");
        Properties properties = new Properties();
        properties.load(new FileReader(outputFile));
    
        Verify.that(properties.getProperty("key1")).isEqual("value1");
        Verify.that(properties.getProperty("key2")).isEqual("value2");
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

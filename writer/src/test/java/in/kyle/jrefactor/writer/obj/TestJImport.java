package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JImport;
import in.kyle.jrefactor.writer.Write;

public class TestJImport {
    
    @Test
    public void test() {
        JImport anImport = new JImport();
        anImport.setName("Test");
        Verify.that(Write.object(anImport)).isEqual("import Test;");
    }
    
    @Test
    public void testPackage() {
        JImport anImport = new JImport();
        anImport.setPackageName("test");
        anImport.setName("Test");
        Verify.that(Write.object(anImport)).isEqual("import test.Test;");
    }
    
    @Test
    public void testStatic() {
        JImport anImport = new JImport();
        anImport.setName("Test");
        anImport.setStaticImport(true);
        Verify.that(Write.object(anImport)).isEqual("import static Test;");
    }
    
    @Test
    public void testWildcard() {
        JImport anImport = new JImport();
        anImport.setPackageName("java.util");
        anImport.setOnDemand(true);
        Verify.that(Write.object(anImport)).isEqual("import java.util.*;");
    }
}

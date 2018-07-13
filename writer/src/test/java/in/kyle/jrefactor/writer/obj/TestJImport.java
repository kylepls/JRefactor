package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JImport;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.writer.Write;

public class TestJImport {
    
    @Test
    public void test() {
        JImport anImport = new JImport();
        anImport.setArea(JPropertyLookup.builder().addAreas("Test").build());
        Verify.that(Write.object(anImport)).isEqual("import Test;");
    }
    
    @Test
    public void testPackage() {
        JImport anImport = new JImport();
        JPropertyLookup lookup = new JPropertyLookup();
        lookup.addArea("test");
        lookup.addArea("Test");
        anImport.setArea(lookup);
        Verify.that(Write.object(anImport)).isEqual("import test.Test;");
    }
    
    @Test
    public void testStatic() {
        JImport anImport = new JImport();
        anImport.setArea(JPropertyLookup.builder().addAreas("Test").build());
        anImport.setStaticImport(true);
        Verify.that(Write.object(anImport)).isEqual("import static Test;");
    }
    
    @Test
    public void testWildcard() {
        JImport anImport = new JImport();
        JPropertyLookup lookup = new JPropertyLookup();
        lookup.addArea("java");
        lookup.addArea("util");
        anImport.setArea(lookup);
        anImport.setOnDemand(true);
        Verify.that(Write.object(anImport)).isEqual("import java.util.*;");
    }
}

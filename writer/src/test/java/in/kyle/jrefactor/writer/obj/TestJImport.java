package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import java.util.Optional;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JImport;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.writer.Write;

public class TestJImport {
    
    @Test
    public void test() {
        JImport anImport = new JImport();
        anImport.setName(new JTypeName("Test"));
        Verify.that(Write.object(anImport)).isEqual("import Test;");
    }
    
    @Test
    public void testPackage() {
        JImport anImport = new JImport();
        JPropertyLookup lookup = new JPropertyLookup();
        lookup.addArea("test");
        anImport.setPackageName(Optional.of(lookup));
        anImport.setName(new JTypeName("Test"));
        Verify.that(Write.object(anImport)).isEqual("import test.Test;");
    }
    
    @Test
    public void testStatic() {
        JImport anImport = new JImport();
        anImport.setName(new JTypeName("Test"));
        anImport.setStaticImport(true);
        Verify.that(Write.object(anImport)).isEqual("import static Test;");
    }
    
    @Test
    public void testWildcard() {
        JImport anImport = new JImport();
        JPropertyLookup lookup = new JPropertyLookup();
        lookup.addArea("java.util");
        anImport.setPackageName(Optional.of(lookup));
        anImport.setOnDemand(true);
        Verify.that(Write.object(anImport)).isEqual("import java.util.*;");
    }
}

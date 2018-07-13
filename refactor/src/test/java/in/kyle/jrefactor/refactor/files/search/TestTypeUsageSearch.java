package in.kyle.jrefactor.refactor.files.search;

import org.junit.Test;

import java.util.List;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.refactor.Projects;
import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.tree.JObj;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.tree.obj.JTypeName;

/**
 * Types can be referenced in the following ways:
 * direct imports
 * wildcard imports
 * fully qualified names
 */
public class TestTypeUsageSearch {
    
    private final JTypeName search = JTypeName.builder()
            .type("File1")
            .area(JPropertyLookup.builder().addAreas("com", "test").build())
            .build();
    
    @Test
    public void testDirectImport() {
        SourceContainer project = Projects.loadProject("files/search/directImport");
        List<JObj> usages = new TypeUsageSearch(project, search).findUsages();
        Verify.that(usages).sizeIs(1);
    }
    
    @Test
    public void testWildcardImport() {
        SourceContainer project = Projects.loadProject("files/search/wildcardImport");
        List<JObj> usages = new TypeUsageSearch(project, search).findUsages();
        Verify.that(usages).sizeIs(1);
    }
    
    @Test
    public void testFullyQualifiedName() {
        SourceContainer project = Projects.loadProject("files/search/fqn");
        List<JObj> usages = new TypeUsageSearch(project, search).findUsages();
        Verify.that(usages).sizeIs(1);
    }
}

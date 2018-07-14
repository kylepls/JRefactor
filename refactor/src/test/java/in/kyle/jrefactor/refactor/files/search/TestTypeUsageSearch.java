package in.kyle.jrefactor.refactor.files.search;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        SourceContainer project = Projects.loadProject("files/search/typeUsageSearch/directImport");
        Stream<JObj> usages = new TypeUsageSearch(project).findUsages(search);
        List<JObj> collect = usages.collect(Collectors.toList());
        Verify.that(collect).sizeIs(1);
    }
    
    @Test
    public void testWildcardImport() {
        SourceContainer project = Projects.loadProject("files/search/typeUsageSearch/wildcardImport");
        Stream<JObj> usages = new TypeUsageSearch(project).findUsages(search);
        List<JObj> collect = usages.collect(Collectors.toList());
        Verify.that(collect).sizeIs(1);
    }
    
    @Test
    public void testFullyQualifiedName() {
        SourceContainer project = Projects.loadProject("files/search/typeUsageSearch/fqn");
        Stream<JObj> usages = new TypeUsageSearch(project).findUsages(search);
        List<JObj> collect = usages.collect(Collectors.toList());
        Verify.that(collect).sizeIs(1);
    }
}

package in.kyle.jrefactor.refactor.files.search;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.refactor.Projects;
import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.refactor.files.resolver.type.JTypeResolver;
import in.kyle.jrefactor.refactor.navigator.ObjectNavigator;
import in.kyle.jrefactor.refactor.util.obj.JPropertyLookupUtils;
import in.kyle.jrefactor.tree.obj.JCompilationUnit;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.tree.obj.JVariableDefinition;
import in.kyle.jrefactor.tree.obj.expression.JExpressionName;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JEnumConstant;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.type.superinterfacetype.JEnum;

public class TestMemberUsageSearch {
    
    @Test
    public void testSingle() {
        SourceContainer project = Projects.loadProject("files/search/memberUsageSearch/singleFile");
        JCompilationUnit unit = project.getAllDefinitions().findFirst().get();
        JVariableDefinition definition =
                new ObjectNavigator(unit).findNextMatch(JVariableDefinition.class).get();
        
        List<JExpressionName> usages =
                new MemberUsageSearch(project).findUsages(definition).collect(Collectors.toList());
        Verify.that(usages).sizeIs(3);
    }
    
    @Test
    public void testOtherFileFqn() {
        SourceContainer project = Projects.loadProject("files/search/memberUsageSearch/otherFileFqn");
        JPropertyLookup lookup = JPropertyLookupUtils.fromStrings("test", "Test");
        
        JEnum type = (JEnum) new JTypeResolver(project).resolve(lookup).get();
        JEnumConstant constant = new ObjectNavigator(type).findNextMatch(JEnumConstant.class).get();
    
        List<JExpressionName> usages =
                new MemberUsageSearch(project).findUsages(constant).collect(Collectors.toList());
        Verify.that(usages).sizeIs(1);
    }
}

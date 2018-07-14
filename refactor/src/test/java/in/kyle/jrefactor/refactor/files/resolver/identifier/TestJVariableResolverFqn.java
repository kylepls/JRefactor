package in.kyle.jrefactor.refactor.files.resolver.identifier;

import org.junit.Test;

import java.util.Optional;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.refactor.Projects;
import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.refactor.util.obj.JPropertyLookupUtils;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JVariableDefinition;
import in.kyle.jrefactor.tree.obj.expression.JExpressionName;

public class TestJVariableResolverFqn {
    
    @Test
    public void test() {
        SourceContainer context = Projects.loadProject("files/resolve/identifier/fqn");
        JExpressionName expressionName = JExpressionName.builder()
                .area(JPropertyLookupUtils.fromStrings("test", "Fqn"))
                .identifier(new JIdentifier("TEST"))
                .build();
        Optional<JVariableDefinition> resolve =
                new JVariableResolverFqn(context).resolve(expressionName);
        Verify.that(resolve).isPresent();
    }
}

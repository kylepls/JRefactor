package in.kyle.jrefactor.refactor.files.search;

import java.util.stream.Stream;

import in.kyle.api.utils.Conditions;
import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.refactor.files.resolver.identifier.JVariableResolver;
import in.kyle.jrefactor.refactor.util.JObjUtilsStreams;
import in.kyle.jrefactor.tree.obj.JCompilationUnit;
import in.kyle.jrefactor.tree.obj.JVariableDefinition;
import in.kyle.jrefactor.tree.obj.expression.JExpressionName;
import lombok.AllArgsConstructor;

import static in.kyle.jrefactor.refactor.util.JObjUtilsStreams.RECURSIVE;

/**
 * Finds where a member is referenced
 */
@AllArgsConstructor
public class MemberUsageSearch implements UsageSearch<JVariableDefinition, JExpressionName> {
    
    private final SourceContainer context;
    
    @Override
    public Stream<JExpressionName> findUsages(JVariableDefinition input) {
        return context.getAllDefinitions().flatMap(unit -> findUsages(unit, input));
    }
    
    private Stream<JExpressionName> findUsages(JCompilationUnit unit, JVariableDefinition input) {
        return JObjUtilsStreams.getChildrenOfType(RECURSIVE, unit, JExpressionName.class)
                .filter(name -> isUsage(unit, name, input));
    }
    
    private boolean isUsage(JCompilationUnit unit,
                            JExpressionName name,
                            JVariableDefinition input) {
        return resolveDefinition(unit, name) == input;
    }
    
    private JVariableDefinition resolveDefinition(JCompilationUnit unit, JExpressionName name) {
        return new JVariableResolver(context, unit).resolve(name)
                .orElseThrow(() -> Conditions.error("Could not find variable definition for {}",
                                                    name));
    }
}

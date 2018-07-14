package in.kyle.jrefactor.refactor.files.resolver.identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.refactor.files.resolver.JResolver;
import in.kyle.jrefactor.refactor.util.JObjUtils;
import in.kyle.jrefactor.tree.obj.JBlock;
import in.kyle.jrefactor.tree.obj.JCompilationUnit;
import in.kyle.jrefactor.tree.obj.JVariableDefinition;
import in.kyle.jrefactor.tree.obj.expression.JExpressionName;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JVariableResolver
        implements JResolver<JExpressionName, Optional<JVariableDefinition>> {
    
    private final SourceContainer context;
    private final JCompilationUnit unit;
    
    @Override
    public Optional<JVariableDefinition> resolve(JExpressionName input) {
        List<JResolver<JExpressionName, Optional<JVariableDefinition>>> strategies =
                new ArrayList<>();
        addStrategies(input, strategies);
        
        for (JResolver<JExpressionName, Optional<JVariableDefinition>> strategy : strategies) {
            Optional<JVariableDefinition> resolve = strategy.resolve(input);
            if (resolve.isPresent()) {
                return resolve;
            }
        }
        return Optional.empty();
    }
    
    private void addStrategies(JExpressionName input,
                               List<JResolver<JExpressionName, Optional<JVariableDefinition>>> 
                                       strategies) {
        if (input.getArea().isPresent()) {
            strategies.add(new JVariableResolverFqn(context));
        } else {
            JBlock block = JObjUtils.getFirstUpwardBlock(unit, input);
            strategies.add(new JVariableResolverBlock(unit, block));
            strategies.add(new JVariableResolverImport(unit, context));
        }
    }
}

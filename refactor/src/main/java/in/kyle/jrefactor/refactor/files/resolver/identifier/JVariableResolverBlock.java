package in.kyle.jrefactor.refactor.files.resolver.identifier;

import java.util.Optional;
import java.util.stream.Stream;

import in.kyle.jrefactor.refactor.files.resolver.JResolver;
import in.kyle.jrefactor.refactor.navigator.ObjectNavigator;
import in.kyle.jrefactor.refactor.util.JObjUtilsStreams;
import in.kyle.jrefactor.tree.JObj;
import in.kyle.jrefactor.tree.obj.JBlock;
import in.kyle.jrefactor.tree.obj.JVariableDefinition;
import in.kyle.jrefactor.tree.obj.expression.JExpressionName;
import lombok.AllArgsConstructor;

import static in.kyle.jrefactor.refactor.util.JObjUtilsStreams.RECURSIVE;

@AllArgsConstructor
public class JVariableResolverBlock
        implements JResolver<JExpressionName, Optional<JVariableDefinition>> {
    
    private final JObj context;
    private final JBlock block;
    
    @Override
    public Optional<JVariableDefinition> resolve(JExpressionName input) {
        ObjectNavigator navigator = new ObjectNavigator(context, block);
        while (navigator.exists()) {
            Optional<JVariableDefinition> any =
                    findVariableInBlock(navigator.get(), input);
            if (any.isPresent()) {
                return any;
            } else {
                navigator.findParentBlock();
            }
        }
        return Optional.empty();
    }
    
    private Optional<JVariableDefinition> findVariableInBlock(JBlock block, JExpressionName input) {
        return getVariableDefinitionsInBlock(block).filter(var -> var.getIdentifier()
                .equals(input.getIdentifier())).findAny();
    }
    
    private Stream<JVariableDefinition> getVariableDefinitionsInBlock(JBlock block) {
        return JObjUtilsStreams.getChildrenOfType(RECURSIVE, block, JVariableDefinition.class);
    }
}

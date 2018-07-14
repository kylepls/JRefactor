package in.kyle.jrefactor.refactor.files.resolver.identifier;

import java.util.Optional;

import in.kyle.api.utils.Conditions;
import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.refactor.files.resolver.JResolver;
import in.kyle.jrefactor.refactor.files.resolver.type.JTypeResolver;
import in.kyle.jrefactor.refactor.navigator.ObjectNavigator;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.tree.obj.JVariableDefinition;
import in.kyle.jrefactor.tree.obj.block.JTypeBody;
import in.kyle.jrefactor.tree.obj.expression.JExpressionName;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JVariableResolverFqn
        implements JResolver<JExpressionName, Optional<JVariableDefinition>> {
    
    private final SourceContainer context;
    
    @Override
    public Optional<JVariableDefinition> resolve(JExpressionName input) {
        JPropertyLookup pl = input.getArea().get();
        JIdentifier identifier = input.getIdentifier();
        
        JType type = new JTypeResolver(context).resolve(pl)
                .orElseThrow(() -> Conditions.error("Could not resolve type from {}", pl));
        return findChild(identifier, type);
    }
    
    private Optional<JVariableDefinition> findChild(JIdentifier identifier, JType type) {
        ObjectNavigator navigator = new ObjectNavigator(type).findChild(JTypeBody.class)
                .findChildMatching(JVariableDefinition.class,
                                   var -> var.getIdentifier().equals(identifier));
        
        return Optional.of(navigator.get());
    }
}

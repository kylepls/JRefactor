package in.kyle.jrefactor.refactor.files.resolver.identifier;

import java.util.Collection;
import java.util.Optional;

import in.kyle.jrefactor.refactor.files.resolver.JResolver;
import in.kyle.jrefactor.tree.JModifier;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JVariableDefinition;
import in.kyle.jrefactor.tree.obj.block.JTypeBody;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.JField;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JEnumConstant;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JType;
import in.kyle.jrefactor.tree.obj.unit.JBodyMember;
import in.kyle.jrefactor.tree.obj.variabledefinition.JVariable;
import lombok.AllArgsConstructor;

/**
 * Finds static variables in the body of a type
 */
@AllArgsConstructor
public class JStaticIdentifierResolver
        implements JResolver<JIdentifier, Optional<JVariableDefinition>> {
    
    private final JType type;
    
    @Override
    public Optional<JVariableDefinition> resolve(JIdentifier input) {
        JTypeBody body = (JTypeBody) type.getBody();
        for (Object o : body.getElements()) {
            JBodyMember element = (JBodyMember) o;
            if (element instanceof JEnumConstant) {
                JEnumConstant constant = (JEnumConstant) element;
                if (constant.getIdentifier().equals(input)) {
                    return Optional.of(constant);
                }
            } else if (element instanceof JField) {
                JField field = (JField) element;
                if (field.getModifiers().contains(JModifier.STATIC)) {
                    Optional<JVariableDefinition> res =
                            resolveVariables(input, field.getVariables());
                    if (res.isPresent()) {
                        return res;
                    }
                }
            }
        }
        
        return Optional.empty();
    }
    
    private Optional<JVariableDefinition> resolveVariables(JIdentifier input,
                                                           Collection<JVariable> variables) {
        return variables.stream()
                .filter(var -> var.getIdentifier().equals(input))
                .map(var -> (JVariableDefinition) var)
                .findAny();
    }
}

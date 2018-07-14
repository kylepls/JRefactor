package in.kyle.jrefactor.refactor.files.resolver.type;

import java.util.Optional;

import in.kyle.api.utils.Conditions;
import in.kyle.jrefactor.refactor.files.resolver.JResolver;
import in.kyle.jrefactor.refactor.navigator.ObjectNavigator;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.tree.obj.block.JTypeBody;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JType;
import lombok.AllArgsConstructor;

/**
 * Resolves nested classes
 */
@AllArgsConstructor
public class JInternalTypeResolver implements JResolver<JPropertyLookup, Optional<JType>> {
    
    private final JType start;
    
    @Override
    public Optional<JType> resolve(JPropertyLookup typeLookup) {
        Conditions.isTrue(typeLookup.getAreas().size() != 0, "Size cannot be 0");
        ObjectNavigator navigator = new ObjectNavigator(start);
        
        for (String area : typeLookup.getAreas().subList(1, typeLookup.getAreas().size())) {
            findType(navigator, area);
            if (!navigator.exists()) {
                break;
            }
        }
        return Optional.ofNullable(navigator.get());
    }
    
    private void findType(ObjectNavigator navigator, String area) {
        navigator.findChild(JTypeBody.class)
                .findChildMatching(JType.class,
                                   type -> type.getIdentifier().getName().equals(area));
    }
}

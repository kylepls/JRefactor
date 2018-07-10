package in.kyle.jrefactor.refactor.files.resolver;

import java.util.Optional;
import java.util.stream.Stream;

import in.kyle.api.utils.Conditions;
import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.refactor.util.JObjUtilsStreams;
import in.kyle.jrefactor.tree.JObj;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JInternalTypeResolver implements JResolver<JPropertyLookup, Optional<JType>> {
    
    private final SourceContainer context;
    private final JPropertyLookup packageName;
    
    @Override
    public Optional<JType> resolve(JPropertyLookup typeLookup) {
        Conditions.isTrue(typeLookup.getAreas().size() != 0, "Size cannot be 0");
        Stream<JType> definitions = context.getDefinitionsInPackage(packageName)
                .flatMap(unit -> unit.getTypes().stream());
        for (int i = 0; i < typeLookup.getAreas().size(); i++) {
            String area = typeLookup.getAreas().get(i);
            definitions = definitions.filter(type -> type.getIdentifier().getName().equals(area));
            if (!definitions.findAny().isPresent()) {
                return Optional.empty();
            } else {
                definitions = definitions.flatMap(this::getInternalTypes);
            }
        }
        
        return definitions.findAny();
    }
    
    private Stream<JType> getInternalTypes(JType type) {
        JObj body = (JObj) type.getBody();
        return JObjUtilsStreams.getDirectChildren(body)
                .filter(obj -> obj.getClass().isAssignableFrom(JType.class))
                .map(obj -> (JType) obj);
    }
}

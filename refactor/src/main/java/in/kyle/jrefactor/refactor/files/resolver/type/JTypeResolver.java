package in.kyle.jrefactor.refactor.files.resolver.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.refactor.files.resolver.JPackageNameResolver;
import in.kyle.jrefactor.refactor.files.resolver.JResolver;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JTypeResolver implements JResolver<JPropertyLookup, Optional<JType>> {
    
    private final SourceContainer context;
    
    /**
     * first try to resolve the packageName as deep as possible
     * then try to resolve the typeName
     */
    @Override
    public Optional<JType> resolve(JPropertyLookup lookup) {
        List<String> remainingAreas = new ArrayList<>(lookup.getAreas());
        
        JPropertyLookup packageName = resolvePackageName(remainingAreas);
        return resolveTypeName(remainingAreas, packageName);
    }
    
    private JPropertyLookup resolvePackageName(List<String> remainingAreas) {
        JPropertyLookup packageGuess = new JPropertyLookup();
        packageGuess.getAreas().addAll(remainingAreas);
        
        return new JPackageNameResolver(context).resolve(packageGuess);
    }
    
    private Optional<JType> resolveTypeName(List<String> remainingAreas,
                                            JPropertyLookup packageName) {
        JPropertyLookup typeLookup = new JPropertyLookup();
        List<String> typeAreas =
                remainingAreas.subList(packageName.getAreas().size(), remainingAreas.size());
        typeLookup.setAreas(typeAreas);
        return new JInternalTypeResolver(context, packageName).resolve(typeLookup);
    }
}

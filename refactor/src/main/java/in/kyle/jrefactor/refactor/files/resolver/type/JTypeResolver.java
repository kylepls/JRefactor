package in.kyle.jrefactor.refactor.files.resolver.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import in.kyle.api.utils.Conditions;
import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.refactor.files.resolver.JPackageNameResolver;
import in.kyle.jrefactor.refactor.files.resolver.JResolver;
import in.kyle.jrefactor.refactor.navigator.SourceNavigator;
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
        
        remainingAreas =
                remainingAreas.subList(packageName.getAreas().size(), remainingAreas.size());
        return resolveTypeName(packageName, remainingAreas);
    }
    
    private JPropertyLookup resolvePackageName(List<String> remainingAreas) {
        JPropertyLookup packageGuess = new JPropertyLookup();
        packageGuess.getAreas().addAll(remainingAreas);
        
        return new JPackageNameResolver(context).resolve(packageGuess);
    }
    
    private Optional<JType> resolveTypeName(JPropertyLookup packageName,
                                            List<String> remainingAreas) {
        
        String typeName = remainingAreas.get(0);
        JType start = getStartFile(packageName, typeName).orElseThrow(() -> Conditions.error(
                "Could not find type {} in {}",
                typeName,
                packageName));
        
        JPropertyLookup pl = JPropertyLookup.builder().addAreas(remainingAreas).build();
        return new JInternalTypeResolver(start).resolve(pl);
    }
    
    private Optional<JType> getStartFile(JPropertyLookup packageName, String typeName) {
        JType type = new SourceNavigator(context, packageName).declaredType(typeName).get();
        return Optional.ofNullable(type);
    }
}

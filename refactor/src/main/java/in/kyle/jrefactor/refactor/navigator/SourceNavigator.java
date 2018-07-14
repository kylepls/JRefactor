package in.kyle.jrefactor.refactor.navigator;

import in.kyle.api.utils.Conditions;
import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class SourceNavigator {
    
    private final SourceContainer context;
    
    @Getter
    private JPropertyLookup packageName = new JPropertyLookup();
    
    public boolean exists() {
        return context.getDefinitionsInPackage(packageName).findAny().isPresent();
    }
    
    public SourceNavigator childPackage(String name) {
        packageName.addArea(name);
        return this;
    }
    
    public SourceNavigator parentPackage() {
        packageName.getAreas().remove(packageName.getAreas().size() - 1);
        return this;
    }
    
    public ObjectNavigator declaredType(String name) {
        JType res = context.getDefinitionsInPackage(packageName)
                .flatMap(unit -> unit.getTypes().stream())
                .filter(type -> type.getIdentifier().getName().equals(name))
                .findAny()
                .orElseThrow(() -> Conditions.error("Could not find type {} in {}",
                                                    name,
                                                    packageName));
        return new ObjectNavigator(res);
    }
}

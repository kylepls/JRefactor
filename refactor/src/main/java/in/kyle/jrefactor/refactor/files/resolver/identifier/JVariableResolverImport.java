package in.kyle.jrefactor.refactor.files.resolver.identifier;

import java.util.Optional;

import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.refactor.files.resolver.JResolver;
import in.kyle.jrefactor.tree.obj.JCompilationUnit;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JImport;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.tree.obj.JVariableDefinition;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JVariableResolverImport
        implements JResolver<JIdentifier, Optional<JVariableDefinition>> {
    
    private final JCompilationUnit unit;
    private final SourceContainer context;
    
    @Override
    public Optional<JVariableDefinition> resolve(JIdentifier input) {
        // oh no...
        
        unit.getImportss().stream().filter(JImport::isStaticImport).map(this::importToLookup)
    }
    
    private JPropertyLookup importToLookup(JImport anImport) {
        JPropertyLookup lookup = new JPropertyLookup();
        anImport.getPackageName().ifPresent(pl -> lookup.setAreas(pl.getAreas()));
        lookup.addArea(anImport.getName().getType());
        if (anImport.isOnDemand()) {
            lookup.addArea("*");
        }
        return lookup;
    }
}

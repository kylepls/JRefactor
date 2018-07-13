package in.kyle.jrefactor.refactor.files.resolver.identifier;

import java.util.List;
import java.util.Optional;

import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.refactor.files.resolver.JResolver;
import in.kyle.jrefactor.refactor.files.resolver.type.JTypeResolver;
import in.kyle.jrefactor.tree.obj.JCompilationUnit;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JImport;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.tree.obj.JVariableDefinition;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JVariableResolverImport
        implements JResolver<JIdentifier, Optional<JVariableDefinition>> {
    
    private final JCompilationUnit unit;
    private final SourceContainer context;
    
    @Override
    public Optional<JVariableDefinition> resolve(JIdentifier input) {
        return unit.getImportss()
                .stream()
                .filter(JImport::isStaticImport)
                .map(anImport -> tryResolve(input, anImport))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findAny();
    }
    
    private Optional<JVariableDefinition> tryResolve(JIdentifier identifier, JImport anImport) {
        List<String> areas = anImport.getArea().getAreas();
        JPropertyLookup lookup = new JPropertyLookup();
        lookup.setAreas(areas.subList(0, areas.size() - 2));
        
        JType type = new JTypeResolver(context).resolve(lookup).get();
        return new JStaticIdentifierResolver(type).resolve(identifier);
    }
}

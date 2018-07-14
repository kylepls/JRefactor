package in.kyle.jrefactor.refactor.files.resolver.identifier;

import java.util.List;
import java.util.Optional;

import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.refactor.files.resolver.JResolver;
import in.kyle.jrefactor.refactor.files.resolver.type.JTypeResolver;
import in.kyle.jrefactor.tree.obj.JCompilationUnit;
import in.kyle.jrefactor.tree.obj.JImport;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.tree.obj.JVariableDefinition;
import in.kyle.jrefactor.tree.obj.expression.JExpressionName;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JType;
import lombok.AllArgsConstructor;

/**
 * Resolves an identifier to a variable definition
 */
@AllArgsConstructor
public class JVariableResolverImport
        implements JResolver<JExpressionName, Optional<JVariableDefinition>> {
    
    private final JCompilationUnit unit;
    private final SourceContainer context;
    
    @Override
    public Optional<JVariableDefinition> resolve(JExpressionName input) {
        return unit.getImportss()
                .stream()
                .filter(JImport::isStaticImport)
                .map(anImport -> tryResolve(input, anImport))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findAny();
    }
    
    private Optional<JVariableDefinition> tryResolve(JExpressionName identifier, JImport anImport) {
        List<String> areas = anImport.getArea().getAreas();
        JPropertyLookup lookup = new JPropertyLookup();
        lookup.setAreas(areas.subList(0, areas.size() - 2));
        
        JType type = new JTypeResolver(context).resolve(lookup).get();
        return new JStaticIdentifierResolver(type).resolve(identifier);
    }
}

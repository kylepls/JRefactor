package in.kyle.jrefactor.refactor.files.resolver.identifier;

import java.util.Optional;

import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.refactor.files.resolver.JResolver;
import in.kyle.jrefactor.tree.obj.JBlock;
import in.kyle.jrefactor.tree.obj.JCompilationUnit;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JVariableDefinition;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JVariableResolver implements JResolver<JIdentifier, Optional<JVariableDefinition>> {
    
    private final SourceContainer context;
    private final JCompilationUnit unit;
    private final JBlock block;
    
    @Override
    public Optional<JVariableDefinition> resolve(JIdentifier input) {
        // check type
        Optional<JVariableDefinition> resolve =
                new JVariableResolverBlock(unit, block).resolve(input);
        if (resolve.isPresent()) {
            return resolve;
        } else {
            return new JVariableResolverImport(unit, context).resolve(input);
        }
    }
}

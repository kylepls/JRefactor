package in.kyle.jrefactor.refactor.files.resolver.identifier;

import java.util.Optional;
import java.util.stream.Stream;

import in.kyle.jrefactor.refactor.files.resolver.JResolver;
import in.kyle.jrefactor.refactor.util.JObjUtils;
import in.kyle.jrefactor.refactor.util.JObjUtilsStreams;
import in.kyle.jrefactor.tree.obj.JBlock;
import in.kyle.jrefactor.tree.obj.JCompilationUnit;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JVariableDefinition;
import lombok.AllArgsConstructor;

import static in.kyle.jrefactor.refactor.util.JObjUtilsStreams.DIRECT;

@AllArgsConstructor
public class JVariableResolverBlock implements JResolver<JIdentifier, Optional<JVariableDefinition>> {
    
    private final JCompilationUnit unit;
    private final JBlock block;
    
    @Override
    public Optional<JVariableDefinition> resolve(JIdentifier input) {
        Stream<JVariableDefinition> stream;
        do {
            stream = JObjUtilsStreams.getChildrenOfType(DIRECT, block, JVariableDefinition.class)
                    .filter(var -> var.getIdentifier().equals(input));
            JObjUtils.getFirstUpwardBlock(unit, block);
        } while (!hasIdentifier(stream) && block != null);
        return stream.findAny();
    }
    
    private boolean hasIdentifier(Stream<JVariableDefinition> stream) {
        return stream.findAny().isPresent();
    }
}

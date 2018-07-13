package in.kyle.jrefactor.refactor.files.resolver.method;

import java.util.List;
import java.util.Optional;

import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.tree.obj.JExpression;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.tree.obj.unit.bodymember.typemember.enummember.classmember.JMethod;
import lombok.AllArgsConstructor;

/**
 * Resolves a method from a name + args
 */
@AllArgsConstructor
public class JMethodResolver {
    
    private final SourceContainer context;
    private final JPropertyLookup name;
    private final List<JExpression> args;
    
    public Optional<JMethod> resolve() {
        // TODO: 7/11/2018  
        return null;
    }
}

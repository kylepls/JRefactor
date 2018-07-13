package in.kyle.jrefactor.refactor.files.search;

import java.util.Collection;

import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.tree.obj.JVariableDefinition;
import in.kyle.jrefactor.tree.obj.expression.JExpressionName;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JType;
import lombok.AllArgsConstructor;

/**
 * Finds where a member is referenced
 * These are the following cases:
 *  static import of member and directly named
 *  fully qualified name of type
 *  
 */
@AllArgsConstructor
public class MemberUsageSearch {
    
    private final SourceContainer context;
    private final JType declaringType;
    private final JVariableDefinition input;
    
    public Collection<JExpressionName> findUsages() {
//        context.getAllDefinitions().
        return null;
    }
    
//    private Stream<JExpressionName> findUsages(JCompilationUnit unit) {
//        JObjUtilsStreams.getChildrenOfType(RECURSIVE, unit, JExpressionName.class).filter(name->name.)
//    }
//    
//    private boolean isUsage(JCompilationUnit unit, JExpressionName name) {
//        
//    }
}

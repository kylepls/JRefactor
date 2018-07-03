package in.kyle.jrefactor.refactor.symbol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.kyle.jrefactor.refactor.JObjUtils;
import in.kyle.jrefactor.tree.JObj;
import in.kyle.jrefactor.tree.obj.JBlock;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UnitSymbolTable {
    
    private final JObj root;
    private final Map<JBlock, BlockScope> scopes = new HashMap<>();
    
    public void compute() {
        scopes.clear();
        new IdentifierListener(this, root).enter(root);
    }
    
    public BlockScope getScope(JObj obj) {
        if (obj instanceof JBlock) {
            return getScope((JBlock) obj);
        } else {
            return getScope(getScopeForObj(obj));
        }
    }
    
    private BlockScope getScope(JBlock block) {
        BlockScope scope = scopes.get(block);
        if (scope == null) {
            scope = new BlockScope();
            scopes.put(block, scope);
        }
        return scope;
    }
    
    private JBlock getScopeForObj(JObj obj) {
        JBlock search = JObjUtils.getFirstUpwardBlock(root, obj);
        while (!scopes.containsKey(search)) {
            search = JObjUtils.getFirstUpwardBlock(root, search);
        }
        return search;
    }
    
    public List<JIdentifier> getUses(JIdentifier identifier) {
        JBlock declaringScope = getScopeForObj(identifier);
        UseListener listener = new UseListener(identifier, this);
        listener.enter(declaringScope);
        return listener.getIdentifiers();
    }
}

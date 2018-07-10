package in.kyle.jrefactor.refactor.symbol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.kyle.jrefactor.refactor.util.JObjUtils;
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
        IdentifierListener listener = new IdentifierListener();
        listener.enter(root);
        for (IdentifierListener.Declaration declaration : listener.getDeclarations()) {
            getScope(declaration.getObject()).addIdentifier(declaration.getIdentifier());
        }
    }
    
    public BlockScope getScope(JObj obj) {
        JBlock block;
        if (obj instanceof JBlock) {
            block = (JBlock) obj;
        } else {
            block = JObjUtils.getFirstUpwardBlock(root, obj);
        }
        return getOrCreateScope(block);
    }
    
    private BlockScope getOrCreateScope(JBlock block) {
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
        UseListener listener = new UseListener(identifier, this);
        JBlock declaringScope = getScopeForObj(identifier);
        listener.enter(declaringScope);
        return listener.getIdentifiers();
    }
}

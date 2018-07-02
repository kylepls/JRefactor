package in.kyle.jrefactor.refactor.symbol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.kyle.jrefactor.refactor.JObjUtils;
import in.kyle.jrefactor.tree.JObj;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.statement.JBlock;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SymbolTable {
    
    private final JObj root;
    private final Map<JBlock, Scope> scopes = new HashMap<>();
    
    public void compute() {
        scopes.clear();
        new IdentifierListener(this, root).enter(root);
    }
    
    public JBlock getDeclaringScope(JIdentifier identifier) {
        JBlock search = JObjUtils.getFirstUpwardBlock(root, identifier);
        while (!scopes.containsKey(search)) {
            search = JObjUtils.getFirstUpwardBlock(root, search);
        }
        return search;
    }
    
    public Scope getScope(JBlock block) {
        Scope scope = scopes.get(block);
        if (scope == null) {
            scope = new Scope();
            scopes.put(block, scope);
        }
        return scope;
    }
    
    public List<JIdentifier> getUses(JIdentifier identifier) {
        JBlock declaringScope = getDeclaringScope(identifier);
        UseListener listener = new UseListener(identifier, this);
        listener.enter(declaringScope);
        return listener.getIdentifiers();
    }
}

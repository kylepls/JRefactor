package in.kyle.jrefactor.refactor.symbol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.statement.JBlock;
import in.kyle.jrefactor.parser.unit.JIdentifier;
import in.kyle.jrefactor.refactor.JObjectUtils;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SymbolTable {
    
    private final JObject root;
    private final Map<JBlock, Scope> scopes = new HashMap<>();
    
    public void compute() {
        scopes.clear();
        new IdentifierVisitor(this, root).visit(root);
    }
    
    public JBlock getDeclaringScope(JIdentifier identifier) {
        JBlock search = JObjectUtils.getFirstUpwardBlock(root, identifier);
        while (!scopes.containsKey(search)) {
            search = JObjectUtils.getFirstUpwardBlock(root, search);
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
    
    /*
        class Main {
            int i = 0;
            void test(int i) { // rename to j
                i++;
            }
        }
        
        ->
        
        class Main {
            int i = 0;
            void test(int j) {
                j++;
            }
        }
            
     */
}

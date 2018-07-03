package in.kyle.jrefactor.refactor.symbol;

import java.util.ArrayList;
import java.util.List;

import in.kyle.jrefactor.tree.obj.JIdentifier;
import lombok.Data;

@Data
class BlockScope {
    private final List<JIdentifier> declaredIdentifiers = new ArrayList<>();
    
    public void addIdentifier(JIdentifier identifier) {
        declaredIdentifiers.add(identifier);
    }
    
    public boolean containsDeclaration(JIdentifier identifier) {
        return declaredIdentifiers.contains(identifier);
    }
}

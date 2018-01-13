package in.kyle.jrefactor.refactor.symbol;

import java.util.ArrayList;
import java.util.List;

import in.kyle.jrefactor.tree.statement.JBlock;
import in.kyle.jrefactor.tree.unit.JIdentifier;
import in.kyle.jrefactor.refactor.JavaBaseListener;
import lombok.Data;

@Data
public class UseListener extends JavaBaseListener {
    
    private final JIdentifier identity;
    private final SymbolTable table;
    private final List<JIdentifier> identifiers = new ArrayList<>();
    private boolean foundFirstUse;
    
    @Override
    public void enterJBlock(JBlock object) {
        Scope scope = table.getScope(object);
        boolean conflict = scope.containsDeclaration(identity);
        if (!foundFirstUse && conflict) {
            foundFirstUse = true;
            super.enterJBlock(object);
        } else if (!scope.containsDeclaration(identity)) {
            super.enterJBlock(object);
        }
    }
    
    @Override
    public void enterJIdentifier(JIdentifier object) {
        if (object.getName().equals(identity.getName())) {
            identifiers.add(object);
        }
    }
}

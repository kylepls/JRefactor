package in.kyle.jrefactor.refactor.symbol;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.statement.JBlock;
import in.kyle.jrefactor.parser.statement.JLocalVariableDeclaration;
import in.kyle.jrefactor.parser.unit.body.JMethod;
import in.kyle.jrefactor.parser.unit.body.JParameter;
import in.kyle.jrefactor.parser.unit.body.JVariable;
import in.kyle.jrefactor.parser.unit.types.classtype.JField;
import in.kyle.jrefactor.refactor.JObjectUtils;
import in.kyle.jrefactor.refactor.JavaBaseListener;
import lombok.Data;

@Data
class IdentifierListener extends JavaBaseListener {

    private final SymbolTable symbolTable;
    private final JObject root;
    
    @Override
    public void enterJMethod(JMethod object) {
        Scope scope = symbolTable.getScope(object.getBody());
        for (JParameter parameter : object.getHeader().getParameterList().getParameters()) {
            scope.addIdentifier(parameter.getIdentifier());
        }
        super.enterJMethod(object);
    }
    
    @Override
    public void enterJLocalVariableDeclaration(JLocalVariableDeclaration object) {
        JBlock block = JObjectUtils.getFirstUpwardBlock(root, object);
        Scope scope = symbolTable.getScope(block);
        for (JVariable variable : object.getVariables()) {
            scope.addIdentifier(variable.getIdentifier());
        }
    }
    
    @Override
    public void enterJField(JField object) {
        JBlock block = JObjectUtils.getFirstUpwardBlock(root, object);
        Scope scope = symbolTable.getScope(block);
        for (JVariable variable : object.getVariables()) {
            scope.addIdentifier(variable.getIdentifier());
        }
    }
}

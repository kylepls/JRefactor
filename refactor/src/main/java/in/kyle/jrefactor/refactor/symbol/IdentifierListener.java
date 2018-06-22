package in.kyle.jrefactor.refactor.symbol;

import in.kyle.jrefactor.tree.JObject;
import in.kyle.jrefactor.tree.statement.JBlock;
import in.kyle.jrefactor.tree.statement.JLocalVariableDeclaration;
import in.kyle.jrefactor.tree.unit.JTypeDeclaration;
import in.kyle.jrefactor.tree.unit.body.JMethod;
import in.kyle.jrefactor.tree.unit.body.JParameter;
import in.kyle.jrefactor.tree.unit.body.JVariable;
import in.kyle.jrefactor.tree.unit.types.classtype.JField;
import in.kyle.jrefactor.refactor.JObjectUtils;
import in.kyle.jrefactor.refactor.JavaBaseListener;
import in.kyle.jrefactor.tree.unit.types.enumtype.JEnumConstant;
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
    
    @Override
    public void enterJTypeDeclaration(JTypeDeclaration object) {
        JBlock block = JObjectUtils.getFirstUpwardBlock(root, object);
        Scope scope = symbolTable.getScope(block);
        scope.addIdentifier(object.getIdentifier());
    }
    
    @Override
    public void enterJEnumConstant(JEnumConstant object) {
        JBlock block = JObjectUtils.getFirstUpwardBlock(root, object);
        Scope scope = symbolTable.getScope(block);
        scope.addIdentifier(object.getIdentifier());
    }
}

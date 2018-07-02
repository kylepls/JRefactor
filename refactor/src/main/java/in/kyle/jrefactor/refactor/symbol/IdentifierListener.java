package in.kyle.jrefactor.refactor.symbol;

import in.kyle.jrefactor.refactor.JObjUtils;
import in.kyle.jrefactor.refactor.JavaBaseListener;
import in.kyle.jrefactor.tree.JObj;
import in.kyle.jrefactor.tree.obj.JVariable;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.JField;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.JParameter;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JEnumConstant;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JType;
import in.kyle.jrefactor.tree.obj.statement.JBlock;
import in.kyle.jrefactor.tree.obj.statement.JStatementLocalVariableDeclaration;
import in.kyle.jrefactor.tree.obj.unit.bodymember.typemember.enummember.classmember.JMethod;
import lombok.Data;

@Data
class IdentifierListener extends JavaBaseListener {

    private final SymbolTable symbolTable;
    private final JObj root;
    
    @Override
    public void enterJMethod(JMethod object) {
        Scope scope = symbolTable.getScope(object.getBody());
        for (JParameter parameter : object.getHeader().getParameters()) {
            scope.addIdentifier(parameter.getName());
        }
        super.enterJMethod(object);
    }
    
    @Override
    public void enterJStatementLocalVariableDeclaration(JStatementLocalVariableDeclaration object) {
        JBlock block = JObjUtils.getFirstUpwardBlock(root, object);
        Scope scope = symbolTable.getScope(block);
        for (JVariable variable : object.getVariables()) {
            scope.addIdentifier(variable.getName());
        }
    }
    
    @Override
    public void enterJField(JField object) {
        JBlock block = JObjUtils.getFirstUpwardBlock(root, object);
        Scope scope = symbolTable.getScope(block);
        for (JVariable variable : object.getVariables()) {
            scope.addIdentifier(variable.getName());
        }
    }
    
    @Override
    public void enterJType(JType object) {
        JBlock block = JObjUtils.getFirstUpwardBlock(root, object);
        Scope scope = symbolTable.getScope(block);
        scope.addIdentifier(object.getIdentifier());
    }
    
    @Override
    public void enterJEnumConstant(JEnumConstant object) {
        JBlock block = JObjUtils.getFirstUpwardBlock(root, object);
        Scope scope = symbolTable.getScope(block);
        scope.addIdentifier(object.getIdentifier());
    }
}

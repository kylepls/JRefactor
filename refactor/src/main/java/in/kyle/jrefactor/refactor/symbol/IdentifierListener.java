package in.kyle.jrefactor.refactor.symbol;

import in.kyle.jrefactor.refactor.JObjUtils;
import in.kyle.jrefactor.refactor.JavaBaseListener;
import in.kyle.jrefactor.tree.JObj;
import in.kyle.jrefactor.tree.obj.JBlock;
import in.kyle.jrefactor.tree.obj.JVariable;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.JField;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.JParameter;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JEnumConstant;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JMethodHeader;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JType;
import in.kyle.jrefactor.tree.obj.statement.JStatementLocalVariableDeclaration;
import lombok.Data;

@Data
class IdentifierListener extends JavaBaseListener {

    private final UnitSymbolTable table;
    private final JObj root;
    
    @Override
    public void enterJMethodHeader(JMethodHeader object) {
        BlockScope scope = table.getScope(object);
        for (JParameter parameter : object.getParameters()) {
            scope.addIdentifier(parameter.getName());
        }
        super.enterJMethodHeader(object);
    }
    
    @Override
    public void enterJStatementLocalVariableDeclaration(JStatementLocalVariableDeclaration object) {
        BlockScope scope = table.getScope(object);
        for (JVariable variable : object.getVariables()) {
            scope.addIdentifier(variable.getName());
        }
    }
    
    @Override
    public void enterJField(JField object) {
        BlockScope scope = table.getScope(object);
        for (JVariable variable : object.getVariables()) {
            scope.addIdentifier(variable.getName());
        }
    }
    
    @Override
    public void enterJType(JType object) {
        JBlock block = JObjUtils.getFirstUpwardBlock(root, object);
        BlockScope scope = table.getScope(block);
        scope.addIdentifier(object.getIdentifier());
    }
    
    @Override
    public void enterJEnumConstant(JEnumConstant object) {
        BlockScope scope = table.getScope(object);
        scope.addIdentifier(object.getIdentifier());
    }
}

package in.kyle.jrefactor.refactor.symbol;

import java.util.ArrayList;
import java.util.List;

import in.kyle.jrefactor.refactor.JavaBaseListener;
import in.kyle.jrefactor.tree.JObj;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JVariable;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.JField;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.JParameter;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JEnumConstant;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JType;
import in.kyle.jrefactor.tree.obj.statement.JStatementLocalVariableDeclaration;
import in.kyle.jrefactor.tree.obj.unit.bodymember.typemember.enummember.classmember.JMethod;
import lombok.Data;
import lombok.Getter;

class IdentifierListener extends JavaBaseListener {

    @Getter
    private final List<Declaration> declarations = new ArrayList<>();
    
    @Override
    public void enterJMethod(JMethod object) {
        for (JParameter parameter : object.getHeader().getParameters()) {
            addDeclaration(object, parameter.getName());
        }
        enterJBlock(object.getBody());
    }
    
    @Override
    public void enterJStatementLocalVariableDeclaration(JStatementLocalVariableDeclaration object) {
        for (JVariable variable : object.getVariables()) {
            addDeclaration(object, variable.getName());
        }
    }
    
    @Override
    public void enterJField(JField object) {
        for (JVariable variable : object.getVariables()) {
            addDeclaration(object, variable.getName());
        }
    }
    
    @Override
    public void enterJType(JType object) {
        addDeclaration(object, object.getIdentifier());
    }
    
    @Override
    public void enterJEnumConstant(JEnumConstant object) {
        addDeclaration(object, object.getIdentifier());
    }
    
    private void addDeclaration(JObj object, JIdentifier identifier) {
        Declaration declaration = new Declaration(object, identifier);
        declarations.add(declaration);
    }
    
    @Data
    public static class Declaration {
        private final JObj object;
        private final JIdentifier identifier;
    }
}

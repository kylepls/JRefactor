package in.kyle.jrefactor.refactor.symbol;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.statement.JBlock;
import in.kyle.jrefactor.parser.statement.JLocalVariableDeclaration;
import in.kyle.jrefactor.parser.unit.body.JMethod;
import in.kyle.jrefactor.parser.unit.body.JParameter;
import in.kyle.jrefactor.parser.unit.body.JVariable;
import in.kyle.jrefactor.parser.unit.body.classtype.JField;
import in.kyle.jrefactor.refactor.JObjectUtils;
import in.kyle.jrefactor.refactor.JavaBaseVisitor;
import lombok.Data;

@Data
class IdentifierVisitor extends JavaBaseVisitor {

    private final SymbolTable symbolTable;
    private final JObject root;

    @Override
    public Object visitJMethod(JMethod object) {
        Scope scope = symbolTable.getScope(object.getBody());
        for (JParameter parameter : object.getHeader().getParameterList().getParameters()) {
            scope.addIdentifier(parameter.getIdentifier());
        }
        return super.visitJMethod(object);
    }

    @Override
    public Object visitJLocalVariableDeclaration(JLocalVariableDeclaration object) {
        JBlock block = JObjectUtils.getFirstUpwardBlock(root, object);
        Scope scope = symbolTable.getScope(block);
        for (JVariable variable : object.getVariables()) {
            scope.addIdentifier(variable.getIdentifier());
        }
        return null;
    }

    @Override
    public Object visitJField(JField object) {
        JBlock block = JObjectUtils.getFirstUpwardBlock(root, object);
        Scope scope = symbolTable.getScope(block);
        for (JVariable variable : object.getVariables()) {
            scope.addIdentifier(variable.getIdentifier());
        }
        return null;
    }
}

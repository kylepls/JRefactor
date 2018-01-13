package in.kyle.jrefactor.tree.statement.control.loops;

import in.kyle.jrefactor.tree.expression.JExpression;
import in.kyle.jrefactor.tree.statement.JStatement;
import in.kyle.jrefactor.tree.unit.JIdentifier;
import in.kyle.jrefactor.tree.unit.JModifierList;
import in.kyle.jrefactor.tree.unit.JTypeName;
import lombok.Data;

@Data
public class JEnhancedForStatement implements JLoopStatement {
    
    private JModifierList modifiers = new JModifierList();
    private JTypeName variableType;
    private JIdentifier variableName;
    private JExpression expression;
    private JStatement statement;
    
}

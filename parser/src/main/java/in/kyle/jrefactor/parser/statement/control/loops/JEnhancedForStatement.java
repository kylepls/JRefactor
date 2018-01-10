package in.kyle.jrefactor.parser.statement.control.loops;

import in.kyle.jrefactor.parser.expression.JExpression;
import in.kyle.jrefactor.parser.statement.JStatement;
import in.kyle.jrefactor.parser.unit.JIdentifier;
import in.kyle.jrefactor.parser.unit.JModifierList;
import in.kyle.jrefactor.parser.unit.JTypeName;
import lombok.Data;

@Data
public class JEnhancedForStatement implements JLoopStatement {
    
    private JModifierList modifiers = new JModifierList();
    private JTypeName variableType;
    private JIdentifier variableName;
    private JExpression expression;
    private JStatement statement;
    
}

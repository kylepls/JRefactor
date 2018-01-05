package in.kyle.jrefactor.parser.statement.control.loops;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.expression.JExpression;
import in.kyle.jrefactor.parser.statement.JStatement;
import in.kyle.jrefactor.parser.unit.JIdentifier;
import in.kyle.jrefactor.parser.unit.JTypeName;
import in.kyle.jrefactor.parser.unit.ModifierList;
import in.kyle.jrefactor.CodeWriter;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Delegate;

@Data
public class JEnhancedForStatement implements JLoopStatement {
    
    @Delegate(excludes = JObject.class)
    @Getter(value = AccessLevel.PRIVATE)
    private final ModifierList set = new ModifierList();
    
    private JTypeName variableType;
    private JIdentifier variableName;
    private JExpression expression;
    private JStatement statement;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("for ({} {}: {}) {}", variableType, variableName, expression, statement);
    }
}

package in.kyle.parser.statement.control.loops;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.expression.JExpression;
import in.kyle.parser.statement.JStatement;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.parser.unit.JIdentifier;
import in.kyle.parser.unit.JTypeName;
import in.kyle.parser.unit.ModifierSet;
import in.kyle.writer.CodeWriter;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Delegate;

@Data
public class JEnhancedForStatement implements JLoopStatement {
    
    @Delegate
    @Getter(value = AccessLevel.PRIVATE)
    private final ModifierSet set = new ModifierSet();
    
    private JTypeName variableType;
    private JIdentifier variableName;
    private JExpression expression;
    private JStatement statement;
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(variableName, expression, statement);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("for ({} {}: {}) {}", variableType, variableName, expression, statement);
    }
}

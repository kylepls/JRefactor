package in.kyle.parser.expression;

import in.kyle.parser.unit.JTypeName;
import in.kyle.writer.CodeWriter;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JTypeReferenceExpression implements JExpression {
    
    private JTypeName reference;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("{}.class", reference);
    }
}

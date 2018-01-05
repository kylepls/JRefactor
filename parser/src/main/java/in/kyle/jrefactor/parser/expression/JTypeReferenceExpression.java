package in.kyle.jrefactor.parser.expression;

import in.kyle.jrefactor.parser.unit.JTypeName;
import in.kyle.jrefactor.CodeWriter;
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

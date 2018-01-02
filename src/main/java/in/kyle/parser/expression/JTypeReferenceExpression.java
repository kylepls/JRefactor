package in.kyle.parser.expression;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.parser.unit.JTypeName;
import in.kyle.writer.CodeWriter;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JTypeReferenceExpression implements JExpression {
    
    private JTypeName reference;
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(reference);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("{}.class", reference);
    }
}

package in.kyle.parser.expression;

import java.util.List;

import in.kyle.parser.RewriteableField;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.parser.unit.JTypeName;
import in.kyle.writer.CodeWriter;

public class JTypeReferenceExpression implements JExpression {
    
    private final RewriteableField<JTypeName> reference = new RewriteableField<>();
    
    public JTypeReferenceExpression(JTypeName reference) {
        setReference(reference);
    }
    
    public void setReference(JTypeName reference) {
        this.reference.setValue(reference);
    }
    
    public JTypeName getReference() {
        return reference.getValue();
    }
    
    @Override
    public List<RewriteableField> getChildren() {
        return CollectionUtils.createList(reference);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("{}.class", reference);
    }
}

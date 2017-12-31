package in.kyle.parser.unit;

import java.util.Collections;
import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.RewriteableField;
import in.kyle.writer.CodeWriter;

public class JAnnotation implements JObject {
    
    private RewriteableField<JTypeName> type = new RewriteableField<>();
    
    public void setType(JTypeName type) {
        this.type.setValue(type);
    }
    
    public JTypeName getType() {
        return type.getValue();
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("@{}", type);
    }
    
    @Override
    public List<RewriteableField> getChildren() {
        return Collections.singletonList(type);
    }
}

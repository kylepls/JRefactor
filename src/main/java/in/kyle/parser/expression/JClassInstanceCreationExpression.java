package in.kyle.parser.expression;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.RewriteableField;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.parser.unit.JArgumentList;
import in.kyle.parser.unit.JClassBody;
import in.kyle.parser.unit.JTypeName;
import in.kyle.parser.unit.TypeArgumentList;
import in.kyle.writer.CodeWriter;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
public class JClassInstanceCreationExpression implements JExpression {
    
    private final RewriteableField<JTypeName> type = new RewriteableField<>();
    private final RewriteableField<JClassBody> body = new RewriteableField<>();
    @Delegate(excludes = JObject.class)
    private JArgumentList argumentList = new JArgumentList();
    @Delegate(excludes = JObject.class)
    private TypeArgumentList typeArgumentList = new TypeArgumentList();
    
    public JClassInstanceCreationExpression(JTypeName type) {
        this.type.setValue(type);
    }
    
    public void setBody(JClassBody body) {
        this.body.setValue(body);
    }
    
    public JClassBody getBody() {
        return body.getValue();
    }
    
    @Override
    public List<RewriteableField> getChildren() {
        return CollectionUtils.createList(body,
                                          argumentList.getChildren(),
                                          typeArgumentList.getChildren());
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("new {}", type);
        typeArgumentList.writeTypeArguments(writer);
        writer.append("(");
        argumentList.write(writer);
        writer.append(")");
        body.ifPresent(writer::append);
    }
}

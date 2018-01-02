package in.kyle.parser.expression;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.parser.unit.body.JArgumentList;
import in.kyle.parser.unit.body.classtype.JClassBody;
import in.kyle.parser.unit.JTypeName;
import in.kyle.parser.unit.TypeArgumentList;
import in.kyle.writer.CodeWriter;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
public class JClassInstanceCreationExpression implements JExpression {
    
    private JTypeName type;
    private JClassBody body;
    @Delegate(excludes = JObject.class)
    private JArgumentList argumentList = new JArgumentList();
    @Delegate(excludes = JObject.class)
    private TypeArgumentList typeArgumentList = new TypeArgumentList();
    
    public JClassInstanceCreationExpression(JTypeName type) {
        this.type = type;
    }
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(body,
                                          argumentList.getArguments(),
                                          typeArgumentList.getChildren());
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("new {}", type);
        typeArgumentList.writeTypeArguments(writer);
        writer.append("(");
        argumentList.write(writer);
        writer.append(")");
        if (body != null) {
            writer.append(body);
        }
    }
}

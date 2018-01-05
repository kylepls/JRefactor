package in.kyle.jrefactor.parser.expression;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.unit.body.JArgumentList;
import in.kyle.jrefactor.parser.unit.body.classtype.JClassBody;
import in.kyle.jrefactor.parser.unit.JTypeName;
import in.kyle.jrefactor.parser.unit.JTypeArgumentList;
import in.kyle.jrefactor.CodeWriter;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
public class JClassInstanceCreationExpression implements JExpression {
    
    private JTypeName type;
    private JClassBody body;
    @Delegate(excludes = JObject.class)
    private JArgumentList argumentList = new JArgumentList();
    @Delegate(excludes = JObject.class)
    private JTypeArgumentList JTypeArgumentList = new JTypeArgumentList();
    
    public JClassInstanceCreationExpression(JTypeName type) {
        this.type = type;
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("new {}", type);
        JTypeArgumentList.writeTypeArguments(writer);
        writer.append("(");
        argumentList.write(writer);
        writer.append(")");
        if (body != null) {
            writer.append(body);
        }
    }
}

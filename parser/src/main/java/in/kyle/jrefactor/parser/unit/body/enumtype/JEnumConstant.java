package in.kyle.jrefactor.parser.unit.body.enumtype;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.unit.AnnotationBase;
import in.kyle.jrefactor.parser.unit.JIdentifier;
import in.kyle.jrefactor.parser.unit.body.JArgumentList;
import in.kyle.jrefactor.parser.unit.body.classtype.JClassBody;
import in.kyle.jrefactor.CodeWriter;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Delegate;

@Data
public class JEnumConstant extends AnnotationBase {
    
    private JIdentifier name;
    @Getter(value = AccessLevel.NONE)
    @Delegate(excludes = JObject.class)
    private JArgumentList argumentList = new JArgumentList();
    private JClassBody body;
    
    public JEnumConstant(JIdentifier name) {
        this.name = name;
    }
    
    @Override
    public void write(CodeWriter writer) {
        writeAnnotations(writer);
        writer.append("{}({})", name, argumentList);
        if (body != null) {
            writer.append(" {}", body);
        }
    }
}

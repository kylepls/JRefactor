package in.kyle.parser.unit.types;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.statement.JStatement;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.parser.unit.JType;
import in.kyle.parser.unit.JTypeName;
import in.kyle.parser.unit.JTypeParameterList;
import in.kyle.parser.unit.body.annotationtype.JAnnotationMember;
import in.kyle.parser.unit.body.classtype.JClassBody;
import in.kyle.parser.unit.body.classtype.JClassMember;
import in.kyle.parser.unit.body.interfacetype.JInterfaceMember;
import in.kyle.writer.CodeWriter;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Delegate;

@Data
public class JClassDeclaration extends JType<JClassBody>
        implements JStatement, JInterfaceMember, JAnnotationMember, JClassMember {
    
    @Delegate(excludes = JObject.class)
    @Getter(value = AccessLevel.NONE)
    private final JTypeParameterList typeParameterList = new JTypeParameterList();
    
    private JTypeName extendsType;
    
    @Getter(value = AccessLevel.NONE)
    @Delegate(excludes = JObject.class)
    private final SuperInterfaceList superInterfaceList = new SuperInterfaceList();
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(super.getChildren(),
                                          extendsType,
                                          superInterfaceList.getSuperInterfaces(),
                                          getBody());
    }
    
    @Override
    public void write(CodeWriter writer) {
        writeModifiers(writer);
        writer.append("class ").append(getIdentifier());
        writeTypeParameters(writer);
        writeExtendsString(writer);
        superInterfaceList.write(writer);
        writer.append(getBody());
    }
    
    private void writeExtendsString(CodeWriter writer) {
        if (extendsType != null) {
            writer.append("extends ").append(extendsType);
        }
    }
}

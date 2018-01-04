package in.kyle.parser.unit.types;

import in.kyle.parser.JObject;
import in.kyle.parser.statement.JStatement;
import in.kyle.parser.unit.JTypeDeclaration;
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
public class JClassDeclaration extends JTypeDeclaration<JClassBody>
        implements JStatement, JInterfaceMember, JAnnotationMember, JClassMember {
    
    @Delegate(excludes = JObject.class)
    @Getter(value = AccessLevel.NONE)
    private final JTypeParameterList typeParameterList = new JTypeParameterList();
    
    private JTypeName extendsType;
    
    @Getter(value = AccessLevel.NONE)
    @Delegate(excludes = JObject.class)
    private final SuperInterfaceList superInterfaceList = new SuperInterfaceList();
    
    @Override
    public void write(CodeWriter writer) {
        super.write(writer);
        writer.append("class ").append(getIdentifier());
        writer.append(typeParameterList);
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

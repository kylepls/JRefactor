package in.kyle.jrefactor.parser.unit.types;

import java.util.Optional;

import in.kyle.jrefactor.parser.statement.JStatement;
import in.kyle.jrefactor.parser.unit.JTypeDeclaration;
import in.kyle.jrefactor.parser.unit.JTypeName;
import in.kyle.jrefactor.parser.unit.JTypeParameterList;
import in.kyle.jrefactor.parser.unit.types.annotationtype.JAnnotationMember;
import in.kyle.jrefactor.parser.unit.types.classtype.JClassBody;
import in.kyle.jrefactor.parser.unit.types.classtype.JClassMember;
import in.kyle.jrefactor.parser.unit.types.interfacetype.JInterfaceMember;
import lombok.Data;

@Data
public class JClassDeclaration extends JTypeDeclaration<JClassBody>
        implements JStatement, JInterfaceMember, JAnnotationMember, JClassMember {
    
    private JTypeParameterList typeParameters = new JTypeParameterList();
    private JSuperInterfaceList superInterfaces = new JSuperInterfaceList();
    private Optional<JTypeName> extendsType = Optional.empty();
    
}

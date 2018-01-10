package in.kyle.jrefactor.parser.unit.types;

import in.kyle.jrefactor.parser.unit.JTypeDeclaration;
import in.kyle.jrefactor.parser.unit.JTypeParameterList;
import in.kyle.jrefactor.parser.unit.types.annotationtype.JAnnotationMember;
import in.kyle.jrefactor.parser.unit.types.classtype.JClassMember;
import in.kyle.jrefactor.parser.unit.types.interfacetype.JInterfaceBody;
import in.kyle.jrefactor.parser.unit.types.interfacetype.JInterfaceMember;
import lombok.Data;

@Data
public class JInterfaceDeclaration extends JTypeDeclaration<JInterfaceBody>
        implements JInterfaceMember, JAnnotationMember, JClassMember {
    
    private JTypeParameterList typeParameters = new JTypeParameterList();
    private JSuperInterfaceList superInterfaces = new JSuperInterfaceList();
    
}

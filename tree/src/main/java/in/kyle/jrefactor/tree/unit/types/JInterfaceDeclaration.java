package in.kyle.jrefactor.tree.unit.types;

import in.kyle.jrefactor.tree.unit.JTypeDeclaration;
import in.kyle.jrefactor.tree.unit.JTypeParameterList;
import in.kyle.jrefactor.tree.unit.types.annotationtype.JAnnotationMember;
import in.kyle.jrefactor.tree.unit.types.classtype.JClassMember;
import in.kyle.jrefactor.tree.unit.types.interfacetype.JInterfaceBody;
import in.kyle.jrefactor.tree.unit.types.interfacetype.JInterfaceMember;
import lombok.Data;

@Data
public class JInterfaceDeclaration extends JTypeDeclaration<JInterfaceBody>
        implements JInterfaceMember, JAnnotationMember, JClassMember {
    
    private JTypeParameterList typeParameters = new JTypeParameterList();
    private JSuperInterfaceList superInterfaces = new JSuperInterfaceList();
    
}

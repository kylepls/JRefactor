package in.kyle.jrefactor.tree.unit.types;

import java.util.Optional;

import in.kyle.jrefactor.tree.statement.JStatement;
import in.kyle.jrefactor.tree.unit.JTypeDeclaration;
import in.kyle.jrefactor.tree.unit.JTypeName;
import in.kyle.jrefactor.tree.unit.JTypeParameterList;
import in.kyle.jrefactor.tree.unit.types.annotationtype.JAnnotationMember;
import in.kyle.jrefactor.tree.unit.types.classtype.JClassBody;
import in.kyle.jrefactor.tree.unit.types.classtype.JClassMember;
import in.kyle.jrefactor.tree.unit.types.interfacetype.JInterfaceMember;
import lombok.Data;

@Data
public class JClassDeclaration extends JTypeDeclaration<JClassBody>
        implements JStatement, JInterfaceMember, JAnnotationMember, JClassMember {
    
    private JTypeParameterList typeParameters = new JTypeParameterList();
    private JSuperInterfaceList superInterfaces = new JSuperInterfaceList();
    private Optional<JTypeName> extendsType = Optional.empty();
    
}

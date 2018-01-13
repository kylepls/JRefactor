package in.kyle.jrefactor.tree.unit.types;

import in.kyle.jrefactor.tree.unit.JTypeDeclaration;
import in.kyle.jrefactor.tree.unit.types.enumtype.JEnumBody;
import lombok.Data;

@Data
public class JEnumDeclaration extends JTypeDeclaration<JEnumBody> {
    
    private JSuperInterfaceList superInterfaces = new JSuperInterfaceList();
    
}

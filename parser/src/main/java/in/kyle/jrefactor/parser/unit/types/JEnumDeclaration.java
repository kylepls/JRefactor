package in.kyle.jrefactor.parser.unit.types;

import in.kyle.jrefactor.parser.unit.JTypeDeclaration;
import in.kyle.jrefactor.parser.unit.types.enumtype.JEnumBody;
import lombok.Data;

@Data
public class JEnumDeclaration extends JTypeDeclaration<JEnumBody> {
    
    private JSuperInterfaceList superInterfaces = new JSuperInterfaceList();
    
}

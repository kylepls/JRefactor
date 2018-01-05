package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.parser.unit.body.classtype.JClassMember;
import in.kyle.jrefactor.parser.unit.body.JTypeBody;
import lombok.Data;

@Data
public abstract class JTypeDeclaration<T extends JTypeBody> extends Modifiable implements
                                                                               JClassMember {
    
    private JIdentifier identifier;
    private T body;
    
}

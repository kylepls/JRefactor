package in.kyle.jrefactor.tree.unit.types.classtype;

import in.kyle.jrefactor.tree.statement.JBlock;
import lombok.Data;

@Data
public abstract class JClassInitializer implements JClassMember {
    
    private JBlock block;
    
}

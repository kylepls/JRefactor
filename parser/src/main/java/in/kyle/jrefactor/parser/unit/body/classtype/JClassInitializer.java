package in.kyle.jrefactor.parser.unit.body.classtype;

import in.kyle.jrefactor.parser.statement.JBlock;
import lombok.Data;

@Data
public abstract class JClassInitializer implements JClassMember {
    
    private JBlock block;
    
}

package in.kyle.parser.unit.body.classtype;

import in.kyle.parser.statement.JBlock;
import lombok.Data;

@Data
public abstract class JClassInitializer implements JClassMember {
    
    private JBlock block;
    
}

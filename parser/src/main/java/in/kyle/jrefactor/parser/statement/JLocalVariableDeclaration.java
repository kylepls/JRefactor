package in.kyle.jrefactor.parser.statement;

import in.kyle.jrefactor.parser.unit.JAnnotatable;
import in.kyle.jrefactor.parser.unit.JAnnotationList;
import in.kyle.jrefactor.parser.unit.body.VariableHolder;
import lombok.Data;

@Data
public class JLocalVariableDeclaration extends VariableHolder implements JAnnotatable, JStatement {
    
    private JAnnotationList annotations = new JAnnotationList();
    
}

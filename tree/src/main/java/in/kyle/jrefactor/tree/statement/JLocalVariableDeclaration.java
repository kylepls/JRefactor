package in.kyle.jrefactor.tree.statement;

import in.kyle.jrefactor.tree.unit.JAnnotatable;
import in.kyle.jrefactor.tree.unit.JAnnotationList;
import in.kyle.jrefactor.tree.unit.body.VariableHolder;
import lombok.Data;

@Data
public class JLocalVariableDeclaration extends VariableHolder implements JAnnotatable, JStatement {
    
    private JAnnotationList annotations = new JAnnotationList();
    
}

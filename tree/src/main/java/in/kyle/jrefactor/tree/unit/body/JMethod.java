package in.kyle.jrefactor.tree.unit.body;

import in.kyle.jrefactor.tree.statement.JBlock;
import in.kyle.jrefactor.tree.unit.types.classtype.JClassMember;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JMethod implements JClassMember {
    
    private JMethodHeader header;
    private JBlock body;
    
}

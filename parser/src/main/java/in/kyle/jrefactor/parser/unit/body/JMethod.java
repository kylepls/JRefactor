package in.kyle.jrefactor.parser.unit.body;

import in.kyle.jrefactor.parser.statement.JBlock;
import in.kyle.jrefactor.parser.unit.types.classtype.JClassMember;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JMethod implements JClassMember {
    
    private JMethodHeader header;
    private JBlock body;
    
}

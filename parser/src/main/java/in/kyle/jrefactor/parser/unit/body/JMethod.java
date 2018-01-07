package in.kyle.jrefactor.parser.unit.body;

import in.kyle.jrefactor.parser.statement.JBlock;
import in.kyle.jrefactor.parser.unit.body.classtype.JClassMember;
import in.kyle.jrefactor.CodeWriter;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JMethod implements JClassMember {
    
    private JMethodHeader header;
    private JBlock body;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append(header).append(body);
    }
}

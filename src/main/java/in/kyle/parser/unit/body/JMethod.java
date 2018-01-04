package in.kyle.parser.unit.body;

import in.kyle.parser.statement.JBlock;
import in.kyle.parser.unit.body.classtype.JClassMember;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JMethod implements JClassMember {
    
    private JMethodHeader header;
    private JBlock body;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append(header).append(body);
    }
}

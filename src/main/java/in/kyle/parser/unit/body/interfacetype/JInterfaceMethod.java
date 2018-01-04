package in.kyle.parser.unit.body.interfacetype;

import in.kyle.parser.statement.JBlock;
import in.kyle.parser.unit.body.JMethodHeader;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JInterfaceMethod implements JInterfaceMember {
    
    private JMethodHeader header;
    private JBlock body;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append(header).append(body);
    }
    
}

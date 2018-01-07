package in.kyle.jrefactor.parser.unit.body.interfacetype;

import in.kyle.jrefactor.parser.statement.JBlock;
import in.kyle.jrefactor.parser.unit.body.JMethodHeader;
import in.kyle.jrefactor.CodeWriter;
import lombok.Data;

@Data
public class JInterfaceMethod implements JInterfaceMember {
    
    private JMethodHeader header;
    private JBlock body;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append(header);
        if (body != null) {
            writer.append(" default {}", body);
        } else {
            writer.append(";");
        }
    }
}

package in.kyle.parser.unit.body.interfacetype;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.statement.JBlock;
import in.kyle.parser.unit.CollectionUtils;
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
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(header, body);
    }
}

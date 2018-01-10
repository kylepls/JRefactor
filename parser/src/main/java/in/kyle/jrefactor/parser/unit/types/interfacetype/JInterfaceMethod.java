package in.kyle.jrefactor.parser.unit.types.interfacetype;

import java.util.Optional;

import in.kyle.jrefactor.parser.statement.JBlock;
import in.kyle.jrefactor.parser.unit.body.JMethodHeader;
import lombok.Data;

@Data
public class JInterfaceMethod implements JInterfaceMember {
    
    private JMethodHeader header;
    private Optional<JBlock> body = Optional.empty();
    
}

package in.kyle.jrefactor.tree.unit.types.interfacetype;

import java.util.Optional;

import in.kyle.jrefactor.tree.statement.JBlock;
import in.kyle.jrefactor.tree.unit.body.JMethodHeader;
import lombok.Data;

@Data
public class JInterfaceMethod implements JInterfaceMember {
    
    private JMethodHeader header;
    private Optional<JBlock> body = Optional.empty();
    
}

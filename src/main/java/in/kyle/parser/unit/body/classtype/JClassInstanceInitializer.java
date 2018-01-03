package in.kyle.parser.unit.body.classtype;

import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JClassInstanceInitializer extends JClassInitializer {
    
    @Override
    public void write(CodeWriter writer) {
        writer.append(getBlock());
    }
}

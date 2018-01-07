package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.CodeWriter;
import lombok.Data;

@Data
public class JArrayTypeName extends JTypeName {
    
    private int dimensions;
    
    public JArrayTypeName(String name, int dimensions) {
        super(name);
        this.dimensions = dimensions;
    }
    
    @Override
    public void write(CodeWriter writer) {
        super.write(writer);
        for (int i = 0; i < dimensions; i++) {
            writer.append("[]");
        }
    }
}

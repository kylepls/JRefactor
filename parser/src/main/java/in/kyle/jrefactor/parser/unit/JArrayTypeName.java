package in.kyle.jrefactor.parser.unit;

import lombok.Data;

@Data
public class JArrayTypeName extends JTypeName {
    
    private int dimensions;
    
    public JArrayTypeName(String name, int dimensions) {
        super(name);
        this.dimensions = dimensions;
    }
}

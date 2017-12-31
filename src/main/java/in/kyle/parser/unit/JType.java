package in.kyle.parser.unit;

import lombok.Data;

@Data
public abstract class JType extends JTypeable implements JClassMember {
    
    private String name;
    
}

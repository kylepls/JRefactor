package in.kyle.parser.unit;

import lombok.Data;

@Data
public abstract class JType extends Typeable implements JClassMember {
    
    private String name;
    
}

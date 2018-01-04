package in.kyle.parser.unit;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Delegate;

@Data
public abstract class Modifiable extends AnnotationBase {
   
    @Getter(value = AccessLevel.PRIVATE)
    @Delegate
    private final ModifierList set = new ModifierList();
    
}

package in.kyle.parser.unit;

import java.util.List;

import in.kyle.parser.JObject;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Delegate;

@Data
public abstract class Modifiable extends AnnotationBase {
   
    @Getter(value = AccessLevel.PRIVATE)
    @Delegate
    private final ModifierSet set = new ModifierSet();
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(super.getChildren(), set.getModifiers());
    }
}

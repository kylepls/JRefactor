package in.kyle.parser.unit;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.RewriteableField;
import lombok.experimental.Delegate;

public abstract class Typeable extends Modifiable {
    
    @Delegate(excludes = JObject.class)
    private final JTypeParameterList typeParameterList = new JTypeParameterList();
    
    @Override
    public List<RewriteableField> getChildren() {
        return CollectionUtils.createList(super.getChildren(), typeParameterList.getChildren());
    }
}

package in.kyle.parser.unit.body.classtype;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.statement.JBlock;
import in.kyle.parser.unit.CollectionUtils;
import lombok.Data;

@Data
public abstract class JClassInitializer implements JClassMember {
    
    private JBlock block;
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(block);
    }
}

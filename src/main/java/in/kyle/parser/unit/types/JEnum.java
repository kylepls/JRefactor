package in.kyle.parser.unit.types;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.parser.unit.JType;
import in.kyle.parser.unit.body.enumtype.JEnumBody;
import in.kyle.writer.CodeWriter;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Delegate;

@Data
public class JEnum extends JType<JEnumBody> {
    
    @Getter(value = AccessLevel.NONE)
    @Delegate(excludes = JObject.class)
    private final SuperInterfaceList superInterfaceList = new SuperInterfaceList();
    
    @Override
    public void write(CodeWriter writer) {
        writeModifiers(writer);
        writer.append("enum ").append(getName());
        superInterfaceList.write(writer);
        writer.append(getBody());
    }
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(super.getChildren(), superInterfaceList);
    }
}

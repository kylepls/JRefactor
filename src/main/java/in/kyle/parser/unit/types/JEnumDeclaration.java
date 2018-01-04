package in.kyle.parser.unit.types;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.JTypeDeclaration;
import in.kyle.parser.unit.body.enumtype.JEnumBody;
import in.kyle.writer.CodeWriter;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Delegate;

@Data
public class JEnumDeclaration extends JTypeDeclaration<JEnumBody> {
    
    @Getter(value = AccessLevel.NONE)
    @Delegate(excludes = JObject.class)
    private final SuperInterfaceList superInterfaceList = new SuperInterfaceList();
    
    @Override
    public void write(CodeWriter writer) {
        super.write(writer);
        writer.append("enum ").append(getIdentifier());
        superInterfaceList.write(writer);
        writer.append(getBody());
    }
    
}

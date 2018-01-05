package in.kyle.jrefactor.parser.unit.types;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.unit.JTypeDeclaration;
import in.kyle.jrefactor.parser.unit.body.enumtype.JEnumBody;
import in.kyle.jrefactor.CodeWriter;
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

package in.kyle.parser.unit.body.interfacetype;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.JType;
import in.kyle.parser.unit.JTypeParameterList;
import in.kyle.parser.unit.types.SuperInterfaceList;
import in.kyle.writer.CodeWriter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Delegate;

public class JInterface extends JType<JInterfaceBody> implements JInterfaceMember {
    
    @Delegate(excludes = JObject.class)
    @Getter(value = AccessLevel.NONE)
    private final JTypeParameterList typeParameterList = new JTypeParameterList();
    
    @Getter(value = AccessLevel.NONE)
    @Delegate(excludes = JObject.class)
    private final SuperInterfaceList superInterfaceList = new SuperInterfaceList();
    
    @Override
    public void write(CodeWriter writer) {
        
    }
}

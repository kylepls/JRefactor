package in.kyle.jrefactor.parser.unit.types;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.unit.JTypeDeclaration;
import in.kyle.jrefactor.parser.unit.JTypeParameterList;
import in.kyle.jrefactor.parser.unit.body.annotationtype.JAnnotationMember;
import in.kyle.jrefactor.parser.unit.body.classtype.JClassMember;
import in.kyle.jrefactor.parser.unit.body.interfacetype.JInterfaceBody;
import in.kyle.jrefactor.parser.unit.body.interfacetype.JInterfaceMember;
import in.kyle.jrefactor.CodeWriter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Delegate;

public class JInterfaceDeclaration extends JTypeDeclaration<JInterfaceBody>
        implements JInterfaceMember, JAnnotationMember, JClassMember {
    
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

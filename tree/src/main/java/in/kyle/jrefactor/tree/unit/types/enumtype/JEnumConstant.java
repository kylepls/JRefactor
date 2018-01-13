package in.kyle.jrefactor.tree.unit.types.enumtype;

import java.util.Optional;

import in.kyle.jrefactor.tree.unit.JAnnotatable;
import in.kyle.jrefactor.tree.unit.JAnnotationList;
import in.kyle.jrefactor.tree.unit.JIdentifier;
import in.kyle.jrefactor.tree.unit.body.JArgumentList;
import in.kyle.jrefactor.tree.unit.types.classtype.JClassBody;
import lombok.Data;

@Data
public class JEnumConstant implements JAnnotatable {
    
    private JAnnotationList annotations = new JAnnotationList();
    private JArgumentList argumentList = new JArgumentList();
    private JIdentifier name;
    private Optional<JClassBody> body = Optional.empty();
    
    public JEnumConstant(JIdentifier name) {
        this.name = name;
    }
}

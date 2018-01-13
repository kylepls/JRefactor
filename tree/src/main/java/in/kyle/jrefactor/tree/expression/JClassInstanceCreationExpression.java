package in.kyle.jrefactor.tree.expression;

import java.util.Optional;

import in.kyle.jrefactor.tree.unit.JTypeArgumentList;
import in.kyle.jrefactor.tree.unit.JTypeName;
import in.kyle.jrefactor.tree.unit.body.JArgumentList;
import in.kyle.jrefactor.tree.unit.types.classtype.JClassBody;
import lombok.Data;

@Data
public class JClassInstanceCreationExpression implements JExpression {
    
    private JTypeName type;
    private Optional<JClassBody> body = Optional.empty();
    private JArgumentList arguments = new JArgumentList();
    private JTypeArgumentList typeArguments = new JTypeArgumentList();
    
    public JClassInstanceCreationExpression(JTypeName type) {
        this.type = type;
    }
}

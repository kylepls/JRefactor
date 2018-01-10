package in.kyle.jrefactor.parser.expression;

import java.util.Optional;

import in.kyle.jrefactor.parser.unit.JIdentifier;
import in.kyle.jrefactor.parser.unit.JTypeArgumentList;
import in.kyle.jrefactor.parser.unit.body.JArgumentList;
import lombok.Data;

@Data
public class JMethodInvocation implements JExpression {
    
    private JTypeArgumentList typeArguments = new JTypeArgumentList();
    private JArgumentList arguments = new JArgumentList();
    private Optional<String> methodArea = Optional.empty(); // TODO: 1/7/2018  
    private JIdentifier identifier;
    
    public JMethodInvocation(JIdentifier identifier) {
        this.identifier = identifier;
    }
}

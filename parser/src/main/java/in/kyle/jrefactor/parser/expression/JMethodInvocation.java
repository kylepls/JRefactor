package in.kyle.jrefactor.parser.expression;

import in.kyle.jrefactor.CodeWriter;
import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.unit.JTypeParameterList;
import lombok.experimental.Delegate;

public class JMethodInvocation implements JExpression {
    
    @Delegate(excludes = JObject.class)
    private final JTypeParameterList typeParameterList = new JTypeParameterList();
    
    @Override
    public void write(CodeWriter writer) {
        
    }
    
}

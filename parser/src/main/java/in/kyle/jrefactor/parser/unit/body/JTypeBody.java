package in.kyle.jrefactor.parser.unit.body;

import in.kyle.jrefactor.parser.JObject;
import lombok.Data;

@Data
public class JTypeBody<T extends JMember> implements JObject {
    
    private JMemberList<T> members = new JMemberList<>();
    
}

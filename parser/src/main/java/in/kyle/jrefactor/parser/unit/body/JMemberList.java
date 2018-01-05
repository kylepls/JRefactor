package in.kyle.jrefactor.parser.unit.body;

import in.kyle.jrefactor.CodeWriter;
import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.JObjectList;
import in.kyle.jrefactor.parser.unit.body.classtype.JField;
import lombok.Data;

@Data
public class JMemberList<T extends JMember> implements JObject {
    
    private JObjectList<T> members = new JObjectList<>();
    
    public boolean addMember(T member) {
        return members.add(member);
    }
    
    public boolean removeMember(T member) {
        return members.remove(member);
    }
    
    @Override
    public void write(CodeWriter writer) {
        for (T member : members) {
            if (member instanceof JField) {
                writer.appendLine("{};", member);
            } else {
                writer.appendLine(member);
            }
        }
    }
}

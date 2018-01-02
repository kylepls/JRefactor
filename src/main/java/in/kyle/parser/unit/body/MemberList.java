package in.kyle.parser.unit.body;

import java.util.LinkedHashSet;
import java.util.Set;

import in.kyle.parser.unit.body.classtype.JField;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class MemberList<T extends JMember> {
    
    private Set<T> members = new LinkedHashSet<>();
    
    public boolean addMember(T member) {
        return members.add(member);
    }
    
    public boolean removeMember(T member) {
        return members.remove(member);
    }
    
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

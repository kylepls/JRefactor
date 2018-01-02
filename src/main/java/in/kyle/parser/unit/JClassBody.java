package in.kyle.parser.unit;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.JObject;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JClassBody implements JObject {
    
    private final Set<JClassMember> members = new LinkedHashSet<>();
    
    public boolean addMember(JClassMember member) {
        return members.add(member);
    }
    
    public boolean removeMember(JClassMember member) {
        return members.remove(member);
    }
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(members);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.appendLine(" {");
        writer.indent();
        writeMembers(writer);
        writer.dedent();
        writer.appendLine("}");
    }
    
    private void writeMembers(CodeWriter writer) {
        for (JClassMember member : members) {
            if (member instanceof JField) {
                writer.appendLine("{};", member);
            } else {
                writer.appendLine(member);
            }
        }
    }
}

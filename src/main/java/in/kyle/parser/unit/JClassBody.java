package in.kyle.parser.unit;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.JObject;
import in.kyle.parser.RewriteableField;
import in.kyle.writer.CodeWriter;

public class JClassBody implements JObject {
    
    private final Set<RewriteableField<JClassMember>> members = new LinkedHashSet<>();
    
    public boolean addMember(JClassMember member) {
        return CollectionUtils.addValue(members, member);
    }
    
    public boolean removeMember(JClassMember member) {
        return CollectionUtils.removeValue(members, member);
    }
    
    public Set<JClassMember> getMembers() {
        return CollectionUtils.convertToJObjectSet(members);
    }
    
    public void setMembers(Set<JClassMember> members) {
        CollectionUtils.overwrite(this.members, members);
    }
    
    @Override
    public List<RewriteableField> getChildren() {
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
        for (RewriteableField<JClassMember> member : members) {
            if (member.getValue() instanceof JField) {
                writer.appendLine("{};", member);
            } else {
                writer.appendLine(member);
            }
        }
    }
}

package in.kyle.parser.unit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.RewriteableField;
import in.kyle.writer.CodeWriter;
import lombok.ToString;

@ToString
public class JClass extends JType {
    
    private final RewriteableField<JTypeName> extendsType = new RewriteableField<>();
    private final Set<RewriteableField<JTypeName>> implementsTypes = new LinkedHashSet<>();
    private final Set<RewriteableField<JClassMember>> members = new LinkedHashSet<>();
    
    public JTypeName getExtendsType() {
        return extendsType.getValue();
    }
    
    public void setExtendsType(JTypeName type) {
        this.extendsType.setValue(type);
    }
    
    public boolean addImplementingType(JTypeName typeName) {
        return CollectionUtils.addValue(implementsTypes, typeName);
    }
    
    public boolean removeImplementingType(JTypeName typeName) {
        return CollectionUtils.removeValue(implementsTypes, typeName);
    }
    
    public boolean addMember(JClassMember member) {
        return CollectionUtils.addValue(members, member);
    }
    
    public boolean removeMember(JClassMember member) {
        return CollectionUtils.removeValue(members, member);
    }
    
    @Override
    public List<RewriteableField> getChildren() {
        List<RewriteableField> list = new ArrayList<>(getRewriteableAnnotations());
        list.add(extendsType);
        list.addAll(implementsTypes);
        list.addAll(members);
        return list;
    }
    
    @Override
    public void write(CodeWriter writer) {
        writeModifiers(writer);
        writer.append("class ").append(getName());
        writeTypeParameters(writer);
        writeExtendsString(writer);
        writeImplementsString(writer);
        writer.appendLine(" {");
        writer.indent();
        writeMembersString(writer);
        writer.dedent();
        writer.appendLine("}");
    }
    
    private void writeExtendsString(CodeWriter writer) {
        extendsType.ifPresent(type -> writer.append("extends ").append(type));
    }
    
    private void writeImplementsString(CodeWriter writer) {
        if (!implementsTypes.isEmpty()) {
            writer.append("implements ");
            for (Iterator<RewriteableField<JTypeName>> iterator =
                 implementsTypes.iterator(); iterator.hasNext(); ) {
                JTypeName type = iterator.next().getValue();
                writer.append(type);
                if (iterator.hasNext()) {
                    writer.append(", ");
                }
            }
        }
    }
    
    private void writeMembersString(CodeWriter writer) {
        if (!members.isEmpty()) {
            
            for (RewriteableField<JClassMember> member : members) {
                writer.appendLine(member.getValue());
            }
        }
    }
}

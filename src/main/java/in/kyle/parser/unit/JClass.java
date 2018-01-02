package in.kyle.parser.unit;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.JObject;
import in.kyle.parser.statement.JStatement;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JClass extends JType implements JStatement {
    
    private JTypeName extendsType;
    private Set<JTypeName> implementsTypes = new LinkedHashSet<>();
    private JClassBody body;
    
    public boolean addImplementingType(JTypeName type) {
        return implementsTypes.add(type);
    }
    
    public boolean removeImplementingType(JTypeName type) {
        return implementsTypes.remove(type);
    }
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(super.getChildren(), extendsType, implementsTypes, body);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writeModifiers(writer);
        writer.append("class ").append(getName());
        writeTypeParameters(writer);
        writeExtendsString(writer);
        writeImplementsString(writer);
        writer.append(body);
    }
    
    private void writeExtendsString(CodeWriter writer) {
        if (extendsType != null) {
            writer.append("extends ").append(extendsType);
        }
    }
    
    private void writeImplementsString(CodeWriter writer) {
        if (!implementsTypes.isEmpty()) {
            writer.append("implements ");
            for (Iterator<JTypeName> iterator = implementsTypes.iterator(); iterator.hasNext(); ) {
                JTypeName type = iterator.next();
                writer.append(type);
                if (iterator.hasNext()) {
                    writer.append(", ");
                }
            }
        }
    }
}

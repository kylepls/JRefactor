package in.kyle.parser.unit;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.RewriteableField;
import in.kyle.parser.statement.JBlockStatement;
import in.kyle.writer.CodeWriter;
import lombok.ToString;

@ToString
public class JClass extends JType implements JBlockStatement {
    
    private final RewriteableField<JTypeName> extendsType = new RewriteableField<>();
    private final Set<RewriteableField<JTypeName>> implementsTypes = new LinkedHashSet<>();
    private final RewriteableField<JClassBody> body = new RewriteableField<>();
    
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
    
    public void setBody(JClassBody body) {
        this.body.setValue(body);
    }
    
    public JClassBody getBody() {
        return body.getValue();
    }
    
    
    @Override
    public List<RewriteableField> getChildren() {
        return CollectionUtils.createList(super.getChildren(),
                                          extendsType,
                                          implementsTypes,
                                          body);
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
}

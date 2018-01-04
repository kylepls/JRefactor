package in.kyle.parser.statement.control;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import in.kyle.parser.JObject;
import in.kyle.parser.statement.JBlock;
import in.kyle.parser.unit.JIdentifier;
import in.kyle.parser.unit.JTypeName;
import in.kyle.parser.unit.ModifierList;
import in.kyle.writer.CodeWriter;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Delegate;

@Data
public class JCatchClause implements JObject {
    
    @Delegate(excludes = JObject.class)
    @Getter(value = AccessLevel.NONE)
    private final ModifierList set = new ModifierList();
    private Set<JTypeName> catchTypes = new LinkedHashSet<>();
    private JIdentifier variable;
    private JBlock block;
    
    public boolean addCatchType(JTypeName type) {
        return catchTypes.add(type);
    }
    
    public boolean removeCatchType(JTypeName type) {
        return catchTypes.remove(type);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("catch (");
        for (Iterator<JTypeName> iterator = catchTypes.iterator(); iterator.hasNext(); ) {
            JTypeName catchType = iterator.next();
            writer.append(catchType);
            if (iterator.hasNext()) {
                writer.append(" | ");
            }
        }
        writer.append(" {}) {}", variable, block);
    }
}

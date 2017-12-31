package in.kyle.parser.unit;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.RewriteableField;
import in.kyle.writer.CodeWriter;

public class TypeArgumentList {
    
    private final Set<RewriteableField<JTypeArgument>> typeArguments = new LinkedHashSet<>();
    
    public boolean addTypeArguement(JTypeArgument argument) {
        return CollectionUtils.addValue(typeArguments, argument);
    }
    
    public boolean removeTypeArguement(JTypeArgument argument) {
        return CollectionUtils.removeValue(typeArguments, argument);
    }
    
    public Set<JTypeArgument> getTypeArguments() {
        return CollectionUtils.convertToJObjectSet(typeArguments);
    }
    
    public void setTypeArguments(Set<JTypeArgument> arguments) {
        CollectionUtils.overwrite(this.typeArguments, arguments);
    }
    
    public List<RewriteableField> getChildren() {
        return CollectionUtils.createList(typeArguments);
    }
    
    public void writeTypeArguments(CodeWriter writer) {
        for (Iterator<RewriteableField<JTypeArgument>> iterator =
             typeArguments.iterator(); iterator.hasNext(); ) {
            RewriteableField<JTypeArgument> argument = iterator.next();
            writer.append(argument);
            if (iterator.hasNext()) {
                writer.append(", ");
            }
        }
    }
}

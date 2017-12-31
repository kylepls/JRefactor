package in.kyle.parser.unit;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.RewriteableField;
import in.kyle.parser.expression.JExpression;
import in.kyle.writer.CodeWriter;

public class JArgumentList {
    
    private final Set<RewriteableField<JExpression>> arguments = new LinkedHashSet<>();
    
    public void setArguments(Set<JExpression> set) {
        CollectionUtils.overwrite(arguments, set);
    }
    
    public Set<JExpression> getArguments() {
        return CollectionUtils.convertToJObjectSet(arguments);
    }
    
    public boolean addArguement(JExpression arg) {
        return CollectionUtils.addValue(arguments, arg);
    }
    
    public boolean removeArguement(JExpression arg) {
        return CollectionUtils.removeValue(arguments, arg);
    }
    
    public void write(CodeWriter writer) {
        for (Iterator<RewriteableField<JExpression>> iterator =
             arguments.iterator(); iterator.hasNext(); ) {
            RewriteableField<JExpression> argument = iterator.next();
            writer.append(argument);
            if (iterator.hasNext()) {
                writer.append(", ");
            }
        }
    }
    
    public List<RewriteableField> getChildren() {
        return CollectionUtils.createList(arguments);
    }
}

package in.kyle.scanner;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.RewriteableField;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RefactorSession {
    
    private JObject root;
    
    public JObject findParent(JObject subject) {
        return findParent(root, subject);
    }
    
    private JObject findParent(JObject search, JObject subject) {
        if (search != null) {
            if (isChild(search, subject)) {
                return search;
            } else {
                List<RewriteableField> children = search.getChildren();
                for (RewriteableField child : children) {
                    JObject result = findParent(child.getValue(), subject);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }
        return null;
    }
    
    private <T extends JObject> RewriteableField<T> findField(JObject parent, T child) {
        for (RewriteableField field : parent.getChildren()) {
            if (field.getValue() == child) {
                return field;
            }
        }
        throw new RuntimeException("Field not found");
    }
    
    public boolean isChild(JObject parent, JObject child) {
        if (parent != null) {
            for (RewriteableField jObject : parent.getChildren()) {
                if (jObject.getValue() == child) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public <T extends JObject> void replace(T subject, T replacement) {
        JObject parent = findParent(subject);
        RewriteableField<T> field = findField(parent, subject);
        field.setValue(replacement);
    }
}

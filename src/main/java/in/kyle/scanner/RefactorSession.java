package in.kyle.scanner;

import java.lang.reflect.Field;
import java.util.List;

import in.kyle.api.utils.Try;
import in.kyle.parser.JObject;
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
                List<JObject> children = search.getChildren();
                for (JObject child : children) {
                    JObject result = findParent(child, subject);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }
        return null;
    }
    
    private <T extends JObject> T findField(JObject parent, T child) {
        for (JObject field : parent.getChildren()) {
            if (field == child) {
                return (T) field;
            }
        }
        throw new RuntimeException("Field not found");
    }
    
    public boolean isChild(JObject parent, JObject child) {
        if (parent != null) {
            for (JObject jObject : parent.getChildren()) {
                if (jObject == child) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public <T extends JObject> void replace(T subject, T replacement) {
        JObject parent = findParent(subject);
        Try.to(() -> replaceField(parent, subject, replacement));
    }
    
    static <T> void replaceField(Object object, T subject, T replacement)
            throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            Object o = field.get(object);
            if (o == subject) {
                field.set(object, replacement);
                break;
            }
        }
    }
}

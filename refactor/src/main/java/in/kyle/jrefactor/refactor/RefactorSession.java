package in.kyle.jrefactor.refactor;

import java.lang.reflect.Field;

import in.kyle.api.utils.Try;
import in.kyle.jrefactor.tree.JObject;
import in.kyle.jrefactor.tree.unit.JIdentifier;
import in.kyle.jrefactor.refactor.symbol.SymbolTable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RefactorSession {
    
    private JObject root;
    
    public void rename(JIdentifier identifier, String newName) {
        SymbolTable table = new SymbolTable(root);
        table.compute();
        for (JIdentifier jIdentifier : table.getUses(identifier)) {
            jIdentifier.setName(newName);
        }
    }
    
    public JObject findParent(JObject subject) {
        return JObjectUtils.findParent(root, subject);
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

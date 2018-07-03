package in.kyle.jrefactor.refactor;

import java.lang.reflect.Field;

import in.kyle.api.utils.Try;
import in.kyle.jrefactor.refactor.symbol.UnitSymbolTable;
import in.kyle.jrefactor.tree.JObj;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RefactorSession {
    
    private JObj root;
    
    public void rename(JIdentifier identifier, String newName) {
        UnitSymbolTable table = new UnitSymbolTable(root);
        table.compute();
        for (JIdentifier jIdentifier : table.getUses(identifier)) {
            jIdentifier.setName(newName);
        }
    }
    
    public JObj findParent(JObj subject) {
        return JObjUtils.findParent(root, subject);
    }
    
    public <T extends JObj> void replace(T subject, T replacement) {
        JObj parent = findParent(subject);
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

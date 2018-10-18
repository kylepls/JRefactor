package in.kyle.jrefactor.refactor.session;

import java.util.List;

import in.kyle.api.utils.Conditions;
import in.kyle.jrefactor.refactor.symbol.UnitSymbolTable;
import in.kyle.jrefactor.refactor.util.JObjUtils;
import in.kyle.jrefactor.tree.JObj;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FileRefactorSession {
    private final JObj root;
    
    public void rename(JIdentifier identifier, String newName) {
        Conditions.isTrue(contains(identifier),
                          "Identifier exact : " + identifier.hashCode() + " not found");
        UnitSymbolTable table = new UnitSymbolTable(root);
        table.compute();
        for (JIdentifier jIdentifier : table.getUses(identifier)) {
            jIdentifier.setName(newName);
        }
    }
    
    public boolean contains(JObj obj) {
        List<JObj> allElements = JObjUtils.getAllElements(root);
        return allElements.stream().anyMatch(o -> {
            return o == obj;
        });
    }
    
    public JObj findParent(JObj subject) {
        return JObjUtils.findParent(root, subject);
    }
    
    public <T extends JObj> void replace(T subject, T replacement) {
        JObj parent = findParent(subject);
        parent.replaceChild(subject, replacement);
    }
}

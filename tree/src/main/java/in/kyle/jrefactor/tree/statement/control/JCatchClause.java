package in.kyle.jrefactor.tree.statement.control;

import in.kyle.jrefactor.tree.JObject;
import in.kyle.jrefactor.tree.JObjectList;
import in.kyle.jrefactor.tree.statement.JBlock;
import in.kyle.jrefactor.tree.unit.JIdentifier;
import in.kyle.jrefactor.tree.unit.JModifierList;
import in.kyle.jrefactor.tree.unit.JTypeName;
import lombok.Data;

@Data
public class JCatchClause implements JObject {
    
    private JModifierList modifiers = new JModifierList();
    private JObjectList<JTypeName> catchTypes = new JObjectList<>();
    private JIdentifier variable;
    private JBlock block;
    
}

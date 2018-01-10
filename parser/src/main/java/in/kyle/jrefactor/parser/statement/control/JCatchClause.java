package in.kyle.jrefactor.parser.statement.control;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.JObjectList;
import in.kyle.jrefactor.parser.statement.JBlock;
import in.kyle.jrefactor.parser.unit.JIdentifier;
import in.kyle.jrefactor.parser.unit.JModifierList;
import in.kyle.jrefactor.parser.unit.JTypeName;
import lombok.Data;

@Data
public class JCatchClause implements JObject {
    
    private JModifierList modifiers = new JModifierList();
    private JObjectList<JTypeName> catchTypes = new JObjectList<>();
    private JIdentifier variable;
    private JBlock block;
    
}

package in.kyle.jrefactor.tree.unit.body;

import in.kyle.jrefactor.tree.JObjectList;
import lombok.Data;

@Data
public abstract class JTypeBody<T extends JMember> extends JObjectList<T> {
}

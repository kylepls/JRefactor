package in.kyle.jrefactor.parser.unit.body;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.CodeWriter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Delegate;

public class JTypeBody<T extends JMember> implements JObject {
    
    @Getter(value = AccessLevel.NONE)
    @Delegate(excludes = JObject.class)
    private final JMemberList<T> memberList = new JMemberList<>();
    
    @Override
    public void write(CodeWriter writer) {
        writer.appendLine(" {");
        writer.indent();
        writer.append(memberList);
        writer.dedent();
        writer.appendLine("}");
    }
}

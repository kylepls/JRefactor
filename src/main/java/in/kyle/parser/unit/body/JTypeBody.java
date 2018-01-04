package in.kyle.parser.unit.body;

import in.kyle.parser.JObject;
import in.kyle.writer.CodeWriter;
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

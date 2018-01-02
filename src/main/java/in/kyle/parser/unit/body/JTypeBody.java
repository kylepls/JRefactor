package in.kyle.parser.unit.body;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.writer.CodeWriter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Delegate;

public class JTypeBody<T extends JMember> implements JObject {
    
    @Getter(value = AccessLevel.NONE)
    @Delegate(excludes = JObject.class)
    private final MemberList<T> memberList = new MemberList<>();
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(memberList.getMembers());
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.appendLine(" {");
        writer.indent();
        memberList.write(writer);
        writer.dedent();
        writer.appendLine("}");
    }
}

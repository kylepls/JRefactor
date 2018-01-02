package in.kyle.parser.statement.control.loops;

import in.kyle.writer.CodeWriter;

public class JDoWhileStatement extends WhileStatement {
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("do {} while ({});", getStatement(), getExpression());
    }
}

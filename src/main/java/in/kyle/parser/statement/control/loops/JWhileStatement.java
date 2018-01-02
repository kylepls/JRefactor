package in.kyle.parser.statement.control.loops;

import in.kyle.writer.CodeWriter;

public class JWhileStatement extends WhileStatement {
    @Override
    public void write(CodeWriter writer) {
        writer.append("while ({}) {}", getExpression(), getStatement());
    }
}

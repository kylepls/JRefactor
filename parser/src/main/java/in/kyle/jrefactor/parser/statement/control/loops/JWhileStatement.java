package in.kyle.jrefactor.parser.statement.control.loops;

import in.kyle.jrefactor.CodeWriter;

public class JWhileStatement extends WhileStatement {
    @Override
    public void write(CodeWriter writer) {
        writer.append("while ({}) {}", getExpression(), getStatement());
    }
}

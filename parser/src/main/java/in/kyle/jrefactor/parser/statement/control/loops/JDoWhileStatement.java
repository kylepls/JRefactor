package in.kyle.jrefactor.parser.statement.control.loops;

import in.kyle.jrefactor.CodeWriter;

public class JDoWhileStatement extends WhileStatement {
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("do {} while ({});", getStatement(), getExpression());
    }
}

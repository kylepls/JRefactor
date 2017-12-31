package in.kyle.parser.statement;

import in.kyle.writer.CodeWriter;

public class JEmptyStatement implements JBlockStatement {
    @Override
    public void write(CodeWriter writer) {
        writer.append(";");
        
    }
}

package in.kyle.parser.statement;

import in.kyle.writer.CodeWriter;

public class JEmptyStatement implements JStatement {
    @Override
    public void write(CodeWriter writer) {
        writer.append(";");
        
    }
}

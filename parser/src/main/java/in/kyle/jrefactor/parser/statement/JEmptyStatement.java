package in.kyle.jrefactor.parser.statement;

import in.kyle.jrefactor.CodeWriter;

public class JEmptyStatement implements JStatement {
    @Override
    public void write(CodeWriter writer) {
        writer.append(";");
    }
    
}

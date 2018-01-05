package in.kyle.jrefactor.parser.expression.literal;

import in.kyle.jrefactor.CodeWriter;

public class JStringLiteral extends JLiteral<String> {
    
    public JStringLiteral(String value) {
        super(value);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("\"{}\"", getValue());
    }
}

package in.kyle.parser.expression.literal;

import in.kyle.writer.CodeWriter;

public class JStringLiteral extends JLiteral<String> {
    
    public JStringLiteral(String value) {
        super(value);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("\"{}\"", getValue());
    }
}

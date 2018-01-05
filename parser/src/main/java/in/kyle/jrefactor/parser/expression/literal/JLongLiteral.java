package in.kyle.jrefactor.parser.expression.literal;

import in.kyle.jrefactor.CodeWriter;

public class JLongLiteral extends JNumericLiteral<Long> {
    public JLongLiteral(Long value) {
        super(value);
    }
    
    @Override
    public void write(CodeWriter writer) {
        super.write(writer);
        writer.append("L");
    }
}

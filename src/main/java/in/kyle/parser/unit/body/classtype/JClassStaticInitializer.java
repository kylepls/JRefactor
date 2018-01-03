package in.kyle.parser.unit.body.classtype;

import in.kyle.writer.CodeWriter;

public class JClassStaticInitializer extends JClassInitializer {
    @Override
    public void write(CodeWriter writer) {
        writer.append("static {}", getBlock());
    }
}

package in.kyle.jrefactor.parser.unit.body.classtype;

import in.kyle.jrefactor.CodeWriter;

public class JClassStaticInitializer extends JClassInitializer {
    @Override
    public void write(CodeWriter writer) {
        writer.append("static {}", getBlock());
    }
}

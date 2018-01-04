package in.kyle.parser.unit.types;

import in.kyle.parser.unit.JTypeDeclaration;
import in.kyle.parser.unit.body.annotationtype.JAnnotationBody;
import in.kyle.writer.CodeWriter;

public class JAnnotationDeclaration extends JTypeDeclaration<JAnnotationBody> {
    @Override
    public void write(CodeWriter writer) {
        super.write(writer);
        writeAnnotations(writer);
        writer.append("@interface {} {}", getIdentifier(), getBody());
    }
}

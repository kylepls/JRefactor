package in.kyle.parser.unit.types;

import in.kyle.parser.unit.JType;
import in.kyle.parser.unit.body.annotationtype.JAnnotationBody;
import in.kyle.writer.CodeWriter;

public class JAnnotationDeclaration extends JType<JAnnotationBody> {
    @Override
    public void write(CodeWriter writer) {
        writeAnnotations(writer);
        writeModifiers(writer);
        writer.append("@interface {} {}", getIdentifier(), getBody());
    }
}

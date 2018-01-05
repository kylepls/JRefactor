package in.kyle.jrefactor.parser.unit.types;

import in.kyle.jrefactor.parser.unit.JTypeDeclaration;
import in.kyle.jrefactor.parser.unit.body.annotationtype.JAnnotationBody;
import in.kyle.jrefactor.CodeWriter;

public class JAnnotationDeclaration extends JTypeDeclaration<JAnnotationBody> {
    @Override
    public void write(CodeWriter writer) {
        super.write(writer);
        writeAnnotations(writer);
        writer.append("@interface {} {}", getIdentifier(), getBody());
    }
}

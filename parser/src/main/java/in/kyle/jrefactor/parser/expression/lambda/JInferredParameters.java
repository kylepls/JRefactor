package in.kyle.jrefactor.parser.expression.lambda;

import in.kyle.jrefactor.CodeWriter;
import in.kyle.jrefactor.parser.JObjectList;
import in.kyle.jrefactor.parser.unit.JIdentifier;
import lombok.Data;

@Data
public class JInferredParameters extends JObjectList<JIdentifier> implements JLambdaParameters {

    @Override
    public void write(CodeWriter writer) {
        writer.append("(");
        super.write(writer);
        writer.append(")");
    }
}

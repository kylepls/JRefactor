package in.kyle.jrefactor.parser.expression.lambda;

import in.kyle.jrefactor.parser.unit.JIdentifier;

public class JIdentifierParameter extends JIdentifier implements JLambdaParameters {

    public JIdentifierParameter(String name) {
        super(name);
    }
}

package in.kyle.jrefactor.tree.expression.lambda;

import in.kyle.jrefactor.tree.unit.JIdentifier;

public class JIdentifierParameter extends JIdentifier implements JLambdaParameters {

    public JIdentifierParameter(String name) {
        super(name);
    }
}

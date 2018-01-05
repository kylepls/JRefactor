package in.kyle.jrefactor.parser.expression.literal;

public abstract class JFloatingLiteral<T extends Number> extends JNumericLiteral<T> {
    public JFloatingLiteral(T value) {
        super(value);
    }
}

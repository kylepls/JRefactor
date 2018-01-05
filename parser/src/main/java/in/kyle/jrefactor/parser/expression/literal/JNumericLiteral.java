package in.kyle.jrefactor.parser.expression.literal;

public abstract class JNumericLiteral<T extends Number> extends JLiteral<T> {
    public JNumericLiteral(T value) {
        super(value);
    }
}

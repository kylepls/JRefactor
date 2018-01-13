package in.kyle.jrefactor.tree.expression.literal;

public abstract class JFloatingLiteral<T extends Number> extends JNumericLiteral<T> {
    public JFloatingLiteral(T value) {
        super(value);
    }
}

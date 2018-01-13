package in.kyle.jrefactor.tree.expression.literal;

public abstract class JNumericLiteral<T extends Number> extends JLiteral<T> {
    public JNumericLiteral(T value) {
        super(value);
    }
}

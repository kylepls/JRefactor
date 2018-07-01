package in.kyle.jrefactor.refactor;

public class JavaBaseVisitor\<T> extends AbstractJObjectVisitor\<T> implements JavaVisitor\<T> {
    <names: { each | public T visit<each.simpleName>(<each.identifier> object) {
    return visitChildren(object);
\}
    }>
}

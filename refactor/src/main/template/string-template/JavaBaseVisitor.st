JavaBaseVisitor(names) ::= <<
package in.kyle.jrefactor.refactor;

public class JavaBaseVisitor\<T> extends AbstractJObjectVisitor\<T> implements JavaVisitor\<T> {
    <names: { each | public T visit<each.simpleName>(<each.name> object) {
    return visitChildren(object);
\}
    }>
}
>>

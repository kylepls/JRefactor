JavaVisitor(names) ::= <<
package in.kyle.jrefactor.refactor;

public interface JavaVisitor\<T> {
    <names: { each | T visit<each.simpleName>(<each.name> object);
    }>
}
>>

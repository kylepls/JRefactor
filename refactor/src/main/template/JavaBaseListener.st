package in.kyle.jrefactor.refactor;

public class JavaBaseListener extends AbstractJObjectListener implements JavaListener {
    <names: { each | public void enter<each.simpleName>(<each.identifier> object) {
    super.enterChildren(object);
\}
public void exit<each.simpleName>(<each.identifier> object) {
\}
    }>
}

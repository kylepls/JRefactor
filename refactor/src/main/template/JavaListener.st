package in.kyle.jrefactor.refactor;

public interface JavaListener {
    <names: { each | 
void enter<each.simpleName>(<each.identifier> object);
void exit<each.simpleName>(<each.identifier> object);
    }>
}

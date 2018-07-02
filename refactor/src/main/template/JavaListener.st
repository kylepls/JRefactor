package in.kyle.jrefactor.refactor;

public interface JavaListener {
    <names: { each | 
void enter<each.simpleName>(<each.name> object);
void exit<each.simpleName>(<each.name> object);
    }>
}

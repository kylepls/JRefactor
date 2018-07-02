package in.kyle.jrefactor.writer;

public abstract class CodeWriter {
    public abstract void write(in.kyle.jrefactor.tree.JObj object);
    
    <names:{each | protected void write<each.simpleName>(<each.name> object) {
    throw new RuntimeException("Writer write<each.simpleName>(<each.name>) not implemented");
\}
    }>
}

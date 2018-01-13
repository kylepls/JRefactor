CodeWriter(names) ::= <<
package in.kyle.jrefactor.writer;

public abstract class CodeWriter {
    public abstract void write(in.kyle.jrefactor.parser.JObject object);
    
    <names:{each | protected void write<each.simpleName>(<each.name> object) {
    throw new RuntimeException("Writer write<each.simpleName>(<each.name>) not implemented");
\}
    }>
}
>>
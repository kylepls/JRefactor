writer_interface(names, name) ::= <<
package in.kyle.jrefactor.writer;

public interface CodeWriter {
    <names:{each | void write(<each> object);
    }>
}
>>
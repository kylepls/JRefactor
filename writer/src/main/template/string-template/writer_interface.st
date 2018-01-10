writer_interface(members, name) ::= <<
package in.kyle.jrefactor.writer;

public interface CodeWriter {
    <members:{each | void write(<each> object);
    }>
}
>>
package in.kyle.ast.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum JavaFileType {
    ABSTRACT_CLASS("abstract class"),
    CLASS("class"),
    ENUM("enum"),
    INTERFACE("interface");
    
    @Getter
    private final String javaName;
}

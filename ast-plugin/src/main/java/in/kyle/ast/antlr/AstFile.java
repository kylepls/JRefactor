package in.kyle.ast.antlr;

import in.kyle.ast.code.CodeModifier;
import in.kyle.ast.code.FileSet;
import lombok.Data;

@Data
public class AstFile {
    private final FileSet files;
    private final CodeModifier modifier;
}

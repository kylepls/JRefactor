package in.kyle.jrefactor.parser.unit;

import java.util.Optional;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.JObjectList;
import lombok.Data;

@Data
public class JCompilationUnit implements JObject {
    
    private Optional<JPackage> packageName = Optional.empty();
    private JObjectList<JImport> imports = new JObjectList<>();
    private JObjectList<JTypeDeclaration> types = new JObjectList<>();
    
}

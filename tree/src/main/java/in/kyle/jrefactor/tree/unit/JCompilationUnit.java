package in.kyle.jrefactor.tree.unit;

import java.util.Optional;

import in.kyle.jrefactor.tree.JObject;
import in.kyle.jrefactor.tree.JObjectList;
import lombok.Data;

@Data
public class JCompilationUnit implements JObject {
    
    private Optional<JPackage> packageName = Optional.empty();
    private JObjectList<JImport> imports = new JObjectList<>();
    private JObjectList<JTypeDeclaration> types = new JObjectList<>();
    
}

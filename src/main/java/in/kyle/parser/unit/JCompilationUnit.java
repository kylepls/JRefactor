package in.kyle.parser.unit;

import in.kyle.JObjectList;
import in.kyle.parser.JObject;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JCompilationUnit implements JObject {
    
    private JPackage packageName;
    private JObjectList<JImport> imports = new JObjectList<>();
    private JObjectList<JTypeDeclaration> types = new JObjectList<>();
    
    public boolean addImport(JImport jImport) {
        return imports.add(jImport);
    }
    
    public boolean removeImport(JImport jImport) {
        return imports.remove(jImport);
    }
    
    public boolean addType(JTypeDeclaration declaration) {
        return types.add(declaration);
    }
    
    public boolean removeType(JTypeDeclaration declaration) {
        return types.remove(declaration);
    }
    
    @Override
    public void write(CodeWriter writer) {
        if (packageName != null) {
            writer.append(packageName).appendLine();
        }
        for (JImport anImport : imports) {
            writer.appendLine(anImport);
        }
        
        writer.appendLine();
        
        for (JTypeDeclaration type : types) {
            writer.appendLine(type);
        }
    }
}

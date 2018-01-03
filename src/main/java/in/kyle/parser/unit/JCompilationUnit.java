package in.kyle.parser.unit;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.types.JClassDeclaration;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JCompilationUnit implements JObject {
    
    private JPackage packageName;
    private Set<JImport> imports = new LinkedHashSet<>();
    private Set<JClassDeclaration> types = new LinkedHashSet<>();
    
    public boolean addImport(JImport jImport) {
        return imports.add(jImport);
    }
    
    public boolean removeImport(JImport jImport) {
        return imports.remove(jImport);
    }
    
    public boolean addType(JClassDeclaration jClassDeclaration) {
        return types.add(jClassDeclaration);
    }
    
    public boolean removeType(JClassDeclaration jClassDeclaration) {
        return types.remove(jClassDeclaration);
    }
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(packageName, imports, types);
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
        
        for (JClassDeclaration type : types) {
            writer.appendLine(type);
        }
    }
}

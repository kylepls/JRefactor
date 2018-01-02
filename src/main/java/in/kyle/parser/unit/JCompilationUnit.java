package in.kyle.parser.unit;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.types.JClass;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JCompilationUnit implements JObject {
    
    private JPackage packageName;
    private Set<JImport> imports = new LinkedHashSet<>();
    private Set<JClass> types = new LinkedHashSet<>();
    
    public boolean addImport(JImport jImport) {
        return imports.add(jImport);
    }
    
    public boolean removeImport(JImport jImport) {
        return imports.remove(jImport);
    }
    
    public boolean addType(JClass jClass) {
        return types.add(jClass);
    }
    
    public boolean removeType(JClass jClass) {
        return types.remove(jClass);
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
        
        for (JClass type : types) {
            writer.appendLine(type);
        }
    }
}

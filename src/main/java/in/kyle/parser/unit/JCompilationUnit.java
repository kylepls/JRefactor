package in.kyle.parser.unit;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.JObject;
import in.kyle.parser.RewriteableField;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JCompilationUnit implements JObject {
    
    private RewriteableField<JPackage> packageName = new RewriteableField<>();
    private Set<RewriteableField<JImport>> imports = new LinkedHashSet<>();
    private Set<RewriteableField<JClass>> types = new LinkedHashSet<>();
    
    public void setPackageName(JPackage packageName) {
        this.packageName.setValue(packageName);
    }
    
    public JPackage getPackageName() {
        return packageName.getValue();
    }
    
    public boolean addImport(JImport jImport) {
        return CollectionUtils.addValue(imports, jImport);
    }
    
    public boolean removeImport(JImport jImport) {
        return CollectionUtils.removeValue(imports, jImport);
    }
    
    public void setImports(Set<JImport> imports) {
        CollectionUtils.overwrite(this.imports, imports);
    }
    
    public Set<JImport> getImports() {
        return CollectionUtils.convertToJObjectSet(imports);
    }
    
    public boolean addType(JClass jClass) {
        return CollectionUtils.addValue(types, jClass);
    }
    
    public boolean removeType(JClass jClass) {
        return CollectionUtils.removeValue(types, jClass);
    }
    
    public void setTypes(Set<JClass> types) {
        CollectionUtils.overwrite(this.types, types);
    } 
    
    public Set<JClass> getTypes() {
        return CollectionUtils.convertToJObjectSet(types);
    }
    
    @Override
    public List<RewriteableField> getChildren() {
        return CollectionUtils.createList(packageName, imports, types);
    }
    
    @Override
    public void write(CodeWriter writer) {
        packageName.ifPresent(jPackage -> writer.append(packageName).appendLine());
    
        for (RewriteableField<JImport> anImport : imports) {
            writer.appendLine(anImport.getValue());
        }
        
        writer.appendLine();
    
        for (RewriteableField<JClass> type : types) {
            writer.appendLine(type.getValue());
        }
    }
}

package in.kyle.ast.code;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import in.kyle.ast.code.file.Field;
import in.kyle.ast.code.file.WritableElement;
import in.kyle.ast.util.Formatter;
import in.kyle.ast.util.Formatter.KV;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JavaFileBuilder implements WritableElement {
    
    private final JavaFile file;
    
    public String getRelPath() {
        return FileSet.createRelPath(file).replace(file.getName(), file.getName() + "Builder");
    }
    
    @Override
    public String write() {
        List<KV<String, Object>> kvs = new ArrayList<>();
        kvs.add(KV.of("simpleName", file.getName()));
        kvs.add(KV.of("name", file.getPackageName() + "." + file.getName()));
        kvs.add(KV.of("fields", makeFields(file)));
        kvs.add(KV.of("package", file.getPackageName()));
        Set<String> imports = new HashSet<>(file.getImports());
        imports.add(file.getPackageName() + "." + file.getName() + ".*");
        kvs.add(KV.of("imports", imports));
        return Formatter.fromTemplate("builder", this, kvs);
    }
    
    private List<BuilderField> makeFields(JavaFile file) {
        List<BuilderField> fields = file.getFields()
                                        .stream()
                                        .map(this::makeBuilderField)
                                        .collect(Collectors.toList());
        if (file.hasSuperType()) {
            fields.addAll(makeFields(file.getSuperType()));
        }
        return fields;
    }
    
    private BuilderField makeBuilderField(Field field) {
        boolean collection = field.getType().equals("Set") || field.getType().equals("List");
        boolean optional = field.getType().endsWith("Optional");
        boolean normalType = !collection && !optional;
        String singularName = field.getName().substring(0, field.getName().length() - 1);
        
        String type;
        if (field.getType().length() == 1) {
            type = file.getGenericSuper();
        } else {
            type = field.getType();
        }
        return new BuilderField(collection,
                                optional,
                                normalType,
                                field.getName(),
                                singularName,
                                type,
                                field.getGeneric());
    }
    
    
    @AllArgsConstructor
    private static class BuilderField {
        private final boolean isCollection;
        private final boolean isOptional;
        private final boolean normalType;
        private final String name;
        private final String nameSingular;
        private final String type;
        private final String generic;
        
        public boolean isCollection() {
            return isCollection;
        }
        
        public boolean isOptional() {
            return isOptional;
        }
        
        public boolean isNormalType() {
            return normalType;
        }
        
        public String getName() {
            return name;
        }
        
        public String getNameSingular() {
            return nameSingular;
        }
        
        public String getGeneric() {
            return generic;
        }
        
        public String getType() {
            return type;
        }
    }
}

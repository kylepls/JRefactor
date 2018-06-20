package in.kyle.ast.code;

import org.antlr.v4.misc.OrderedHashMap;
import org.antlr.v4.runtime.misc.OrderedHashSet;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import in.kyle.ast.util.Formatter;
import lombok.Data;

@Data
public class JavaFile {
    
    private final Map<String, String> fields = new OrderedHashMap<>();
    private final Set<JavaFile> innerClasses = new OrderedHashSet<>();
    private final Map<String, String> enumElements = new OrderedHashMap<>();
    private String name;
    private Type type;
    private String packageName;
    private JavaFile superType;
    
    public JavaFile(Type type, String name) {
        this.name = name;
        this.type = type;
    }
    
    public void addField(String type, String name) {
        fields.put(type, name);
    }
    
    public void addInnerClass(JavaFile file) {
        innerClasses.add(file);
    }
    
    public void addEnumElement(String name, String value) {
        enumElements.put(name, value);
    }
    
    public String classToString(boolean innerClass, CodeContext context) {
        StringBuilder builder = new StringBuilder();
        writePackage(innerClass, builder);
        writeImports(builder, context);
        if (type == Type.CLASS) {
            builder.append("import lombok.Data;\n");
            builder.append("@Data\n");
        }
        writeHeader(builder);
        builder.append(" {\n");
        writeClassContent(builder, context);
        builder.append("\n}");
        return builder.toString();
    }
    
    private void writeHeader(StringBuilder builder) {
        String typeName = type.name().toLowerCase();
        Formatter.append(builder, "public %s %s;\n\n", typeName, name);
        if (superType != null) {
            if (superType.getType() == Type.INTERFACE && type != Type.INTERFACE) {
                builder.append(" implements ");
            } else {
                builder.append(" extends ");
            }
            builder.append(superType.getName());
        }
    }
    
    private void writePackage(boolean innerClass, StringBuilder builder) {
        if (!innerClass && !packageName.isEmpty()) {
            Formatter.append(builder, "package %s;\n\n", packageName);
        }
    }
    
    private void writeImports(StringBuilder builder, CodeContext context) {
        Set<String> toImport = getTypesToImport();
        builder.append(context.getImports(toImport));
    }
    
    private void writeClassContent(StringBuilder builder, CodeContext context) {
        writeEnumValues(builder);
        writeFields(builder);
        writeMethods(builder, context);
        writeInnerClasses(builder);
    }
    
    private void writeMethods(StringBuilder builder, CodeContext context) {
        for (Map.Entry<String, String> field : fields.entrySet()) {
            String type = field.getKey().substring(field.getKey().indexOf("<"));
            String methods = context.getMethodForField(type);
            if (methods != null) {
                writeFieldMethods(builder, field, methods);
            }
        }
        
        if (type == Type.ENUM) {
            String methods = context.getEnumMethods();
            methods = Formatter.format(methods, "{type}", getName());
            methods = Formatter.indent(methods);
            builder.append(methods);
        }
    }
    
    private void writeFieldMethods(StringBuilder builder,
                                   Map.Entry<String, String> field,
                                   String methods) {
        String key = field.getKey();
        String diamondType = key.substring(key.indexOf("<") + 1, key.lastIndexOf(">"));
        String fieldName = field.getValue();
        String singularName = fieldName.substring(0, fieldName.length() - 1);
        methods = Formatter.format(methods,
                                   "diamond_type",
                                   diamondType,
                                   "field_name",
                                   fieldName,
                                   "singular_name",
                                   singularName,
                                   "singular_name_upper",
                                   singularName.substring(0, 1).toUpperCase() +
                                   singularName.substring(1, singularName.length()));
        methods = methods.replaceAll("^", "    ");
        builder.append(methods);
    }
    
    private Set<String> getTypesToImport() {
        Set<String> toImport = new HashSet<>();
        for (String type : fields.keySet()) {
            if (type.contains("<")) {
                String innerType = type.substring(type.indexOf("<") + 1, type.lastIndexOf(">"));
                toImport.add(innerType);
                type = type.substring(type.indexOf("<"));
            }
            toImport.add(type);
        }
        if (superType != null) {
            toImport.add(superType.getName());
        }
        return toImport;
    }
    
    private void writeEnumValues(StringBuilder builder) {
        boolean hasValue = false;
        for (Iterator<Map.Entry<String, String>> iterator =
             enumElements.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, String> entry = iterator.next();
            builder.append("    ");
            String content = entry.getValue() != null ? entry.getValue() : "";
            if (!content.isEmpty()) {
                hasValue = true;
            }
            builder.append(entry.getKey()).append("(").append(content).append(")");
            if (iterator.hasNext()) {
                builder.append(",");
            } else {
                builder.append(";");
            }
            builder.append("\n");
        }
        if (hasValue) {
            builder.append("public final String value;\n");
            builder.append(name).append("(String value) { this.value = value; }\n");
        }
    }
    
    private void writeInnerClasses(StringBuilder builder) {
        for (JavaFile innerClass : innerClasses) {
            builder.append("    ");
            String inner =
                    innerClass.classToString(true, new CodeContext()).replaceAll("^", "    ");
            builder.append(inner);
            builder.append("\n");
        }
    }
    
    private void writeFields(StringBuilder builder) {
        for (Map.Entry<String, String> fieldEntry : fields.entrySet()) {
            builder.append("    private ")
                   .append(fieldEntry.getKey())
                   .append(" ")
                   .append(fieldEntry.getValue())
                   .append(";\n");
        }
    }
    
    public enum Type {
        CLASS,
        ENUM,
        INTERFACE
    }
}

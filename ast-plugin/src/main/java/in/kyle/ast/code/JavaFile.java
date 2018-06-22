package in.kyle.ast.code;

import org.antlr.v4.runtime.misc.OrderedHashSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import in.kyle.ast.code.file.EnumElement;
import in.kyle.ast.code.file.Field;
import in.kyle.ast.code.file.JavaFileHeader;
import in.kyle.ast.code.file.WritableElement;
import in.kyle.ast.util.Formatter;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
public class JavaFile implements WritableElement {
    
    @Delegate(excludes = WritableElement.class)
    private final JavaFileHeader header;
    private final Set<String> imports = new HashSet<>();
    private final Set<Field> fields = new OrderedHashSet<>();
    private final Set<JavaFile> innerClasses = new OrderedHashSet<>();
    private final Set<EnumElement> enumElements = new OrderedHashSet<>();
    private final Set<String> methods = new OrderedHashSet<>();
    private String packageName;
    private boolean innerClass;
    
    public JavaFile(String name) {
        this.header = new JavaFileHeader();
        this.header.setName(name);
        setType(JavaFileType.CLASS);
    }
    
    public void addInnerClass(JavaFile file) {
        innerClasses.add(file);
        file.setInnerClass(true);
    }
    
    private void writeFieldMethods(StringBuilder builder,
                                   Map.Entry<String, String> field,
                                   String methods) {
        //        String key = field.getKey();
        //        String diamondType = key.substring(key.indexOf("<") + 1, key.lastIndexOf(">"));
        //        String fieldName = field.getValue();
        //        String singularName = fieldName.substring(0, fieldName.length() - 1);
        //        methods = Formatter.format(methods,
        //                                   "diamond_type",
        //                                   diamondType,
        //                                   "field_name",
        //                                   fieldName,
        //                                   "singular_name",
        //                                   singularName,
        //                                   "singular_name_upper",
        //                                   singularName.substring(0, 1).toUpperCase() +
        //                                   singularName.substring(1, singularName.length()));
        //        methods = methods.replaceAll("^", "    ");
        //        builder.append(methods);
    }
    
    @Override
    public String write() {
        List<String> stringFields =
                fields.stream().map(WritableElement::write).collect(Collectors.toList());
        if (getType() == JavaFileType.ENUM) {
            stringFields.addAll(computeEnumFields());
        }
        Object[] kv = {"isEnum", getType() == JavaFileType.ENUM, "isClass",
                       getType() == JavaFileType.CLASS, "stringFields", stringFields, "enumStrings",
                       computeEnumStrings(), "innerClassStrings", computeInnerClassStrings()};
        return Formatter.fromTemplate("file", this, kv);
    }
    
    private List<String> computeEnumFields() {
        List<String> fields = new ArrayList<>();
        if (enumElements.size() > 0) {
            int size = enumElements.iterator().next().getValues().size();
            for (int j = 0; j < size; j++) {
                Field field = new Field("final String", null, "value" + (j + 1), null);
                fields.add(field.write());
            }
        }
        return fields;
    }
    
    private List<String> computeEnumStrings() {
        return enumElements.stream().map(WritableElement::write).collect(Collectors.toList());
    }
    
    private List<String> computeInnerClassStrings() {
        return innerClasses.stream().map(WritableElement::write).collect(Collectors.toList());
    }
}

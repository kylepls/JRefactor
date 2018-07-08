package in.kyle.ast.code.processors;

import org.antlr.v4.runtime.misc.OrderedHashSet;
import org.stringtemplate.v4.ST;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import in.kyle.ast.code.JavaFile;
import in.kyle.ast.code.file.JavaField;
import in.kyle.ast.util.StringTemplate;

public class FieldMethodProcessor implements CodeProcessor {
    
    private final Map<String, ST> fieldMethods = new HashMap<String, ST>() {{
        ST collectionMethods = StringTemplate.get("collectionMethods");
        put("List", collectionMethods);
        put("Set", collectionMethods);
    }};
    
    public void addFieldMethod(String type, ST method) {
        fieldMethods.put(type, method);
    }
    
    @Override
    public void process(JavaFile file) {
        Set<JavaField> fields = new OrderedHashSet<>();
        fields.addAll(file.getFields());
        for (JavaField field : fields) {
            String type = field.getType();
            if (fieldMethods.containsKey(type)) {
                addFieldMethod(file, field, type);
            }
        }
    }
    
    private void addFieldMethod(JavaFile file, JavaField field, String type) {
        ST st = fieldMethods.get(type);
        String methods = StringTemplate.render(st, field);
        file.addBodyElement(methods);
    }
}

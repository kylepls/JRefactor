package in.kyle.ast.code.processors;

import org.antlr.v4.runtime.misc.OrderedHashSet;
import org.stringtemplate.v4.ST;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import in.kyle.ast.code.file.JavaField;
import in.kyle.ast.code.file.JavaFile;
import in.kyle.ast.util.StringTemplate;

public class FieldMethodProcessor implements CodeProcessor {
    
    private final Map<String, ST> fieldMethods = new HashMap<String, ST>() {{
        ST collectionMethods = StringTemplate.get("collectionMethods/addMethods");
        put("java.util.List", collectionMethods);
        put("java.util.Set", collectionMethods);
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
                addFieldMethod(file, field, fieldMethods.get(type));
            }
        }
    }
    
    private void addFieldMethod(JavaFile file, JavaField field, ST st) {
        String methods = StringTemplate.render(new ST(st), field);
        file.addBodyElement(methods);
    }
}

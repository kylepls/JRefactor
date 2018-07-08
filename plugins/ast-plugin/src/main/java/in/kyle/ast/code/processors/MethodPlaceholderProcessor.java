package in.kyle.ast.code.processors;

import org.stringtemplate.v4.ST;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import in.kyle.ast.code.file.JavaFile;

public class MethodPlaceholderProcessor implements CodeProcessor {
    
    private final Map<String, String> variables = new HashMap<>();
    
    public void addVariable(String id, String value) {
        variables.put(id, value);
    }
    
    @Override
    public void process(JavaFile file) {
        Set<String> methods = new HashSet<>(file.getBodyElements());
        for (String method : methods) {
            if (variables.containsKey(method)) {
                file.removeBodyElement(method);
                String methodString = variables.get(method);
                ST st = new ST(methodString);
                st.add("name", file.getName());
                file.addBodyElement(st.render());
            }
        }
    }
}

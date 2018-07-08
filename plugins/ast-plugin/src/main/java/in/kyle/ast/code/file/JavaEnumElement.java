package in.kyle.ast.code.file;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class JavaEnumElement {
    
    private final String name;
    private List<String> values = new ArrayList<>();
    
    public void addValue(String value) {
        values.add(value);
    }
}

package in.kyle.ast.code.file;

import java.util.ArrayList;
import java.util.List;

import in.kyle.ast.util.StringTemplate;
import lombok.Data;

@Data
public class EnumElement implements WritableElement {
    
    private final String name;
    private List<String> values = new ArrayList<>();
    
    public void addValue(String value) {
        values.add(value);
    }
    
    @Override
    public String write() {
        return StringTemplate.render("enumElement", this);
    }
}

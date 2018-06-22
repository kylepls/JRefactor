package in.kyle.ast.code.file;

import java.util.ArrayList;
import java.util.List;

import in.kyle.ast.util.Formatter;
import lombok.Data;

@Data
public class EnumElement implements WritableElement {
    
    private final String name;
    private List<String> values = new ArrayList<>();
    
    @Override
    public String write() {
        return Formatter.fromTemplate("enumElement", this);
    }
}

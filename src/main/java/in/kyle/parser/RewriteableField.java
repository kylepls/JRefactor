package in.kyle.parser;

import java.util.function.Consumer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class RewriteableField<T extends JObject> {
    
    @Getter
    private T value;
    
    public boolean isPresent() {
        return value != null;
    }
    
    public void ifPresent(Consumer<T> action) {
        if (isPresent()) {
            action.accept(value);
        }
    }
    
    public void setValue(T value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Test {
    @AllArgsConstructor
    public enum Test2 {
        ONE();
    }
}

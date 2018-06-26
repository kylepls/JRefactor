import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Test {
    @AllArgsConstructor
    public enum Test2 {
        ONE();
    }
}

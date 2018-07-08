@lombok.Data
public class Test {
    public Test() {
        super();
    }
    @lombok.Getter
    @lombok.AllArgsConstructor
    public enum Test2 {
        ONE();
        
    }
}

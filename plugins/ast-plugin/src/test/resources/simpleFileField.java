import lombok.Data;

@Data
public class Test 
{
    private String string;
    public static TestBuilder builder() {
        return TestBuilder.create();
    }
}

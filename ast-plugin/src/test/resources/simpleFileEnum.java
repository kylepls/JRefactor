import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Test {
    ONE("one"),
    TWO("two"),
    THREE("three");
    private final String value1;
}
@lombok.Getter
@lombok.AllArgsConstructor
public enum Test {
    ONE("one"),
    TWO("two"),
    THREE("three");
    private final String value;
}
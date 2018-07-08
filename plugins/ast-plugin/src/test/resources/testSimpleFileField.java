@lombok.Data
public class Test {
    private String string;
    public Test(String string) {
        super();
        this.string = string;
    }
    
    public Test() {
        super();
    }
    
    public static TestBuilder builder() {
        return new TestBuilder();
    }

    public static class TestBuilder {

        private final Test test;

        private TestBuilder() {
            test = new Test();
        }

        public TestBuilder string(String string) {
            test.setString(string);
            return this;
        }

        public Test build() {
            return test;
        }
    }
}

@lombok.Data
public class Test {
    private java.util.List<String> strings;
    public Test(java.util.List<String> strings) {
        super();
        this.strings = strings;
    }

    public Test() {
        super();
    }

    public void addString(String string) { 
        strings.add(string); 
    }

    public void removeString(String string) { 
        strings.remove(string); 
    }

    public void setStrings(java.util.Collection<String> strings) {
        this.strings.clear();
        this.strings.addAll(strings);
    }

    public static TestBuilder builder() {
        return new TestBuilder();
    }

    public static class TestBuilder {

        private final Test test;

        private TestBuilder() {
            test = new Test();
        }

        public TestBuilder addStrings(String... string) {
            for (String e : string) {
                test.addString(e);
            }
            return this;
        }

        public Test build() {
            return test;
        }
    }
}
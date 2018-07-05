package in.kyle.jrefactor.parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import in.kyle.gen.JObjParameters;
import lombok.AllArgsConstructor;

// To remind me to test everything
@RunWith(Parameterized.class)
@AllArgsConstructor
public class TestAllTestsPresent {
    
    private final String className;
    
    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() throws IOException {
        return JObjParameters.getConcreteClasses()
                             .stream()
                             .map(JObjParameters.ClassInfo::getSimpleName)
                             .map(name -> new Object[]{name})
                             .collect(Collectors.toList());
    }
    
    @Test
    public void test() {
        String path = String.format("in.kyle.jrefactor.parser.obj.Test%s", className);
        try {
            Class.forName(path);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Test for class " + className + " not found in " + path);
        }
    }
}

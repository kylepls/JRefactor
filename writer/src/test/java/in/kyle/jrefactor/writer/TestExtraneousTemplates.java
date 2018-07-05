package in.kyle.jrefactor.writer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.stringtemplate.v4.STGroupFile;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import in.kyle.api.verify.Verify;
import in.kyle.gen.JObjParameters;
import lombok.AllArgsConstructor;

@RunWith(Parameterized.class)
@AllArgsConstructor
public class TestExtraneousTemplates {
    
    private static final STGroupFile file = new STGroupFile("string-template/SimpleWriter.stg");
    
    public static Collection<String> concreteClasses = getConcreteClasses();
    
    private final String name;
    
    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return file.getTemplateNames()
                   .stream()
                   .map(name -> name.substring(1))
                   .map(name -> new Object[]{name})
                   .collect(Collectors.toList());
    }
    
    private static Collection<String> getConcreteClasses() {
        try {
            return JObjParameters.getConcreteClasses()
                                 .stream()
                                 .map(JObjParameters.ClassInfo::getSimpleName)
                                 .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    public void test() {
        Verify.that(concreteClasses).contains(name);
    }
}

package in.kyle.jrefactor.writer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.stringtemplate.v4.STGroupFile;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.stream.Collectors;

import in.kyle.api.verify.Verify;
import in.kyle.gen.JObjParameters;
import lombok.AllArgsConstructor;

// Remind me to clean up after myself
// Extra templates should be added to utils or other template
@RunWith(Parameterized.class)
@AllArgsConstructor
public class TestMissingTemplates {
    
    private static Collection<Class<?>> concreteClasses = getConcreteClasses();
    private static Collection<String> templates = getTemplates();
    
    private final Class<?> clazz;
    private final String name;
    
    @Parameterized.Parameters(name = "{1}")
    public static Collection<Object[]> data() {
        return concreteClasses.stream()
                              .map(clazz -> new Object[]{clazz, clazz.getSimpleName()})
                              .collect(Collectors.toList());
    }
    
    private static Collection<String> getTemplates() {
        STGroupFile file = new STGroupFile("string-template/SimpleWriter.stg");
        return file.getTemplateNames()
                   .stream()
                   .map(name -> name.substring(1))
                   .collect(Collectors.toList());
    }
    
    private static Collection<Class<?>> getConcreteClasses() {
        try {
            return JObjParameters.getConcreteClasses()
                                 .stream()
                                 .map(JObjParameters.ClassInfo::getClazz)
                                 .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    public void test() {
        Verify.that(!Modifier.isAbstract(clazz.getModifiers())).isTrue();
        Verify.that(templates).contains(clazz.getSimpleName());
    }
}

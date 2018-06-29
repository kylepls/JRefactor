package in.kyle.ast.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import in.kyle.api.verify.Verify;
import in.kyle.ast.code.TestJavaFile;

public class FileUtils {
    public static void matchesFile(String text, String name) throws IOException {
        List<String> oldText = readFile(name + ".java");
        List<String> newText = Arrays.asList(text.split("\n"));
        oldText.replaceAll(String::trim);
        newText.replaceAll(String::trim);
        
        String oldTextString = oldText.stream().collect(Collectors.joining("\n"));
        String newTextString = newText.stream().collect(Collectors.joining("\n"));
        
        Verify.that(oldTextString).diffEqual(newTextString);
    }
    
    private static List<String> readFile(String path) throws IOException {
        return IOUtils.readLines(TestJavaFile.class.getResourceAsStream("/" + path), "UTF-8");
    }
}

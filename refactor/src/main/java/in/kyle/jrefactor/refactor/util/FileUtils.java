package in.kyle.jrefactor.refactor.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Consumer;

import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FileUtils {
    
    public static void transverseFiles(Path file, Consumer<Path> processor) {
        try {
            Files.walkFileTree(file, new FileProcessor(processor));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @AllArgsConstructor
    private static class FileProcessor extends SimpleFileVisitor<Path> {
        private final Consumer<Path> processor;
    
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                throws IOException {
            processor.accept(file);
            return super.visitFile(file, attrs);
        }
    }
}

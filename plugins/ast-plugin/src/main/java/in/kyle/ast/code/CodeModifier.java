package in.kyle.ast.code;

import org.antlr.v4.misc.OrderedHashMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import in.kyle.ast.code.file.JavaFile;
import in.kyle.ast.code.processors.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter(AccessLevel.PACKAGE)
public class CodeModifier {
    
    private final ClassMap<CodeProcessor> processors = new ClassMap<>();
    
    {
        addProcessor(new PackageNamePreprocessor());
        addProcessor(new TypeNameProcessor());
        addProcessor(new FQNProcessor());
        addProcessor(new ChildMethodsProcessor());
        addProcessor(new FieldDefaultProcessor());
        addProcessor(new FieldMethodProcessor());
        addProcessor(new GenericProcessor());
        addProcessor(new MethodPlaceholderProcessor());
    }
    
    public void addProcessor(CodeProcessor processor) {
        processors.put(processor.getClass(), processor);
    }
    
    public <T extends CodeProcessor> T getProcessor(Class<? extends T> clazz) {
        return (T) processors.get(clazz);
    }
    
    public void processFiles(FileSet fileSet) {
        List<JavaFile> files = getAllFiles(fileSet);
        runPhase(files, CodeProcessor::preprocess);
        runPhase(files, CodeProcessor::process);
        runPhase(files, CodeProcessor::postprocess);
    }
    
    private void runPhase(Collection<JavaFile> files, BiConsumer<CodeProcessor, JavaFile> phase) {
        files.forEach(file -> processors.values()
                                        .forEach(processor -> phase.accept(processor, file)));
    }
    
    List<JavaFile> getAllFiles(FileSet files) {
        return files.getFiles()
                    .stream()
                    .map(this::getAllFilesRecursively)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
    }
    
    List<JavaFile> getAllFilesRecursively(JavaFile file) {
        List<JavaFile> result = new ArrayList<>();
        result.add(file);
        for (JavaFile inner : file.getInnerClasses()) {
            result.addAll(getAllFilesRecursively(inner));
        }
        return result;
    }
    
    private static class ClassMap<T> extends OrderedHashMap<Class<? extends T>, T> {
    }
}

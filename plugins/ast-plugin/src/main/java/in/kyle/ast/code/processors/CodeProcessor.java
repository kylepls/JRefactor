package in.kyle.ast.code.processors;

import in.kyle.ast.code.file.JavaFile;

public interface CodeProcessor {
    
    default void preprocess(JavaFile file) {
        
    }
    
    default void process(JavaFile file) {
        
    }
    
    default void postprocess(JavaFile file) {
        
    }
}

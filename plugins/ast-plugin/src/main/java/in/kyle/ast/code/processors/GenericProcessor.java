package in.kyle.ast.code.processors;

import in.kyle.ast.code.file.JavaFile;
import in.kyle.ast.code.file.JavaField;

public class GenericProcessor implements CodeProcessor {
    
    @Override
    public void process(JavaFile file) {
        if (file.getGenericDefine() != null) {
            boolean usesGeneric = false;
            String generic = file.getGenericDefine();
            for (JavaField field : file.getFields()) {
                if (field.getType().equals(generic) ||
                    (field.getGeneric() != null && field.getGeneric().equals(generic))) {
                    usesGeneric = true;
                    break;
                }
            }
        
            if (!usesGeneric) {
                file.setGenericSuper(generic);
            }
        }
    }
}

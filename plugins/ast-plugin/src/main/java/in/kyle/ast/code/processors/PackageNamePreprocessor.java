package in.kyle.ast.code.processors;

import in.kyle.ast.code.JavaFile;
import lombok.Setter;

public class PackageNamePreprocessor implements CodeProcessor {
    
    @Setter
    private String packagePrefix = "";
    
    @Override
    public void preprocess(JavaFile file) {
        if (!packagePrefix.isEmpty()) {
            if (file.hasPackage()) {
                file.setPackageName(packagePrefix + "." + file.getPackageName());
            } else {
                file.setPackageName(packagePrefix);
            }
        }
    }
}

package in.kyle.ast.code.processors;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import in.kyle.ast.code.JavaFile;
import in.kyle.ast.code.st.JavaFieldAdapter;
import in.kyle.ast.code.st.JavaFileAdaptor;
import in.kyle.ast.code.file.JavaField;

public class ChildMethodsProcessor implements CodeProcessor {
    
    private final STGroupFile groupFile;
    private final ST addMethods;
    
    {
        groupFile = new STGroupFile("string-template/childMethods.stg");
        groupFile.registerModelAdaptor(JavaField.class, new JavaFieldAdapter());
        groupFile.registerModelAdaptor(JavaField.class, new JavaFileAdaptor());
        addMethods = groupFile.getInstanceOf("addMethods");
    }
    
    @Override
    public void process(JavaFile file) {
        ST st = new ST(addMethods);
        st.add("file", file);
        file.addBodyElement(st.render());
    }
}

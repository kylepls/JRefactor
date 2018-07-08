package in.kyle.ast.code.processors;

import org.stringtemplate.v4.ST;

import in.kyle.ast.code.file.JavaFile;
import in.kyle.ast.util.StringTemplate;

public class ChildMethodsProcessor implements CodeProcessor {
    
    private final ST addMethods;
    
    {
        addMethods = StringTemplate.get("childMethods/addMethods");
    }
    
    @Override
    public void process(JavaFile file) {
        ST st = new ST(addMethods);
        String render = StringTemplate.render(st, file);
        if (!render.isEmpty()) {
            file.addBodyElement(render);
        }
    }
}

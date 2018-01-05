package in.kyle.jrefactor.refactor;

import java.io.IOException;
import java.io.InputStream;

import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.parser.unit.JCompilationUnit;
import in.kyle.jrefactor.writer.BasicWriter;

public class TestGeneration {
    
    private static BasicWriter writer = new BasicWriter();
    
    public static void main(String[] args) throws IOException {
        JCompilationUnit unit = loadFile();
        printFile(unit);
        optimizeFile(unit);
        printFile(unit);
    }
    
    private static JCompilationUnit loadFile() throws IOException {
        InputStream is = TestGeneration.class.getResourceAsStream("/test");
        return Parser.parseFile(is);
    }
    
    private static void printFile(JCompilationUnit unit) {
        writer.append(unit);
        System.out.println(writer.toString());
        writer.clear();
    }
    
    private static void optimizeFile(JCompilationUnit unit) {
        LiteralOptimizer visitor = new LiteralOptimizer(new RefactorSession(unit));
        while (visitor.isRerun()) {
            visitor.setRerun(false);
            visitor.visit(unit);
        }
    }
}

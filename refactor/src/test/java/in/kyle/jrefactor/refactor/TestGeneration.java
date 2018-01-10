package in.kyle.jrefactor.refactor;

import java.io.IOException;
import java.io.InputStream;

import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.parser.statement.JLocalVariableDeclaration;
import in.kyle.jrefactor.parser.unit.JCompilationUnit;
import in.kyle.jrefactor.parser.unit.JIdentifier;
import in.kyle.jrefactor.parser.unit.body.JVariable;
import in.kyle.jrefactor.parser.unit.types.classtype.JClassInitializer;
import in.kyle.jrefactor.parser.unit.types.JClassDeclaration;
import in.kyle.jrefactor.writer.AbstractWriter;
import in.kyle.jrefactor.writer.BaseWriter;

public class TestGeneration {
    
    private static AbstractWriter writer = new BaseWriter();
    
    public static void main(String[] args) throws IOException {
        JCompilationUnit unit = loadFile();
        printFile(unit);
        RefactorSession session = new RefactorSession(unit);
        //optimizeFile(unit, session);
        renameVariable(unit, session);
        printFile(unit);
    }
    
    private static void renameVariable(JCompilationUnit unit, RefactorSession session) {
        JClassDeclaration type = (JClassDeclaration) unit.getTypes().get(0);
        
        JClassInitializer initializer = (JClassInitializer) type.getBody().getMembers().get(0);
        JLocalVariableDeclaration declaration =
                (JLocalVariableDeclaration) initializer.getBlock().getStatements().get(0);
        JVariable variable = declaration.getVariables().get(0);
        
        JIdentifier identifier = variable.getIdentifier();
        session.rename(identifier, "swag");
    }
    
    private static JCompilationUnit loadFile() throws IOException {
        InputStream is = TestGeneration.class.getResourceAsStream("/test");
        return Parser.parseFile(is);
    }
    
    private static void printFile(JCompilationUnit unit) {
        writer.write(unit);
        System.out.println(writer.toString());
        writer.clear();
    }
    
    private static void optimizeFile(JCompilationUnit unit, RefactorSession session) {
        LiteralOptimizer visitor = new LiteralOptimizer(session);
        while (visitor.isRerun()) {
            visitor.setRerun(false);
            visitor.visit(unit);
        }
    }
}

package in.kyle.jrefactor.refactor;

import java.io.IOException;
import java.io.InputStream;

import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.JCompilationUnit;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JVariable;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.type.superinterfacetype
        .typeparametertype.JClass;
import in.kyle.jrefactor.tree.obj.statement.JStatementLocalVariableDeclaration;
import in.kyle.jrefactor.tree.obj.unit.bodymember.typemember.enummember.classmember
        .JClassInitializer;
import in.kyle.jrefactor.writer.EzWriter;

public class TestGeneration {
    
    private static EzWriter writer = new EzWriter();
    
    public static void main(String[] args) throws IOException {
        JCompilationUnit unit = loadFile();
        printFile(unit);
        RefactorSession session = new RefactorSession(unit);
        //optimizeFile(unit, session);
//        renameVariable(unit, session);
        printFile(unit);
        System.out.println("Done");
    }
    
    private static void renameVariable(JCompilationUnit unit, RefactorSession session) {
        JClass type = (JClass) unit.getTypes().get(0);
        
        JClassInitializer initializer = (JClassInitializer) type.getBody().getElements().get(0);
        JStatementLocalVariableDeclaration declaration =
                (JStatementLocalVariableDeclaration) initializer.getBlock().getElements().get(0);
        JVariable variable = declaration.getVariables().get(0);
        
        JIdentifier identifier = variable.getName();
        session.rename(identifier, "swag");
    }
    
    private static JCompilationUnit loadFile() throws IOException {
        InputStream is = TestGeneration.class.getResourceAsStream("/test");
        return Parser.parseFile(is);
    }
    
    private static void printFile(JCompilationUnit unit) {
        System.out.println("Write ");
        String write = writer.write(unit);
        
        System.out.println(write);
    }
    
    private static void optimizeFile(JCompilationUnit unit, RefactorSession session) {
        LiteralOptimizer visitor = new LiteralOptimizer(session);
        while (visitor.isRerun()) {
            visitor.setRerun(false);
            visitor.visit(unit);
        }
    }
}

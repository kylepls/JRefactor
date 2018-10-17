package in.kyle.jrefactor.refactor;

import java.io.InputStream;

import in.kyle.jrefactor.parser.JavaParser;
import in.kyle.jrefactor.refactor.session.FileRefactorSession;
import in.kyle.jrefactor.tree.obj.JCompilationUnit;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.type.superinterfacetype.typeparametertype.JClass;
import in.kyle.jrefactor.tree.obj.statement.JStatementLocalVariableDeclaration;
import in.kyle.jrefactor.tree.obj.unit.bodymember.typemember.enummember.classmember.JClassInitializer;
import in.kyle.jrefactor.tree.obj.variabledefinition.JVariable;
import in.kyle.jrefactor.writer.EzWriter;

public class TestGeneration {
    
    private static EzWriter writer = new EzWriter();
    
    public static void main(String[] args) {
        JCompilationUnit unit = loadFile();
        printFile(unit);
        FileRefactorSession session = new FileRefactorSession(unit);
//        optimizeFile(unit, session);
        renameVariable(unit, session);
        printFile(unit);
        System.out.println("Done");
    }
    
    private static void renameVariable(JCompilationUnit unit, FileRefactorSession session) {
        JClass type = (JClass) unit.getTypes().get(0);
        
        JClassInitializer initializer = (JClassInitializer) type.getBody().getElements().get(0);
        JStatementLocalVariableDeclaration declaration =
                (JStatementLocalVariableDeclaration) initializer.getBlock().getElements().get(1);
        JVariable variable = declaration.getVariables().get(0);
        
        JIdentifier identifier = variable.getIdentifier();
        session.rename(identifier, "swag");
    }
    
    private static JCompilationUnit loadFile() {
        InputStream is = TestGeneration.class.getResourceAsStream("/Test.java");
        return JavaParser.parseFile(is);
    }
    
    private static void printFile(JCompilationUnit unit) {
        System.out.println("====================================================== ");
        String write = writer.write(unit);
        System.out.println(write);
    }
    
    private static void optimizeFile(JCompilationUnit unit, FileRefactorSession session) {
        LiteralOptimizer visitor = new LiteralOptimizer(session);
        do {
            visitor.setRerun(false);
            visitor.visit(unit);
            System.out.println(writer.write(unit));
        } while (visitor.isRerun());
    }
}

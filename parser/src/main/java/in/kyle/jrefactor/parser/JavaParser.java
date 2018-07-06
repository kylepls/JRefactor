package in.kyle.jrefactor.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStream;

import java.io.IOException;
import java.io.InputStream;

import in.kyle.jrefactor.tree.JObj;
import in.kyle.jrefactor.tree.antlr.gen.Java8Lexer;
import in.kyle.jrefactor.tree.antlr.gen.Java8Parser;
import in.kyle.jrefactor.tree.obj.JCompilationUnit;

public class JavaParser {
    
    private static final JavaCompositionVisitor VISITOR = new JavaCompositionVisitor();
    
    public static JCompilationUnit parseFile(InputStream is) {
        try {
            return parseInternal(is, JCompilationUnit.class);
        } catch (IOException e) {
            throw new ParseException(e);
        }
    }
    
    private static <T extends JObj> T parseInternal(InputStream is, Class<T> clazz)
            throws IOException {
        ANTLRInputStream ais = new ANTLRInputStream(is);
        Java8Lexer lexer = new Java8Lexer(ais);
        TokenStream ts = new CommonTokenStream(lexer);
        Java8Parser parser = new Java8Parser(ts);
        ParserRuleContext context = parser.compilationUnit();
        return (T) VISITOR.visit(context);
    }
}

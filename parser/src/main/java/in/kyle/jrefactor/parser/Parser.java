package in.kyle.jrefactor.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import in.kyle.jrefactor.tree.JObj;
import in.kyle.jrefactor.tree.antlr.gen.Java8Lexer;
import in.kyle.jrefactor.tree.antlr.gen.Java8Parser;
import in.kyle.jrefactor.tree.obj.JCompilationUnit;

public class Parser {
    
    private static final JavaCompositionVisitor VISITOR = new JavaCompositionVisitor();
    private static final BaseMapper MAPPER = new BaseMapper();
    
    public static <T extends JObj> T parse(String string, Class<T> clazz) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(string.getBytes("UTF-8"));
            return parseInternal(bais, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static JCompilationUnit parseFile(InputStream is) throws IOException {
        return parseInternal(is, JCompilationUnit.class);
    }
    
    private static <T extends JObj> T parseInternal(InputStream is, Class<T> clazz)
            throws IOException {
        ANTLRInputStream ais = new ANTLRInputStream(is);
        Java8Lexer lexer = new Java8Lexer(ais);
        TokenStream ts = new CommonTokenStream(lexer);
        Java8Parser parser = new Java8Parser(ts);
        ParserRuleContext context = MAPPER.parsePart(parser, clazz);
        if (context != null) {
            T visit = (T) VISITOR.visit(context);
            if (visit != null) {
                return visit;
            } else {
                throw new ParseException("No parser implemented for type " + clazz.getName());
            }
        } else {
            throw new ParseException("No parser defined for type " + clazz.getName());
        }
    }
}

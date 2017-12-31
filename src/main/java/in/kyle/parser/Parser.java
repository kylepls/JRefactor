package in.kyle.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

import in.kyle.antlr.JavaCompositionVisitor;
import in.kyle.antlr.gen.Java8Lexer;
import in.kyle.antlr.gen.Java8Parser;
import in.kyle.parser.unit.JCompilationUnit;

public class Parser {
    
    private static final JavaCompositionVisitor VISITOR = new JavaCompositionVisitor();
    
    public static <T extends ParserRuleContext> T parse(InputStream is,
                                                        Function<Java8Parser, T> result)
            throws IOException {
        ANTLRInputStream ais = new ANTLRInputStream(is);
        Java8Lexer lexer = new Java8Lexer(ais);
        TokenStream ts = new CommonTokenStream(lexer);
        Java8Parser parser = new Java8Parser(ts);
        return result.apply(parser);
    }
    
    public static <T, R extends ParserRuleContext> T createTree(R r) {
        return (T) VISITOR.visit(r);
    }
    
    public static JCompilationUnit parseFile(InputStream is) throws IOException {
        Java8Parser.CompilationUnitContext unitContext = parse(is, Java8Parser::compilationUnit);
        return createTree(unitContext);
    }
}

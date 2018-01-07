package in.kyle.jrefactor.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

import in.kyle.api.utils.Try;
import in.kyle.jrefactor.parser.antlr.JavaCompositionVisitor;
import in.kyle.jrefactor.parser.antlr.gen.Java8Lexer;
import in.kyle.jrefactor.parser.antlr.gen.Java8Parser;
import in.kyle.jrefactor.parser.statement.JBlock;
import in.kyle.jrefactor.parser.unit.JCompilationUnit;

public class Parser {
    
    private static final JavaCompositionVisitor VISITOR = new JavaCompositionVisitor();
    
    public static <T extends JObject> T parse(String string,
                                              Function<Java8Parser, ? extends ParserRuleContext> 
                                                      result)
            throws IOException {
        return parse(new ByteArrayInputStream(string.getBytes("UTF-8")), result);
    }
    
    public static <T extends JObject> T parse(InputStream is,
                                              Function<Java8Parser, ? extends ParserRuleContext> 
                                                      result)
            throws IOException {
        ANTLRInputStream ais = new ANTLRInputStream(is);
        Java8Lexer lexer = new Java8Lexer(ais);
        TokenStream ts = new CommonTokenStream(lexer);
        Java8Parser parser = new Java8Parser(ts);
        ParserRuleContext apply = result.apply(parser);
        return (T) VISITOR.visit(apply);
    }
    
    public static JBlock parseBlock(String block) {
        return Try.to(() -> new JBlock(parse(block, Java8Parser::blockStatements)));
    }
    
    
    public static JCompilationUnit parseFile(InputStream is) throws IOException {
        return parse(is, Java8Parser::compilationUnit);
    }
}

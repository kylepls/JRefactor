package in.kyle.ast.antlr;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenSource;

import java.io.IOException;
import java.io.InputStream;

import in.kyle.ast.AstLexer;
import in.kyle.ast.AstParser;
import in.kyle.ast.code.FileTree;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class AstGenerator {
    
    public static FileTree generateAstFromStream(InputStream stream) throws IOException {
        TokenSource lexer = new AstLexer(CharStreams.fromStream(stream));
        CommonTokenStream cts = new CommonTokenStream(lexer);
        AstParser parser = new AstParser(cts);
        CodeGenAstVisitor visitor = new CodeGenAstVisitor();
        return visitor.visitAst(parser.ast());
    }
}

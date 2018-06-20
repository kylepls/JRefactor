package in.kyle.ast.antlr;

import java.io.File;

import in.kyle.ast.AstBaseVisitor;
import in.kyle.ast.AstParser;
import in.kyle.ast.code.FileTree;
import in.kyle.ast.code.JavaFile;
import lombok.Data;

public class CodeGenAstVisitor extends AstBaseVisitor {
    
    @Override
    public FileTree visitAst(AstParser.AstContext ctx) {
        FileTree tree = new FileTree();
        for (AstParser.Ast_elementContext element : ctx.ast_element()) {
            tree.addChildren("", visitAst_element(element));
        }
        return tree;
    }
    
    @Override
    public FileTree visitAst_element(AstParser.Ast_elementContext ctx) {
        if (ctx.enum_element() != null) {
            JavaFile file = visitEnum_element(ctx.enum_element());
            FileTree tree = new FileTree();
            tree.addFile(ctx.enum_element().IDENTIFIER().getText() + ".java", file);
            return tree;
        } else {
            return visitObject(ctx.object());
        }
    }
    
    @Override
    public FileTree visitObject(AstParser.ObjectContext ctx) {
        return visitObject(ctx, null);
    }
    
    private FileTree visitObject(AstParser.ObjectContext ctx, JavaFile superObject) {
        FileTree tree = new FileTree();
        JavaFile file = new JavaFile(JavaFile.Type.CLASS, ctx.IDENTIFIER().toString());
        int elementCount = 0;
        if (superObject != null) {
            file.setSuperType(superObject);
        }
        String className = ctx.IDENTIFIER().toString();
        for (AstParser.Object_elementContext element : ctx.object_block().object_element()) {
            if (element.inside_object() != null) {
                JavaFile innerEnum = visitEnum_element(element.inside_object().enum_element());
                file.addInnerClass(innerEnum);
                elementCount++;
            } else if (element.object() != null) {
                FileTree subObject = visitObject(element.object(), file);
                tree.addChildren(className.toLowerCase() + File.separator, subObject);
            } else if (element.object_variable() != null) {
                ObjectVariable type = visitObject_variable(element.object_variable());
                file.addField(type.getKey(), type.getValue());
                elementCount++;
            }
        }
        if (elementCount == 0) {
            file.setType(JavaFile.Type.INTERFACE);
        }
        tree.addFile(className + ".java", file);
        return tree;
    }
    
    @Override
    public ObjectVariable visitObject_variable(AstParser.Object_variableContext ctx) {
        return new ObjectVariable(ctx.IDENTIFIER().get(0) + visitDiamondType(ctx.diamondType()),
                                  ctx.IDENTIFIER().get(1).getText());
    }
    
    @Override
    public String visitDiamondType(AstParser.DiamondTypeContext ctx) {
        return ctx != null ? ctx.getText() : "";
    }
    
    @Override
    public JavaFile visitEnum_element(AstParser.Enum_elementContext ctx) {
        JavaFile file = new JavaFile(JavaFile.Type.ENUM, ctx.IDENTIFIER().getText());
        appendEnumElements(ctx, file);
        return file;
    }
    
    private void appendEnumElements(AstParser.Enum_elementContext ctx, JavaFile builder) {
        for (AstParser.Enum_partContext part : ctx.enum_block().enum_part()) {
            String value = part.STRING() != null ? part.STRING().toString() : null;
            builder.addEnumElement(part.IDENTIFIER().getText(), value);
        }
    }
    
    @Data
    private static class ObjectVariable {
        private final String key;
        private final String value;
    }
}

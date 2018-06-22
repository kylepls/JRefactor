package in.kyle.ast.antlr;

import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import in.kyle.ast.AstBaseVisitor;
import in.kyle.ast.AstParser;
import in.kyle.ast.code.CodeModifier;
import in.kyle.ast.code.FileTree;
import in.kyle.ast.code.JavaFile;
import in.kyle.ast.code.JavaFileType;
import in.kyle.ast.code.file.EnumElement;
import in.kyle.ast.code.file.Field;
import lombok.Data;

public class CodeGenAstVisitor extends AstBaseVisitor {
    
    private final CodeModifier modifier = new CodeModifier();
    
    @Override
    public FileTree visitAst(AstParser.AstContext ctx) {
        FileTree tree = new FileTree();
        for (AstParser.Ast_elementContext element : ctx.ast_element()) {
            if (element.default_definition() == null) {
                tree.addChildren("", visitAst_element(element));
            } else {
                visitDefault_definition(element.default_definition());
            }
        }
        tree.setModifier(modifier);
        return tree;
    }
    
    @Override
    public FileTree visitAst_element(AstParser.Ast_elementContext ctx) {
        if (ctx.default_definition() != null) {
            visitDefault_definition(ctx.default_definition());
            return null;
        } else if (ctx.enum_element() != null) {
            JavaFile file = visitEnum_element(ctx.enum_element());
            FileTree tree = new FileTree();
            tree.addFile(ctx.enum_element().IDENTIFIER().getText() + ".java", file);
            return tree;
        } else {
            return visitObject(ctx.object());
        }
    }
    
    @Override
    public Object visitDefault_definition(AstParser.Default_definitionContext ctx) {
        String value = ctx.STRING().getText();
        value = value.substring(1, value.length() - 1);
        modifier.getFieldDefaults().put(ctx.IDENTIFIER().getText(), value);
        return null;
    }
    
    @Override
    public FileTree visitObject(AstParser.ObjectContext ctx) {
        return visitObject(ctx, null);
    }
    
    private FileTree visitObject(AstParser.ObjectContext ctx, JavaFile superObject) {
        FileTree tree = new FileTree();
        JavaFile file = new JavaFile(ctx.IDENTIFIER().toString());
        int elementCount = 0;
        if (superObject != null) {
            file.setSuperType(superObject);
        }
        
        if (ctx.object_generic() != null) {
            addGenerics(ctx.object_generic(), file);
        }
        
        if (ctx.object_implements() != null) {
            visitObject_implements(ctx.object_implements()).forEach(file::addImplementingType);
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
                Field field = new Field(type.getKey(), type.getValue());
                file.getFields().add(field);
                elementCount++;
            }
        }
        if (elementCount == 0) {
            if (superObject == null || superObject.getType() != JavaFileType.CLASS) {
                file.setType(JavaFileType.INTERFACE);
            }
        }
        tree.addFile(className + ".java", file);
        return tree;
    }
    
    private void addGenerics(AstParser.Object_genericContext ctx, JavaFile file) {
        if (ctx.object_generic_define() != null) {
            file.setGenericDefine(ctx.object_generic_define().UPPER_LETTER().getText());
        } else {
            file.setGenericSuper(ctx.object_generic_super().IDENTIFIER().getText());
        }
    }
    
    @Override
    public List<String> visitObject_implements(AstParser.Object_implementsContext ctx) {
        return ctx.IDENTIFIER().stream().map(Object::toString).collect(Collectors.toList());
    }
    
    @Override
    public ObjectVariable visitObject_variable(AstParser.Object_variableContext ctx) {
        return new ObjectVariable(
                ctx.variable_type().getText() + visitDiamondType(ctx.diamondType()),
                ctx.IDENTIFIER().getText());
    }
    
    @Override
    public String visitDiamondType(AstParser.DiamondTypeContext ctx) {
        return ctx != null ? ctx.getText() : "";
    }
    
    @Override
    public JavaFile visitEnum_element(AstParser.Enum_elementContext ctx) {
        JavaFile file = new JavaFile(ctx.IDENTIFIER().getText());
        appendEnumElements(ctx, file);
        return file;
    }
    
    private void appendEnumElements(AstParser.Enum_elementContext ctx, JavaFile file) {
        for (AstParser.Enum_partContext part : ctx.enum_block().enum_part()) {
            EnumElement element = new EnumElement(part.IDENTIFIER().getText());
            for (TerminalNode value : part.STRING()) {
                element.getValues().add(value.toString());
            }
            file.getEnumElements().add(element);
        }
    }
    
    @Data
    private static class ObjectVariable {
        private final String key;
        private final String value;
    }
}

package in.kyle.ast.antlr;

import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import in.kyle.ast.AstBaseVisitor;
import in.kyle.ast.AstParser;
import in.kyle.ast.code.CodeModifier;
import in.kyle.ast.code.FileSet;
import in.kyle.ast.code.JavaFile;
import in.kyle.ast.code.JavaFileType;
import in.kyle.ast.code.file.EnumElement;
import in.kyle.ast.code.file.Field;
import lombok.Getter;

import static in.kyle.ast.code.JavaFileType.ABSTRACT_CLASS;
import static in.kyle.ast.code.JavaFileType.CLASS;

public class CodeGenAstVisitor extends AstBaseVisitor {
    
    @Getter
    private final CodeModifier modifier = new CodeModifier();
    
    @Override
    public AstFile visitAst(AstParser.AstContext ctx) {
        FileSet tree = new FileSet();
        for (AstParser.Ast_elementContext element : ctx.ast_element()) {
            if (element.default_definition() == null) {
                tree.addFiles(visitAst_element(element));
            } else {
                visitDefault_definition(element.default_definition());
            }
        }
        return new AstFile(tree, modifier);
    }
    
    @Override
    public Collection<JavaFile> visitAst_element(AstParser.Ast_elementContext ctx) {
        if (ctx.default_definition() != null) {
            visitDefault_definition(ctx.default_definition());
            return Collections.emptyList();
        } else if (ctx.enum_element() != null) {
            JavaFile file = visitEnum_element(ctx.enum_element());
            return Collections.singleton(file);
        } else if (ctx.variable_definition() != null) {
            visitVariable_definition(ctx.variable_definition());
            return Collections.emptyList();
        } else {
            return visitObject(ctx.object());
        }
    }
    
    @Override
    public Object visitVariable_definition(AstParser.Variable_definitionContext ctx) {
        String text = ctx.STRING().getText();
        text = text.substring(1, text.length() - 1);
        modifier.addVariable(ctx.IDENTIFIER().getText(), text);
        return null;
    }
    
    @Override
    public Object visitDefault_definition(AstParser.Default_definitionContext ctx) {
        String value = ctx.STRING().getText();
        value = value.substring(1, value.length() - 1);
        modifier.addFieldDefault(ctx.IDENTIFIER().getText(), value);
        return null;
    }
    
    @Override
    public Collection<JavaFile> visitObject(AstParser.ObjectContext ctx) {
        return visitObject(ctx, null);
    }
    
    private Collection<JavaFile> visitObject(AstParser.ObjectContext ctx, JavaFile superObject) {
        JavaFile file = new JavaFile(ctx.IDENTIFIER().toString(), CLASS);
        if (ctx.CONCRETE() != null) {
            file.setConcrete(true);
        }
        if (superObject != null) {
            if (superObject.typeIs(CLASS) || superObject.typeIs(ABSTRACT_CLASS)) {
                file.setSuperType(superObject);
            } else {
                file.addIsType(superObject.getName());
            }
            file.setPackageName(getPackageName(superObject));
        }
        addHeaderInfo(ctx, file);
        
        AstParser.Object_elementsContext elementsCtx = ctx.object_block().object_elements();
        Collection<JavaFile> files = visitObject_elements(file, elementsCtx);
        
        if (file.hasSuperType() && superObject.typeIs(CLASS) && file.typeIs(CLASS)) {
            if (!superObject.isConcrete()) {
                superObject.setType(ABSTRACT_CLASS);
            }
        }
        files.add(file);
        return files;
    }
    
    private Collection<JavaFile> visitObject_elements(JavaFile file,
                                                      AstParser.Object_elementsContext ctx) {
        if (ctx.object_variable().isEmpty()) {
            boolean isSuperTypeInterface = file.getSuperType() == null ||
                                           file.getSuperType().getType() == JavaFileType.INTERFACE;
            if (isSuperTypeInterface && !file.isConcrete()) {
                file.setType(JavaFileType.INTERFACE);
            }
        }
        
        for (AstParser.Object_variableContext variableCtx : ctx.object_variable()) {
            file.addField(visitObject_variable(variableCtx));
        }
        
        List<JavaFile> subObjects = new ArrayList<>();
        for (AstParser.ObjectContext objectCtx : ctx.object()) {
            Collection<JavaFile> javaFiles = visitObject(objectCtx, file);
            subObjects.addAll(javaFiles);
        }
        
        for (AstParser.Enum_elementContext enumCtx : ctx.enum_element()) {
            subObjects.add(visitEnum_element(enumCtx));
        }
        
        for (AstParser.Inside_objectContext insideCtx : ctx.inside_object()) {
            JavaFile innerEnum = visitEnum_element(insideCtx.enum_element());
            innerEnum.setType(JavaFileType.ENUM);
            file.addInnerClass(innerEnum);
        }
        
        for (AstParser.Variable_placeholderContext varCtx : ctx.variable_placeholder()) {
            file.addMethod(varCtx.IDENTIFIER().getText());
        }
        return subObjects;
    }
    
    
    private String getPackageName(JavaFile superObject) {
        String packageName = "";
        if (superObject.getPackageName() != null) {
            packageName += superObject.getPackageName() + ".";
        }
        packageName += superObject.getName().toLowerCase();
        return packageName;
    }
    
    private void addHeaderInfo(AstParser.ObjectContext ctx, JavaFile file) {
        if (ctx.object_generic() != null) {
            addGenerics(ctx.object_generic(), file);
        }
        
        if (ctx.object_implements() != null) {
            List<String> implementing = visitObject_implements(ctx.object_implements());
            implementing.forEach(file::addIsType);
        }
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
    public Field visitObject_variable(AstParser.Object_variableContext ctx) {
        return new Field(ctx.variable_type().getText(),
                         visitDiamondType(ctx.diamondType()),
                         ctx.IDENTIFIER().getText(),
                         null);
    }
    
    @Override
    public String visitDiamondType(AstParser.DiamondTypeContext ctx) {
        return ctx != null ? ctx.typeList().getText() : null;
    }
    
    @Override
    public JavaFile visitEnum_element(AstParser.Enum_elementContext ctx) {
        JavaFile file = new JavaFile(ctx.IDENTIFIER().getText(), JavaFileType.ENUM);
        if (ctx.object_implements() != null) {
            file.addIsTypes(visitObject_implements(ctx.object_implements()));
        }
        appendEnumElements(ctx, file);
        return file;
    }
    
    private void appendEnumElements(AstParser.Enum_elementContext ctx, JavaFile file) {
        for (TerminalNode variableCtx : ctx.enum_block().enum_header().IDENTIFIER()) {
            file.addEnumVariable(variableCtx.getText());
        }
        for (AstParser.Enum_partContext part : ctx.enum_block().enum_part()) {
            EnumElement element = new EnumElement(part.IDENTIFIER().getText());
            for (TerminalNode value : part.STRING()) {
                element.getValues().add(value.toString());
            }
            file.getEnumElements().add(element);
        }
        for (AstParser.Variable_placeholderContext varCtx : ctx.enum_block()
                                                               .variable_placeholder()) {
            file.addMethod(varCtx.IDENTIFIER().getText());
        }
    }
}
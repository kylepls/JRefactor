package in.kyle.antlr;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import in.kyle.antlr.gen.Java8BaseVisitor;
import in.kyle.antlr.gen.Java8Parser;
import in.kyle.antlr.gen.Java8Parser.AdditionalBoundContext;
import in.kyle.antlr.gen.Java8Parser.AnnotationContext;
import in.kyle.antlr.gen.Java8Parser.ClassBodyDeclarationContext;
import in.kyle.antlr.gen.Java8Parser.ClassDeclarationContext;
import in.kyle.antlr.gen.Java8Parser.ClassModifierContext;
import in.kyle.antlr.gen.Java8Parser.ExceptionTypeContext;
import in.kyle.antlr.gen.Java8Parser.FieldModifierContext;
import in.kyle.antlr.gen.Java8Parser.FormalParameterContext;
import in.kyle.antlr.gen.Java8Parser.FormalParameterListContext;
import in.kyle.antlr.gen.Java8Parser.ImportDeclarationContext;
import in.kyle.antlr.gen.Java8Parser.InterfaceTypeContext;
import in.kyle.antlr.gen.Java8Parser.MethodHeaderContext;
import in.kyle.antlr.gen.Java8Parser.MethodModifierContext;
import in.kyle.antlr.gen.Java8Parser.PackageDeclarationContext;
import in.kyle.antlr.gen.Java8Parser.TypeDeclarationContext;
import in.kyle.antlr.gen.Java8Parser.TypeParameterContext;
import in.kyle.antlr.gen.Java8Parser.TypeParametersContext;
import in.kyle.antlr.gen.Java8Parser.VariableDeclaratorContext;
import in.kyle.parser.block.JBlock;
import in.kyle.parser.expression.JConditionalExpression;
import in.kyle.parser.expression.JExpression;
import in.kyle.parser.expression.JLeftRightExpression;
import in.kyle.parser.expression.JParenthesisExpression;
import in.kyle.parser.expression.literal.JIntegerLiteral;
import in.kyle.parser.expression.literal.JStringLiteral;
import in.kyle.parser.unit.JAnnotation;
import in.kyle.parser.unit.JClass;
import in.kyle.parser.unit.JCompilationUnit;
import in.kyle.parser.unit.JField;
import in.kyle.parser.unit.JImport;
import in.kyle.parser.unit.JMethod;
import in.kyle.parser.unit.JModifier;
import in.kyle.parser.unit.JPackage;
import in.kyle.parser.unit.JParameter;
import in.kyle.parser.unit.JType;
import in.kyle.parser.unit.JTypeName;
import in.kyle.parser.unit.JTypeParameter;
import in.kyle.parser.unit.JVariable;

public class JavaCompositionVisitor extends Java8BaseVisitor<Object> {
    
    @Override
    public Object visitCompilationUnit(Java8Parser.CompilationUnitContext ctx) {
        JCompilationUnit unit = new JCompilationUnit();
        JPackage jPackage = null;
        if (ctx.packageDeclaration() != null) {
            jPackage = (JPackage) visitPackageDeclaration(ctx.packageDeclaration());
        }
        Set<JImport> imports = visitImports(ctx.importDeclaration());
        Set<JClass> types = visitTypeDeclarations(ctx.typeDeclaration());
        
        unit.setPackageName(jPackage);
        unit.setImports(imports);
        unit.setTypes(types);
        
        return unit;
    }
    
    @Override
    public Object visitPackageDeclaration(PackageDeclarationContext ctx) {
        String name = ctx.packageName().getText();
        JPackage jPackage = new JPackage();
        jPackage.setName(name);
        return jPackage;
    }
    
    @Override
    public Object visitImportDeclaration(ImportDeclarationContext ctx) {
        JImport jImport = new JImport();
        jImport.setName(ctx.packageOrTypeName().getText());
        if (ctx.import_static() != null) {
            jImport.setStatik(true);
        }
        if (ctx.import_wildcard() != null) {
            jImport.setOnDemand(true);
        }
        return jImport;
    }
    
    @Override
    public Object visitClassDeclaration(ClassDeclarationContext ctx) {
        
        Set<JAnnotation> annotations = visitAnnotations(ctx.annotation());
        
        Set<JModifier> modifiers = new LinkedHashSet<>();
        for (ClassModifierContext classModifierCtx : ctx.classModifier()) {
            modifiers.add(JModifier.valueOf(classModifierCtx.getText().toUpperCase()));
        }
        
        JClass jClass = new JClass();
        jClass.setAnnotations(annotations);
        jClass.setModifiers(modifiers);
        jClass.setName(ctx.Identifier().getText());
        
        setClassSuperClass(ctx, jClass);
        setClassSuperInterfaces(ctx, jClass);
        setClassTypeParameters(ctx, jClass);
        
        List<ClassBodyDeclarationContext> declarationContexts =
                ctx.classBody().classBodyDeclaration();
        for (ClassBodyDeclarationContext declarationContext : declarationContexts) {
            addClassDeclarationElement(jClass, declarationContext);
        }
        
        return jClass;
    }
    
    private void setClassTypeParameters(ClassDeclarationContext ctx, JClass jClass) {
        if (ctx.typeParameters() != null) {
            List<TypeParameterContext> list =
                    ctx.typeParameters().typeParameterList().typeParameter();
            Set<JTypeParameter> parameters = new LinkedHashSet<>();
            for (TypeParameterContext typeParameterContext : list) {
                parameters.add((JTypeParameter) visit(typeParameterContext));
            }
            jClass.setTypeParameters(parameters);
        }
    }
    
    @Override
    public Object visitTypeParameter(TypeParameterContext ctx) {
        JTypeParameter typeParameter = new JTypeParameter(ctx.Identifier().getText());
        if (ctx.typeBound() != null) {
            typeParameter.setBounds((Set<JTypeName>) visit(ctx.typeBound()));
        }
        return typeParameter;
    }
    
    @Override
    public Object visitSimpleTypeBound(Java8Parser.SimpleTypeBoundContext ctx) {
        JTypeName typeName = new JTypeName(ctx.typeVariable().getText());
        return new LinkedHashSet<>(Arrays.asList(typeName));
    }
    
    @Override
    public Object visitClassTypeBound(Java8Parser.ClassTypeBoundContext ctx) {
        JTypeName initial = new JTypeName(ctx.classOrInterfaceType().getText());
        LinkedHashSet<JTypeName> set = new LinkedHashSet<>();
        set.add(initial);
        for (AdditionalBoundContext e : ctx.additionalBound()) {
            set.add(new JTypeName(e.getText()));
        }
        return set;
    }
    
    private void setClassSuperClass(ClassDeclarationContext ctx, JClass jClass) {
        if (ctx.superclass() != null) {
            JTypeName extendsType = new JTypeName(ctx.superclass().getText());
            jClass.setExtendsType(extendsType);
        }
    }
    
    private void setClassSuperInterfaces(ClassDeclarationContext ctx, JClass jClass) {
        if (ctx.superinterfaces() != null) {
            List<InterfaceTypeContext> interfaceContexts =
                    ctx.superinterfaces().interfaceTypeList().interfaceType();
            if (!interfaceContexts.isEmpty()) {
                for (InterfaceTypeContext interfaceContext : interfaceContexts) {
                    JTypeName typeName = new JTypeName(interfaceContext.getText());
                    jClass.addImplementingType(typeName);
                }
            }
        }
    }
    
    private void addClassDeclarationElement(JClass jClass, ClassBodyDeclarationContext ctx) {
        if (ctx.classDeclaration() != null) {
            JType type = (JType) visitClassDeclaration(ctx.classDeclaration());
            jClass.addMember(type);
        } else if (ctx.interfaceDeclaration() != null) {
            JType type = (JType) visitInterfaceDeclaration(ctx.interfaceDeclaration());
            jClass.addMember(type);
        } else if (ctx.fieldDeclaration() != null) {
            jClass.addMember((JField) visitFieldDeclaration(ctx.fieldDeclaration()));
        } else if (ctx.methodDeclaration() != null) {
            jClass.addMember((JMethod) visitMethodDeclaration(ctx.methodDeclaration()));
        } else if (ctx.constructorDeclaration() != null) {
            // constructor
        } else if (ctx.instanceInitializer() != null) {
            // instance
        } else if (ctx.staticInitializer() != null) {
            // static
        }
    }
    
    @Override
    public Object visitFieldDeclaration(Java8Parser.FieldDeclarationContext ctx) {
        String type = ctx.unannType().getText();
        JTypeName typeName = new JTypeName(type);
        
        JField field = new JField();
        for (VariableDeclaratorContext var : ctx.variableDeclaratorList().variableDeclarator()) {
            field.addVariable((JVariable) visitVariableDeclarator(var));
        }
        
        field.setModifiers(new LinkedHashSet<>(createFieldModifiers(ctx.fieldModifier())));
        field.setType(typeName);
        
        return field;
    }
    
    @Override
    public Object visitExpressionParenthesis(Java8Parser.ExpressionParenthesisContext ctx) {
        return new JParenthesisExpression((JExpression) visit(ctx.expression()));
    }
    
    @Override
    public Object visitExpressionAdd(Java8Parser.ExpressionAddContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.ADD,
                                        (JExpression) visit(ctx.additiveExpression()),
                                        (JExpression) visit(ctx.multiplicativeExpression()));
    }
    
    @Override
    public Object visitConditionalTernary(Java8Parser.ConditionalTernaryContext ctx) {
        return new JConditionalExpression((JExpression) visit(ctx.conditionalOrExpression()),
                                          (JExpression) visit(ctx.expression()),
                                          (JExpression) visit(ctx.conditionalExpression()));
    }
    
    @Override
    public Object visitConditionalOr(Java8Parser.ConditionalOrContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.CONDITIONAL_OR,
                                        (JExpression) visit(ctx.conditionalOrExpression()),
                                        (JExpression) visit(ctx.conditionalAndExpression()));
    }
    
    @Override
    public Object visitConditionalAnd(Java8Parser.ConditionalAndContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.CONDITIONAL_OR,
                                        (JExpression) visit(ctx.conditionalAndExpression()),
                                        (JExpression) visit(ctx.inclusiveOrExpression()));
    }
    
    @Override
    public Object visitBinaryInclusiveOr(Java8Parser.BinaryInclusiveOrContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.BINARY_INCLUSIVE_OR,
                                        (JExpression) visit(ctx.inclusiveOrExpression()),
                                        (JExpression) visit(ctx.exclusiveOrExpression()));
    }
    
    @Override
    public Object visitBinaryExclusiveOr(Java8Parser.BinaryExclusiveOrContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.BINARY_EXCLUSIVE_OR,
                                        (JExpression) visit(ctx.exclusiveOrExpression()),
                                        (JExpression) visit(ctx.andExpression()));
    }
    
    @Override
    public Object visitBinaryAnd(Java8Parser.BinaryAndContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.BINARY_AND,
                                        (JExpression) visit(ctx.andExpression()),
                                        (JExpression) visit(ctx.equalityExpression()));
    }
    
    @Override
    public Object visitConditionalEquality(Java8Parser.ConditionalEqualityContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.EQUAL,
                                        (JExpression) visit(ctx.equalityExpression()),
                                        (JExpression) visit(ctx.relationalExpression()));
    }
    
    @Override
    public Object visitConditionalNotEquality(Java8Parser.ConditionalNotEqualityContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.NOT_EQUAL,
                                        (JExpression) visit(ctx.equalityExpression()),
                                        (JExpression) visit(ctx.relationalExpression()));
    }
    
    @Override
    public Object visitConditionalLessThan(Java8Parser.ConditionalLessThanContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.CONDITIONAL_LESS_THAN,
                                        (JExpression) visit(ctx.relationalExpression()),
                                        (JExpression) visit(ctx.shiftExpression()));
    }
    
    @Override
    public Object visitConditionalGreaterThan(Java8Parser.ConditionalGreaterThanContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.CONDITIONAL_GREATER_THAN,
                                        (JExpression) visit(ctx.relationalExpression()),
                                        (JExpression) visit(ctx.shiftExpression()));
    }
    
    @Override
    public Object visitConditionalLessThanEq(Java8Parser.ConditionalLessThanEqContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.CONDITIONAL_LESS_THAN_EQUAL,
                                        (JExpression) visit(ctx.relationalExpression()),
                                        (JExpression) visit(ctx.shiftExpression()));
    }
    
    @Override
    public Object visitConditionalGreatherThanEq(Java8Parser.ConditionalGreatherThanEqContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.CONDITIONAL_GREATER_EQUAL,
                                        (JExpression) visit(ctx.relationalExpression()),
                                        (JExpression) visit(ctx.shiftExpression()));
    }
    
    @Override
    public Object visitConditionalInstanceOf(Java8Parser.ConditionalInstanceOfContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.INSTANCE_OF,
                                        (JExpression) visit(ctx.relationalExpression()),
                                        (JExpression) visit(ctx.referenceType()));
    }
    
    @Override
    public Object visitBinaryShiftLeft(Java8Parser.BinaryShiftLeftContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.BINARY_SHIFT_LEFT,
                                        (JExpression) visit(ctx.shiftExpression()),
                                        (JExpression) visit(ctx.additiveExpression()));
    }
    
    @Override
    public Object visitBinaryShiftRight(Java8Parser.BinaryShiftRightContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.BINARY_SHIFT_RIGHT,
                                        (JExpression) visit(ctx.shiftExpression()),
                                        (JExpression) visit(ctx.additiveExpression()));
    }
    
    @Override
    public Object visitBinarcyAllignRight(Java8Parser.BinarcyAllignRightContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.BINARY_ALLIGN_RIGHT,
                                        (JExpression) visit(ctx.shiftExpression()),
                                        (JExpression) visit(ctx.additiveExpression()));
    }
    
    @Override
    public Object visitExpressionSubtract(Java8Parser.ExpressionSubtractContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.SUBTRACT,
                                        (JExpression) visit(ctx.additiveExpression()),
                                        (JExpression) visit(ctx.multiplicativeExpression()));
    }
    
    @Override
    public Object visitExpressionDivide(Java8Parser.ExpressionDivideContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.DIVIDE,
                                        (JExpression) visit(ctx.multiplicativeExpression()),
                                        (JExpression) visit(ctx.unaryExpression()));
    }
    
    @Override
    public Object visitExpressionMultiply(Java8Parser.ExpressionMultiplyContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.MULTIPLY,
                                        (JExpression) visit(ctx.multiplicativeExpression()),
                                        (JExpression) visit(ctx.unaryExpression()));
    }
    
    @Override
    public Object visitExpressionModulus(Java8Parser.ExpressionModulusContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.MODULUS,
                                        (JExpression) visit(ctx.multiplicativeExpression()),
                                        (JExpression) visit(ctx.unaryExpression()));
    }
    
    @Override
    public Object visitIntegerLiteral(Java8Parser.IntegerLiteralContext ctx) {
        return new JIntegerLiteral(Integer.parseInt(ctx.getText()));
    }
    
    @Override
    public Object visitStringLiteral(Java8Parser.StringLiteralContext ctx) {
        return new JStringLiteral(ctx.getText().substring(1, ctx.getText().length() - 1));
    }
    
    @Override
    public Object visitVariableDeclarator(VariableDeclaratorContext ctx) {
        String name = ctx.variableDeclaratorId().getText();
        
        JVariable variable = new JVariable();
        variable.setName(name);
        
        if (ctx.variableInitializer() != null) {
            variable.setInitializer((JExpression) visit(ctx.variableInitializer()));
        }
        
        return variable;
    }
    
    private List<JModifier> createFieldModifiers(Collection<FieldModifierContext> ctx) {
        return ctx.stream()
                  .map(fieldModifier -> JModifier.valueOf(fieldModifier.getText().toUpperCase()))
                  .collect(Collectors.toList());
    }
    
    @Override
    public Object visitEnumDeclaration(Java8Parser.EnumDeclarationContext ctx) {
        return super.visitEnumDeclaration(ctx);
    }
    
    @Override
    public Object visitInterfaceDeclaration(Java8Parser.InterfaceDeclarationContext ctx) {
        return super.visitInterfaceDeclaration(ctx);
    }
    
    private Set<JAnnotation> visitAnnotations(Collection<AnnotationContext> list) {
        return list.stream()
                   .map(this::visitAnnotation)
                   .map(JAnnotation.class::cast)
                   .collect(Collectors.toSet());
    }
    
    private Set<JClass> visitTypeDeclarations(List<TypeDeclarationContext> list) {
        return list.stream()
                   .map(this::visitTypeDeclaration)
                   .map(JClass.class::cast)
                   .collect(Collectors.toSet());
    }
    
    private Set<JImport> visitImports(Collection<ImportDeclarationContext> imports) {
        return imports.stream()
                      .map(this::visitImportDeclaration)
                      .map(JImport.class::cast)
                      .collect(Collectors.toSet());
    }
    
    @Override
    public Object visitMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
        MethodHeaderContext header = ctx.methodHeader();
        JMethod method = new JMethod(header.methodDeclarator().Identifier().getText());
        
        Set<JAnnotation> annotations = visitAnnotations(ctx.annotation());
        Set<JModifier> modifiers = getMethodModifiers(ctx.methodModifier());
        Set<JTypeParameter> typeParameters = getMethodTypes(header.typeParameters());
        
        Set<JParameter> parameters = new LinkedHashSet<>();
        
        if (header.methodDeclarator().formalParameterList() != null) {
            Set<JParameter> add =
                    visitFormalParameterList(header.methodDeclarator().formalParameterList());
            parameters.addAll(add);
        }
        JTypeName result = new JTypeName(header.result().getText());
        
        Set<JTypeName> throwsTypes = new LinkedHashSet<>();
        if (header.throws_() != null) {
            Set<JTypeName> add = visitThrows_(header.throws_());
            throwsTypes.addAll(add);
        }
        
        JBlock block = visitBlock(ctx.methodBody().block());
        
        method.setAnnotations(annotations);
        method.setModifiers(modifiers);
        method.setTypeParameters(typeParameters);
        method.setParameters(parameters);
        method.setResultType(result);
        method.setThrowsTypes(throwsTypes);
        method.setBody(block);
        return method;
    }
    
    @Override
    public JBlock visitBlock(Java8Parser.BlockContext ctx) {
        return new JBlock();
    }
    
    @Override
    public Set<JTypeName> visitThrows_(Java8Parser.Throws_Context ctx) {
        return visitExceptionTypeList(ctx.exceptionTypeList());
    }
    
    @Override
    public Set<JTypeName> visitExceptionTypeList(Java8Parser.ExceptionTypeListContext ctx) {
        Set<JTypeName> types = new LinkedHashSet<>();
        for (ExceptionTypeContext exceptionCtx : ctx.exceptionType()) {
            types.add(new JTypeName(exceptionCtx.getText()));
        }
        return types;
    }
    
    @Override
    public Set<JParameter> visitFormalParameterList(FormalParameterListContext ctx) {
        Set<JParameter> parameters = new LinkedHashSet<>();
        
        if (ctx.formalParameters() != null) {
            for (FormalParameterContext formalCtx : ctx.formalParameters().formalParameter()) {
                parameters.add((JParameter) visitFormalParameter(formalCtx));
            }
        }
        
        if (ctx.lastFormalParameter() != null) {
            parameters.add((JParameter) visitLastFormalParameter(ctx.lastFormalParameter()));
        }
        
        return parameters;
    }
    
    @Override
    public Object visitFormalParameter(FormalParameterContext ctx) {
        String name = ctx.variableDeclaratorId().getText();
        JParameter parameter = new JParameter(name);
        JTypeName type = new JTypeName(ctx.unannType().getText());
        parameter.setType(type);
        return parameter;
    }
    
    @Override
    public Object visitLastFormalParameter(Java8Parser.LastFormalParameterContext ctx) {
        String name = ctx.variableDeclaratorId().getText();
        JParameter parameter = new JParameter(name);
        JTypeName type = new JTypeName(ctx.unannType().getText());
        parameter.setType(type);
        return parameter;
    }
    
    private Set<JTypeParameter> getMethodTypes(TypeParametersContext ctx) {
        if (ctx != null) {
            List<TypeParameterContext> list = ctx.typeParameterList().typeParameter();
            return list.stream()
                       .map(e -> (JTypeParameter) visitTypeParameter(e))
                       .collect(Collectors.toCollection(LinkedHashSet::new));
        } else {
            return Collections.emptySet();
        }
    }
    
    private Set<JModifier> getMethodModifiers(Collection<MethodModifierContext> set) {
        return set.stream()
                  .map(e -> JModifier.fromJava(e.getText()))
                  .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}

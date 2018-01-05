package in.kyle.jrefactor.parser.antlr;

import org.antlr.v4.runtime.RuleContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.JObjectList;
import in.kyle.jrefactor.parser.antlr.gen.Java8BaseVisitor;
import in.kyle.jrefactor.parser.antlr.gen.Java8Parser;
import in.kyle.jrefactor.parser.antlr.gen.Java8Parser.*;
import in.kyle.jrefactor.parser.expression.*;
import in.kyle.jrefactor.parser.expression.lambda.JIdentifierParameter;
import in.kyle.jrefactor.parser.expression.lambda.JLambdaBody;
import in.kyle.jrefactor.parser.expression.lambda.JLambdaExpression;
import in.kyle.jrefactor.parser.expression.lambda.JLambdaParameters;
import in.kyle.jrefactor.parser.expression.lambda.JInferredParameters;
import in.kyle.jrefactor.parser.expression.literal.*;
import in.kyle.jrefactor.parser.statement.*;
import in.kyle.jrefactor.parser.statement.control.*;
import in.kyle.jrefactor.parser.statement.control.loops.JDoWhileStatement;
import in.kyle.jrefactor.parser.statement.control.loops.JEnhancedForStatement;
import in.kyle.jrefactor.parser.statement.control.loops.JWhileStatement;
import in.kyle.jrefactor.parser.unit.*;
import in.kyle.jrefactor.parser.unit.body.JArgumentList;
import in.kyle.jrefactor.parser.unit.body.JConstructorDeclaration;
import in.kyle.jrefactor.parser.unit.body.JMethod;
import in.kyle.jrefactor.parser.unit.body.JMethodHeader;
import in.kyle.jrefactor.parser.unit.body.JParameter;
import in.kyle.jrefactor.parser.unit.body.JVariable;
import in.kyle.jrefactor.parser.unit.body.annotationtype.JAnnotationBody;
import in.kyle.jrefactor.parser.unit.body.annotationtype.JAnnotationMember;
import in.kyle.jrefactor.parser.unit.body.annotationtype.JAnnotationTypeElement;
import in.kyle.jrefactor.parser.unit.body.annotationtype.JElementValue;
import in.kyle.jrefactor.parser.unit.body.classtype.JClassBody;
import in.kyle.jrefactor.parser.unit.body.classtype.JClassInstanceInitializer;
import in.kyle.jrefactor.parser.unit.body.classtype.JClassMember;
import in.kyle.jrefactor.parser.unit.body.classtype.JClassStaticInitializer;
import in.kyle.jrefactor.parser.unit.body.classtype.JField;
import in.kyle.jrefactor.parser.unit.body.enumtype.JEnumBody;
import in.kyle.jrefactor.parser.unit.body.enumtype.JEnumConstant;
import in.kyle.jrefactor.parser.unit.body.interfacetype.JInterfaceBody;
import in.kyle.jrefactor.parser.unit.body.interfacetype.JInterfaceMember;
import in.kyle.jrefactor.parser.unit.body.interfacetype.JInterfaceMethod;
import in.kyle.jrefactor.parser.unit.types.JAnnotationDeclaration;
import in.kyle.jrefactor.parser.unit.types.JClassDeclaration;
import in.kyle.jrefactor.parser.unit.types.JEnumDeclaration;
import in.kyle.jrefactor.parser.unit.types.JInterfaceDeclaration;

public class JavaCompositionVisitor extends Java8BaseVisitor<Object> {
    
    private static <T extends JObject> Collector<T, ?, JObjectList<T>> createListCollector() {
        return Collectors.toCollection(JObjectList::new);
    }
    
    @Override
    public JCompilationUnit visitCompilationUnit(Java8Parser.CompilationUnitContext ctx) {
        JCompilationUnit unit = new JCompilationUnit();
        JPackage jPackage = null;
        if (ctx.packageDeclaration() != null) {
            jPackage = visitPackageDeclaration(ctx.packageDeclaration());
        }
        JObjectList<JImport> imports = visitImports(ctx.importDeclaration());
        JObjectList<JTypeDeclaration> types = visitTypeDeclarations(ctx.typeDeclaration());
        
        unit.setPackageName(jPackage);
        unit.setImports(imports);
        unit.setTypes(types);
        
        return unit;
    }
    
    @Override
    public JPackage visitPackageDeclaration(PackageDeclarationContext ctx) {
        String name = ctx.packageName().getText();
        JPackage jPackage = new JPackage();
        jPackage.setName(name);
        return jPackage;
    }
    
    @Override
    public JImport visitImportDeclaration(ImportDeclarationContext ctx) {
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
    public JClassDeclaration visitClassDeclaration(ClassDeclarationContext ctx) {
        JObjectList<JAnnotation> annotations = visitAnnotations(ctx.annotation());
        
        
        JClassDeclaration jClassDeclaration = new JClassDeclaration();
        jClassDeclaration.setAnnotations(annotations);
        jClassDeclaration.setModifiers(visitClassModifiers(ctx.classModifier()));
        jClassDeclaration.setIdentifier(visitIdentifier(ctx.identifier()));
        
        setClassSuperClass(ctx, jClassDeclaration);
        if (ctx.superinterfaces() != null) {
            jClassDeclaration.setSuperInterfaces(visitSuperinterfaces(ctx.superinterfaces()));
        }
        setClassTypeParameters(ctx, jClassDeclaration);
        
        
        jClassDeclaration.setBody(visitClassBody(ctx.classBody()));
        return jClassDeclaration;
    }
    
    @Override
    public JObjectList<JTypeName> visitSuperinterfaces(SuperinterfacesContext ctx) {
        return visitInterfaceTypeList(ctx.interfaceTypeList());
    }
    
    @Override
    public JObjectList<JTypeName> visitInterfaceTypeList(InterfaceTypeListContext ctx) {
        JObjectList<JTypeName> types = new JObjectList<>();
        for (InterfaceTypeContext interfaceContext : ctx.interfaceType()) {
            JTypeName typeName = new JTypeName(interfaceContext.getText());
            types.add(typeName);
        }
        return types;
    }
    
    private void setClassTypeParameters(ClassDeclarationContext ctx,
                                        JClassDeclaration jClassDeclaration) {
        if (ctx.typeParameters() != null) {
            List<TypeParameterContext> list =
                    ctx.typeParameters().typeParameterList().typeParameter();
            JObjectList<JTypeParameter> parameters = new JObjectList<>();
            for (TypeParameterContext typeParameterContext : list) {
                parameters.add(visitTypeParameter(typeParameterContext));
            }
            jClassDeclaration.setTypeParameters(parameters);
        }
    }
    
    @Override
    public JTypeParameter visitTypeParameter(TypeParameterContext ctx) {
        JTypeParameter typeParameter = new JTypeParameter(visitIdentifier(ctx.identifier()));
        if (ctx.typeBound() != null) {
            typeParameter.setBounds((JObjectList<JTypeName>) visit(ctx.typeBound()));
        }
        return typeParameter;
    }
    
    @Override
    public JObjectList<JTypeName> visitSimpleTypeBound(Java8Parser.SimpleTypeBoundContext ctx) {
        JTypeName typeName = new JTypeName(ctx.typeVariable().getText());
        return new JObjectList<>(Collections.singletonList(typeName));
    }
    
    @Override
    public JObjectList<JTypeName> visitClassTypeBound(Java8Parser.ClassTypeBoundContext ctx) {
        JTypeName initial = new JTypeName(ctx.classOrInterfaceType().getText());
        JObjectList<JTypeName> set = new JObjectList<>();
        set.add(initial);
        for (AdditionalBoundContext e : ctx.additionalBound()) {
            set.add(new JTypeName(e.getText()));
        }
        return set;
    }
    
    private void setClassSuperClass(ClassDeclarationContext ctx,
                                    JClassDeclaration jClassDeclaration) {
        if (ctx.superclass() != null) {
            JTypeName extendsType = new JTypeName(ctx.superclass().getText());
            jClassDeclaration.setExtendsType(extendsType);
        }
    }
    
    
    @Override
    public JClassBody visitClassBody(ClassBodyContext ctx) {
        JClassBody body = new JClassBody();
        body.setMembers(visitClassBodyDeclarations(ctx.classBodyDeclaration()));
        return body;
    }
    
    @Override
    public JClassMember visitClassBodyDeclaration(ClassBodyDeclarationContext ctx) {
        if (ctx.classDeclaration() != null) {
            return visitClassDeclaration(ctx.classDeclaration());
        } else if (ctx.interfaceDeclaration() != null) {
            return (JClassMember) visitInterfaceDeclaration(ctx.interfaceDeclaration());
        } else if (ctx.fieldDeclaration() != null) {
            return visitFieldDeclaration(ctx.fieldDeclaration());
        } else if (ctx.methodDeclaration() != null) {
            return visitMethodDeclaration(ctx.methodDeclaration());
        } else if (ctx.constructorDeclaration() != null) {
            // constructor
        } else if (ctx.instanceInitializer() != null) {
            // instance
        } else if (ctx.staticInitializer() != null) {
            // static
        }
        return null;
    }
    
    @Override
    public JField visitFieldDeclaration(Java8Parser.FieldDeclarationContext ctx) {
        String type = ctx.unannType().getText();
        JTypeName typeName = new JTypeName(type);
        
        JField field = new JField();
        field.setVariables(visitVariableDeclaratorList(ctx.variableDeclaratorList()));
        field.setModifiers(new JObjectList<>(createFieldModifiers(ctx.fieldModifier())));
        field.setType(typeName);
        
        return field;
    }
    
    @Override
    public JObjectList<JVariable> visitVariableDeclaratorList(VariableDeclaratorListContext ctx) {
        JObjectList<JVariable> variables = new JObjectList<>();
        for (VariableDeclaratorContext var : ctx.variableDeclarator()) {
            variables.add(visitVariableDeclarator(var));
        }
        return variables;
    }
    
    @Override
    public JParenthesisExpression visitExpressionParenthesis(Java8Parser.ExpressionParenthesisContext ctx) {
        return new JParenthesisExpression((JExpression) visit(ctx.expression()));
    }
    
    @Override
    public JLeftRightExpression visitExpressionAdd(Java8Parser.ExpressionAddContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.ADD,
                                        (JExpression) visit(ctx.additiveExpression()),
                                        (JExpression) visit(ctx.multiplicativeExpression()));
    }
    
    @Override
    public JTernaryExpression visitConditionalTernary(Java8Parser.ConditionalTernaryContext ctx) {
        return new JTernaryExpression((JExpression) visit(ctx.conditionalOrExpression()),
                                      (JExpression) visit(ctx.expression()),
                                      (JExpression) visit(ctx.conditionalExpression()));
    }
    
    @Override
    public JLeftRightExpression visitConditionalOr(Java8Parser.ConditionalOrContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.CONDITIONAL_OR,
                                        (JExpression) visit(ctx.conditionalOrExpression()),
                                        (JExpression) visit(ctx.conditionalAndExpression()));
    }
    
    @Override
    public JLeftRightExpression visitConditionalAnd(Java8Parser.ConditionalAndContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.CONDITIONAL_OR,
                                        (JExpression) visit(ctx.conditionalAndExpression()),
                                        (JExpression) visit(ctx.inclusiveOrExpression()));
    }
    
    @Override
    public JLeftRightExpression visitBinaryInclusiveOr(Java8Parser.BinaryInclusiveOrContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.BINARY_INCLUSIVE_OR,
                                        (JExpression) visit(ctx.inclusiveOrExpression()),
                                        (JExpression) visit(ctx.exclusiveOrExpression()));
    }
    
    @Override
    public JLeftRightExpression visitBinaryExclusiveOr(Java8Parser.BinaryExclusiveOrContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.BINARY_EXCLUSIVE_OR,
                                        (JExpression) visit(ctx.exclusiveOrExpression()),
                                        (JExpression) visit(ctx.andExpression()));
    }
    
    @Override
    public JLeftRightExpression visitBinaryAnd(Java8Parser.BinaryAndContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.BINARY_AND,
                                        (JExpression) visit(ctx.andExpression()),
                                        (JExpression) visit(ctx.equalityExpression()));
    }
    
    @Override
    public JLeftRightExpression visitConditionalEquality(Java8Parser.ConditionalEqualityContext 
                                                                     ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.EQUAL,
                                        (JExpression) visit(ctx.equalityExpression()),
                                        (JExpression) visit(ctx.relationalExpression()));
    }
    
    @Override
    public JLeftRightExpression visitConditionalNotEquality(Java8Parser.ConditionalNotEqualityContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.NOT_EQUAL,
                                        (JExpression) visit(ctx.equalityExpression()),
                                        (JExpression) visit(ctx.relationalExpression()));
    }
    
    @Override
    public JLeftRightExpression visitConditionalLessThan(Java8Parser.ConditionalLessThanContext 
                                                                     ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.CONDITIONAL_LESS_THAN,
                                        (JExpression) visit(ctx.relationalExpression()),
                                        (JExpression) visit(ctx.shiftExpression()));
    }
    
    @Override
    public JLeftRightExpression visitConditionalGreaterThan(Java8Parser.ConditionalGreaterThanContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.CONDITIONAL_GREATER_THAN,
                                        (JExpression) visit(ctx.relationalExpression()),
                                        (JExpression) visit(ctx.shiftExpression()));
    }
    
    @Override
    public JLeftRightExpression visitConditionalLessThanEq(Java8Parser.ConditionalLessThanEqContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.CONDITIONAL_LESS_THAN_EQUAL,
                                        (JExpression) visit(ctx.relationalExpression()),
                                        (JExpression) visit(ctx.shiftExpression()));
    }
    
    @Override
    public JLeftRightExpression visitConditionalGreatherThanEq(Java8Parser.ConditionalGreatherThanEqContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.CONDITIONAL_GREATER_EQUAL,
                                        (JExpression) visit(ctx.relationalExpression()),
                                        (JExpression) visit(ctx.shiftExpression()));
    }
    
    @Override
    public JLeftRightExpression visitConditionalInstanceOf(Java8Parser.ConditionalInstanceOfContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.INSTANCE_OF,
                                        (JExpression) visit(ctx.relationalExpression()),
                                        (JExpression) visit(ctx.referenceType()));
    }
    
    @Override
    public JLeftRightExpression visitBinaryShiftLeft(Java8Parser.BinaryShiftLeftContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.BINARY_SHIFT_LEFT,
                                        (JExpression) visit(ctx.shiftExpression()),
                                        (JExpression) visit(ctx.additiveExpression()));
    }
    
    @Override
    public JLeftRightExpression visitBinaryShiftRight(Java8Parser.BinaryShiftRightContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.BINARY_SHIFT_RIGHT,
                                        (JExpression) visit(ctx.shiftExpression()),
                                        (JExpression) visit(ctx.additiveExpression()));
    }
    
    @Override
    public JLeftRightExpression visitBinarcyAllignRight(Java8Parser.BinarcyAllignRightContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.BINARY_ALLIGN_RIGHT,
                                        (JExpression) visit(ctx.shiftExpression()),
                                        (JExpression) visit(ctx.additiveExpression()));
    }
    
    @Override
    public JLeftRightExpression visitExpressionSubtract(Java8Parser.ExpressionSubtractContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.SUBTRACT,
                                        (JExpression) visit(ctx.additiveExpression()),
                                        (JExpression) visit(ctx.multiplicativeExpression()));
    }
    
    @Override
    public JLeftRightExpression visitExpressionDivide(Java8Parser.ExpressionDivideContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.DIVIDE,
                                        (JExpression) visit(ctx.multiplicativeExpression()),
                                        (JExpression) visit(ctx.unaryExpression()));
    }
    
    @Override
    public JLeftRightExpression visitExpressionMultiply(Java8Parser.ExpressionMultiplyContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.MULTIPLY,
                                        (JExpression) visit(ctx.multiplicativeExpression()),
                                        (JExpression) visit(ctx.unaryExpression()));
    }
    
    @Override
    public JLeftRightExpression visitExpressionModulus(Java8Parser.ExpressionModulusContext ctx) {
        return new JLeftRightExpression(JLeftRightExpression.Operation.MODULUS,
                                        (JExpression) visit(ctx.multiplicativeExpression()),
                                        (JExpression) visit(ctx.unaryExpression()));
    }
    
    @Override
    public JNumericLiteral visitIntegerLiteral(Java8Parser.IntegerLiteralContext ctx) {
        String text = ctx.IntegerLiteral().getText();
        if (text.endsWith("L")) {
            return new JLongLiteral(Long.parseLong(ctx.getText()
                                                      .substring(0, ctx.getText().length() - 1)));
        } else {
            return new JIntegerLiteral(Integer.parseInt(ctx.getText()));
        }
    }
    
    @Override
    public JStringLiteral visitStringLiteral(Java8Parser.StringLiteralContext ctx) {
        return new JStringLiteral(ctx.getText().substring(1, ctx.getText().length() - 1));
    }
    
    @Override
    public JVariable visitVariableDeclarator(VariableDeclaratorContext ctx) {
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
    public JEnumDeclaration visitEnumDeclaration(Java8Parser.EnumDeclarationContext ctx) {
        JEnumDeclaration jEnumDeclaration = new JEnumDeclaration();
        jEnumDeclaration.setAnnotations(visitAnnotations(ctx.annotation()));
        jEnumDeclaration.setModifiers(visitClassModifiers(ctx.classModifier()));
        jEnumDeclaration.setIdentifier(visitIdentifier(ctx.identifier()));
        jEnumDeclaration.setSuperInterfaces(visitSuperinterfaces(ctx.superinterfaces()));
        jEnumDeclaration.setBody(visitEnumBody(ctx.enumBody()));
        return jEnumDeclaration;
    }
    
    @Override
    public JEnumBody visitEnumBody(EnumBodyContext ctx) {
        JEnumBody body = new JEnumBody();
        body.setConstants(visitEnumConstantList(ctx.enumConstantList()));
        body.setMembers(visitEnumBodyDeclarations(ctx.enumBodyDeclarations()));
        return body;
    }
    
    @Override
    public JObjectList<JClassMember> visitEnumBodyDeclarations(EnumBodyDeclarationsContext ctx) {
        return visitClassBodyDeclarations(ctx.classBodyDeclaration());
    }
    
    private JObjectList<JClassMember> visitClassBodyDeclarations
            (List<ClassBodyDeclarationContext> ctx) {
        JObjectList<JClassMember> members = new JObjectList<>();
        for (ClassBodyDeclarationContext c : ctx) {
            members.add(visitClassBodyDeclaration(c));
        }
        return members;
    }
    
    @Override
    public JObjectList<JEnumConstant> visitEnumConstantList(EnumConstantListContext ctx) {
        JObjectList<JEnumConstant> constants = new JObjectList<>();
        for (EnumConstantContext enumConstantContext : ctx.enumConstant()) {
            constants.add(visitEnumConstant(enumConstantContext));
        }
        return constants;
    }
    
    @Override
    public JEnumConstant visitEnumConstant(EnumConstantContext ctx) {
        JEnumConstant constant = new JEnumConstant(visitIdentifier(ctx.identifier()));
        constant.setAnnotations(visitAnnotations(ctx.annotation()));
        constant.setArgumentList(visitArgumentList(ctx.argumentList()));
        constant.setBody(visitClassBody(ctx.classBody()));
        return constant;
    }
    
    
    public JObjectList<JModifier> visitClassModifiers(Collection<ClassModifierContext> ctx) {
        JObjectList<JModifier> modifiers = new JObjectList<>();
        for (ClassModifierContext classModifierCtx : ctx) {
            modifiers.add(JModifier.valueOf(classModifierCtx.getText().toUpperCase()));
        }
        return modifiers;
    }
    
    @Override
    public JInterfaceDeclaration visitNormalInterfaceDeclaration
            (NormalInterfaceDeclarationContext ctx) {
        JInterfaceDeclaration jInterfaceDeclaration = new JInterfaceDeclaration();
        jInterfaceDeclaration.setAnnotations(visitAnnotations(ctx.annotation()));
        if (ctx.interfaceModifier() != null) {
            jInterfaceDeclaration.setModifiers(visitInterfaceModifiers(ctx.interfaceModifier()));
        }
        jInterfaceDeclaration.setIdentifier(visitIdentifier(ctx.identifier()));
        if (ctx.typeParameters() != null) {
            jInterfaceDeclaration.setTypeParameters(visitTypeParameters(ctx.typeParameters()));
        }
        
        if (ctx.extendsInterfaces() != null) {
            jInterfaceDeclaration.setSuperInterfaces(visitExtendsInterfaces(ctx.extendsInterfaces
                    ()));
        }
        
        jInterfaceDeclaration.setBody(visitInterfaceBody(ctx.interfaceBody()));
        return jInterfaceDeclaration;
    }
    
    @Override
    public JInterfaceBody visitInterfaceBody(InterfaceBodyContext ctx) {
        JInterfaceBody body = new JInterfaceBody();
        if (ctx.interfaceMemberDeclaration() != null) {
            body.setMembers(visitInterfaceMemberDeclarations(ctx.interfaceMemberDeclaration()));
        }
        return body;
    }
    
    private JObjectList<JInterfaceMember> visitInterfaceMemberDeclarations
            (List<InterfaceMemberDeclarationContext> ctx) {
        return ctx.stream()
                  .map(this::visitInterfaceMemberDeclaration)
                  .collect(createListCollector());
    }
    
    @Override
    public JInterfaceMethod visitInterfaceMethodDeclaration(InterfaceMethodDeclarationContext ctx) {
        JInterfaceMethod method = new JInterfaceMethod();
        method.setHeader(visitMethodHeader(ctx.methodHeader()));
        method.setBody(visitMethodBody(ctx.methodBody()));
        return method;
    }
    
    @Override
    public JBlock visitMethodBody(MethodBodyContext ctx) {
        return visitBlock(ctx.block());
    }
    
    @Override
    public JInterfaceMember visitInterfaceMemberDeclaration(InterfaceMemberDeclarationContext ctx) {
        return (JInterfaceMember) super.visitInterfaceMemberDeclaration(ctx);
    }
    
    @Override
    public JObjectList<JTypeName> visitExtendsInterfaces(ExtendsInterfacesContext ctx) {
        return visitInterfaceTypeList(ctx.interfaceTypeList());
    }
    
    private JObjectList<JModifier> visitInterfaceModifiers(List<InterfaceModifierContext> ctx) {
        return ctx.stream()
                  .map(mod -> JModifier.fromJava(mod.getText()))
                  .collect(createListCollector());
    }
    
    private JObjectList<JAnnotation> visitAnnotations(Collection<AnnotationContext> list) {
        return list.stream()
                   .map(this::visitAnnotation)
                   .map(JAnnotation.class::cast)
                   .collect(createListCollector());
    }
    
    private JObjectList<JTypeDeclaration> visitTypeDeclarations(List<TypeDeclarationContext> list) {
        return list.stream()
                   .map(this::visitTypeDeclaration)
                   .map(JTypeDeclaration.class::cast)
                   .collect(createListCollector());
    }
    
    private JObjectList<JImport> visitImports(Collection<ImportDeclarationContext> imports) {
        return imports.stream()
                      .map(this::visitImportDeclaration)
                      .map(JImport.class::cast)
                      .collect(createListCollector());
    }
    
    @Override
    public JMethod visitMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
        JMethod method = new JMethod();
        method.setHeader(visitMethodHeader(ctx.methodHeader()));
        method.setBody(visitBlock(ctx.methodBody().block()));
        
        JObjectList<JModifier> modifiers = getMethodModifiers(ctx.methodModifier());
        method.getHeader().setModifiers(modifiers);
        
        return method;
    }
    
    @Override
    public JMethodHeader visitMethodHeader(MethodHeaderContext ctx) {
        JMethodHeader header =
                new JMethodHeader(visitIdentifier(ctx.methodDeclarator().identifier()));
        header.setAnnotations(visitAnnotations(ctx.annotation()));
        if (ctx.typeParameters() != null) {
            header.setTypeParameters(visitTypeParameters(ctx.typeParameters()));
        }
        
        if (ctx.methodDeclarator().formalParameterList() != null) {
            header.setParameterList(visitFormalParameterList(ctx.methodDeclarator()
                                                                .formalParameterList()));
        }
        
        header.setResultType(visitResult(ctx.result()));
        if (ctx.throws_() != null) {
            header.setThrowsList(visitThrows_(ctx.throws_()));
        }
        return header;
    }
    
    @Override
    public JTypeName visitResult(ResultContext ctx) {
        return new JTypeName(ctx.getText());
    }
    
    @Override
    public JObjectList<JTypeParameter> visitTypeParameters(TypeParametersContext ctx) {
        List<TypeParameterContext> list = ctx.typeParameterList().typeParameter();
        return list.stream().map(this::visitTypeParameter).collect(createListCollector());
    }
    
    @Override
    public JBlock visitBlock(Java8Parser.BlockContext ctx) {
        JBlock jBlock = new JBlock();
        for (BlockStatementContext statementCtx : ctx.blockStatement()) {
            JStatement statement = (JStatement) visitBlockStatement(statementCtx);
            jBlock.addStatement(statement);
        }
        return jBlock;
    }
    
    // k?
    @Override
    public Object visitExpressionStatement(ExpressionStatementContext ctx) {
        return visit(ctx.statementExpression());
    }
    
    @Override
    public JLambdaExpression visitLambdaExpression(LambdaExpressionContext ctx) {
        JLambdaExpression expression = new JLambdaExpression();
        expression.setParameters((JLambdaParameters) visit(ctx.lambdaParameters()));
        expression.setBody(visitLambdaBody(ctx.lambdaBody()));
        return expression;
    }
    
    @Override
    public JIdentifierParameter visitLambdaIdentifierParameter(
            LambdaIdentifierParameterContext ctx) {
        return new JIdentifierParameter(ctx.getText());
    }
    
    @Override
    public JLambdaParameters visitLambdaParameterList(LambdaParameterListContext ctx) {
        if (ctx.formalParameterList() != null) {
            return visitFormalParameterList(ctx.formalParameterList());
        } else {
            return new JParameterList();
        }
    }
    
    @Override
    public JLambdaParameters visitLambdaInferedParameterList(LambdaInferedParameterListContext 
                                                                         ctx) {
        JInferredParameters parameters = new JInferredParameters();
        parameters.addAll(visitInferredFormalParameterList(ctx.inferredFormalParameterList()));
        return parameters;
    }
    
    @Override
    public Collection<JIdentifier> visitInferredFormalParameterList(
            InferredFormalParameterListContext ctx) {
        return ctx.identifier().stream().map(this::visitIdentifier).collect(createListCollector());
    }
    
    @Override
    public JLambdaBody visitLambdaBody(LambdaBodyContext ctx) {
        return (JLambdaBody) super.visitLambdaBody(ctx);
    }
    
    @Override
    public JThrowsList visitThrows_(Java8Parser.Throws_Context ctx) {
        return visitExceptionTypeList(ctx.exceptionTypeList());
    }
    
    @Override
    public JThrowsList visitExceptionTypeList(Java8Parser.ExceptionTypeListContext ctx) {
        JThrowsList list = new JThrowsList();
        for (ExceptionTypeContext exceptionCtx : ctx.exceptionType()) {
            list.addThrowsType(new JTypeName(exceptionCtx.getText()));
        }
        return list;
    }
    
    @Override
    public JParameterList visitFormalParameterList(FormalParameterListContext ctx) {
        JParameterList list = new JParameterList();
        
        if (ctx.formalParameters() != null) {
            for (FormalParameterContext formalCtx : ctx.formalParameters().formalParameter()) {
                list.getParameters().add(visitFormalParameter(formalCtx));
            }
        }
        
        if (ctx.lastFormalParameter() != null) {
            list.getParameters().add(visitLastFormalParameter(ctx.lastFormalParameter()));
        }
        
        return list;
    }
    
    @Override
    public JParameter visitFormalParameter(FormalParameterContext ctx) {
        String name = ctx.variableDeclaratorId().getText();
        JTypeName type = new JTypeName(ctx.unannType().getText());
        JParameter parameter = new JParameter(name, type);
        parameter.setType(type);
        return parameter;
    }
    
    @Override
    public JParameter visitLastFormalParameter(Java8Parser.LastFormalParameterContext ctx) {
        String name = ctx.variableDeclaratorId().getText();
        JTypeName type = new JTypeName(ctx.unannType().getText());
        JParameter parameter = new JParameter(name, type);
        parameter.setType(type);
        return parameter;
    }
    
    @Override
    public JLocalVariableDeclaration visitLocalVariableDeclarationStatement(
            LocalVariableDeclarationStatementContext ctx) {
        return visitLocalVariableDeclaration(ctx.localVariableDeclaration());
    }
    
    @Override
    public JLocalVariableDeclaration visitLocalVariableDeclaration
            (LocalVariableDeclarationContext ctx) {
        JLocalVariableDeclaration declaration = new JLocalVariableDeclaration();
        declaration.setAnnotations(visitAnnotations(ctx.annotation()));
        declaration.setModifiers(visitVariableModifiers(ctx.variableModifier()));
        declaration.setType(visitUnannType(ctx.unannType()));
        declaration.setVariables(visitVariableDeclaratorList(ctx.variableDeclaratorList()));
        return declaration;
    }
    
    @Override
    public JEmptyStatement visitEmptyStatement(Java8Parser.EmptyStatementContext ctx) {
        return new JEmptyStatement();
    }
    
    @Override
    public Object visitAssignementStatement(Java8Parser.AssignementStatementContext ctx) {
        return new JExpressionStatement(visitAssignment(ctx.assignment()));
    }
    
    @Override
    public JAssignment visitAssignment(Java8Parser.AssignmentContext ctx) {
        JAssignment assignment = new JAssignment();
        assignment.setLeft(visitLeftHandSide(ctx.leftHandSide()));
        assignment.setOperator(JAssignment.Operator.fromJava(ctx.assignmentOperator().getText()));
        assignment.setRight(visitExpression(ctx.expression()));
        return assignment;
    }
    
    @Override
    public JExpressionName visitExpressionName(ExpressionNameContext ctx) {
        return new JExpressionName(new JIdentifier(ctx.getText()));
    }
    
    @Override
    public JExpressionStatement visitPreIncrementStatement(Java8Parser.PreIncrementStatementContext ctx) {
        return new JExpressionStatement(visitPreIncrementExpression(ctx.preIncrementExpression()));
    }
    
    @Override
    public JUnaryExpression visitPreIncrementExpression(Java8Parser.PreIncrementExpressionContext
                                                                    ctx) {
        JUnaryExpression expression = new JUnaryExpression();
        expression.setOperator(JUnaryExpression.Operator.PRE_INCREMENT);
        expression.setExpression((JExpression) visit(ctx.unaryExpression()));
        return expression;
    }
    
    @Override
    public JExpressionStatement visitPreDecrementStatement(Java8Parser.PreDecrementStatementContext ctx) {
        return new JExpressionStatement(visitPreDecrementExpression(ctx.preDecrementExpression()));
    }
    
    @Override
    public JUnaryExpression visitPreDecrementExpression(Java8Parser.PreDecrementExpressionContext
                                                                    ctx) {
        JUnaryExpression expression = new JUnaryExpression();
        expression.setOperator(JUnaryExpression.Operator.PRE_DECREMENT);
        expression.setExpression((JExpression) visit(ctx.unaryExpression()));
        return expression;
    }
    
    @Override
    public JExpressionStatement visitPostIncrementStatement(Java8Parser.PostIncrementStatementContext ctx) {
        return new JExpressionStatement(visitPostIncrementExpression(ctx.postIncrementExpression
                ()));
    }
    
    @Override
    public JUnaryExpression visitPostIncrementExpression(Java8Parser.PostIncrementExpressionContext ctx) {
        JUnaryExpression expression = new JUnaryExpression();
        expression.setOperator(JUnaryExpression.Operator.POST_INCREMENT);
        expression.setExpression((JExpression) visit(ctx.postfixExpression()));
        return expression;
    }
    
    @Override
    public Object visitPostDecrementStatement(Java8Parser.PostDecrementStatementContext ctx) {
        return new JExpressionStatement(visitPostDecrementExpression(ctx.postDecrementExpression
                ()));
    }
    
    @Override
    public JUnaryExpression visitPostDecrementExpression(Java8Parser.PostDecrementExpressionContext ctx) {
        JUnaryExpression expression = new JUnaryExpression();
        expression.setOperator(JUnaryExpression.Operator.POST_DECREMENT);
        expression.setExpression((JExpression) visit(ctx.postfixExpression()));
        return expression;
    }
    
    @Override
    public JExpression visitLeftHandSide(Java8Parser.LeftHandSideContext ctx) {
        return (JExpression) super.visitLeftHandSide(ctx);
    }
    
    @Override
    public JExpression visitExpression(Java8Parser.ExpressionContext ctx) {
        return (JExpression) super.visitExpression(ctx);
    }
    
    @Override
    public JTypeName visitUnannType(Java8Parser.UnannTypeContext ctx) {
        return new JTypeName(ctx.getText());
    }
    
    private JObjectList<JModifier> visitVariableModifiers(Collection<VariableModifierContext> ctx) {
        return ctx.stream().map(this::visitVariableModifier).collect(createListCollector());
    }
    
    @Override
    public JModifier visitVariableModifier(VariableModifierContext ctx) {
        return JModifier.fromJava(ctx.getText());
    }
    
    
    private JObjectList<JModifier> getMethodModifiers(Collection<MethodModifierContext> set) {
        return set.stream()
                  .map(e -> JModifier.fromJava(e.getText()))
                  .collect(createListCollector());
    }
    
    @Override
    public JTypeReferenceExpression visitPrimaryClassType(PrimaryClassTypeContext ctx) {
        String brackets =
                ctx.bracketPair().stream().map(RuleContext::getText).collect(Collectors.joining());
        String typeName = ctx.primaryClassTypeAlternates().getText();
        return new JTypeReferenceExpression(new JTypeName(typeName + brackets));
    }
    
    @Override
    public JExpressionStatement visitClassInstanceCreationStatement(
            ClassInstanceCreationStatementContext ctx) {
        return new JExpressionStatement(visitClassInstanceCreationExpression(ctx.classInstanceCreationExpression()));
    }
    
    @Override
    public JClassInstanceCreationExpression visitClassInstanceCreationExpression(
            ClassInstanceCreationExpressionContext ctx) {
        JTypeName type = visitClassIdentifier(ctx.classIdentifier());
        JClassInstanceCreationExpression creationExpression =
                new JClassInstanceCreationExpression(type);
        
        if (ctx.typeArgumentsOrDiamond() != null) {
            JTypeArgumentList argumentList =
                    (JTypeArgumentList) visit(ctx.typeArgumentsOrDiamond());
            creationExpression.setJTypeArgumentList(argumentList);
            
        }
        
        if (ctx.argumentList() != null) {
            JArgumentList argumentList = visitArgumentList(ctx.argumentList());
            creationExpression.setArgumentList(argumentList);
        }
        
        if (ctx.classBody() != null) {
            JClassBody body = visitClassBody(ctx.classBody());
            creationExpression.setBody(body);
        }
        
        return creationExpression;
    }
    
    @Override
    public JTypeArgumentList visitDiamond(DiamondContext ctx) {
        JTypeArgumentList list = new JTypeArgumentList();
        list.setShowTypeParametersEmpty(true);
        return list;
    }
    
    @Override
    public JTypeArgumentList visitTypeArguments(TypeArgumentsContext ctx) {
        JTypeArgumentList argumentList = new JTypeArgumentList();
        List<TypeArgumentContext> types = ctx.typeArgumentList().typeArgument();
        for (TypeArgumentContext type : types) {
            JTypeArgument argument = (JTypeArgument) visitTypeArgument(type);
            argumentList.addTypeArgument(argument);
        }
        return argumentList;
    }
    
    @Override
    public JTypeArgument visitWildcard(WildcardContext ctx) {
        JTypeArgument.JWildcardTypeArgument.Type type = null;
        JTypeName reference = null;
        if (ctx.wildcardBounds() != null) {
            String text = ctx.wildcardBounds().boundType.getText();
            type = JTypeArgument.JWildcardTypeArgument.Type.fromJava(text);
            reference = new JTypeName(ctx.wildcardBounds().referenceType().getText());
        }
        return new JTypeArgument.JWildcardTypeArgument(reference, type);
    }
    
    @Override
    public JTypeArgument.JReferenceTypeArgument visitReferenceType(ReferenceTypeContext ctx) {
        return new JTypeArgument.JReferenceTypeArgument(new JTypeName(ctx.getText()));
    }
    
    @Override
    public JArgumentList visitArgumentList(ArgumentListContext ctx) {
        JArgumentList argumentList = new JArgumentList();
        for (ExpressionContext expressionContext : ctx.expression()) {
            argumentList.addArgument(visitExpression(expressionContext));
        }
        return argumentList;
    }
    
    @Override
    public JTypeName visitClassIdentifier(ClassIdentifierContext ctx) {
        return new JTypeName(ctx.getText());
    }
    
    @Override
    public Object visitLabeledStatement(LabeledStatementContext ctx) {
        JLabledStatement statement = new JLabledStatement();
        statement.setIdentifier(visitIdentifier(ctx.identifier()));
        statement.setStatement(visitStatement(ctx.statement()));
        return statement;
    }
    
    @Override
    public Object visitLabeledStatementNoShortIf(LabeledStatementNoShortIfContext ctx) {
        JLabledStatement statement = new JLabledStatement();
        statement.setIdentifier(visitIdentifier(ctx.identifier()));
        statement.setStatement(visitStatementNoShortIf(ctx.statementNoShortIf()));
        return statement;
    }
    
    @Override
    public JStatement visitStatement(StatementContext ctx) {
        return (JStatement) super.visitStatement(ctx);
    }
    
    @Override
    public JStatement visitStatementNoShortIf(StatementNoShortIfContext ctx) {
        return (JStatement) super.visitStatementNoShortIf(ctx);
    }
    
    @Override
    public JIfThenStatement visitIfThenStatement(IfThenStatementContext ctx) {
        JIfThenStatement statement = new JIfThenStatement();
        statement.setExpression(visitExpression(ctx.expression()));
        statement.setStatement(visitStatement(ctx.statement()));
        return statement;
    }
    
    @Override
    public JIfThenElseStatement visitIfThenElseStatement(IfThenElseStatementContext ctx) {
        JIfThenElseStatement statement = new JIfThenElseStatement();
        statement.setExpression(visitExpression(ctx.expression()));
        statement.setIfCondition(visitStatementNoShortIf(ctx.statementNoShortIf()));
        statement.setElseCondition(visitStatement(ctx.statement()));
        return statement;
    }
    
    @Override
    public JIfThenElseStatement visitIfThenElseStatementNoShortIf(
            IfThenElseStatementNoShortIfContext ctx) {
        JIfThenElseStatement statement = new JIfThenElseStatement();
        statement.setExpression(visitExpression(ctx.expression()));
        statement.setIfCondition(visitStatementNoShortIf(ctx.statementNoShortIf(0)));
        statement.setElseCondition(visitStatementNoShortIf(ctx.statementNoShortIf(1)));
        return statement;
    }
    
    @Override
    public JAssertStatement visitAssertStatement(AssertStatementContext ctx) {
        JAssertStatement statement = new JAssertStatement();
        statement.setAssertion(visitExpression(ctx.expression(0)));
        if (ctx.expression().size() > 1) {
            statement.setMessage(visitExpression(ctx.expression(1)));
        }
        return statement;
    }
    
    @Override
    public JWhileStatement visitWhileStatement(WhileStatementContext ctx) {
        JWhileStatement statement = new JWhileStatement();
        statement.setExpression(visitExpression(ctx.expression()));
        statement.setStatement(visitStatement(ctx.statement()));
        return statement;
    }
    
    @Override
    public JWhileStatement visitWhileStatementNoShortIf(WhileStatementNoShortIfContext ctx) {
        JWhileStatement statement = new JWhileStatement();
        statement.setExpression(visitExpression(ctx.expression()));
        statement.setStatement(visitStatementNoShortIf(ctx.statementNoShortIf()));
        return statement;
    }
    
    @Override
    public JDoWhileStatement visitDoStatement(DoStatementContext ctx) {
        JDoWhileStatement statement = new JDoWhileStatement();
        statement.setStatement(visitStatement(ctx.statement()));
        statement.setExpression(visitExpression(ctx.expression()));
        return statement;
    }
    
    @Override
    public JEnhancedForStatement visitEnhancedForStatement(EnhancedForStatementContext ctx) {
        JEnhancedForStatement statement = new JEnhancedForStatement();
        statement.setModifiers(visitVariableModifiers(ctx.variableModifier()));
        statement.setVariableType(visitUnannType(ctx.unannType()));
        statement.setVariableName(visitVariableDeclaratorId(ctx.variableDeclaratorId()));
        statement.setExpression(visitExpression(ctx.expression()));
        statement.setStatement(visitStatement(ctx.statement()));
        return statement;
    }
    
    @Override
    public Object visitEnhancedForStatementNoShortIf(EnhancedForStatementNoShortIfContext ctx) {
        JEnhancedForStatement statement = new JEnhancedForStatement();
        statement.setModifiers(visitVariableModifiers(ctx.variableModifier()));
        statement.setVariableType(visitUnannType(ctx.unannType()));
        statement.setVariableName(visitVariableDeclaratorId(ctx.variableDeclaratorId()));
        statement.setExpression(visitExpression(ctx.expression()));
        statement.setStatement(visitStatementNoShortIf(ctx.statementNoShortIf()));
        return statement;
    }
    
    @Override
    public JBreakStatement visitBreakStatement(BreakStatementContext ctx) {
        JBreakStatement statement = new JBreakStatement();
        if (ctx.identifier() != null) {
            statement.setIdentifier(visitIdentifier(ctx.identifier()));
        }
        return statement;
    }
    
    @Override
    public JContinueStatement visitContinueStatement(ContinueStatementContext ctx) {
        JContinueStatement statement = new JContinueStatement();
        if (ctx.identifier() != null) {
            statement.setIdentifier(visitIdentifier(ctx.identifier()));
        }
        return statement;
    }
    
    @Override
    public JReturnStatement visitReturnStatement(ReturnStatementContext ctx) {
        JReturnStatement statement = new JReturnStatement();
        if (ctx.expression() != null) {
            statement.setExpression(visitExpression(ctx.expression()));
        }
        return statement;
    }
    
    @Override
    public JThrowStatement visitThrowStatement(ThrowStatementContext ctx) {
        JThrowStatement statement = new JThrowStatement();
        if (ctx.expression() != null) {
            statement.setExpression(visitExpression(ctx.expression()));
        }
        return statement;
    }
    
    @Override
    public JSynchronizedStatement visitSynchronizedStatement(SynchronizedStatementContext ctx) {
        JSynchronizedStatement statement = new JSynchronizedStatement();
        statement.setExpression(visitExpression(ctx.expression()));
        statement.setBlock(visitBlock(ctx.block()));
        return statement;
    }
    
    @Override
    public JTryStatement visitTryCatchStatement(TryCatchStatementContext ctx) {
        JTryStatement statement = new JTryStatement();
        statement.setBlock(visitBlock(ctx.block()));
        statement.setCatchClauses(visitCatches(ctx.catches()));
        return statement;
    }
    
    @Override
    public JTryStatement visitTryCatchFinallyStatement(TryCatchFinallyStatementContext ctx) {
        JTryStatement statement = new JTryStatement();
        statement.setBlock(visitBlock(ctx.block()));
        if (ctx.catches() != null) {
            statement.setCatchClauses(visitCatches(ctx.catches()));
        }
        statement.setFinallyBlock(visitFinally_(ctx.finally_()));
        return statement;
    }
    
    @Override
    public JBlock visitFinally_(Finally_Context ctx) {
        return visitBlock(ctx.block());
    }
    
    @Override
    public JObjectList<JCatchClause> visitCatches(CatchesContext ctx) {
        JObjectList<JCatchClause> catches = new JObjectList<>();
        for (CatchClauseContext catchClauseContext : ctx.catchClause()) {
            catches.add(visitCatchClause(catchClauseContext));
        }
        return catches;
    }
    
    @Override
    public JCatchClause visitCatchClause(CatchClauseContext ctx) {
        JCatchClause clause = new JCatchClause();
        clause.setModifiers(visitVariableModifiers(ctx.variableModifier()));
        clause.addCatchType(visitUnannClassType(ctx.unannClassType()));
        if (ctx.classType() != null) {
            for (ClassTypeContext classTypeContext : ctx.classType()) {
                clause.addCatchType(visitClassType(classTypeContext));
            }
        }
        clause.setVariable(visitVariableDeclaratorId(ctx.variableDeclaratorId()));
        clause.setBlock(visitBlock(ctx.block()));
        return clause;
    }
    
    @Override
    public Object visitCharacterLiteral(CharacterLiteralContext ctx) {
        String text = ctx.CharacterLiteral().getText();
        return new JCharacterLiteral(text.charAt(1));
    }
    
    @Override
    public JBooleanLiteral visitBooleanLiteral(BooleanLiteralContext ctx) {
        return new JBooleanLiteral(Boolean.parseBoolean(ctx.getText()));
    }
    
    @Override
    public JTypeName visitClassType(ClassTypeContext ctx) {
        return new JTypeName(ctx.identifier().getText());
    }
    
    
    @Override
    public JTypeName visitUnannClassType(UnannClassTypeContext ctx) {
        return new JTypeName(ctx.Identifier().getText());
    }
    
    @Override
    public JIdentifier visitVariableDeclaratorId(VariableDeclaratorIdContext ctx) {
        return visitIdentifier(ctx.identifier());
    }
    
    @Override
    public JIdentifier visitIdentifier(IdentifierContext ctx) {
        return new JIdentifier(ctx.getText());
    }
    
    @Override
    public JField visitConstantDeclaration(ConstantDeclarationContext ctx) {
        JField field = new JField();
        field.setAnnotations(visitAnnotations(ctx.annotation()));
        field.setModifiers(visitConstantModifiers(ctx.constantModifier()));
        field.setType(visitUnannType(ctx.unannType()));
        field.setVariables(visitVariableDeclaratorList(ctx.variableDeclaratorList()));
        return field;
    }
    
    private JObjectList<JModifier> visitConstantModifiers(List<ConstantModifierContext> list) {
        return list.stream().map(this::visitConstantModifier).collect(createListCollector());
    }
    
    @Override
    public JClassInstanceInitializer visitInstanceInitializer(InstanceInitializerContext ctx) {
        JClassInstanceInitializer initializer = new JClassInstanceInitializer();
        initializer.setBlock(visitBlock(ctx.block()));
        return initializer;
    }
    
    @Override
    public JClassStaticInitializer visitStaticInitializer(StaticInitializerContext ctx) {
        JClassStaticInitializer initializer = new JClassStaticInitializer();
        initializer.setBlock(visitBlock(ctx.block()));
        return initializer;
    }
    
    @Override
    public JConstructorDeclaration visitConstructorDeclaration(ConstructorDeclarationContext ctx) {
        JConstructorDeclaration declaration = new JConstructorDeclaration();
        declaration.setAnnotations(visitAnnotations(ctx.annotation()));
        declaration.setTypeParameters(visitTypeParameters(ctx.constructorDeclarator()
                                                             .typeParameters()));
        declaration.setIdentifier(visitSimpleTypeName(ctx.constructorDeclarator()
                                                         .simpleTypeName()));
        if (ctx.throws_() != null) {
            declaration.setThrowsList(visitThrows_(ctx.throws_()));
        }
        declaration.setBody(visitConstructorBody(ctx.constructorBody()));
        
        return declaration;
    }
    
    @Override
    public JBlock visitConstructorBody(ConstructorBodyContext ctx) {
        JBlock block = new JBlock();
        
        JObjectList<JStatement> statements = new JObjectList<>();
        if (ctx.explicitConstructorInvocation() != null) {
            statements.add((JStatement) visitExplicitConstructorInvocation(ctx.explicitConstructorInvocation()));
        }
        
        if (ctx.blockStatements() != null) {
            List<JStatement> temp = visitBlockStatements(ctx.blockStatements());
            statements.addAll(temp);
        }
        
        block.setStatements(statements);
        return block;
    }
    
    
    @Override
    public JAnnotationDeclaration visitAnnotationTypeDeclaration(AnnotationTypeDeclarationContext
                                                                             ctx) {
        JAnnotationDeclaration declaration = new JAnnotationDeclaration();
        if (ctx.annotation() != null) {
            declaration.setAnnotations(visitAnnotations(ctx.annotation()));
        }
        
        if (ctx.interfaceModifier() != null) {
            declaration.setModifiers(visitInterfaceModifiers(ctx.interfaceModifier()));
        }
        
        declaration.setIdentifier(visitIdentifier(ctx.identifier()));
        declaration.setBody(visitAnnotationTypeBody(ctx.annotationTypeBody()));
        return declaration;
    }
    
    @Override
    public JAnnotationBody visitAnnotationTypeBody(AnnotationTypeBodyContext ctx) {
        JAnnotationBody body = new JAnnotationBody();
        JObjectList<JAnnotationMember> members = ctx.annotationTypeMemberDeclaration()
                                                    .stream()
                                                    .map(this::visitAnnotationTypeMemberDeclaration)
                                                    .collect(createListCollector());
        body.setMembers(members);
        return body;
    }
    
    @Override
    public JAnnotationMember visitAnnotationTypeMemberDeclaration(
            AnnotationTypeMemberDeclarationContext ctx) {
        return (JAnnotationMember) super.visitAnnotationTypeMemberDeclaration(ctx);
    }
    
    @Override
    public JAnnotationTypeElement visitAnnotationTypeElementDeclaration(
            AnnotationTypeElementDeclarationContext ctx) {
        JAnnotationTypeElement element = new JAnnotationTypeElement();
        element.setAnnotations(visitAnnotations(ctx.annotation()));
        element.setModifiers(visitAnnotationTypeElementModifiers(ctx.annotationTypeElementModifier()));
        element.setType(visitUnannType(ctx.unannType()));
        element.setIdentifier(visitIdentifier(ctx.identifier()));
        
        if (ctx.defaultValue() != null) {
            element.setDefaultValue((JElementValue) visitDefaultValue(ctx.defaultValue()));
        }
        return element;
    }
    
    @Override
    public JExpression visitElementValueExpression(ElementValueExpressionContext ctx) {
        return (JExpression) visit(ctx.conditionalExpression());
    }
    
    @Override
    public JAnnotation visitElementValueAnnotation(ElementValueAnnotationContext ctx) {
        return visitAnnotation(ctx.annotation());
    }
    
    @Override
    public JAnnotation visitAnnotation(AnnotationContext ctx) {
        return (JAnnotation) super.visitAnnotation(ctx);
    }
    
    @Override
    public JAnnotation visitNormalAnnotation(NormalAnnotationContext ctx) {
        JAnnotation annotation = new JAnnotation();
        annotation.setType(visitTypeName(ctx.typeName()));
        if (ctx.elementValuePairList() != null) {
            annotation.setValue(visitElementValuePairList(ctx.elementValuePairList()));
        }
        return annotation;
    }
    
    @Override
    public JAnnotation visitMarkerAnnotation(MarkerAnnotationContext ctx) {
        JAnnotation annotation = new JAnnotation();
        annotation.setType(visitTypeName(ctx.typeName()));
        return annotation;
    }
    
    @Override
    public JAnnotation visitSingleElementAnnotation(SingleElementAnnotationContext ctx) {
        JAnnotation annotation = new JAnnotation();
        annotation.setType(visitTypeName(ctx.typeName()));
        JAnnotationValue.JSingleValue value = new JAnnotationValue.JSingleValue();
        value.setValue((JElementValue) visit(ctx.elementValue()));
        annotation.setValue(value);
        return annotation;
    }
    
    @Override
    public JAnnotationValue.JPairCollection visitElementValuePairList(ElementValuePairListContext
                                                                                  ctx) {
        JAnnotationValue.JPairCollection collection = new JAnnotationValue.JPairCollection();
        JObjectList<JAnnotationValue.JElementPair> values = ctx.elementValuePair()
                                                               .stream()
                                                               .map(this::visitElementValuePair)
                                                               .collect(createListCollector());
        collection.setValues(values);
        return collection;
    }
    
    @Override
    public JAnnotationValue.JElementPair visitElementValuePair(ElementValuePairContext ctx) {
        JAnnotationValue.JElementPair pair = new JAnnotationValue.JElementPair();
        pair.setIdentifier(visitIdentifier(ctx.identifier()));
        pair.setValue((JElementValue) visit(ctx.elementValue()));
        return pair;
    }
    
    @Override
    public JTypeName visitTypeName(TypeNameContext ctx) {
        return new JTypeName(ctx.getText());
    }
    
    private JObjectList<JModifier> visitAnnotationTypeElementModifiers
            (List<AnnotationTypeElementModifierContext> ctx) {
        return ctx.stream()
                  .map(v -> JModifier.fromJava(v.getText()))
                  .collect(createListCollector());
    }
    
    @Override
    public List<JStatement> visitBlockStatements(BlockStatementsContext ctx) {
        List<JStatement> statements = new ArrayList<>();
        for (BlockStatementContext statementCtx : ctx.blockStatement()) {
            JStatement statement = (JStatement) visitBlockStatement(statementCtx);
            statements.add(statement);
        }
        return statements;
    }
    
    @Override
    public JTypeName visitSimpleTypeName(SimpleTypeNameContext ctx) {
        return new JTypeName(ctx.Identifier().getText());
    }
    
    @Override
    public JModifier visitConstantModifier(ConstantModifierContext ctx) {
        return JModifier.fromJava(ctx.getText());
    }
    
    @Override
    public JFloatingLiteral visitFloatingPointLiteral(FloatingPointLiteralContext ctx) {
        if (ctx.getText().endsWith("D")) {
            return new JDoubleLiteral(Double.parseDouble(ctx.getText()));
        } else {
            return new JFloatLiteral(Float.parseFloat(ctx.getText()));
        }
    }
}

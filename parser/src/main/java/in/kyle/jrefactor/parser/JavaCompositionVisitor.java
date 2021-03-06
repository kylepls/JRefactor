package in.kyle.jrefactor.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import in.kyle.jrefactor.tree.JModifier;
import in.kyle.jrefactor.tree.antlr.gen.Java8BaseVisitor;
import in.kyle.jrefactor.tree.antlr.gen.Java8Parser;
import in.kyle.jrefactor.tree.antlr.gen.Java8Parser.*;
import in.kyle.jrefactor.tree.obj.*;
import in.kyle.jrefactor.tree.obj.annotationvalue.JValuePair;
import in.kyle.jrefactor.tree.obj.block.JStatementBlock;
import in.kyle.jrefactor.tree.obj.block.typebody.JAnnotationBody;
import in.kyle.jrefactor.tree.obj.block.typebody.JClassBody;
import in.kyle.jrefactor.tree.obj.block.typebody.JEnumBody;
import in.kyle.jrefactor.tree.obj.block.typebody.JInterfaceBody;
import in.kyle.jrefactor.tree.obj.expression.*;
import in.kyle.jrefactor.tree.obj.expression.JExpressionAssignment.JAssignmentOperator;
import in.kyle.jrefactor.tree.obj.expression.JExpressionLeftRight.JLeftRightOperatorBinary;
import in.kyle.jrefactor.tree.obj.expression.JExpressionLeftRight.JLeftRightOperatorConditional;
import in.kyle.jrefactor.tree.obj.expression.JExpressionLeftRight.JLeftRightOperatorMath;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralBoolean;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralCharacter;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralNumeric;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralString;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.JLiteralFloating;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.JLiteralInteger;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.JLiteralLong;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.literalfloating
        .JLiteralDouble;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.literalfloating
        .JLiteralFloat;
import in.kyle.jrefactor.tree.obj.expression.expressionunary.JUnaryCast;
import in.kyle.jrefactor.tree.obj.expression.expressionunary.JUnaryPrePost;
import in.kyle.jrefactor.tree.obj.expression.expressionunary.JUnaryPrePost.JUnaryOperator;
import in.kyle.jrefactor.tree.obj.modifiable.JCatchClause;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.JConstructorDeclaration;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.JField;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.JParameter;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JAnnotationField;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JEnumConstant;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JMethodHeader;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JType;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JTypeParameter;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.type.JAnnotationType;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.type.superinterfacetype.JEnum;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.type.superinterfacetype
        .typeparametertype.JClass;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.type.superinterfacetype
        .typeparametertype.JInterface;
import in.kyle.jrefactor.tree.obj.reference.JTypeArgument;
import in.kyle.jrefactor.tree.obj.reference.typeargument.JTypeArgumentReference;
import in.kyle.jrefactor.tree.obj.reference.typeargument.JTypeArgumentWildcard;
import in.kyle.jrefactor.tree.obj.reference.typeargument.JTypeArgumentWildcard.JWildcardType;
import in.kyle.jrefactor.tree.obj.statement.JStatementAssert;
import in.kyle.jrefactor.tree.obj.statement.JStatementEmpty;
import in.kyle.jrefactor.tree.obj.statement.JStatementExpression;
import in.kyle.jrefactor.tree.obj.statement.JStatementLabeled;
import in.kyle.jrefactor.tree.obj.statement.JStatementLocalVariableDeclaration;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementIf;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementReturn;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementSwitch;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementSynchronized;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementThrow;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementTry;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.identifiablestatement.JStatementBreak;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.identifiablestatement
        .JStatementContinue;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.statementcontrolloop.JStatementWhile;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.statementcontrolloop.statementfor
        .JStatementBasicFor;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.statementcontrolloop.statementfor
        .JStatementEnhancedFor;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.statementcontrolloop.statementwhile
        .JStatementDoWhile;
import in.kyle.jrefactor.tree.obj.switchcase.JSwitchCaseDefault;
import in.kyle.jrefactor.tree.obj.switchcase.JSwitchCaseExpression;
import in.kyle.jrefactor.tree.obj.typename.JArrayTypeName;
import in.kyle.jrefactor.tree.obj.unit.bodymember.typemember.JAnnotationMember;
import in.kyle.jrefactor.tree.obj.unit.bodymember.typemember.JInterfaceMember;
import in.kyle.jrefactor.tree.obj.unit.bodymember.typemember.enummember.JClassMember;
import in.kyle.jrefactor.tree.obj.unit.bodymember.typemember.enummember.classmember.JMethod;
import in.kyle.jrefactor.tree.obj.unit.bodymember.typemember.enummember.classmember
        .classinitializer.JClassInstanceInitializer;
import in.kyle.jrefactor.tree.obj.unit.bodymember.typemember.enummember.classmember
        .classinitializer.JClassStaticInitializer;
import in.kyle.jrefactor.tree.obj.unit.bodymember.typemember.interfacemember.JInterfaceMethod;
import in.kyle.jrefactor.tree.obj.variabledefinition.JVariable;

public class JavaCompositionVisitor extends Java8BaseVisitor<Object> {
    
    private boolean has(Object object) {
        return object != null;
    }
    
    @Override
    public JCompilationUnit visitCompilationUnit(Java8Parser.CompilationUnitContext ctx) {
        JCompilationUnit unit = new JCompilationUnit();
        if (has(ctx.packageDeclaration())) {
            JPropertyLookup packageName = visitPackageDeclaration(ctx.packageDeclaration());
            unit.setPackageName(Optional.of(packageName));
        }
        List<JImport> imports = visitImports(ctx.importDeclaration());
        List<JType> types = visitTypeDeclarations(ctx.typeDeclaration());
        
        unit.setImportss(imports);
        unit.setTypes(types);
        
        return unit;
    }
    
    @Override
    public JPropertyLookup visitPropertyLookup(PropertyLookupContext ctx) {
        JPropertyLookup lookup = new JPropertyLookup();
        ctx.Identifier().forEach(area -> lookup.addArea(area.getText()));
        return lookup;
    }
    
    @Override
    public JPropertyLookup visitPackageDeclaration(PackageDeclarationContext ctx) {
        return visitPropertyLookup(ctx.packageName().propertyLookup());
    }
    
    @Override
    public JImport visitImportDeclaration(ImportDeclarationContext ctx) {
        JImport jImport = new JImport();
        
        JPropertyLookup area;
        if (has(ctx.packageName())) {
            area = visitPackageName(ctx.packageName());
        } else {
            area = new JPropertyLookup();
        }
        area.addArea(ctx.typeName().getText());
        jImport.setArea(area);
        
        if (has(ctx.import_static())) {
            jImport.setStaticImport(true);
        }
        if (has(ctx.import_wildcard())) {
            jImport.setOnDemand(true);
        }
        return jImport;
    }
    
    @Override
    public JPropertyLookup visitPackageName(PackageNameContext ctx) {
        return visitPropertyLookup(ctx.propertyLookup());
    }
    
    @Override
    public JClass visitClassDeclaration(ClassDeclarationContext ctx) {
        List<JAnnotation> annotations = visitAnnotations(ctx.annotation());
        
        JClass clazz = new JClass();
        clazz.setAnnotations(annotations);
        clazz.setModifiers(visitModifiers(ctx.modifier()));
        clazz.setIdentifier(visitIdentifier(ctx.identifier()));
        
        setClassSuperClass(ctx, clazz);
        if (has(ctx.superinterfaces())) {
            clazz.setSuperInterfaces(visitSuperinterfaces(ctx.superinterfaces()));
        }
        
        if (has(ctx.typeParameters())) {
            clazz.setTypeParameters(visitTypeParameters(ctx.typeParameters()));
        }
        
        clazz.setBody(visitClassBody(ctx.classBody()));
        return clazz;
    }
    
    @Override
    public Collection<JTypeName> visitSuperinterfaces(SuperinterfacesContext ctx) {
        return ctx.interfaceTypeList()
                .interfaceType()
                .stream()
                .map(type -> visitClassType(type.classType()))
                .collect(Collectors.toSet());
    }
    
    @Override
    public JExpressionMethodInvocation visitMethodInvocation(MethodInvocationContext ctx) {
        JIdentifier identifier = visitIdentifier(ctx.identifier());
        JExpressionMethodInvocation invocation = new JExpressionMethodInvocation();
        invocation.setMethodName(identifier);
        if (has(ctx.methodArea())) {
            JPropertyLookup area = visitMethodArea(ctx.methodArea());
            invocation.setMethodArea(Optional.of(area));
        }
        if (has(ctx.typeArguments())) {
            List<JTypeArgument> typeArguments = visitTypeArguments(ctx.typeArguments());
            invocation.setTypeArguments(typeArguments);
        }
        if (has(ctx.argumentList())) {
            List<JExpression> arguments = visitArgumentList(ctx.argumentList());
            invocation.setArguments(arguments);
        }
        return invocation;
    }
    
    @Override
    public JPropertyLookup visitMethodArea(MethodAreaContext ctx) {
        List<String> areas = Arrays.stream(ctx.getText().split("\\.")).collect(Collectors.toList());
        JPropertyLookup lookup = new JPropertyLookup();
        lookup.setAreas(areas);
        return lookup;
    }
    
    @Override
    public JExpression visitUnaryExpression(UnaryExpressionContext ctx) {
        if (has(ctx.preExpression())) {
            return visitPreExpression(ctx.preExpression());
        } else if (has(ctx.postfixExpression())) {
            return visitPostfixExpression(ctx.postfixExpression());
        } else if (has(ctx.binaryExpression())) {
            return visitBinaryExpression(ctx.binaryExpression());
        } else if (has(ctx.castExpression())) {
            return visitCastExpression(ctx.castExpression());
        } else {
            return null;
        }
    }
    
    @Override
    public JUnaryCast visitCastExpression(CastExpressionContext ctx) {
        return (JUnaryCast) super.visitCastExpression(ctx);
    }
    
    @Override
    public JUnaryCast visitCastPrimitive(CastPrimitiveContext ctx) {
        JUnaryCast cast = new JUnaryCast();
        cast.setExpression(visitUnaryExpression(ctx.unaryExpression()));
        cast.addBound(visitPrimitiveType(ctx.primitiveType()));
        return cast;
    }
    
    @Override
    public JTypeName visitPrimitiveType(PrimitiveTypeContext ctx) {
        return new JTypeName(ctx.getText());
    }
    
    @Override
    public JUnaryCast visitCastReference(CastReferenceContext ctx) {
        JUnaryCast cast = new JUnaryCast();
        if (has(ctx.unaryExpression())) {
            cast.setExpression(visitUnaryExpression(ctx.unaryExpression()));
        }
        
        cast.addBound(visitReferenceType(ctx.referenceType()));
        ctx.additionalBound().forEach(b -> cast.addBound(visitAdditionalBound(b)));
        return cast;
    }
    
    @Override
    public JTypeName visitAdditionalBound(AdditionalBoundContext ctx) {
        return visitInterfaceType(ctx.interfaceType());
    }
    
    @Override
    public JTypeName visitInterfaceType(InterfaceTypeContext ctx) {
        return visitClassType(ctx.classType());
    }
    
    @Override
    public JExpressionUnary visitBinaryExpression(BinaryExpressionContext ctx) {
        return new JUnaryPrePost(visitUnaryExpression(ctx.unaryExpression()),
                                 visitBinaryOperator(ctx.binaryOperator()));
    }
    
    @Override
    public JUnaryOperator visitBinaryOperator(BinaryOperatorContext ctx) {
        return JUnaryOperator.fromJava(ctx.getText());
    }
    
    @Override
    public JExpression visitPostfixExpression(PostfixExpressionContext ctx) {
        if (has(ctx.postfixOperator())) {
            JUnaryPrePost expression = new JUnaryPrePost();
            if (has(ctx.primary())) {
                expression.setExpression(visitPrimary(ctx.primary()));
            } else {
                expression.setExpression(visitExpressionName(ctx.expressionName()));
            }
            expression.setOperator(visitPostfixOperator(ctx.postfixOperator()));
            return expression;
        } else if (has(ctx.primary())) {
            return visitPrimary(ctx.primary());
        } else if (has(ctx.expressionName())) {
            return visitExpressionName(ctx.expressionName());
        } else {
            return null;
        }
    }
    
    @Override
    public JExpression visitPrimary(PrimaryContext ctx) {
        return (JExpression) super.visitPrimary(ctx);
    }
    
    @Override
    public List<JTypeName> visitInterfaceTypeList(InterfaceTypeListContext ctx) {
        return ctx.interfaceType()
                .stream()
                .map(c -> new JTypeName(c.getText()))
                .collect(Collectors.toList());
    }
    
    @Override
    public JTypeParameter visitTypeParameter(TypeParameterContext ctx) {
        JTypeParameter typeParameter = new JTypeParameter(visitIdentifier(ctx.identifier()));
        if (has(ctx.typeBound())) {
            typeParameter.setBounds((List<JTypeName>) visit(ctx.typeBound()));
        }
        return typeParameter;
    }
    
    @Override
    public List<JTypeName> visitSimpleTypeBound(Java8Parser.SimpleTypeBoundContext ctx) {
        JTypeName typeName = new JTypeName(ctx.typeVariable().getText());
        return Collections.singletonList(typeName);
    }
    
    @Override
    public List<JTypeName> visitClassTypeBound(Java8Parser.ClassTypeBoundContext ctx) {
        JTypeName initial = new JTypeName(ctx.classOrInterfaceType().getText());
        List<JTypeName> bounds = new ArrayList<>();
        bounds.add(initial);
        for (AdditionalBoundContext e : ctx.additionalBound()) {
            bounds.add(new JTypeName(e.getText()));
        }
        return bounds;
    }
    
    private void setClassSuperClass(ClassDeclarationContext ctx, JClass clazz) {
        if (has(ctx.superclass())) {
            JTypeName extendsType = new JTypeName(ctx.superclass().getText());
            clazz.setSuperType(Optional.of(extendsType));
        }
    }
    
    
    @Override
    public JClassBody visitClassBody(ClassBodyContext ctx) {
        return visitClassBodyDeclarations(ctx.classBodyDeclaration());
    }
    
    @Override
    public JClassMember visitClassBodyDeclaration(ClassBodyDeclarationContext ctx) {
        if (has(ctx.classDeclaration())) {
            return visitClassDeclaration(ctx.classDeclaration());
        } else if (has(ctx.interfaceDeclaration())) {
            return (JClassMember) visitInterfaceDeclaration(ctx.interfaceDeclaration());
        } else if (has(ctx.fieldDeclaration())) {
            return visitFieldDeclaration(ctx.fieldDeclaration());
        } else if (has(ctx.methodDeclaration())) {
            return visitMethodDeclaration(ctx.methodDeclaration());
        } else if (has(ctx.constructorDeclaration())) {
            return visitConstructorDeclaration(ctx.constructorDeclaration());
        } else if (has(ctx.instanceInitializer())) {
            return visitInstanceInitializer(ctx.instanceInitializer());
        } else if (has(ctx.staticInitializer())) {
            return visitStaticInitializer(ctx.staticInitializer());
        }
        return null;
    }
    
    @Override
    public JField visitFieldDeclaration(Java8Parser.FieldDeclarationContext ctx) {
        String type = ctx.unannType().getText();
        JTypeName typeName = new JTypeName(type);
        
        JField field = new JField();
        field.setVariables(visitVariableDeclaratorList(ctx.variableDeclaratorList()));
        field.setModifiers(visitModifiers(ctx.modifier()));
        field.setType(typeName);
        
        return field;
    }
    
    @Override
    public List<JVariable> visitVariableDeclaratorList(VariableDeclaratorListContext ctx) {
        return ctx.variableDeclarator()
                .stream()
                .map(this::visitVariableDeclarator)
                .collect(Collectors.toList());
    }
    
    @Override
    public JExpressionParenthesis visitExpressionParenthesis(Java8Parser.ExpressionParenthesisContext ctx) {
        return new JExpressionParenthesis((JExpression) visit(ctx.expression()));
    }
    
    @Override
    public Object visitLeftRightExpression(LeftRightExpressionContext ctx) {
        JLeftRightOperator operator = visitLeftRightOperator(ctx.leftRightOperator());
        JExpression left = (JExpression) visit(ctx.conditionalExpression(0));
        JExpression right = (JExpression) visit(ctx.conditionalExpression(1));
        return new JExpressionLeftRight(operator, left, right);
    }
    
    @Override
    public JLeftRightOperator visitLeftRightOperator(LeftRightOperatorContext ctx) {
        JLeftRightOperator operator;
        String text = ctx.getText();
        if (has((operator = getOperator(JLeftRightOperatorConditional.values(), text)))) {
            return operator;
        } else if (has((operator = getOperator(JLeftRightOperatorBinary.values(), text)))) {
            return operator;
        } else if (has((operator = getOperator(JLeftRightOperatorMath.values(), text)))) {
            return operator;
        } else {
            throw new IllegalArgumentException(text);
        }
    }
    
    private JLeftRightOperator getOperator(JLeftRightOperator[] values, String text) {
        for (JLeftRightOperator value : values) {
            if (value.getJavaString().equals(text)) {
                return value;
            }
        }
        return null;
    }
    
    @Override
    public JExpressionTernary visitConditionalTernary(ConditionalTernaryContext ctx) {
        JExpression left = (JExpression) visit(ctx.conditionalExpression(0));
        JExpression right = (JExpression) visit(ctx.conditionalExpression(1));
        JExpression condition = visitExpression(ctx.expression());
        return new JExpressionTernary(left, condition, right);
    }
    
    @Override
    public JLiteralNumeric visitIntegerLiteral(Java8Parser.IntegerLiteralContext ctx) {
        String text = ctx.IntegerLiteral().getText().replace("_", "");
        if (text.endsWith("L")) {
            return new JLiteralLong(Long.parseLong(text.substring(0, text.length() - 1)));
        } else if (text.contains("x")) {
            int value = Integer.parseInt(text.substring(2), 16);
            return new JLiteralInteger(value);
        } else if (text.contains("b")) {
            int value = Integer.parseInt(text.substring(2), 2);
            return new JLiteralInteger(value);
        } else {
            return new JLiteralInteger(Integer.parseInt(text));
        }
    }
    
    @Override
    public JLiteralString visitStringLiteral(Java8Parser.StringLiteralContext ctx) {
        String string = ctx.getText().substring(1, ctx.getText().length() - 1);
        return new JLiteralString(JavaStringUtils.unescapeString(string));
    }
    
    @Override
    public JVariable visitVariableDeclarator(VariableDeclaratorContext ctx) {
        JVariable variable = new JVariable();
        variable.setIdentifier(visitVariableDeclaratorId(ctx.variableDeclaratorId()));
        
        if (has(ctx.variableInitializer())) {
            JExpression visit = (JExpression) visit(ctx.variableInitializer());
            variable.setInitializer(Optional.of(visit));
        }
        
        return variable;
    }
    
    @Override
    public JEnum visitEnumDeclaration(Java8Parser.EnumDeclarationContext ctx) {
        JEnum jEnum = new JEnum();
        jEnum.setAnnotations(visitAnnotations(ctx.annotation()));
        jEnum.setModifiers(visitModifiers(ctx.modifier()));
        jEnum.setIdentifier(visitIdentifier(ctx.identifier()));
        if (has(ctx.superinterfaces())) {
            jEnum.setSuperInterfaces(visitSuperinterfaces(ctx.superinterfaces()));
        }
        jEnum.setBody(visitEnumBody(ctx.enumBody()));
        return jEnum;
    }
    
    @Override
    public JEnumBody visitEnumBody(EnumBodyContext ctx) {
        JEnumBody body = visitEnumBodyDeclarations(ctx.enumBodyDeclarations());
        visitEnumConstantList(ctx.enumConstantList()).forEach(body::addElement);
        return body;
    }
    
    @Override
    public JEnumBody visitEnumBodyDeclarations(EnumBodyDeclarationsContext ctx) {
        JClassBody classBody = visitClassBodyDeclarations(ctx.classBodyDeclaration());
        JEnumBody body = new JEnumBody();
        for (JClassMember member : classBody.getElements()) {
            body.addElement(member);
        }
        return body;
    }
    
    private JClassBody visitClassBodyDeclarations(Collection<ClassBodyDeclarationContext> ctx) {
        JClassBody body = new JClassBody();
        ctx.stream().map(this::visitClassBodyDeclaration).forEachOrdered(body::addElement);
        return body;
    }
    
    @Override
    public List<JEnumConstant> visitEnumConstantList(EnumConstantListContext ctx) {
        List<JEnumConstant> constants = new ArrayList<>();
        for (EnumConstantContext enumConstantContext : ctx.enumConstant()) {
            constants.add(visitEnumConstant(enumConstantContext));
        }
        return constants;
    }
    
    @Override
    public JEnumConstant visitEnumConstant(EnumConstantContext ctx) {
        JEnumConstant constant = new JEnumConstant(visitIdentifier(ctx.identifier()));
        constant.setAnnotations(visitAnnotations(ctx.annotation()));
        if (has(ctx.argumentList())) {
            constant.setArguments(visitArgumentList(ctx.argumentList()));
        }
        if (has(ctx.classBody())) {
            JClassBody body = visitClassBody(ctx.classBody());
            constant.setBody(Optional.of(body));
        }
        return constant;
    }
    
    
    private List<JModifier> visitModifiers(Collection<ModifierContext> ctx) {
        return ctx.stream().map(this::visitModifier).collect(Collectors.toList());
    }
    
    @Override
    public JModifier visitModifier(ModifierContext ctx) {
        return JModifier.fromJava(ctx.getText());
    }
    
    @Override
    public JInterface visitNormalInterfaceDeclaration(NormalInterfaceDeclarationContext ctx) {
        JInterface jInterface = new JInterface();
        jInterface.setAnnotations(visitAnnotations(ctx.annotation()));
        if (has(ctx.modifier())) {
            jInterface.setModifiers(visitModifiers(ctx.modifier()));
        }
        jInterface.setIdentifier(visitIdentifier(ctx.identifier()));
        if (has(ctx.typeParameters())) {
            jInterface.setTypeParameters(visitTypeParameters(ctx.typeParameters()));
        }
        
        if (has(ctx.extendsInterfaces())) {
            jInterface.setSuperInterfaces(visitExtendsInterfaces(ctx.extendsInterfaces()));
        }
        
        jInterface.setBody(visitInterfaceBody(ctx.interfaceBody()));
        return jInterface;
    }
    
    @Override
    public JInterfaceBody visitInterfaceBody(InterfaceBodyContext ctx) {
        JInterfaceBody body = new JInterfaceBody();
        if (has(ctx.interfaceMemberDeclaration())) {
            visitInterfaceMemberDeclarations(ctx.interfaceMemberDeclaration()).forEach
                    (body::addElement);
        }
        return body;
    }
    
    private List<JInterfaceMember> visitInterfaceMemberDeclarations
            (Collection<InterfaceMemberDeclarationContext> ctx) {
        return ctx.stream().map(this::visitInterfaceMemberDeclaration).collect(Collectors.toList());
    }
    
    @Override
    public JInterfaceMethod visitInterfaceMethodDeclaration(InterfaceMethodDeclarationContext ctx) {
        JInterfaceMethod method = new JInterfaceMethod();
        method.setHeader(visitMethodHeader(ctx.methodHeader()));
        if (has(ctx.methodBody().block())) {
            JBlock block = visitMethodBody(ctx.methodBody());
            method.setBody(Optional.of(block));
        }
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
    public List<JTypeName> visitExtendsInterfaces(ExtendsInterfacesContext ctx) {
        return visitInterfaceTypeList(ctx.interfaceTypeList());
    }
    
    private List<JAnnotation> visitAnnotations(Collection<AnnotationContext> list) {
        return list.stream()
                .map(this::visitAnnotation)
                .map(JAnnotation.class::cast)
                .collect(Collectors.toList());
    }
    
    private List<JType> visitTypeDeclarations(List<TypeDeclarationContext> list) {
        return list.stream()
                .map(this::visitTypeDeclaration)
                .map(JType.class::cast)
                .collect(Collectors.toList());
    }
    
    private List<JImport> visitImports(Collection<ImportDeclarationContext> imports) {
        return imports.stream()
                .map(this::visitImportDeclaration)
                .map(JImport.class::cast)
                .collect(Collectors.toList());
    }
    
    @Override
    public JMethod visitMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
        JMethodHeader header = visitMethodHeader(ctx.methodHeader());
        JBlock block = visitBlock(ctx.methodBody().block());
        JMethod method = new JMethod(header, block);
        
        method.getHeader().setModifiers(visitModifiers(ctx.modifier()));
        
        return method;
    }
    
    @Override
    public JMethodHeader visitMethodHeader(MethodHeaderContext ctx) {
        JMethodHeader header = new JMethodHeader();
        header.setIdentifier(visitIdentifier(ctx.methodDeclarator().identifier()));
        header.setAnnotations(visitAnnotations(ctx.annotation()));
        if (has(ctx.typeParameters())) {
            header.setTypeParameters(visitTypeParameters(ctx.typeParameters()));
        }
        
        if (has(ctx.methodDeclarator().formalParameterList())) {
            header.setParameters(visitFormalParameterList(ctx.methodDeclarator()
                                                                  .formalParameterList()));
        }
        
        header.setReturns(visitResult(ctx.result()));
        if (has(ctx.throws_())) {
            header.setThrowsTypes(visitThrows_(ctx.throws_()));
        }
        return header;
    }
    
    @Override
    public JTypeName visitResult(ResultContext ctx) {
        return new JTypeName(ctx.getText());
    }
    
    @Override
    public List<JTypeParameter> visitTypeParameters(TypeParametersContext ctx) {
        List<TypeParameterContext> list = ctx.typeParameterList().typeParameter();
        return list.stream().map(this::visitTypeParameter).collect(Collectors.toList());
    }
    
    @Override
    public JBlock visitBlock(Java8Parser.BlockContext ctx) {
        JBlock block = new JStatementBlock();
        for (BlockStatementContext statementCtx : ctx.blockStatement()) {
            JStatement statement = (JStatement) visitBlockStatement(statementCtx);
            block.addElement(statement);
        }
        return block;
    }
    
    // k?
    @Override
    public Object visitExpressionStatement(ExpressionStatementContext ctx) {
        return visit(ctx.statementExpression());
    }
    
    @Override
    public JExpressionLambda visitLambdaExpression(LambdaExpressionContext ctx) {
        JLambdaBody body = visitLambdaBody(ctx.lambdaBody());
        JExpressionLambda lambda = new JExpressionLambda(body);
        lambda.setParameters(visitLambdaParameters(ctx.lambdaParameters()));
        return lambda;
    }
    
    @Override
    public List<JLambdaParameter> visitLambdaParameters(LambdaParametersContext ctx) {
        if (has(ctx.lambdaIdentifierParameter())) {
            return Collections.singletonList(visitLambdaIdentifierParameter(ctx.lambdaIdentifierParameter()));
        } else if (has(ctx.lambdaParameterList())) {
            return visitLambdaParameterList(ctx.lambdaParameterList());
        } else {
            return (List<JLambdaParameter>) visit(ctx.lambdaInteredParameterList());
        }
    }
    
    
    @Override
    public JLambdaParameter visitLambdaIdentifierParameter(LambdaIdentifierParameterContext ctx) {
        return new JIdentifier(ctx.getText());
    }
    
    @Override
    public List<JLambdaParameter> visitLambdaParameterList(LambdaParameterListContext ctx) {
        if (has(ctx.formalParameterList())) {
            return new ArrayList<>(visitFormalParameterList(ctx.formalParameterList()));
        } else {
            return Collections.emptyList();
        }
    }
    
    @Override
    public List<JLambdaParameter> visitLambdaInferedParameterList
            (LambdaInferedParameterListContext ctx) {
        return new ArrayList<>(visitInferredFormalParameterList(ctx.inferredFormalParameterList()));
    }
    
    @Override
    public List<JIdentifier> visitInferredFormalParameterList(InferredFormalParameterListContext 
                                                                          ctx) {
        return ctx.identifier().stream().map(this::visitIdentifier).collect(Collectors.toList());
    }
    
    @Override
    public JLambdaBody visitLambdaBody(LambdaBodyContext ctx) {
        return (JLambdaBody) super.visitLambdaBody(ctx);
    }
    
    @Override
    public List<JTypeName> visitThrows_(Java8Parser.Throws_Context ctx) {
        return visitExceptionTypeList(ctx.exceptionTypeList());
    }
    
    @Override
    public List<JTypeName> visitExceptionTypeList(Java8Parser.ExceptionTypeListContext ctx) {
        return ctx.exceptionType()
                .stream()
                .map(c -> new JTypeName(c.getText()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<JParameter> visitFormalParameterList(FormalParameterListContext ctx) {
        List<JParameter> parameters = new ArrayList<>();
        
        if (has(ctx.receiverParameter())) {
            parameters.add(visitReceiverParameter(ctx.receiverParameter()));
        }
        if (has(ctx.formalParameters())) {
            ctx.formalParameters()
                    .formalParameter()
                    .forEach(c -> parameters.add(visitFormalParameter(c)));
        }
        
        
        if (has(ctx.lastFormalParameter())) {
            parameters.add(visitLastFormalParameter(ctx.lastFormalParameter()));
        }
        return parameters;
    }
    
    @Override
    public JParameter visitFormalParameter(FormalParameterContext ctx) {
        return createJParameter(ctx.variableDeclaratorId(), ctx.unannType());
    }
    
    @Override
    public JParameter visitReceiverParameter(ReceiverParameterContext ctx) {
        throw new UnsupportedOperationException("I haven't implemented this yet");
    }
    
    private JParameter createJParameter(VariableDeclaratorIdContext variableDeclaratorIdContext,
                                        UnannTypeContext unannTypeContext) {
        JIdentifier name = visitVariableDeclaratorId(variableDeclaratorIdContext);
        JTypeName type = new JTypeName(unannTypeContext.getText());
        JParameter parameter = new JParameter(type, name);
        parameter.setType(type);
        return parameter;
    }
    
    @Override
    public JParameter visitLastFormalParameter(Java8Parser.LastFormalParameterContext ctx) {
        if (has(ctx.formalParameter())) {
            return visitFormalParameter(ctx.formalParameter());
        } else {
            return createJParameter(ctx.variableDeclaratorId(), ctx.unannType());
        }
    }
    
    @Override
    public JStatementLocalVariableDeclaration visitLocalVariableDeclarationStatement(
            LocalVariableDeclarationStatementContext ctx) {
        return visitLocalVariableDeclaration(ctx.localVariableDeclaration());
    }
    
    @Override
    public JStatementLocalVariableDeclaration visitLocalVariableDeclaration(
            LocalVariableDeclarationContext ctx) {
        JStatementLocalVariableDeclaration declaration = new JStatementLocalVariableDeclaration();
        declaration.setAnnotations(visitAnnotations(ctx.annotation()));
        declaration.setModifiers(visitModifiers(ctx.modifier()));
        declaration.setType(visitUnannType(ctx.unannType()));
        declaration.setVariables(visitVariableDeclaratorList(ctx.variableDeclaratorList()));
        return declaration;
    }
    
    @Override
    public JStatementEmpty visitEmptyStatement(Java8Parser.EmptyStatementContext ctx) {
        return new JStatementEmpty() {
        };
    }
    
    @Override
    public JStatementExpression visitStatementAssignment_nocol(StatementAssignment_nocolContext 
                                                                           ctx) {
        return new JStatementExpression(visitAssignment(ctx.assignment()));
    }
    
    @Override
    public JExpressionAssignment visitAssignment(Java8Parser.AssignmentContext ctx) {
        JExpressionAssignment assignment = new JExpressionAssignment();
        assignment.setLeft(visitLeftHandSide(ctx.leftHandSide()));
        assignment.setOperator(visitAssignmentOperator(ctx.assignmentOperator()));
        assignment.setRight(visitExpression(ctx.expression()));
        return assignment;
    }
    
    @Override
    public JAssignmentOperator visitAssignmentOperator(AssignmentOperatorContext ctx) {
        return JAssignmentOperator.fromJava(ctx.getText());
    }
    
    @Override
    public JExpressionName visitExpressionName(ExpressionNameContext ctx) {
        JExpressionName name = new JExpressionName(new JIdentifier(ctx.Identifier().getText()));
        if (has(ctx.propertyLookup())) {
            name.setArea(Optional.of(visitPropertyLookup(ctx.propertyLookup())));
        }
        return name;
    }
    
    @Override
    public JStatementExpression visitStatementPre_nocol(StatementPre_nocolContext ctx) {
        return new JStatementExpression(visitPreExpression(ctx.preExpression()));
    }
    
    @Override
    public JExpressionUnary visitPreExpression(PreExpressionContext ctx) {
        return new JUnaryPrePost((JExpression) visit(ctx.unaryExpression()),
                                 visitPrefixOperator(ctx.prefixOperator()));
    }
    
    @Override
    public JUnaryOperator visitPrefixOperator(PrefixOperatorContext ctx) {
        if (ctx.getText().equals("++")) {
            return JUnaryOperator.PRE_INCREMENT;
        } else {
            return JUnaryOperator.PRE_DECREMENT;
        }
    }
    
    @Override
    public JUnaryOperator visitPostfixOperator(PostfixOperatorContext ctx) {
        if (ctx.getText().equals("++")) {
            return JUnaryOperator.POST_INCREMENT;
        } else {
            return JUnaryOperator.POST_DECREMENT;
        }
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
    
    @Override
    public JExpressionClassReference visitPrimaryClassType(PrimaryClassTypeContext ctx) {
        JTypeName type = visitPrimaryClassTypeAlternates(ctx.primaryClassTypeAlternates());
        
        if (ctx.bracketPair().size() != 0) {
            Optional<JPropertyLookup> area = type.getArea();
            type = new JArrayTypeName(type.getType(), ctx.bracketPair().size());
            type.setArea(area);
        }
        
        return new JExpressionClassReference(type);
    }
    
    @Override
    public JTypeName visitPrimaryClassTypeAlternates(PrimaryClassTypeAlternatesContext ctx) {
        return (JTypeName) super.visitPrimaryClassTypeAlternates(ctx);
    }
    
    @Override
    public JArrayTypeName visitArrayType(ArrayTypeContext ctx) {
        return new JArrayTypeName(ctx.arrayTypeName().getText(), ctx.arrayDimension().size());
    }
    
    @Override
    public JStatementExpression visitStatementClassInstanceCreation_nocol(
            StatementClassInstanceCreation_nocolContext ctx) {
        return new JStatementExpression(visitClassInstanceCreationExpression(ctx.classInstanceCreationExpression()));
    }
    
    @Override
    public JStatementBasicFor visitBasicForStatement(BasicForStatementContext ctx) {
        JStatementBasicFor statement = new JStatementBasicFor();
        statement.setStatement(visitStatement(ctx.statement()));
        if (has(ctx.forInit() )) {
            statement.setInit(visitForInit(ctx.forInit()));
        }
        if (has(ctx.expression() )) {
            statement.setCondition(Optional.of(visitExpression(ctx.expression())));
        }
        if (has(ctx.forUpdate())) {
            statement.setUpdate(visitForUpdate(ctx.forUpdate()));
        }
        return statement;
    }
    
    @Override
    public List<JStatement> visitForUpdate(ForUpdateContext ctx) {
        return visitStatementExpressionList(ctx.statementExpressionList());
    }
    
    @Override
    public List<JStatement> visitForInit(ForInitContext ctx) {
        if (has(ctx.localVariableDeclaration())) {
            return Collections.singletonList(visitLocalVariableDeclaration(ctx.localVariableDeclaration()));
        } else {
            return visitStatementExpressionList(ctx.statementExpressionList());
        }
    }
    
    @Override
    public JStatement visitStatementExpression(StatementExpressionContext ctx) {
        return (JStatement) super.visitStatementExpression(ctx);
    }
    
    @Override
    public List<JStatement> visitStatementExpressionList(StatementExpressionListContext ctx) {
        return ctx.statementExpression()
                .stream()
                .map(this::visitStatementExpression)
                .collect(Collectors.toList());
    }
    
    @Override
    public JStatement visitStatementPost_nocol(StatementPost_nocolContext ctx) {
        return new JStatementExpression(visitPostExpression(ctx.postExpression()));
    }
    
    @Override
    public JExpression visitPostExpression(PostExpressionContext ctx) {
        JExpression expression;
        if (has(ctx.primary())) {
            expression = visitPrimary(ctx.primary());
        } else {
            expression = visitExpressionName(ctx.expressionName());
        }
        return new JUnaryPrePost(expression, visitPostfixOperator(ctx.postfixOperator()));
    }
    
    @Override
    public JExpressionClassInstanceCreation visitClassInstanceCreationExpression(
            ClassInstanceCreationExpressionContext ctx) {
        JTypeName type = visitClassIdentifier(ctx.classIdentifier());
        JExpressionClassInstanceCreation creationExpression =
                new JExpressionClassInstanceCreation(type);
        
        if (has(ctx.typeArgumentsOrDiamond())) {
            List<JTypeArgument> arguments =
                    (List<JTypeArgument>) visit(ctx.typeArgumentsOrDiamond());
            creationExpression.setTypeArguments(arguments);
            
        }
        
        if (has(ctx.argumentList())) {
            List<JExpression> argumentList = visitArgumentList(ctx.argumentList());
            creationExpression.setArguments(argumentList);
        }
        
        if (has(ctx.classBody())) {
            JClassBody body = visitClassBody(ctx.classBody());
            creationExpression.setBody(Optional.of(body));
        }
        
        return creationExpression;
    }
    
    @Override
    public List<JTypeArgument> visitDiamond(DiamondContext ctx) {
        return Collections.emptyList();
    }
    
    @Override
    public List<JTypeArgument> visitTypeArguments(TypeArgumentsContext ctx) {
        return ctx.typeArgumentList()
                .typeArgument()
                .stream()
                .map(this::visitTypeArgument)
                .collect(Collectors.toList());
    }
    
    @Override
    public JTypeArgument visitTypeArgument(TypeArgumentContext ctx) {
        if (has(ctx.wildcard())) {
            return visitWildcard(ctx.wildcard());
        } else {
            return new JTypeArgumentReference(visitReferenceType(ctx.referenceType()));
        }
    }
    
    @Override
    public JTypeArgument visitWildcard(WildcardContext ctx) {
        
        JTypeArgumentWildcard wildcard = new JTypeArgumentWildcard();
        if (has(ctx.wildcardBounds())) {
            JWildcardType type = visitWildcardType(ctx.wildcardBounds().wildcardType());
            JTypeName reference = visitReferenceType(ctx.wildcardBounds().referenceType());
            wildcard.setReference(Optional.of(reference));
            wildcard.setType(Optional.of(type));
        }
        return wildcard;
    }
    
    @Override
    public JWildcardType visitWildcardType(WildcardTypeContext ctx) {
        return JWildcardType.fromJava(ctx.getText());
    }
    
    @Override
    public JTypeName visitReferenceType(ReferenceTypeContext ctx) {
        return new JTypeName(ctx.getText());
    }
    
    @Override
    public List<JExpression> visitArgumentList(ArgumentListContext ctx) {
        return ctx.expression().stream().map(this::visitExpression).collect(Collectors.toList());
    }
    
    @Override
    public JTypeName visitClassIdentifier(ClassIdentifierContext ctx) {
        return new JTypeName(ctx.getText());
    }
    
    @Override
    public JStatementLabeled visitLabeledStatement(LabeledStatementContext ctx) {
        JStatementLabeled statement = new JStatementLabeled();
        statement.setIdentifier(visitIdentifier(ctx.identifier()));
        statement.setStatement(visitStatement(ctx.statement()));
        return statement;
    }
    
    @Override
    public JStatementLabeled visitLabeledStatementNoShortIf(LabeledStatementNoShortIfContext ctx) {
        JStatementLabeled statement = new JStatementLabeled();
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
    public JStatementIf visitIfThenStatement(IfThenStatementContext ctx) {
        JStatementIf statement = new JStatementIf();
        statement.setExpression(visitExpression(ctx.expression()));
        statement.setStatement(visitStatement(ctx.statement()));
        return statement;
    }
    
    @Override
    public JStatementIf visitIfThenElseStatement(IfThenElseStatementContext ctx) {
        JStatementIf statement = new JStatementIf();
        statement.setExpression(visitExpression(ctx.expression()));
        statement.setStatement(visitStatementNoShortIf(ctx.statementNoShortIf()));
        statement.setElseStatement(Optional.of(visitStatement(ctx.statement())));
        return statement;
    }
    
    @Override
    public JStatementIf visitIfThenElseStatementNoShortIf(IfThenElseStatementNoShortIfContext ctx) {
        JStatementIf statement = new JStatementIf();
        statement.setExpression(visitExpression(ctx.expression()));
        statement.setStatement(visitStatementNoShortIf(ctx.statementNoShortIf(0)));
        statement.setElseStatement(Optional.of(visitStatementNoShortIf(ctx.statementNoShortIf(1))));
        return statement;
    }
    
    @Override
    public JStatementAssert visitAssertStatement(AssertStatementContext ctx) {
        JStatementAssert statement = new JStatementAssert();
        statement.setAssertion(visitExpression(ctx.expression(0)));
        if (ctx.expression().size() > 1) {
            JExpression message = visitExpression(ctx.expression(1));
            statement.setMessage(Optional.of(message));
        }
        return statement;
    }
    
    @Override
    public JStatementWhile visitWhileStatement(WhileStatementContext ctx) {
        JStatementWhile statement = new JStatementWhile();
        statement.setExpression(visitExpression(ctx.expression()));
        statement.setStatement(visitStatement(ctx.statement()));
        return statement;
    }
    
    @Override
    public JStatementWhile visitWhileStatementNoShortIf(WhileStatementNoShortIfContext ctx) {
        JStatementWhile statement = new JStatementWhile();
        statement.setExpression(visitExpression(ctx.expression()));
        statement.setStatement(visitStatementNoShortIf(ctx.statementNoShortIf()));
        return statement;
    }
    
    @Override
    public JStatementDoWhile visitDoStatement(DoStatementContext ctx) {
        JStatementDoWhile statement = new JStatementDoWhile();
        statement.setStatement(visitStatement(ctx.statement()));
        statement.setExpression(visitExpression(ctx.expression()));
        return statement;
    }
    
    @Override
    public JStatementEnhancedFor visitEnhancedForStatement(EnhancedForStatementContext ctx) {
        JStatementEnhancedFor statement = new JStatementEnhancedFor();
        JTypeName variableType = visitUnannType(ctx.unannType());
        JIdentifier variableName = visitVariableDeclaratorId(ctx.variableDeclaratorId());
        JParameter variable = new JParameter(variableType, variableName);
        statement.setVariableModifiers(visitModifiers(ctx.modifier()));
        statement.setVariable(variable);
        statement.setExpression(visitExpression(ctx.expression()));
        statement.setStatement(visitStatement(ctx.statement()));
        return statement;
    }
    
    @Override
    public JStatementEnhancedFor visitEnhancedForStatementNoShortIf(
            EnhancedForStatementNoShortIfContext ctx) {
        JStatementEnhancedFor statement = new JStatementEnhancedFor();
        JTypeName variableType = visitUnannType(ctx.unannType());
        JIdentifier variableName = visitVariableDeclaratorId(ctx.variableDeclaratorId());
        JParameter variable = new JParameter(variableType, variableName);
        statement.setVariable(variable);
        statement.setExpression(visitExpression(ctx.expression()));
        statement.setStatement(visitStatementNoShortIf(ctx.statementNoShortIf()));
        return statement;
    }
    
    @Override
    public JStatementBreak visitBreakStatement(BreakStatementContext ctx) {
        JStatementBreak statement = new JStatementBreak();
        if (has(ctx.identifier())) {
            JIdentifier identifier = visitIdentifier(ctx.identifier());
            statement.setIdentifier(Optional.of(identifier));
        }
        return statement;
    }
    
    @Override
    public JStatementContinue visitContinueStatement(ContinueStatementContext ctx) {
        JStatementContinue statement = new JStatementContinue();
        if (has(ctx.identifier())) {
            JIdentifier identifier = visitIdentifier(ctx.identifier());
            statement.setIdentifier(Optional.of(identifier));
        }
        return statement;
    }
    
    @Override
    public JStatementReturn visitReturnStatement(ReturnStatementContext ctx) {
        JStatementReturn statement = new JStatementReturn();
        if (has(ctx.expression())) {
            JExpression expression = visitExpression(ctx.expression());
            statement.setExpression(Optional.of(expression));
        }
        return statement;
    }
    
    @Override
    public JStatementThrow visitThrowStatement(ThrowStatementContext ctx) {
        JStatementThrow statement = new JStatementThrow();
        if (has(ctx.expression())) {
            statement.setExpression(visitExpression(ctx.expression()));
        }
        return statement;
    }
    
    @Override
    public JStatementSynchronized visitSynchronizedStatement(SynchronizedStatementContext ctx) {
        JStatementSynchronized statement = new JStatementSynchronized();
        statement.setExpression(visitExpression(ctx.expression()));
        statement.setBlock(visitBlock(ctx.block()));
        return statement;
    }
    
    @Override
    public JStatementTry visitTryCatchStatement(TryCatchStatementContext ctx) {
        JStatementTry statement = new JStatementTry();
        statement.setBlock(visitBlock(ctx.block()));
        statement.setCatchClauses(visitCatches(ctx.catches()));
        return statement;
    }
    
    @Override
    public JStatementTry visitTryCatchFinallyStatement(TryCatchFinallyStatementContext ctx) {
        JStatementTry statement = new JStatementTry();
        statement.setBlock(visitBlock(ctx.block()));
        if (has(ctx.catches())) {
            statement.setCatchClauses(visitCatches(ctx.catches()));
        }
        JBlock finallyBlock = visitFinally_(ctx.finally_());
        statement.setFinallyBlock(Optional.of(finallyBlock));
        return statement;
    }
    
    @Override
    public JBlock visitFinally_(Finally_Context ctx) {
        return visitBlock(ctx.block());
    }
    
    @Override
    public List<JCatchClause> visitCatches(CatchesContext ctx) {
        List<JCatchClause> catches = new ArrayList<>();
        for (CatchClauseContext catchClauseContext : ctx.catchClause()) {
            catches.add(visitCatchClause(catchClauseContext));
        }
        return catches;
    }
    
    @Override
    public JCatchClause visitCatchClause(CatchClauseContext ctx) {
        JCatchClause clause = new JCatchClause();
        clause.setModifiers(visitModifiers(ctx.modifier()));
        List<JTypeName> catchTypes = new ArrayList<>();
        catchTypes.add(visitUnannClassType(ctx.unannClassType()));
        if (has(ctx.classType())) {
            for (ClassTypeContext classTypeContext : ctx.classType()) {
                catchTypes.add(visitClassType(classTypeContext));
            }
        }
        clause.setCatchTypes(catchTypes);
        clause.setVariable(visitVariableDeclaratorId(ctx.variableDeclaratorId()));
        clause.setBlock(visitBlock(ctx.block()));
        return clause;
    }
    
    @Override
    public JLiteralCharacter visitCharacterLiteral(CharacterLiteralContext ctx) {
        String text = ctx.CharacterLiteral().getText();
        text = text.substring(1, text.length() - 1);
        text = JavaStringUtils.unescapeString(text);
        return new JLiteralCharacter(text.charAt(0));
    }
    
    @Override
    public JLiteralBoolean visitBooleanLiteral(BooleanLiteralContext ctx) {
        return new JLiteralBoolean(Boolean.parseBoolean(ctx.getText()));
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
        field.setModifiers(visitModifiers(ctx.modifier()));
        field.setType(visitUnannType(ctx.unannType()));
        field.setVariables(visitVariableDeclaratorList(ctx.variableDeclaratorList()));
        return field;
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
        declaration.setIdentifier(visitSimpleTypeName(ctx.constructorDeclarator()
                                                              .simpleTypeName()));
        if (has(ctx.throws_())) {
            declaration.setThrowsTypes(visitThrows_(ctx.throws_()));
        }
        declaration.setBody(visitConstructorBody(ctx.constructorBody()));
        if (has(ctx.constructorDeclarator().formalParameterList())) {
            declaration.setParameters(visitFormalParameterList(ctx.constructorDeclarator()
                                                                       .formalParameterList()));
        }
        
        return declaration;
    }
    
    @Override
    public JBlock visitConstructorBody(ConstructorBodyContext ctx) {
        JBlock block = new JStatementBlock();
        
        List<JStatement> statements = new ArrayList<>();
        if (has(ctx.explicitConstructorInvocation())) {
            statements.add((JStatement) visitExplicitConstructorInvocation(ctx.explicitConstructorInvocation()));
        }
        
        if (has(ctx.blockStatements())) {
            List<JStatement> temp = visitBlockStatements(ctx.blockStatements());
            statements.addAll(temp);
        }
        
        block.setElements(statements);
        return block;
    }
    
    
    @Override
    public JAnnotationType visitAnnotationTypeDeclaration(AnnotationTypeDeclarationContext ctx) {
        JAnnotationType declaration = new JAnnotationType();
        if (has(ctx.annotation())) {
            declaration.setAnnotations(visitAnnotations(ctx.annotation()));
        }
        
        if (has(ctx.modifier())) {
            declaration.setModifiers(visitModifiers(ctx.modifier()));
        }
        
        declaration.setIdentifier(visitIdentifier(ctx.identifier()));
        declaration.setBody(visitAnnotationTypeBody(ctx.annotationTypeBody()));
        return declaration;
    }
    
    @Override
    public JAnnotationBody visitAnnotationTypeBody(AnnotationTypeBodyContext ctx) {
        JAnnotationBody body = new JAnnotationBody();
        body.setElements(visitAnnotationBody(ctx));
        return body;
    }
    
    private List<JAnnotationMember> visitAnnotationBody(AnnotationTypeBodyContext ctx) {
        return ctx.annotationTypeMemberDeclaration()
                .stream()
                .map(this::visitAnnotationTypeMemberDeclaration)
                .collect(Collectors.toList());
    }
    
    @Override
    public JAnnotationMember visitAnnotationTypeMemberDeclaration(
            AnnotationTypeMemberDeclarationContext ctx) {
        return (JAnnotationMember) super.visitAnnotationTypeMemberDeclaration(ctx);
    }
    
    @Override
    public JAnnotationField visitAnnotationTypeElementDeclaration(
            AnnotationTypeElementDeclarationContext ctx) {
        JAnnotationField element = new JAnnotationField();
        element.setAnnotations(visitAnnotations(ctx.annotation()));
        element.setModifiers(visitModifiers(ctx.modifier()));
        element.setType(visitUnannType(ctx.unannType()));
        element.setIdentifier(visitIdentifier(ctx.identifier()));
        
        if (has(ctx.defaultValue())) {
            element.setDefaultValue(Optional.of((JAnnotationValue) visitDefaultValue(ctx.defaultValue())));
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
        if (has(ctx.elementValuePairList())) {
            annotation.setValues(visitElementValuePairList(ctx.elementValuePairList()));
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
        annotation.addValue((JAnnotationValue) visit(ctx.elementValue()));
        return annotation;
    }
    
    @Override
    public List<JAnnotationValue> visitElementValuePairList(ElementValuePairListContext ctx) {
        return ctx.elementValuePair()
                .stream()
                .map(this::visitElementValuePair)
                .collect(Collectors.toList());
    }
    
    @Override
    public JAnnotationValue visitElementValuePair(ElementValuePairContext ctx) {
        JValuePair pair = new JValuePair();
        pair.setIdentifier(visitIdentifier(ctx.identifier()));
        pair.setValue((JAnnotationElementValue) visit(ctx.elementValue()));
        return pair;
    }
    
    @Override
    public JTypeName visitTypeName(TypeNameContext ctx) {
        JTypeName typeName = new JTypeName(ctx.Identifier().getText());
        if (has(ctx.packageName())) {
            typeName.setArea(Optional.of(visitPackageName(ctx.packageName())));
        }
        return typeName;
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
    public JLiteralFloating<? extends Number> visitFloatingPointLiteral
            (FloatingPointLiteralContext ctx) {
        if (ctx.getText().endsWith("D")) {
            return new JLiteralDouble(Double.parseDouble(ctx.getText()));
        } else {
            return new JLiteralFloat(Float.parseFloat(ctx.getText()));
        }
    }
    
    @Override
    public JStatementSwitch visitSwitchStatement(SwitchStatementContext ctx) {
        JStatementSwitch statement = new JStatementSwitch();
        statement.setExpression(visitExpression(ctx.expression()));
        statement.setCaseElements(visitSwitchBlock(ctx.switchBlock()));
        return statement;
        
    }
    
    @Override
    public List<JSwitchCase> visitSwitchBlock(SwitchBlockContext ctx) {
        List<JSwitchCase> cases = new ArrayList<>();
        for (SwitchCaseContext caseContext : ctx.switchCase()) {
            JSwitchCase switchCase = visitSwitchCase(caseContext);
            if (has(caseContext.blockStatements())) {
                switchCase.setStatements(visitBlockStatements(caseContext.blockStatements()));
            }
            cases.add(switchCase);
        }
        if (has(ctx.defaultSwitchCase())) {
            cases.add(visitDefaultSwitchCase(ctx.defaultSwitchCase()));
        }
        return cases;
    }
    
    @Override
    public JSwitchCaseExpression visitSwitchCase(SwitchCaseContext ctx) {
        JSwitchCaseExpression switchCase = new JSwitchCaseExpression();
        switchCase.setCondition(visitExpression(ctx.expression()));
        if (has(ctx.blockStatements())) {
            switchCase.setStatements(visitBlockStatements(ctx.blockStatements()));
        }
        return switchCase;
    }
    
    @Override
    public JSwitchCaseDefault visitDefaultSwitchCase(DefaultSwitchCaseContext ctx) {
        JSwitchCaseDefault switchCase = new JSwitchCaseDefault();
        if (has(ctx.blockStatements())) {
            switchCase.setStatements(visitBlockStatements(ctx.blockStatements()));
        }
        return switchCase;
    }
}

package in.kyle.jrefactor.parser;

import org.antlr.v4.runtime.RuleContext;

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
import in.kyle.jrefactor.tree.obj.annotationvalue.JValueSingle;
import in.kyle.jrefactor.tree.obj.expression.*;
import in.kyle.jrefactor.tree.obj.expression.JExpressionLeftRight.JLeftRightOperator;
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
import in.kyle.jrefactor.tree.obj.reference.JExpressionTypeReference;
import in.kyle.jrefactor.tree.obj.reference.JTypeArgument;
import in.kyle.jrefactor.tree.obj.reference.typeargument.JTypeArgumentReference;
import in.kyle.jrefactor.tree.obj.reference.typeargument.JTypeArgumentWildcard;
import in.kyle.jrefactor.tree.obj.statement.JBlock;
import in.kyle.jrefactor.tree.obj.statement.JStatementAssert;
import in.kyle.jrefactor.tree.obj.statement.JStatementEmpty;
import in.kyle.jrefactor.tree.obj.statement.JStatementExpression;
import in.kyle.jrefactor.tree.obj.statement.JStatementLabeled;
import in.kyle.jrefactor.tree.obj.statement.JStatementLocalVariableDeclaration;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementIf;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementReturn;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementSynchronized;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementThrow;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementTry;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.identifiablestatement.JStatementBreak;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.identifiablestatement
        .JStatementContinue;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.statementcontrolloop.JStatementWhile;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.statementcontrolloop.statementfor
        .JStatementEnhancedFor;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.statementcontrolloop.statementwhile
        .JStatementDoWhile;
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
import in.kyle.jrefactor.tree.obj.unit.typebody.JAnnotationBody;
import in.kyle.jrefactor.tree.obj.unit.typebody.JClassBody;
import in.kyle.jrefactor.tree.obj.unit.typebody.JEnumBody;
import in.kyle.jrefactor.tree.obj.unit.typebody.JInterfaceBody;

public class JavaCompositionVisitor extends Java8BaseVisitor<Object> {
    
    @Override
    public JCompilationUnit visitCompilationUnit(Java8Parser.CompilationUnitContext ctx) {
        JCompilationUnit unit = new JCompilationUnit();
        if (ctx.packageDeclaration() != null) {
            String packageName = visitPackageDeclaration(ctx.packageDeclaration());
            unit.setPackageName(Optional.of(packageName));
        }
        List<JImport> imports = visitImports(ctx.importDeclaration());
        List<JType> types = visitTypeDeclarations(ctx.typeDeclaration());
        
        unit.setImportss(imports);
        unit.setTypes(types);
        
        return unit;
    }
    
    @Override
    public String visitPackageDeclaration(PackageDeclarationContext ctx) {
        return ctx.packageName().getText();
    }
    
    @Override
    public JImport visitImportDeclaration(ImportDeclarationContext ctx) {
        JImport jImport = new JImport();
        jImport.setName(ctx.packageOrTypeName().getText());
        if (ctx.import_static() != null) {
            jImport.setStatic(true);
        }
        if (ctx.import_wildcard() != null) {
            jImport.setOnDemand(true);
        }
        return jImport;
    }
    
    @Override
    public JClass visitClassDeclaration(ClassDeclarationContext ctx) {
        List<JAnnotation> annotations = visitAnnotations(ctx.annotation());
        
        JClass clazz = new JClass();
        clazz.setAnnotations(annotations);
        clazz.setModifiers(visitModifiers(ctx.modifier()));
        clazz.setIdentifier(visitIdentifier(ctx.identifier()));
        
        setClassSuperClass(ctx, clazz);
        if (ctx.superinterfaces() != null) {
            clazz.setSuperInterfaces(visitSuperinterfaces(ctx.superinterfaces()));
        }
        
        if (ctx.typeParameters() != null) {
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
        if (ctx.methodArea() != null) {
            JPropertyLookup area = visitMethodArea(ctx.methodArea());
            invocation.setMethodArea(Optional.of(area));
        }
        if (ctx.typeArguments() != null) {
            List<JTypeArgument> typeArguments = visitTypeArguments(ctx.typeArguments());
            invocation.setTypeArguments(typeArguments);
        }
        if (ctx.argumentList() != null) {
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
    public JExpressionUnary visitPrimaryPostInc(PrimaryPostIncContext ctx) {
        return new JExpressionUnary(JExpressionUnary.JOperator.POST_INCREMENT,
                                    (JExpression) visitPrimary(ctx.primary()));
    }
    
    @Override
    public JExpressionUnary visitPrimaryPostDec(PrimaryPostDecContext ctx) {
        return new JExpressionUnary(JExpressionUnary.JOperator.POST_DECREMENT,
                                    (JExpression) visitPrimary(ctx.primary()));
    }
    
    @Override
    public JExpressionUnary visitExpressionPostInc(ExpressionPostIncContext ctx) {
        return new JExpressionUnary(JExpressionUnary.JOperator.POST_INCREMENT,
                                    visitExpressionName(ctx.expressionName()));
    }
    
    @Override
    public JExpressionUnary visitExpressionPostDec(ExpressionPostDecContext ctx) {
        return new JExpressionUnary(JExpressionUnary.JOperator.POST_DECREMENT,
                                    visitExpressionName(ctx.expressionName()));
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
        if (ctx.typeBound() != null) {
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
        if (ctx.superclass() != null) {
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
        if (ctx.classDeclaration() != null) {
            return visitClassDeclaration(ctx.classDeclaration());
        } else if (ctx.interfaceDeclaration() != null) {
            return (JClassMember) visitInterfaceDeclaration(ctx.interfaceDeclaration());
        } else if (ctx.fieldDeclaration() != null) {
            return visitFieldDeclaration(ctx.fieldDeclaration());
        } else if (ctx.methodDeclaration() != null) {
            return visitMethodDeclaration(ctx.methodDeclaration());
        } else if (ctx.constructorDeclaration() != null) {
            return visitConstructorDeclaration(ctx.constructorDeclaration());
        } else if (ctx.instanceInitializer() != null) {
            return visitInstanceInitializer(ctx.instanceInitializer());
        } else if (ctx.staticInitializer() != null) {
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
    public JExpressionLeftRight visitExpressionAdd(Java8Parser.ExpressionAddContext ctx) {
        return new JExpressionLeftRight(JLeftRightOperator.ADD,
                                        (JExpression) visit(ctx.additiveExpression()),
                                        (JExpression) visit(ctx.multiplicativeExpression()));
    }
    
    @Override
    public JExpressionTernary visitConditionalTernary(Java8Parser.ConditionalTernaryContext ctx) {
        return new JExpressionTernary((JExpression) visit(ctx.expression()),
                                      (JExpression) visit(ctx.conditionalOrExpression()),
                                      (JExpression) visit(ctx.conditionalExpression()));
    }
    
    @Override
    public JExpressionLeftRight visitConditionalOr(Java8Parser.ConditionalOrContext ctx) {
        return new JExpressionLeftRight(JLeftRightOperator.CONDITIONAL_OR,
                                        (JExpression) visit(ctx.conditionalOrExpression()),
                                        (JExpression) visit(ctx.conditionalAndExpression()));
    }
    
    @Override
    public JExpressionLeftRight visitConditionalAnd(Java8Parser.ConditionalAndContext ctx) {
        return new JExpressionLeftRight(JLeftRightOperator.CONDITIONAL_OR,
                                        (JExpression) visit(ctx.conditionalAndExpression()),
                                        (JExpression) visit(ctx.inclusiveOrExpression()));
    }
    
    @Override
    public JExpressionLeftRight visitBinaryInclusiveOr(Java8Parser.BinaryInclusiveOrContext ctx) {
        return new JExpressionLeftRight(JLeftRightOperator.BINARY_INCLUSIVE_OR,
                                        (JExpression) visit(ctx.inclusiveOrExpression()),
                                        (JExpression) visit(ctx.exclusiveOrExpression()));
    }
    
    @Override
    public JExpressionLeftRight visitBinaryExclusiveOr(Java8Parser.BinaryExclusiveOrContext ctx) {
        return new JExpressionLeftRight(JLeftRightOperator.BINARY_EXCLUSIVE_OR,
                                        (JExpression) visit(ctx.exclusiveOrExpression()),
                                        (JExpression) visit(ctx.andExpression()));
    }
    
    @Override
    public JExpressionLeftRight visitBinaryAnd(Java8Parser.BinaryAndContext ctx) {
        return new JExpressionLeftRight(JLeftRightOperator.BINARY_AND,
                                        (JExpression) visit(ctx.andExpression()),
                                        (JExpression) visit(ctx.equalityExpression()));
    }
    
    @Override
    public JExpressionLeftRight visitConditionalEquality(Java8Parser.ConditionalEqualityContext 
                                                                     ctx) {
        return new JExpressionLeftRight(JLeftRightOperator.EQUAL,
                                        (JExpression) visit(ctx.equalityExpression()),
                                        (JExpression) visit(ctx.relationalExpression()));
    }
    
    @Override
    public JExpressionLeftRight visitConditionalNotEquality(Java8Parser.ConditionalNotEqualityContext ctx) {
        return new JExpressionLeftRight(JLeftRightOperator.NOT_EQUAL,
                                        (JExpression) visit(ctx.equalityExpression()),
                                        (JExpression) visit(ctx.relationalExpression()));
    }
    
    @Override
    public JExpressionLeftRight visitConditionalLessThan(Java8Parser.ConditionalLessThanContext 
                                                                     ctx) {
        return new JExpressionLeftRight(JLeftRightOperator.CONDITIONAL_LESS_THAN,
                                        (JExpression) visit(ctx.relationalExpression()),
                                        (JExpression) visit(ctx.shiftExpression()));
    }
    
    @Override
    public JExpressionLeftRight visitConditionalGreaterThan(Java8Parser.ConditionalGreaterThanContext ctx) {
        return new JExpressionLeftRight(JLeftRightOperator.CONDITIONAL_GREATER_THAN,
                                        (JExpression) visit(ctx.relationalExpression()),
                                        (JExpression) visit(ctx.shiftExpression()));
    }
    
    @Override
    public JExpressionLeftRight visitConditionalLessThanEq(Java8Parser.ConditionalLessThanEqContext ctx) {
        return new JExpressionLeftRight(JLeftRightOperator.CONDITIONAL_LESS_THAN_EQUAL,
                                        (JExpression) visit(ctx.relationalExpression()),
                                        (JExpression) visit(ctx.shiftExpression()));
    }
    
    @Override
    public JExpressionLeftRight visitConditionalGreatherThanEq(Java8Parser.ConditionalGreatherThanEqContext ctx) {
        return new JExpressionLeftRight(JLeftRightOperator.CONDITIONAL_GREATER_EQUAL,
                                        (JExpression) visit(ctx.relationalExpression()),
                                        (JExpression) visit(ctx.shiftExpression()));
    }
    
    @Override
    public JExpressionLeftRight visitConditionalInstanceOf(Java8Parser.ConditionalInstanceOfContext ctx) {
        
        JLeftRightOperator operator = JLeftRightOperator.INSTANCE_OF;
        JExpression left = (JExpression) visit(ctx.relationalExpression());
        JExpression right =
                new JExpressionTypeReference(visitReferenceType(ctx.referenceType()).getReference
                        ());
        return new JExpressionLeftRight(operator, left, right);
    }
    
    @Override
    public JExpressionLeftRight visitBinaryShiftLeft(Java8Parser.BinaryShiftLeftContext ctx) {
        return new JExpressionLeftRight(JLeftRightOperator.BINARY_SHIFT_LEFT,
                                        (JExpression) visit(ctx.shiftExpression()),
                                        (JExpression) visit(ctx.additiveExpression()));
    }
    
    @Override
    public JExpressionLeftRight visitBinaryShiftRight(Java8Parser.BinaryShiftRightContext ctx) {
        return new JExpressionLeftRight(JLeftRightOperator.BINARY_SHIFT_RIGHT,
                                        (JExpression) visit(ctx.shiftExpression()),
                                        (JExpression) visit(ctx.additiveExpression()));
    }
    
    @Override
    public JExpressionLeftRight visitBinarcyAllignRight(Java8Parser.BinarcyAllignRightContext ctx) {
        return new JExpressionLeftRight(JLeftRightOperator.BINARY_ALLIGN_RIGHT,
                                        (JExpression) visit(ctx.shiftExpression()),
                                        (JExpression) visit(ctx.additiveExpression()));
    }
    
    @Override
    public JExpressionLeftRight visitExpressionSubtract(Java8Parser.ExpressionSubtractContext ctx) {
        return new JExpressionLeftRight(JLeftRightOperator.SUBTRACT,
                                        (JExpression) visit(ctx.additiveExpression()),
                                        (JExpression) visit(ctx.multiplicativeExpression()));
    }
    
    @Override
    public JExpressionLeftRight visitExpressionDivide(Java8Parser.ExpressionDivideContext ctx) {
        return new JExpressionLeftRight(JLeftRightOperator.DIVIDE,
                                        (JExpression) visit(ctx.multiplicativeExpression()),
                                        (JExpression) visit(ctx.unaryExpression()));
    }
    
    @Override
    public JExpressionLeftRight visitExpressionMultiply(Java8Parser.ExpressionMultiplyContext ctx) {
        return new JExpressionLeftRight(JLeftRightOperator.MULTIPLY,
                                        (JExpression) visit(ctx.multiplicativeExpression()),
                                        (JExpression) visit(ctx.unaryExpression()));
    }
    
    @Override
    public JExpressionLeftRight visitExpressionModulus(Java8Parser.ExpressionModulusContext ctx) {
        return new JExpressionLeftRight(JLeftRightOperator.MODULUS,
                                        (JExpression) visit(ctx.multiplicativeExpression()),
                                        (JExpression) visit(ctx.unaryExpression()));
    }
    
    @Override
    public JLiteralNumeric visitIntegerLiteral(Java8Parser.IntegerLiteralContext ctx) {
        String text = ctx.IntegerLiteral().getText();
        if (text.endsWith("L")) {
            return new JLiteralLong(Long.parseLong(ctx.getText()
                                                      .substring(0, ctx.getText().length() - 1)));
        } else {
            return new JLiteralInteger(Integer.parseInt(ctx.getText()));
        }
    }
    
    @Override
    public JLiteralString visitStringLiteral(Java8Parser.StringLiteralContext ctx) {
        return new JLiteralString(ctx.getText().substring(1, ctx.getText().length() - 1));
    }
    
    @Override
    public JVariable visitVariableDeclarator(VariableDeclaratorContext ctx) {
        JVariable variable = new JVariable();
        variable.setName(visitVariableDeclaratorId(ctx.variableDeclaratorId()));
        
        if (ctx.variableInitializer() != null) {
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
        jEnum.setSuperInterfaces(visitSuperinterfaces(ctx.superinterfaces()));
        jEnum.setBody(visitEnumBody(ctx.enumBody()));
        return jEnum;
    }
    
    @Override
    public JEnumBody visitEnumBody(EnumBodyContext ctx) {
        JEnumBody body = visitEnumBodyDeclarations(ctx.enumBodyDeclarations());
        body.setConstants(visitEnumConstantList(ctx.enumConstantList()));
        return body;
    }
    
    @Override
    public JEnumBody visitEnumBodyDeclarations(EnumBodyDeclarationsContext ctx) {
        JClassBody classBody = visitClassBodyDeclarations(ctx.classBodyDeclaration());
        JEnumBody body = new JEnumBody();
        for (JClassMember member : classBody.getMembers()) {
            body.addMember(member);
        }
        return body;
    }
    
    private JClassBody visitClassBodyDeclarations(Collection<ClassBodyDeclarationContext> ctx) {
        JClassBody body = new JClassBody();
        ctx.stream().map(this::visitClassBodyDeclaration).forEachOrdered(body::addMember);
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
        constant.setArguments(visitArgumentList(ctx.argumentList()));
        if (ctx.classBody() != null) {
            JClassBody body = visitClassBody(ctx.classBody());
            constant.setBody(Optional.of(body));
        }
        return constant;
    }
    
    
    private List<JModifier> visitModifiers(Collection<ModifierContext> ctx) {
        return ctx.stream()
                  .map(c -> JModifier.valueOf(c.getText().toUpperCase()))
                  .collect(Collectors.toList());
    }
    
    @Override
    public JInterface visitNormalInterfaceDeclaration(NormalInterfaceDeclarationContext ctx) {
        JInterface jInterface = new JInterface();
        jInterface.setAnnotations(visitAnnotations(ctx.annotation()));
        if (ctx.modifier() != null) {
            jInterface.setModifiers(visitModifiers(ctx.modifier()));
        }
        jInterface.setIdentifier(visitIdentifier(ctx.identifier()));
        if (ctx.typeParameters() != null) {
            jInterface.setTypeParameters(visitTypeParameters(ctx.typeParameters()));
        }
        
        if (ctx.extendsInterfaces() != null) {
            jInterface.setSuperInterfaces(visitExtendsInterfaces(ctx.extendsInterfaces()));
        }
        
        jInterface.setBody(visitInterfaceBody(ctx.interfaceBody()));
        return jInterface;
    }
    
    @Override
    public JInterfaceBody visitInterfaceBody(InterfaceBodyContext ctx) {
        JInterfaceBody body = new JInterfaceBody();
        if (ctx.interfaceMemberDeclaration() != null) {
            visitInterfaceMemberDeclarations(ctx.interfaceMemberDeclaration()).forEach
                    (body::addMember);
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
        if (ctx.methodBody() != null) {
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
        if (ctx.typeParameters() != null) {
            header.setTypeParameters(visitTypeParameters(ctx.typeParameters()));
        }
        
        if (ctx.methodDeclarator().formalParameterList() != null) {
            header.setParameters(visitFormalParameterList(ctx.methodDeclarator()
                                                             .formalParameterList()));
        }
        
        header.setReturns(visitResult(ctx.result()));
        if (ctx.throws_() != null) {
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
    public JExpressionLambda visitLambdaExpression(LambdaExpressionContext ctx) {
        JLambdaBody body = visitLambdaBody(ctx.lambdaBody());
        JExpressionLambda lambda = new JExpressionLambda(body);
        lambda.setParameters(visitLambdaParameters(ctx.lambdaParameters()));
        return lambda;
    }
    
    @Override
    public List<JLambdaParameter> visitLambdaParameters(LambdaParametersContext ctx) {
        if (ctx.lambdaIdentifierParameter() != null) {
            return Collections.singletonList(visitLambdaIdentifierParameter(ctx.lambdaIdentifierParameter()));
        } else if (ctx.lambdaParameterList() != null) {
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
        if (ctx.formalParameterList() != null) {
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
        
        if (ctx.formalParameters() != null) {
            for (FormalParameterContext formalCtx : ctx.formalParameters().formalParameter()) {
                parameters.add(visitFormalParameter(formalCtx));
            }
        }
        
        if (ctx.lastFormalParameter() != null) {
            parameters.add(visitLastFormalParameter(ctx.lastFormalParameter()));
        }
        
        return parameters;
    }
    
    @Override
    public JParameter visitFormalParameter(FormalParameterContext ctx) {
        return createJParameter(ctx.variableDeclaratorId(), ctx.unannType());
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
        return createJParameter(ctx.variableDeclaratorId(), ctx.unannType());
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
    public JStatementExpression visitAssignmentStatement(AssignmentStatementContext ctx) {
        return new JStatementExpression(visitAssignment(ctx.assignment()));
    }
    
    @Override
    public JExpressionAssignment visitAssignment(Java8Parser.AssignmentContext ctx) {
        JExpressionAssignment assignment = new JExpressionAssignment();
        assignment.setLeft(visitLeftHandSide(ctx.leftHandSide()));
        assignment.setOperator(JExpressionAssignment.JAssignmentOperator.fromJava(ctx.assignmentOperator()
                                                                                     .getText()));
        assignment.setRight(visitExpression(ctx.expression()));
        return assignment;
    }
    
    @Override
    public JExpressionName visitExpressionName(ExpressionNameContext ctx) {
        return new JExpressionName(new JIdentifier(ctx.getText()));
    }
    
    @Override
    public JStatementExpression visitPreIncrementStatement(Java8Parser.PreIncrementStatementContext ctx) {
        return new JStatementExpression(visitPreIncrementExpression(ctx.preIncrementExpression()));
    }
    
    @Override
    public JExpressionUnary visitPreIncrementExpression(Java8Parser.PreIncrementExpressionContext
                                                                    ctx) {
        return new JExpressionUnary(JExpressionUnary.JOperator.PRE_INCREMENT,
                                    (JExpression) visit(ctx.unaryExpression()));
    }
    
    @Override
    public JStatementExpression visitPreDecrementStatement(Java8Parser.PreDecrementStatementContext ctx) {
        return new JStatementExpression(visitPreDecrementExpression(ctx.preDecrementExpression()));
    }
    
    @Override
    public JExpressionUnary visitPreDecrementExpression(Java8Parser.PreDecrementExpressionContext
                                                                    ctx) {
        return new JExpressionUnary(JExpressionUnary.JOperator.PRE_DECREMENT,
                                    (JExpression) visit(ctx.unaryExpression()));
    }
    
    @Override
    public JStatementExpression visitPostIncrementStatement(Java8Parser.PostIncrementStatementContext ctx) {
        return new JStatementExpression(visitPostIncrementExpression(ctx.postIncrementExpression
                ()));
    }
    
    @Override
    public JExpressionUnary visitPostIncrementExpression(Java8Parser.PostIncrementExpressionContext ctx) {
        
        return new JExpressionUnary(JExpressionUnary.JOperator.POST_INCREMENT,
                                    (JExpression) visit(ctx.postfixExpression()));
    }
    
    @Override
    public Object visitPostDecrementStatement(Java8Parser.PostDecrementStatementContext ctx) {
        return new JStatementExpression(visitPostDecrementExpression(ctx.postDecrementExpression
                ()));
    }
    
    @Override
    public JExpressionUnary visitPostDecrementExpression(Java8Parser.PostDecrementExpressionContext ctx) {
        return new JExpressionUnary(JExpressionUnary.JOperator.POST_DECREMENT,
                                    (JExpression) visit(ctx.postfixExpression()));
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
    public JExpressionTypeReference visitPrimaryClassType(PrimaryClassTypeContext ctx) {
        String brackets =
                ctx.bracketPair().stream().map(RuleContext::getText).collect(Collectors.joining());
        String typeName = ctx.primaryClassTypeAlternates().getText();
        return new JExpressionTypeReference(new JTypeName(typeName + brackets));
    }
    
    @Override
    public JArrayTypeName visitArrayType(ArrayTypeContext ctx) {
        return new JArrayTypeName(ctx.arrayTypeName().getText(), ctx.arrayDimension().size());
    }
    
    @Override
    public JStatementExpression visitClassInstanceCreationStatement(
            ClassInstanceCreationStatementContext ctx) {
        return new JStatementExpression(visitClassInstanceCreationExpression(ctx.classInstanceCreationExpression()));
    }
    
    @Override
    public JExpressionClassInstanceCreation visitClassInstanceCreationExpression(
            ClassInstanceCreationExpressionContext ctx) {
        JTypeName type = visitClassIdentifier(ctx.classIdentifier());
        JExpressionClassInstanceCreation creationExpression =
                new JExpressionClassInstanceCreation(type);
        
        if (ctx.typeArgumentsOrDiamond() != null) {
            List<JTypeArgument> arguments =
                    (List<JTypeArgument>) visit(ctx.typeArgumentsOrDiamond());
            creationExpression.setTypeArguments(arguments);
            
        }
        
        if (ctx.argumentList() != null) {
            List<JExpression> argumentList = visitArgumentList(ctx.argumentList());
            creationExpression.setArguments(argumentList);
        }
        
        if (ctx.classBody() != null) {
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
                  .map(c -> (JTypeArgument) visitTypeArgument(c))
                  .collect(Collectors.toList());
    }
    
    @Override
    public JTypeArgument visitWildcard(WildcardContext ctx) {
        
        JTypeArgumentWildcard.JWildcardType type = null;
        JTypeName reference = null;
        if (ctx.wildcardBounds() != null) {
            String text = ctx.wildcardBounds().boundType.getText();
            type = JTypeArgumentWildcard.JWildcardType.valueOf(text.toUpperCase());
            reference = new JTypeName(ctx.wildcardBounds().referenceType().getText());
        }
        return new JTypeArgumentWildcard(reference, type);
    }
    
    @Override
    public JReference visitReferenceType(ReferenceTypeContext ctx) {
        return new JTypeArgumentReference(new JTypeName(ctx.getText()));
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
        
        JStatementElse anElse = new JStatementElse();
        anElse.setCondition(Optional.of(visitStatement(ctx.statement())));
        statement.addElseStatement(anElse);
        return statement;
    }
    
    @Override
    public JStatementIf visitIfThenElseStatementNoShortIf(IfThenElseStatementNoShortIfContext ctx) {
        JStatementIf statement = new JStatementIf();
        statement.setExpression(visitExpression(ctx.expression()));
        statement.setStatement(visitStatementNoShortIf(ctx.statementNoShortIf(0)));
        JStatementElse anElse = new JStatementElse();
        anElse.setCondition(Optional.of(visitStatementNoShortIf(ctx.statementNoShortIf(1))));
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
        if (ctx.identifier() != null) {
            JIdentifier identifier = visitIdentifier(ctx.identifier());
            statement.setIdentifier(Optional.of(identifier));
        }
        return statement;
    }
    
    @Override
    public JStatementContinue visitContinueStatement(ContinueStatementContext ctx) {
        JStatementContinue statement = new JStatementContinue();
        if (ctx.identifier() != null) {
            JIdentifier identifier = visitIdentifier(ctx.identifier());
            statement.setIdentifier(Optional.of(identifier));
        }
        return statement;
    }
    
    @Override
    public JStatementReturn visitReturnStatement(ReturnStatementContext ctx) {
        JStatementReturn statement = new JStatementReturn();
        if (ctx.expression() != null) {
            JExpression expression = visitExpression(ctx.expression());
            statement.setExpression(Optional.of(expression));
        }
        return statement;
    }
    
    @Override
    public JStatementThrow visitThrowStatement(ThrowStatementContext ctx) {
        JStatementThrow statement = new JStatementThrow();
        if (ctx.expression() != null) {
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
        if (ctx.catches() != null) {
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
        if (ctx.classType() != null) {
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
        return new JLiteralCharacter(text.charAt(1));
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
        declaration.setTypeParameters(visitTypeParameters(ctx.constructorDeclarator()
                                                             .typeParameters()));
        declaration.setIdentifier(visitSimpleTypeName(ctx.constructorDeclarator()
                                                         .simpleTypeName()));
        if (ctx.throws_() != null) {
            declaration.setThrowsTypes(visitThrows_(ctx.throws_()));
        }
        declaration.setBody(visitConstructorBody(ctx.constructorBody()));
        
        return declaration;
    }
    
    @Override
    public JBlock visitConstructorBody(ConstructorBodyContext ctx) {
        JBlock block = new JBlock();
        
        List<JStatement> statements = new ArrayList<>();
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
    public JAnnotationType visitAnnotationTypeDeclaration(AnnotationTypeDeclarationContext ctx) {
        JAnnotationType declaration = new JAnnotationType();
        if (ctx.annotation() != null) {
            declaration.setAnnotations(visitAnnotations(ctx.annotation()));
        }
        
        if (ctx.modifier() != null) {
            declaration.setModifiers(visitModifiers(ctx.modifier()));
        }
        
        declaration.setIdentifier(visitIdentifier(ctx.identifier()));
        declaration.setBody(visitAnnotationTypeBody(ctx.annotationTypeBody()));
        return declaration;
    }
    
    @Override
    public JAnnotationBody visitAnnotationTypeBody(AnnotationTypeBodyContext ctx) {
        JAnnotationBody body = new JAnnotationBody();
        body.setMembers(visitAnnotationBody(ctx));
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
        
        if (ctx.defaultValue() != null) {
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
        if (ctx.elementValuePairList() != null) {
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
        JValueSingle value = new JValueSingle();
        value.setValue((JAnnotationElementValue) visit(ctx.elementValue()));
        annotation.addValue(value);
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
        return new JTypeName(ctx.getText());
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
}

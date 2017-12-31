package in.kyle.antlr;

import org.antlr.v4.runtime.RuleContext;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import in.kyle.antlr.gen.Java8BaseVisitor;
import in.kyle.antlr.gen.Java8Parser;
import in.kyle.antlr.gen.Java8Parser.*;
import in.kyle.parser.expression.*;
import in.kyle.parser.expression.literal.JIntegerLiteral;
import in.kyle.parser.expression.literal.JStringLiteral;
import in.kyle.parser.statement.JBlock;
import in.kyle.parser.statement.JBlockStatement;
import in.kyle.parser.statement.JEmptyStatement;
import in.kyle.parser.statement.JExpressionStatement;
import in.kyle.parser.statement.JLocalVariableDeclaration;
import in.kyle.parser.unit.*;

public class JavaCompositionVisitor extends Java8BaseVisitor<Object> {
    
    @Override
    public JCompilationUnit visitCompilationUnit(Java8Parser.CompilationUnitContext ctx) {
        JCompilationUnit unit = new JCompilationUnit();
        JPackage jPackage = null;
        if (ctx.packageDeclaration() != null) {
            jPackage = visitPackageDeclaration(ctx.packageDeclaration());
        }
        Set<JImport> imports = visitImports(ctx.importDeclaration());
        Set<JClass> types = visitTypeDeclarations(ctx.typeDeclaration());
        
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
    public JClass visitClassDeclaration(ClassDeclarationContext ctx) {
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
        
        
        jClass.setBody(visitClassBody(ctx.classBody()));
        return jClass;
    }
    
    private void setClassTypeParameters(ClassDeclarationContext ctx, JClass jClass) {
        if (ctx.typeParameters() != null) {
            List<TypeParameterContext> list =
                    ctx.typeParameters().typeParameterList().typeParameter();
            Set<JTypeParameter> parameters = new LinkedHashSet<>();
            for (TypeParameterContext typeParameterContext : list) {
                parameters.add(visitTypeParameter(typeParameterContext));
            }
            jClass.setTypeParameters(parameters);
        }
    }
    
    @Override
    public JTypeParameter visitTypeParameter(TypeParameterContext ctx) {
        JTypeParameter typeParameter = new JTypeParameter(ctx.Identifier().getText());
        if (ctx.typeBound() != null) {
            typeParameter.setBounds((Set<JTypeName>) visit(ctx.typeBound()));
        }
        return typeParameter;
    }
    
    @Override
    public Set<JTypeName> visitSimpleTypeBound(Java8Parser.SimpleTypeBoundContext ctx) {
        JTypeName typeName = new JTypeName(ctx.typeVariable().getText());
        return new LinkedHashSet<>(Collections.singletonList(typeName));
    }
    
    @Override
    public Set<JTypeName> visitClassTypeBound(Java8Parser.ClassTypeBoundContext ctx) {
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
    
    @Override
    public JClassBody visitClassBody(ClassBodyContext ctx) {
        JClassBody body = new JClassBody();
        for (ClassBodyDeclarationContext c : ctx.classBodyDeclaration()) {
            body.addMember(visitClassBodyDeclaration(c));
        }
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
        field.setModifiers(new LinkedHashSet<>(createFieldModifiers(ctx.fieldModifier())));
        field.setType(typeName);
        
        return field;
    }
    
    @Override
    public Set<JVariable> visitVariableDeclaratorList(VariableDeclaratorListContext ctx) {
        Set<JVariable> variables = new LinkedHashSet<>();
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
    public JConditionalExpression visitConditionalTernary(Java8Parser.ConditionalTernaryContext 
                                                                      ctx) {
        return new JConditionalExpression((JExpression) visit(ctx.conditionalOrExpression()),
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
    public JIntegerLiteral visitIntegerLiteral(Java8Parser.IntegerLiteralContext ctx) {
        return new JIntegerLiteral(Integer.parseInt(ctx.getText()));
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
    public JMethod visitMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
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
        JBlock jBlock = new JBlock();
        for (BlockStatementContext statementCtx : ctx.blockStatement()) {
            JBlockStatement statement = (JBlockStatement) visitBlockStatement(statementCtx);
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
        String name = ctx.variableDeclaratorId().getText();
        JParameter parameter = new JParameter(name);
        JTypeName type = new JTypeName(ctx.unannType().getText());
        parameter.setType(type);
        return parameter;
    }
    
    @Override
    public JParameter visitLastFormalParameter(Java8Parser.LastFormalParameterContext ctx) {
        String name = ctx.variableDeclaratorId().getText();
        JParameter parameter = new JParameter(name);
        JTypeName type = new JTypeName(ctx.unannType().getText());
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
    
    private Set<JModifier> visitVariableModifiers(Collection<VariableModifierContext> ctx) {
        return ctx.stream()
                  .map(this::visitVariableModifier)
                  .collect(Collectors.toCollection(LinkedHashSet::new));
    }
    
    @Override
    public JModifier visitVariableModifier(VariableModifierContext ctx) {
        return JModifier.fromJava(ctx.getText());
    }
    
    private Set<JTypeParameter> getMethodTypes(TypeParametersContext ctx) {
        if (ctx != null) {
            List<TypeParameterContext> list = ctx.typeParameterList().typeParameter();
            return list.stream()
                       .map(this::visitTypeParameter)
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
            TypeArgumentList argumentList = visitTypeArguments(ctx.typeArguments());
            creationExpression.setTypeArgumentList(argumentList);
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
    public JTypeParameterList visitDiamond(DiamondContext ctx) {
        JTypeParameterList list = new JTypeParameterList();
        list.setShowTypeParametersEmpty(true);
        return list;
    }
    
    @Override
    public TypeArgumentList visitTypeArguments(TypeArgumentsContext ctx) {
        TypeArgumentList argumentList = new TypeArgumentList();
        List<TypeArgumentContext> types = ctx.typeArgumentList().typeArgument();
        for (TypeArgumentContext type : types) {
            JTypeArgument argument = (JTypeArgument) visitTypeArgument(type);
            argumentList.addTypeArguement(argument);
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
            argumentList.addArguement(visitExpression(expressionContext));
        }
        return argumentList;
    }
    
    @Override
    public JTypeName visitClassIdentifier(ClassIdentifierContext ctx) {
        return new JTypeName(ctx.getText());
    }
}

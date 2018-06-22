package in.kyle.jrefactor.writer;

import java.util.Iterator;

import in.kyle.jrefactor.tree.JObject;
import in.kyle.jrefactor.tree.JObjectList;
import in.kyle.jrefactor.tree.expression.*;
import in.kyle.jrefactor.tree.expression.lambda.JInferredParameters;
import in.kyle.jrefactor.tree.expression.lambda.JLambdaExpression;
import in.kyle.jrefactor.tree.expression.literal.*;
import in.kyle.jrefactor.tree.statement.*;
import in.kyle.jrefactor.tree.statement.control.*;
import in.kyle.jrefactor.tree.statement.control.loops.JBasicForStatement;
import in.kyle.jrefactor.tree.statement.control.loops.JDoWhileStatement;
import in.kyle.jrefactor.tree.statement.control.loops.JEnhancedForStatement;
import in.kyle.jrefactor.tree.statement.control.loops.JWhileStatement;
import in.kyle.jrefactor.tree.unit.*;
import in.kyle.jrefactor.tree.unit.body.*;
import in.kyle.jrefactor.tree.unit.types.JAnnotationDeclaration;
import in.kyle.jrefactor.tree.unit.types.JClassDeclaration;
import in.kyle.jrefactor.tree.unit.types.JEnumDeclaration;
import in.kyle.jrefactor.tree.unit.types.JInterfaceDeclaration;
import in.kyle.jrefactor.tree.unit.types.JSuperInterfaceList;
import in.kyle.jrefactor.tree.unit.types.annotationtype.JAnnotationTypeElement;
import in.kyle.jrefactor.tree.unit.types.classtype.JClassBody;
import in.kyle.jrefactor.tree.unit.types.classtype.JClassInstanceInitializer;
import in.kyle.jrefactor.tree.unit.types.classtype.JClassStaticInitializer;
import in.kyle.jrefactor.tree.unit.types.enumtype.JEnumConstant;
import in.kyle.jrefactor.tree.unit.types.interfacetype.JInterfaceMethod;

public class SimpleWriter extends AbstractWriter {
    
    @Override
    protected void writeJAssignmentOperator(JAssignment.JAssignmentOperator object) {
        writeString(object.getJavaString());
    }
    
    @Override
    protected void writeJAssignment(JAssignment object) {
        write(object.getLeft());
        writeString(" ");
        write(object.getOperator());
        writeString(" ");
        write(object.getRight());
    }
    
    @Override
    protected void writeJClassInstanceCreationExpression(JClassInstanceCreationExpression object) {
        writeString("new ");
        write(object.getType());
        write(object.getTypeArguments());
        writeString("(");
        write(object.getArguments());
        writeString(")");
        object.getBody().ifPresent(this::write);
    }
    
    @Override
    protected void writeJExpressionName(JExpressionName object) {
        write(object.getIdentifier());
    }
    
    @Override
    protected void writeOperation(JLeftRightExpression.Operation object) {
        writeString(object.getJavaString());
    }
    
    @Override
    protected void writeJLeftRightExpression(JLeftRightExpression object) {
        write(object.getLeft());
        write(object.getOperation());
        write(object.getRight());
    }
    
    @Override
    protected void writeJMethodInvocation(JMethodInvocation object) {
        if (object.getMethodArea().isPresent()) {
            writeString(object.getMethodArea().get());
            writeString(" ");
        }
        write(object.getTypeArguments());
        write(object.getIdentifier());
        writeString("(");
        write(object.getArguments());
        writeString(")");
    }
    
    @Override
    protected void writeJParenthesisExpression(JParenthesisExpression object) {
        writeString("(");
        write(object.getValue());
        writeString(")");
    }
    
    @Override
    protected void writeJTernaryExpression(JTernaryExpression object) {
        write(object.getCondition());
        writeString(" ? ");
        write(object.getLeft());
        writeString(" : ");
        write(object.getRight());
    }
    
    @Override
    protected void writeJTypeReferenceExpression(JTypeReferenceExpression object) {
        write(object.getReference());
        writeString(".class");
    }
    
    @Override
    protected void writeOperator(JUnaryExpression.Operator object) {
        writeString(object.getJavaString());
    }
    
    @Override
    protected void writeJUnaryExpression(JUnaryExpression object) {
        if (object.getOperator().isBeforeOperator()) {
            write(object.getOperator());
            write(object.getExpression());
        } else {
            write(object.getExpression());
            write(object.getOperator());
        }
    }
    
    @Override
    protected void writeJInferredParameters(JInferredParameters object) {
        writeString("(");
        write(object);
        writeString(")");
    }
    
    @Override
    protected void writeJLambdaExpression(JLambdaExpression object) {
        write(object.getParameters());
        writeString(" -> ");
        write(object.getBody());
    }
    
    @Override
    protected void writeJBooleanLiteral(JBooleanLiteral object) {
        writeString(object.getValue().toString());
    }
    
    @Override
    protected void writeJCharacterLiteral(JCharacterLiteral object) {
        writeString("'");
        writeString(object.getValue().toString());
        writeString("'");
    }
    
    @Override
    protected void writeJDoubleLiteral(JDoubleLiteral object) {
        writeString(object.getValue().toString());
    }
    
    @Override
    protected void writeJFloatLiteral(JFloatLiteral object) {
        writeString(object.getValue().toString());
    }
    
    @Override
    protected void writeJIntegerLiteral(JIntegerLiteral object) {
        writeString(object.getValue().toString());
    }
    
    @Override
    protected void writeJLongLiteral(JLongLiteral object) {
        writeString(object.getValue().toString());
        writeString("L");
    }
    
    @Override
    protected void writeJStringLiteral(JStringLiteral object) {
        writeString("\"");
        writeString(object.getValue());
        writeString("\"");
    }
    
    @Override
    protected void writeJObjectList(JObjectList object) {
        for (Iterator iterator = object.iterator(); iterator.hasNext(); ) {
            JObject o = (JObject) iterator.next();
            write(o);
            if (iterator.hasNext()) {
                writeString(", ");
            }
        }
    }
    
    @Override
    protected void writeJBreakStatement(JBreakStatement object) {
        writeString("break");
        if (object.getIdentifier().isPresent()) {
            writeString(" ");
            write(object.getIdentifier().get());
        }
    }
    
    @Override
    protected void writeJCatchClause(JCatchClause object) {
        writeString("catch (");
        for (Iterator<JTypeName> iterator =
             object.getCatchTypes().iterator(); iterator.hasNext(); ) {
            JTypeName jTypeName = iterator.next();
            write(jTypeName);
            if (iterator.hasNext()) {
                writeString(" | ");
            }
        }
        writeString(" ");
        write(object.getVariable());
        writeString(")");
        
        write(object.getBlock());
    }
    
    @Override
    protected void writeJContinueStatement(JContinueStatement object) {
        writeString("continue");
        if (object.getIdentifier().isPresent()) {
            writeString(" ");
            write(object.getIdentifier().get());
        }
    }
    
    @Override
    protected void writeJIfThenElseStatement(JIfThenElseStatement object) {
        writeString("if (");
        write(object.getExpression());
        writeString(") ");
        write(object.getIfCondition());
        writeString("else ");
        write(object.getElseCondition());
    }
    
    @Override
    protected void writeJIfThenStatement(JIfThenStatement object) {
        writeString("if (");
        write(object.getExpression());
        writeString(") ");
        write(object.getStatement());
    }
    
    @Override
    protected void writeJReturnStatement(JReturnStatement object) {
        writeString("return");
        if (object.getExpression().isPresent()) {
            writeString(" ");
            write(object.getExpression().get());
        }
    }
    
    @Override
    protected void writeJSynchronizedStatement(JSynchronizedStatement object) {
        writeString("synchronized (");
        write(object.getExpression());
        writeString(")");
        write(object.getBlock());
    }
    
    @Override
    protected void writeJThrowStatement(JThrowStatement object) {
        writeString("throw ");
        write(object.getExpression());
    }
    
    @Override
    protected void writeJTryStatement(JTryStatement object) {
        writeString("try ");
        write(object.getBlock());
        for (JCatchClause catchClause : object.getCatchClauses()) {
            write(catchClause);
        }
        if (object.getFinallyBlock().isPresent()) {
            writeString("finally ");
            write(object.getFinallyBlock().get());
        }
    }
    
    @Override
    protected void writeJBasicForStatement(JBasicForStatement object) {
        writeString("for (");
        object.getInit().ifPresent(this::write);
        writeString("; ");
        object.getCondition().ifPresent(this::write);
        writeString("; ");
        object.getUpdate().ifPresent(this::write);
        writeString(") ");
        write(object.getStatement());
    }
    
    @Override
    protected void writeJDoWhileStatement(JDoWhileStatement object) {
        writeString("do ");
        write(object.getStatement());
        writeString(" while (");
        write(object.getExpression());
        writeString(");");
    }
    
    @Override
    protected void writeJEnhancedForStatement(JEnhancedForStatement object) {
        writeString("for (");
        write(object.getVariableType());
        writeString(" ");
        write(object.getVariableName());
        writeString(" : ");
        write(object.getExpression());
        writeString(") ");
        write(object.getStatement());
    }
    
    @Override
    protected void writeJWhileStatement(JWhileStatement object) {
        writeString("while (");
        write(object.getExpression());
        writeString(") ");
        write(object.getStatement());
    }
    
    @Override
    protected void writeJAssertStatement(JAssertStatement object) {
        writeString("assert ");
        write(object.getAssertion());
        if (object.getMessage().isPresent()) {
            writeString(" : ");
            write(object.getMessage().get());
        }
    }
    
    @Override
    protected void writeJBlock(JBlock object) {
        writeString("{");
        indent();
        newLine();
        for (JStatement jStatement : object.getStatements()) {
            write(jStatement);
            newLine();
        }
        dedent();
        newLine();
        writeString("}");
    }
    
    @Override
    protected void writeJEmptyStatement(JEmptyStatement object) {
        writeString(";");
    }
    
    @Override
    protected void writeJExpressionStatement(JExpressionStatement object) {
        write(object.getExpression());
        writeString(";");
    }
    
    @Override
    protected void writeJLabledStatement(JLabledStatement object) {
        write(object.getIdentifier());
        writeString(": ");
        write(object.getStatement());
    }
    
    @Override
    protected void writeJLocalVariableDeclaration(JLocalVariableDeclaration object) {
        writeVariableHolder(object);
        writeString(";");
    }
    
    @Override
    protected void writeJArgumentList(JArgumentList object) {
        writeJObjectList(object);
    }
    
    @Override
    protected void writeJConstructorDeclaration(JConstructorDeclaration object) {
        write(object.getModifiers());
        write(object.getTypeParameters());
        write(object.getIdentifier());
        writeString("(");
        write(object.getParameterList());
        writeString(") ");
        write(object.getThrowsList());
        write(object.getBody());
    }
    
    @Override
    protected void writeJMemberList(JMemberList object) {
        for (Object o : object) {
            JObject member = (JObject) o;
            write(member);
            newLine();
        }
    }
    
    @Override
    protected void writeJMethod(JMethod object) {
        write(object.getHeader());
        write(object.getBody());
    }
    
    protected void writeTypeable(Typeable object) {
        writeJModifierList(object.getModifiers());
        writeJAnnotationList(object.getAnnotations());
        writeJTypeParameterList(object.getTypeParameters());
    }
    
    @Override
    protected void writeJMethodHeader(JMethodHeader object) {
        writeTypeable(object);
        write(object.getTypeParameters());
        write(object.getResultType());
        writeString(" ");
        write(object.getName());
        write(object.getParameterList());
        write(object.getThrowsList());
    }
    
    @Override
    protected void writeJParameter(JParameter object) {
        write(object.getAnnotations());
        write(object.getModifiers());
        write(object.getType());
        writeString(" ");
        write(object.getIdentifier());
    }
    
    @Override
    protected void writeJTypeBody(JTypeBody object) {
        writeString("{");
        indent();
        newLine();
        for (Object o : object) {
            write((JObject) o);
            newLine();
        }
        dedent();
        newLine();
        writeString("}");
    }
    
    @Override
    protected void writeJVariable(JVariable object) {
        write(object.getIdentifier());
        if (object.getInitializer().isPresent()) {
            writeString(" = ");
            write(object.getInitializer().get());
        }
    }
    
    protected void writeVariableHolder(VariableHolder object) { // TODO: 1/10/2018  
        write(object.getModifiers());
        write(object.getType());
        writeString(" ");
        write(object.getVariables());
    }
    
    @Override
    protected void writeJArrayTypeName(JArrayTypeName object) {
        writeJTypeName(object);
        for (int i = 0; i < object.getDimensions(); i++) {
            writeString("[]");
        }
    }
    
    @Override
    protected void writeJCompilationUnit(JCompilationUnit object) {
        if (object.getPackageName().isPresent()) {
            write(object.getPackageName().get());
            newLine();
        }
        
        for (JImport jImport : object.getImports()) {
            write(jImport);
            newLine();
        }
        
        for (JTypeDeclaration typeDeclaration : object.getTypes()) {
            write(typeDeclaration);
            newLine();
        }
    }
    
    @Override
    protected void writeJIdentifier(in.kyle.jrefactor.tree.unit.JIdentifier object) {
        writeString(object.getName());
    }
    
    @Override
    protected void writeJImport(JImport object) {
        writeString("import ");
        if (object.isStatik()) {
            writeString("static ");
        }
        if (!object.getPackageName().isEmpty()) {
            writeString(object.getPackageName());
            writeString(".");
        }
        writeString(object.getName());
        writeString(";");
    }
    
    @Override
    protected void writeJModifier(JModifier object) {
        writeString(object.name().toLowerCase());
    }
    
    @Override
    protected void writeJPackage(JPackage object) {
        writeString("package ");
        writeString(object.getName());
        writeString(";");
    }
    
    @Override
    protected void writeJParameterList(JParameterList object) {
        write(object.getParameters());
    }
    
    @Override
    protected void writeJThrowsList(JThrowsList object) {
        if (!object.isEmpty()) {
            writeString("throws ");
            writeJObjectList(object);
        }
    }
    
    @Override
    protected void writeJReferenceTypeArgument(JTypeArgument.JReferenceTypeArgument object) {
        write(object.getReferenceType());
    }
    
    @Override
    protected void writeType(JTypeArgument.JWildcardTypeArgument.Type object) {
        writeString(object.name().toLowerCase());
    }
    
    @Override
    protected void writeJWildcardTypeArgument(JTypeArgument.JWildcardTypeArgument object) {
        writeString("?");
        if (object.getReferenceType() != null) {
            writeString(" ");
            write(object.getBoundType());
            writeString(" ");
            write(object.getReferenceType());
        }
    }
    
    @Override
    protected void writeJTypeName(JTypeName object) {
        writeString(object.getName());
    }
    
    @Override
    protected void writeJTypeParameter(JTypeParameter object) {
        write(object.getName());
        if (!object.getBounds().isEmpty()) {
            writeString(" extends ");
            for (Iterator<JTypeName> iterator =
                 object.getBounds().iterator(); iterator.hasNext(); ) {
                JTypeName bound = iterator.next();
                write(bound);
                if (iterator.hasNext()) {
                    writeString(" & ");
                }
            }
        }
    }
    
    @Override
    protected void writeJTypeParameterList(JTypeParameterList object) {
        if (!object.isEmpty()) {
            writeString("<");
            write(object);
            writeString(">");
        } else if (object.isShowTypeParametersEmpty()) {
            writeString("<>");
        }
    }
    
    @Override
    protected void writeJAnnotationList(JAnnotationList object) {
        writeJObjectList(object);
    }
    
    @Override
    protected void writeJAnnotation(JAnnotation object) {
        writeString("@");
        write(object.getType());
        write(object.getValue());
    }
    
    @Override
    protected void writeJElementPair(JAnnotationValue.JElementPair object) {
        write(object.getIdentifier());
        writeString(" = ");
        write(object.getValue());
    }
    
    @Override
    protected void writeJPairCollection(JAnnotationValue.JPairCollection object) {
        write(object.getValues());
    }
    
    @Override
    protected void writeJSingleValue(JAnnotationValue.JSingleValue object) {
        write(object.getValue());
    }
    
    @Override
    protected void writeJAnnotationTypeElement(JAnnotationTypeElement object) {
        write(object.getAnnotations());
        write(object.getModifiers());
        write(object.getType());
        writeString(" ");
        write(object.getIdentifier());
        writeString("() ");
        write(object.getDefaultValue());
    }
    
    
    @Override
    protected void writeJClassInstanceInitializer(JClassInstanceInitializer object) {
        write(object.getBlock());
    }
    
    @Override
    protected void writeJClassStaticInitializer(JClassStaticInitializer object) {
        writeString("static ");
        write(object.getBlock());
    }
    
    @Override
    protected void writeJEnumConstant(JEnumConstant object) {
        write(object.getAnnotations());
        write(object.getIdentifier());
        writeString("(");
        write(object.getArgumentList());
        writeString(")");
        if (object.getBody().isPresent()) {
            writeString(" ");
            write(object.getBody().get());
        }
    }
    
    @Override
    protected void writeJInterfaceMethod(JInterfaceMethod object) {
        write(object.getHeader());
        if (object.getBody().isPresent()) {
            writeString(" default ");
            write(object.getBody().get());
        } else {
            writeString(";");
        }
    }
    
    protected void writeJTypeDeclaration(JTypeDeclaration object) {
        writeJAnnotationList(object.getAnnotations());
        writeJModifierList(object.getModifiers());
    }
    
    @Override
    protected void writeJAnnotationDeclaration(JAnnotationDeclaration object) {
        writeJTypeDeclaration(object);
        write(object.getAnnotations());
        writeString("@interface ");
        write(object.getIdentifier());
        writeString(" ");
        write(object.getBody());
    }
    
    @Override
    protected void writeJClassDeclaration(JClassDeclaration object) {
        writeJTypeDeclaration(object);
        writeString("class ");
        write(object.getIdentifier());
        writeString(" ");
        write(object.getTypeParameters());
        if (object.getExtendsType().isPresent()) {
            writeString("extends ");
            write(object.getExtendsType().get());
        }
        write(object.getSuperInterfaces());
        write(object.getBody());
    }
    
    @Override
    protected void writeJEnumDeclaration(JEnumDeclaration object) {
        writeJTypeDeclaration(object);
        writeString("enum ");
        write(object.getIdentifier());
        write(object.getSuperInterfaces());
        write(object.getBody());
    }
    
    @Override
    protected void writeJInterfaceDeclaration(JInterfaceDeclaration object) {
        writeJTypeDeclaration(object);
        writeString("interface ");
        write(object.getIdentifier());
        writeString(" ");
        write(object.getBody());
    }
    
    @Override
    protected void writeJSuperInterfaceList(JSuperInterfaceList object) {
        if (!object.isEmpty()) {
            writeString(" implements ");
            writeJObjectList(object);
        }
    }
    
    @Override
    protected void writeJModifierList(JModifierList object) {
        writeJObjectList(object);
    }
    
    @Override
    protected void writeJClassBody(JClassBody object) {
        writeJTypeBody(object);
    }
}

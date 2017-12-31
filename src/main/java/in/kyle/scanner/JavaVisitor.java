package in.kyle.scanner;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.JAnnotatable;
import in.kyle.parser.unit.JAnnotation;
import in.kyle.parser.unit.JClass;
import in.kyle.parser.unit.JClassMember;
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
import in.kyle.parser.unit.JVariableInitializer;

public interface JavaVisitor<T> {
    T visitJAnnotatable(JAnnotatable object);
    T visitJClassMember(JClassMember object);
    T visitJObject(JObject object);
    T visitJType(JType object);
    T visitJVariableInitializer(JVariableInitializer object);
    T visitJConditionalExpression(in.kyle.parser.expression.JConditionalExpression object);
    T visitJExpression(in.kyle.parser.expression.JExpression object);
    T visitJLamdaExpression(in.kyle.parser.expression.JLamdaExpression object);
    T visitOperation(in.kyle.parser.expression.JLeftRightExpression.Operation object);
    T visitJLeftRightExpression(in.kyle.parser.expression.JLeftRightExpression object);
    T visitJParenthesisExpression(in.kyle.parser.expression.JParenthesisExpression object);
    T visitJByteLiteral(in.kyle.parser.expression.literal.JByteLiteral object);
    T visitJDoubleLiteral(in.kyle.parser.expression.literal.JDoubleLiteral object);
    T visitJFloatLiteral(in.kyle.parser.expression.literal.JFloatLiteral object);
    T visitJIntegerLiteral(in.kyle.parser.expression.literal.JIntegerLiteral object);
    T visitJLiteral(in.kyle.parser.expression.literal.JLiteral object);
    T visitJNumericLiteral(in.kyle.parser.expression.literal.JNumericLiteral object);
    T visitJShortLiteral(in.kyle.parser.expression.literal.JShortLiteral object);
    T visitJStringLiteral(in.kyle.parser.expression.literal.JStringLiteral object);
    T visitJAnnotation(JAnnotation object);
    T visitJClass(JClass object);
    T visitJCompilationUnit(JCompilationUnit object);
    T visitJField(JField object);
    T visitJGeneric(JTypeParameter object);
    T visitJImport(JImport object);
    T visitJMethod(JMethod object);
    T visitJModifier(JModifier object);
    T visitJPackage(JPackage object);
    T visitJParameter(JParameter object);
    T visitJTypeName(JTypeName object);
    T visitJVariable(JVariable object);

}
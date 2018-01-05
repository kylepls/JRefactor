package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.CodeWriter;
import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.expression.JExpression;
import lombok.AllArgsConstructor;
import lombok.Data;

public interface JTypeArgument extends JObject {
    
    @Data
    class JWildcardTypeArgument extends JReferenceTypeArgument {
        
        private Type boundType;
    
        public JWildcardTypeArgument(JTypeName referenceType, Type boundType) {
            super(referenceType);
            this.boundType = boundType;
        }
    
        @Override
        public void write(CodeWriter writer) {
            writer.append("?");
            if (getReferenceType() != null) {
                writer.append(" {} {}", boundType, getReferenceType());
            }
        }
    
        public enum Type implements JObject {
            EXTENDS,
            SUPER;
            
            @Override
            public void write(CodeWriter writer) {
                writer.append(name().toLowerCase());
            }
        
            public static Type fromJava(String string) {
                return valueOf(string.toUpperCase());
            }
        }
    }
    
    @Data
    @AllArgsConstructor
    class JReferenceTypeArgument implements JTypeArgument, JExpression {
        
        private JTypeName referenceType;
    
        @Override
        public void write(CodeWriter writer) {
            writer.append(referenceType);
        }
    
    }
}

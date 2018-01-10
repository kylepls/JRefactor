package in.kyle.jrefactor.parser.unit;

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
    
        public enum Type implements JObject {
            EXTENDS,
            SUPER;
            
            public static Type fromJava(String string) {
                return valueOf(string.toUpperCase());
            }
        }
    }
    
    @Data
    @AllArgsConstructor
    class JReferenceTypeArgument implements JTypeArgument, JExpression {
        
        private JTypeName referenceType;
    
    }
}

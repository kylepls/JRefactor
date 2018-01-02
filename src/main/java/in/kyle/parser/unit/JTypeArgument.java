package in.kyle.parser.unit;

import java.util.Collections;
import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.writer.CodeWriter;
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
        
        @Override
        public List<JObject> getChildren() {
            return super.getChildren();
        }
        
        public enum Type implements JObject {
            EXTENDS,
            SUPER;
            
            @Override
            public void write(CodeWriter writer) {
                writer.append(name().toLowerCase());
            }
    
            @Override
            public List<JObject> getChildren() {
                return Collections.emptyList();
            }
    
            public static Type fromJava(String string) {
                return valueOf(string.toUpperCase());
            }
        }
    }
    
    @Data
    @AllArgsConstructor
    class JReferenceTypeArgument implements JTypeArgument {
        
        private JTypeName referenceType;
    
        @Override
        public void write(CodeWriter writer) {
            writer.append(referenceType);
        }
        
        @Override
        public List<JObject> getChildren() {
            return CollectionUtils.createList(referenceType);
        }
    }
}

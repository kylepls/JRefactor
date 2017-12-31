package in.kyle.parser.unit;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.RewriteableField;
import in.kyle.writer.CodeWriter;
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
        public List<RewriteableField> getChildren() {
            return super.getChildren();
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
    
    class JReferenceTypeArgument implements JTypeArgument {
        
        private final RewriteableField<JTypeName> referenceType = new RewriteableField<>();
    
        public JReferenceTypeArgument(JTypeName referenceType) {
            this.referenceType.setValue(referenceType);
        }
    
        public void setReferenceType(JTypeName type) {
            referenceType.setValue(type);
        }
        
        public JTypeName getReferenceType() {
            return referenceType.getValue();
        }
        
        @Override
        public void write(CodeWriter writer) {
            writer.append(referenceType);
        }
        
        @Override
        public List<RewriteableField> getChildren() {
            return CollectionUtils.createList(referenceType);
        }
    }
}

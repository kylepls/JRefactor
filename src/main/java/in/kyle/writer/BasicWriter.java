package in.kyle.writer;

import in.kyle.api.utils.StringUtils;
import in.kyle.parser.JObject;
import in.kyle.parser.RewriteableField;

public class BasicWriter implements CodeWriter {
    
    private final StringBuilder buffer = new StringBuilder();
    private String indentString = "    ";
    private String currentIndent = "";
    private boolean newLine = false;
    
    public void setIndentAmount(int amount) {
        indentString = createIndentString(amount);
    }
    
    public BasicWriter indent() {
        currentIndent += indentString;
        return this;
    }
    
    public BasicWriter dedent() {
        currentIndent = currentIndent.substring(0, currentIndent.length() - indentString.length());
        return this;
    }
    
    @Override
    public CodeWriter appendLine() {
        buffer.append("\n");
        newLine = true;
        return this;
    }
    
    private void appendIndent() {
        buffer.append(currentIndent);
    }
    
    @Override
    public CodeWriter appendLine(Object object) {
        String string = objectToString(object);
        if (string.isEmpty()) {
        } else {
            appendIndent();
            buffer.append(string);
        }
        appendLine();
        return this;
    }
    
    @Override
    public CodeWriter append(Object object) {
        if (newLine) {
            appendIndent();
            newLine = false;
        }
        buffer.append(objectToString(object));
        return this;
    }
    
    protected String objectToString(Object o) {
        if (o instanceof JObject) {
            BasicWriter writer = new BasicWriter();
            ((JObject) o).write(writer);
            return writer.toString();
        } else if (o instanceof RewriteableField) {
            return objectToString(((RewriteableField) o).getValue());
        } else {
            return o != null ? o.toString() : "ERROR";
        }
    }
    
    protected void objectArrayToStrings(Object[] o) {
        for (int i = 0; i < o.length; i++) {
            o[i] = objectToString(o[i]);
        }
    }
    
    @Override
    public CodeWriter append(String format, Object... args) {
        objectArrayToStrings(args);
        return append(StringUtils.replaceVariables(format, args));
    }
    
    @Override
    public CodeWriter appendLine(String format, Object... args) {
        objectArrayToStrings(args);
        return appendLine(StringUtils.replaceVariables(format, args));
    }
    
    @Override
    public void clear() {
        buffer.setLength(0);
    }
    
    private String createIndentString(int amount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return buffer.toString();
    }
}

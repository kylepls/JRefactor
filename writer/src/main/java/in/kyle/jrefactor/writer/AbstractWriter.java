package in.kyle.jrefactor.writer;

import in.kyle.jrefactor.parser.JObject;

public abstract class AbstractWriter implements CodeWriter {
    
    private final StringBuilder buffer = new StringBuilder();
    private final StringBuilder currentIndent = new StringBuilder();
    private String indentString = "    ";
    private boolean newLine = false;
    
    public void setIndentAmount(int amount) {
        indentString = createIndentString(amount);
    }
    
    public void indent() {
        currentIndent.append(indentString);
    }
    
    public void dedent() {
        currentIndent.setLength(currentIndent.length() - indentString.length());
    }
    
    protected void append(Object object) {
        if (newLine) {
            appendIndent();
            newLine = false;
        }
        if (object instanceof JObject) {
            write((JObject) object);
        } else {
            buffer.append(object.toString());
        }
    }
    
    protected void writeString(String string) {
        append(string);
    }
    
    public void newLine() {
        buffer.append("\n");
        newLine = true;
    }
    
    private void appendIndent() {
        buffer.append(currentIndent.toString());
    }
    
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

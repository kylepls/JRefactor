package in.kyle.jrefactor.writer;

public abstract class AbstractWriter extends CodeWriter {
//    
//    private static final String BASE_CLASS = "in.kyle.jrefactor.writer.CodeWriter";
//    
//    private static final Map<Class<?>, Method> METHODS;
//    
//    static {
//        Class<?> CLAZZ = Try.to(() -> Class.forName(BASE_CLASS));
//        METHODS = Arrays.stream(CLAZZ.getDeclaredMethods())
//                        .filter(method -> Modifier.isProtected(method.getModifiers()))
//                        .filter(method -> method.getParameterCount() == 1)
//                        .collect(Collectors.toMap(method -> method.getParameterTypes()[0],
//                                                  method -> method));
//    }
//    
//    private StringBuilder buffer = new StringBuilder();
//    private StringBuilder currentIndent = new StringBuilder();
//    private String indentString = "    ";
//    private boolean newLine = false;
//    
//    @Override
//    public void write(JObj object) {
//        beginWrite();
//        if (object != null) {
//            Method method = METHODS.get(object.getClass());
//            if (method == null) {
//                throw new RuntimeException("No such writer for " + object);
//            } else {
//                Try.to(() -> method.invoke(this, object));
//            }
//        }
//    }
//    
//    protected void writef(String format, Object... params) {
//        beginWrite();
//        for (int i = 0; i < params.length; i++) {
//            Object param = params[i];
//            Method method = METHODS.get(param.getClass());
//            if (method != null) {
//                Try.to(()->method.invoke(this, param));
//            }
//        }
//    }
//    
//    private String jobjToString(JObj obj) {
//        StringBuilder tempBuffer = buffer;
//        StringBuilder tempIndent = currentIndent;
//        buffer = new StringBuilder();
//        buffer.setLength(0);
//        
//    }
//    
//    protected void writeString(String string) {
//        beginWrite();
//        buffer.append(string);
//    }
//    
//    private void beginWrite() {
//        if (newLine) {
//            appendIndent();
//            newLine = false;
//        }
//    }
//    
//    
//    protected void indent() {
//        currentIndent.append(indentString);
//    }
//    
//    protected void dedent() {
//        currentIndent.setLength(currentIndent.length() - indentString.length());
//    }
//    
//    protected void newLine() {
//        buffer.append("\n");
//        newLine = true;
//    }
//    
//    private void appendIndent() {
//        buffer.append(currentIndent.toString());
//    }
//    
//    public void clear() {
//        buffer.setLength(0);
//    }
//    
//    @Override
//    public String toString() {
//        return buffer.toString();
//    }
}

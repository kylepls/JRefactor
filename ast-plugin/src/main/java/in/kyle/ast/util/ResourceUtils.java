package in.kyle.ast.util;


import java.io.IOException;
import java.io.InputStream;

public class ResourceUtils {
    public static String loadResource(String path) throws IOException {
        InputStream stream = ResourceUtils.class.getResourceAsStream(path);
        if (stream == null) {
            stream = ResourceUtils.class.getResourceAsStream("/" + path);
        }
        StringBuilder sb = new StringBuilder();
        int i;
        while ((i = stream.read()) != -1) {
            sb.append((char) i);
        }
        return sb.toString();
    }
}

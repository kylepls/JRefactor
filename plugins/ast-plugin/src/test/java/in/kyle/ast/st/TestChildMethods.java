package in.kyle.ast.st;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.ast.code.file.JavaField;
import in.kyle.ast.util.StringTemplate;

public class TestChildMethods {
    
    @Test
    public void testReplaceFieldObject() {
        JavaField field = new JavaField("String", "string");
        String replaceField = StringTemplate.render("childMethods/replaceField", field);
        Verify.that(replaceField)
              .isEqual("if (string == child) {\n    this.string = (String) replacement;\n    return;\n}");
    }
    
    @Test
    public void testReplaceFieldOptional() {
        JavaField field = new JavaField("Optional", "String", "string", null);
        String replaceField = StringTemplate.render("childMethods/replaceField", field);
        // @formatter::off
        String test = "if (string.isPresent() && string.get() == child) {\n" +
                      "    this.string = java.util.Optional.of((String) replacement);\n" +
                      "    return;\n" +
                      "}";
        // @formatter:on
        Verify.that(replaceField).diffEqual(test);
    }
    
    @Test
    public void testReplaceFieldSet() {
        JavaField field = new JavaField("Set", "String", "string", null);
        String replaceField = StringTemplate.render("childMethods/replaceField", field);
        // @formatter:off
        String test = "if (string.removeIf(s -> s == child)) {\n" +
                      "    string.add((String) replacement);\n" +
                      "    return;\n" +
                      "}";        
        // @formatter:on
        Verify.that(replaceField).diffEqual(test);
    }
    
    @Test
    public void testReplaceFieldList() {
        JavaField field = new JavaField("List", "String", "string", null);
        String replaceField = StringTemplate.render("childMethods/replaceField", field);
        // @formatter::off
        String test = "for (int i = 0; i < string.size(); i++) {\n" +
                      "    if (string.get(i) == child) {\n" +
                      "        string.set(i, (String) replacement);\n" +
                      "        return;\n" +
                      "    }\n" +
                      "}";
        // @formatter:on
        Verify.that(replaceField).diffEqual(test);
    }
    
    @Test
    public void testAddFieldObject() {
        JavaField field = new JavaField("String", "string");
        String replaceField = StringTemplate.render("childMethods/addField", field);
        Verify.that(replaceField).isEqual("children.add(string);");
    }
    
    @Test
    public void testAddFieldOptional() {
        JavaField field = new JavaField("Optional", "String", "string", null);
        String replaceField = StringTemplate.render("childMethods/addField", field);
        Verify.that(replaceField).isEqual("string.ifPresent(children::add);");
    }
    
    @Test
    public void testAddFieldCollection() {
        JavaField field = new JavaField("Set", "String", "string", null);
        String replaceField = StringTemplate.render("childMethods/addField", field);
        Verify.that(replaceField).isEqual("children.addAll(string);");
    }
}

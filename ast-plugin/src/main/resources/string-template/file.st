<if(!innerClass)><if(packageName)>package <packageName>;

<endif><imports: {import | import <import>;
}><if(isClass)>import lombok.Data;

@Data
<endif><endif>
<header> {
    <enumStrings ; separator=",\n"><if(enumStrings)>;<endif>
    <constructors ; separator="\n\n">
    <stringFields : { field | private <field>;}; separator="\n"><if(stringFields)>
<endif>
    <methods ; separator="\n"><if(methods)>
<endif>
    <innerClassStrings ; separator="\n\n">
}
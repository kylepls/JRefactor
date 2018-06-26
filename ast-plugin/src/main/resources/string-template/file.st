<if(!innerClass)><if(packageName)>package <packageName>;

<endif><imports: {import | import <import>;
}><if(isClass)>import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data<if(stringFields)>
@NoArgsConstructor<endif>
<endif><if(isEnum)>import lombok.AllArgsConstructor;

<endif><endif><if(isEnum)>@AllArgsConstructor<endif>
<header> {
    <enumStrings ; separator=",\n"><if(enumStrings)>;<endif>
    <stringFields : { field | private <field>;}; separator="\n"><if(stringFields)>
<endif>
    <methods ; separator="\n"><if(methods)>
<endif>
    <innerClassStrings ; separator="\n\n">
}
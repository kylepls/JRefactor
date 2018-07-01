public void add<singular_name_upper>(<generic_type> <singular_name>) { <field_name>.add(<singular_name>); }
public void remove<singular_name_upper>(<generic_type> <singular_name>) { <field_name>.remove(<singular_name>); }
public void set<field_name_upper>(java.util.Collection\<<generic_type>> <field_name>) {
    this.<field_name>.clear();
    this.<field_name>.addAll(<field_name>);
}

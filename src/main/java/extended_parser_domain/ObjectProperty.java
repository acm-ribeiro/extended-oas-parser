package extended_parser_domain;

public class ObjectProperty extends Property {

    private String ref;

    public ObjectProperty(String name, String type, boolean required, boolean gen, String ref) {
        super(name, type, required, gen);
        this.ref = ref;
    }
}

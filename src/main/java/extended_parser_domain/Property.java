package extended_parser_domain;

public class Property {

    private String name, type;
    private boolean required, gen;

    public Property(String name, String type, boolean required, boolean gen) {
        this.name = name;
        this.type = type;
        this.required = required;
        this.gen = gen;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isGen() {
        return gen;
    }
}

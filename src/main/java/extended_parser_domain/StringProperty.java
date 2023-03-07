package extended_parser_domain;

public class StringProperty extends Property {

    private String pattern;

    public StringProperty(String name, String type, boolean required, boolean gen, String pattern) {
        super(name, type, required, gen);
        this.pattern = pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern(){
        return pattern;
    }
}

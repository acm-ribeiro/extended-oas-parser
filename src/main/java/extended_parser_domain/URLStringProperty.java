package extended_parser_domain;

public class URLStringProperty extends URLProperty {

    private String name, type, pattern;

    public URLStringProperty(String name, String type, String pattern){
        this.name = name;
        this.type = type;
        this.pattern = pattern;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPattern() {
        return pattern;
    }
}

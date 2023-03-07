package extended_parser_domain;

public class URLIntegerProperty extends URLProperty {

    private String name, type, format;
    private int min, max;

    public URLIntegerProperty() {}

    public URLIntegerProperty(String name, String type, int min, int max, String format) {
        this.name = name;
        this.type = type;
        this.min = min;
        this.max = max;
        this.format = format;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public String getFormat() {
        return format;
    }
}

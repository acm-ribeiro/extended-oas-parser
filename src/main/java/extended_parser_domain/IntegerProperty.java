package extended_parser_domain;

public class IntegerProperty extends Property {

    private String format;
    private int min, max;

    public IntegerProperty(String name, String type, boolean required, boolean gen, int min, int max, String format) {
        super(name, type, required, gen);
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}

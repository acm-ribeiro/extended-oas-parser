package extended_parser_domain;

public class ArrayProperty extends Property {

    private String itemsType;

    public ArrayProperty(String name, String type, boolean required, boolean gen, String itemsType) {
        super(name, type, required, gen);
        this.itemsType = itemsType;
    }

    public String getItemsType() {
        return itemsType;
    }
}

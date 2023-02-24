package parser_domain;

public class URLArrayProperty extends URLProperty {

    private String name, type, itemsType;


    public URLArrayProperty(String name, String type, String itemsType) {
        this.name = name;
        this.type = type;
        this.itemsType = itemsType;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getItemsType() {
        return itemsType;
    }
}

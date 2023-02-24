package parser_domain;

import java.util.List;

public class URLParameter {

    private String name, in;
    private boolean required;
    private URLProperty schema;

    public URLParameter(String in, String name, boolean required, List<APIProperty> schema) {
        this.in = in;
        this.name = name;
        this.required = required;

        for (APIProperty prop : schema) {
            switch (prop.getType().toLowerCase()) {
                case "integer" ->
                        this.schema = new URLIntegerProperty(prop.getName(), prop.getType(), prop.getMin(), prop.getMax(), prop.getFormat());
                case "string" -> {
                    String pattern = prop.getPattern() != null ? prop.getPattern() : "";
                    this.schema = new URLStringProperty(prop.getName(), prop.getType(), pattern);
                }
                case "array" -> {
                    System.out.println(prop.getItemType());
                    this.schema = new URLArrayProperty(prop.getName(), prop.getType(), prop.getItemType());
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public URLProperty getSchema() {
        return schema;
    }

    public boolean isRequired() {
        return required;
    }
}

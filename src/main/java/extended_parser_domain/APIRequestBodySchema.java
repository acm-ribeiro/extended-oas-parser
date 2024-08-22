package extended_parser_domain;

import java.util.ArrayList;
import java.util.List;

public class APIRequestBodySchema extends RequestBodySchema {
    private String name, type;
    private List<Property> properties;

    public APIRequestBodySchema(String type, String name, List<APIProperty> properties) {
        this.type = type;
        this.name = name;
        this.properties = new ArrayList<>();

        for (APIProperty prop : properties) {
            // TODO case "object"
            switch (prop.getType().toLowerCase()) {
                case "integer" -> this.properties.add(
                        new IntegerProperty(prop.getName(), prop.getType(), prop.isRequired(),
                                prop.isGen(), prop.getMinimum(), prop.getMaximum(), prop.getFormat())
                );
                case "string" -> this.properties.add(
                        new StringProperty(prop.getName(), prop.getType(), prop.isRequired(),
                                prop.isGen(), prop.getPattern())
                );
                case "array" -> this.properties.add(
                        new ArrayProperty(prop.getName(), prop.getType(), prop.isRequired(),
                                prop.isGen(), prop.getItemsType())
                );
            }
        }
    }

    public String getName() {
        return name;
    }
}

package parser_domain;

import java.util.ArrayList;
import java.util.List;

public class APIRequestBodySchema extends RequestBodySchema {
    private String name, type;
    private List<Property> properties;

    public APIRequestBodySchema(String type, String name, List<APIProperty> properties) {
        this.type = type;
        this.name = name;
        this.properties = new ArrayList<>();

        for(APIProperty prop : properties) {
            // TODO case "object"
            switch (prop.getType().toLowerCase()){
                case "integer":
                    this.properties.add(
                            new IntegerProperty(prop.getName(), prop.getType(), prop.isRequired(), prop.gen(),
                                    prop.getMin(), prop.getMax(), prop.getFormat())
                    );
                    break;
                case "string":
                    this.properties.add(
                            new StringProperty(prop.getName(), prop.getType(), prop.isRequired(), prop.gen(), prop.getPattern())
                    );
                    break;
                case "array":
                    this.properties.add(
                            new ArrayProperty(prop.getName(), prop.getType(), prop.isRequired(), prop.gen(), prop.getItemType())
                    );
                    break;
            }
        }
    }

    public String getName() {
        return name;
    }
}

package parser;

import com.google.gson.*;
import extended_parser_domain.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Parser {

    // tokens 
    public static final String REF = "ref";
    public static final String TYPE = "type";
    public static final String NAME = "name";
    public static final String PATTERN = "pattern";
    public static final String FORMAT = "format";
    public static final String ITEMS_TYPE = "itemsType";
    public static final String MIN = "min";
    public static final String MAX = "max";
    public static final String NO_VAL = "";

    // data types
    public static final String STRING = "string";
    public static final String INTEGER = "integer";
    public static final String ARRAY = "array";

    public static Specification parse(String fileLocation) {
        GsonBuilder gsonBuilder = new GsonBuilder();

        // Custom json deserializer for abstract class RequestBodySchema
        JsonDeserializer<RequestBodySchema> requestBodyDeserializer = (json, typeOfT, context) -> {
            JsonObject object = json.getAsJsonObject();
            if(object.has(REF))
                return new ReferencedBodySchema(object.get(REF).getAsString());
            else {
                // TODO: do we want to deal with explicit schemas?
                return new Schema(object.get(NAME).getAsString(), object.get(NAME).getAsString(), null);
            }
        };

        // Custom json deserializer for abstract class URLProperty
        JsonDeserializer<URLProperty> urlPropertyDeserializer = (json, typeOfT, context) -> {
            URLProperty property = null;
            JsonObject obj = json.getAsJsonObject();

            if(obj.has(REF))
                property = new ReferencedURLProperty(obj.get(REF).getAsString());
            else {
                String name = obj.get(NAME).getAsString();
                String type = obj.get(TYPE).getAsString().toLowerCase();

                switch (type) {
                    case STRING -> {
                        String pattern = obj.has(PATTERN)? obj.get(PATTERN).getAsString() : NO_VAL;
                        property = new URLStringProperty(name, type, pattern);
                    }
                    case INTEGER -> {
                        int min = obj.get(MIN).getAsInt();
                        int max = obj.get(MAX).getAsInt();
                        String format = obj.has(FORMAT)? obj.get(FORMAT).getAsString() : NO_VAL;
                        property = new URLIntegerProperty(name, type, min, max, format);
                    }
                    case ARRAY -> property = new URLArrayProperty(name, type,
                            obj.get(ITEMS_TYPE).getAsString());

                }
            }

            return property;
        };

        // Registering custom deserializers
        gsonBuilder.registerTypeAdapter(RequestBodySchema.class, requestBodyDeserializer);
        gsonBuilder.registerTypeAdapter(URLProperty.class, urlPropertyDeserializer);

        Gson customGson = gsonBuilder.create();

        try {
            Specification spec = customGson.fromJson(new FileReader(fileLocation), Specification.class);
            spec.initDerivedFields();
            spec.parseFormulas();
            return spec;
        } catch (FileNotFoundException e) {
            System.err.println("No file at " + fileLocation);
        }

        return null;
    }

}

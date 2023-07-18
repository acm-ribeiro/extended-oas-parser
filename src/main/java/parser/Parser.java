package parser;

import com.google.gson.*;
import extended_parser_domain.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Parser {

    public static Specification parse(String fileLocation) {
        GsonBuilder gsonBuilder = new GsonBuilder();

        // Custom json deserializer for abstract class RequestBodySchema
        JsonDeserializer<RequestBodySchema> requestBodyDeserializer = (json, typeOfT, context) -> {
            JsonObject object = json.getAsJsonObject();

            if(object.has("ref"))
                return new ReferencedBodySchema(object.get("ref").getAsString());
            else {
                // TODO: do we want to deal with explicit schemas?
                return new Schema(object.get("type").getAsString(), object.get("name").getAsString(), null);
            }
        };

        // Custom json deserializer for abstract class URLProperty
        JsonDeserializer<URLProperty> urlPropertyDeserializer = (json, typeOfT, context) -> {
            URLProperty property = null;
            JsonObject obj = json.getAsJsonObject();

            if(obj.has("ref"))
                property = new ReferencedURLProperty(obj.get("ref").getAsString());
            else {
                String name = obj.get("name").getAsString();
                String type = obj.get("type").getAsString().toLowerCase();

                switch (type) {
                    case "string" -> {
                        String pattern = obj.has("pattern")? obj.get("pattern").getAsString() : "";
                        property = new URLStringProperty(name, type, pattern);
                    }
                    case "integer" -> {
                        int min = obj.get("min").getAsInt();
                        int max = obj.get("max").getAsInt();
                        String format = obj.has("format")? obj.get("format").getAsString() : "";
                        property = new URLIntegerProperty(name, type, min, max, format);
                    }
                    case "array" -> property = new URLArrayProperty(name, type, obj.get("itemsType").getAsString());

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
            System.err.println("File not found. [" + fileLocation + "].");
        }

        return null;
    }

}

package extended_parser_domain;

import java.util.List;

public class Response {
    public static final int DEFAULT = 999;

    private int responseCode;
    private transient String description, contentSchemaType;
    private ResponseSchema contentSchema;
    private List<Link> links;
    private List<String> contentTypes;

    public Response(String responseCode, String description, ResponseSchema contentSchema, String contentSchemaType, List<Link> links, List<String> contentTypes) {
        try {
            this.responseCode = Integer.valueOf(responseCode);
        } catch (NumberFormatException e) {
            // it's a default response
            this.responseCode = DEFAULT;
        }
        this.description = description;
        this.contentSchema = contentSchema;
        this.contentSchemaType = contentSchemaType;
        this.links = links;
        this.contentTypes = contentTypes;
    }

    public List<String> getContentTypes () {
        return contentTypes;
    }

    public int getResponseCode () {
        return responseCode;
    }

    public ResponseSchema getContentSchema() {
        return contentSchema;
    }

    public List<Link> getLinks() {
        return links;
    }

    public boolean hasLinks () {
        return links != null;
    }

}

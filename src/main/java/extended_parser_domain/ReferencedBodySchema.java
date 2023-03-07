package extended_parser_domain;

public class ReferencedBodySchema extends RequestBodySchema {

    private static final String PATH = "#/schemas/";

    private String ref;

    public ReferencedBodySchema(String name) {
        ref = name;
    }

    public String getRef(){
        return ref;
    }

    public String getName() {
        return ref.replace(PATH, "");
    }
}

package parser_domain;

public class ReferencedURLProperty extends URLProperty {

    private String ref;

    public ReferencedURLProperty(String ref) {
        this.ref = ref;
    }

    public String getRef() {
        return ref;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }
}

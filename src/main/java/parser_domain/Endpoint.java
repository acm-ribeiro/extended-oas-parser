package parser_domain;

import java.util.List;

public class Endpoint {

    private String uri;
    private List<URLParameter> parameters;

    public Endpoint(String uri, List<URLParameter> parameters) {
        this.uri = uri;
        this.parameters = parameters;
    }

    public String getUri() {
        return uri;
    }

    public List<URLParameter> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("uri: " + uri);

        if(!parameters.isEmpty())
            str.append("\nparams: {");

        for (URLParameter p : parameters)
            str.append(p.getName()).append(", ");

        if(!parameters.isEmpty()) {
            str.deleteCharAt(str.length() - 2); // remove last comma
            str.deleteCharAt(str.length() - 1); // remove last space
            str.append("}");
        }

        return str.toString();
    }
}

package parser_domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Endpoint {

    private String uri;
    private Map<String, URLParameter> parameters;   // Parameters by name : <parameter_name, parameter>
    private Map<String, String> paramValues;        // Replaced parameters : <parameter_name, value>

    public Endpoint(String uri, List<URLParameter> path, List<URLParameter> query) {
        this.uri = uri;
        parameters = new HashMap<>();

        // Initializing parameters
        for (URLParameter p : path)
            parameters.put(p.getName(), p);

        for (URLParameter q : query)
            parameters.put(q.getName(), q);

        // Initializing parameter values as ""
        for(Entry<String, URLParameter> e : parameters.entrySet())
            paramValues.put(e.getKey(), "");

    }

    public String getUri() {
        return uri;
    }

    public Map<String, URLParameter> getParameters() {
        return parameters;
    }
    public Map<String, String> getParameterValues() {
        return paramValues;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("uri: " + uri);

        if(!parameters.isEmpty())
            str.append("\nparams: {");

        for (Entry<String, URLParameter> e : parameters.entrySet())
            str.append(e.getKey()).append(", ");

        if(!parameters.isEmpty()) {
            str.deleteCharAt(str.length() - 2); // remove last comma
            str.deleteCharAt(str.length() - 1); // remove last space
            str.append("}");
        }

        return str.toString();
    }
}

package parser_domain;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Endpoint {

    private String uri;
    private Map<String, URLParameter> parameters;       // Parameters by name : <parameter_name, parameter>
    private LinkedHashMap<String, String> pathValues;   // Path parameters values : <path_name, value>
    private Map<String, String> queryValues;   // Path parameters values : <query_name, value>

    public Endpoint(String uri, List<URLParameter> path, List<URLParameter> query) {
        this.uri = uri;
        parameters = new HashMap<>();


        // Initializing parameters
        for (URLParameter p : path)
            parameters.put(p.getName(), p);

        for (URLParameter q : query)
            parameters.put(q.getName(), q);

        // Initializing parameter values as ""
        String in;
        for(Entry<String, URLParameter> e : parameters.entrySet()) {
            in = e.getValue().getIn();
            if (in.equalsIgnoreCase("path"))
                pathValues.put(e.getKey(), "");
            else
                queryValues.put(e.getKey(), "");
        }
    }

    public String getUri() {
        return uri;
    }

    public Map<String, URLParameter> getParameters() {
        return parameters;
    }
    public LinkedHashMap<String, String> getPathValues() {
        return pathValues;
    }
    public Map<String, String> getQueryValues() {
        return queryValues;
    }

    public void putPathValue(String name, String value) {
        pathValues.put(name, value);
    }

    public void putQueryValue(String name, String value) {
        queryValues.put(name, value);
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

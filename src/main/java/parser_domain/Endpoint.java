package parser_domain;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Endpoint {

    public static final String PATH = "path";
    public static final String QUERY = "query";

    private String uri;
    private Map<String, URLParameter> parameters;       // Parameters by name : <parameter_name, parameter>
    private LinkedHashMap<String, String> pathValues;   // Path parameters values : <path_name, value>
    private Map<String, String> queryValues;            // Path parameters values : <query_name, value>

    public Endpoint(String uri, List<URLParameter> path, List<URLParameter> query) {
        this.uri = uri;
        parameters = new HashMap<>();
        pathValues = new LinkedHashMap<>();
        queryValues = new HashMap<>();

        // Initializing parameters
        for (URLParameter p : path)
            parameters.put(p.getName(), p);

        for (URLParameter q : query)
            parameters.put(q.getName(), q);

        // Initializing parameter values as ""
        String in;
        for(Entry<String, URLParameter> e : parameters.entrySet()) {
            in = e.getValue().getIn();
            if (in.equalsIgnoreCase(PATH))
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

    /**
     * Current string representation of the endpoint with all the replaced parameters
     * @return uri with all parameter values
     */
    @Override
    public String toString() {
        // removing first occurrence of '/' and splitting the uri by the remaining '/'
        String[] parts = uri.replaceFirst("/", "").split("/");
        StringBuilder str = new StringBuilder();

        for(String s : parts) {
            str.append("/");
            if (s.contains("{")) {
                // is path parameter
                str.append(pathValues.get(s.replace("{", "").replace("}", "")));
            } else {
                // is resource name
                str.append(s);
            }
        }

        // adding query parameter values
        for (Entry<String, String> q : queryValues.entrySet()) {
            str.append("?");
            str.append(q.getKey()).append("=").append(q.getValue());
        }

        return str.toString();
    }

}

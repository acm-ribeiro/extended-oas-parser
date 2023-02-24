package parser_domain;

import java.util.Map;

public class Link {

    private String name;
    private String operationId;
    private Map<String, String> parameters; // key: parameter name; value: parameter expression

    public Link(String name, String operationId, Map<String, String> parameters) {
        this.name = name;
        this.operationId = operationId;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }
    public String getOperationId() {
        return operationId;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "APILink{" +
                "name='" + name + '\'' +
                ", operationId = '" + operationId + '\'' +
                ", parameters = " + parameters +
                '}';
    }
}

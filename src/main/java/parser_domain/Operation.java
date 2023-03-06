package parser_domain;

import java.util.ArrayList;
import java.util.List;

public class Operation {

    private String operationID, verb, url;
    private List<String> requires, ensures;
    private List<URLParameter> queryParams, pathParams;
    private RequestBodySchema requestBody;
    private List<Response> responses;
    private Endpoint endpoint;

    public Operation() {
    }

    public void initEndpoint() {
        endpoint = new Endpoint(url, queryParams, pathParams);
    }

    public String getOperationID() {
        return operationID;
    }

    public void setVerb(String verb){
        this.verb = verb;
    }

    public String getVerb(){
        return verb;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public List<URLParameter> getQueryParams() {
        return queryParams;
    }

    public List<URLParameter> getPathParams() {
        return pathParams;
    }

    public RequestBodySchema getRequestBody() {
        return requestBody;
    }

    public List<String> getRequires() {
        return requires;
    }

    public List<String> getEnsures() {
        return ensures;
    }

    public List<Response> getResponses() {
        return responses;
    }

}

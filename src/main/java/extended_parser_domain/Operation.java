package extended_parser_domain;

import magmact_domain.Formula;

import java.util.List;

public class Operation {

    private String operationID, verb, url;
    private List<String> pre, pos;
    private List<Formula> requires, ensures;
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

    public List<String> getPre() {
        return pre;
    }

    public List<String> getPos() {
        return pos;
    }

    public List<Formula> getRequires() {
        return requires;
    }

    public List<Formula> getEnsures() {
        return ensures;
    }

    public void addRequires(Formula f) {
        requires.add(f);
    }

    public void addEnsures(Formula f) {
        ensures.add(f);
    }


    public List<Response> getResponses() {
        return responses;
    }

}

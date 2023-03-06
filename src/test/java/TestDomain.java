import org.junit.jupiter.api.Test;
import parser_domain.APIProperty;
import parser_domain.Endpoint;
import parser_domain.URLParameter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDomain {

    String SPEC_FILE = "/Users/acm.ribeiro/Desktop/run-specs/apostl-specs/tournaments-apostl-spec-extended.json";

    @Test
    void endpoint_test1() {
        // Path parameters
        List<URLParameter> path = new ArrayList<>();
        APIProperty prop = new APIProperty("resourceId", "string", "", "", "", -99, 99, false, true, false);
        List<APIProperty> schema = new ArrayList<>();
        schema.add(prop);
        URLParameter par = new URLParameter("path", "resourceId", true, schema);

        // Empty query parameters
        List<URLParameter> query = new ArrayList<>();

        Endpoint e = new Endpoint("/resources/{resourceId}", path, query);

        // Value for the path parameter
        String val = "123";
        e.putPathValue(par.getName(), val);

        String expected = "/resources/123";
        String actual = e.toString();

        assertEquals(expected, actual);
    }

    @Test
    void endpoint_test2 () {
        // Path parameters
        List<URLParameter> path = new ArrayList<>();
        APIProperty prop = new APIProperty("resourceId", "string", "", "", "", -99, 99, false, true, false);
        List<APIProperty> schema = new ArrayList<>();
        schema.add(prop);
        URLParameter par = new URLParameter("path", "resourceId", true, schema);

        // Empty query parameters
        List<URLParameter> query = new ArrayList<>();
        APIProperty q_prop = new APIProperty("sort", "string", "", "", "", -99, 99, false, false, false);
        List<APIProperty> q_schema = new ArrayList<>();
        q_schema.add(q_prop);
        URLParameter q_par = new URLParameter("path", "sort", false, q_schema);

        Endpoint e = new Endpoint("/resources/{resourceId}", path, query);

        // Value for the path parameter
        String val = "123";
        e.putPathValue(par.getName(), val);

        // Value for query parameter
        String q_val = "name";
        e.putQueryValue("sort", "name");

        String expected = "/resources/123?sort=name";
        String actual = e.toString();

        assertEquals(expected, actual);
    }
}
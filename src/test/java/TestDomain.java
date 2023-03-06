import org.junit.jupiter.api.Test;
import parser_domain.Endpoint;
import parser_domain.URLParameter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDomain {

    @Test
    void endpoint_test1() {
        List<URLParameter> path = new ArrayList<>();
        List<URLParameter> query = new ArrayList<>();
        Endpoint e = new Endpoint("/resources/{resourceId}", path, query);

        // updating value
        e.putPathValue("resourceId", "123");

        String expected = "/resources/123";
        String actual = e.toString();
        assertEquals(expected, actual);
    }

    @Test
    void endpoint_test2 () {
        List<URLParameter> path = new ArrayList<>();
        List<URLParameter> query = new ArrayList<>();
        Endpoint e = new Endpoint("/resources/{resourceId}", path, query);

        // updating values
        e.putPathValue("resourceId", "123");
        e.putQueryValue("sort", "name");

        String expected = "/resources/123?sort=name";
        String actual = e.toString();
        assertEquals(expected, actual);
    }
}
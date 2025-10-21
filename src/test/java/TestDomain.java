import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import parser.Parser;
import extended_parser_domain.Endpoint;
import extended_parser_domain.Operation;
import extended_parser_domain.Specification;
import extended_parser_domain.URLParameter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDomain {
    static Specification spec;

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

    @Test
    void test_operation_endpoint() {
        Operation op = spec.getOperations().get("getPlayer");
        Endpoint e = op.getEndpoint();

        String expected = "/players/{pid}";
        String actual = e.toString();

        assertEquals(expected, actual);
    }

    @Test
    void test_operation_endpoint_multiple_params() {
        Operation op = spec.getOperations().get("checkTournamentEnrollment");
        Endpoint e = op.getEndpoint();

        String expected = "/tournaments/{tid}/players/{pid}";
        String actual = e.toString();

        assertEquals(expected, actual);
    }

    @Test
    void test_operation_endpoint_multiple_params_replacement() {
        Operation op = spec.getOperations().get("checkTournamentEnrollment");
        Endpoint e = op.getEndpoint();
        e.putPathValue("pid", "123");
        e.putPathValue("tid", "1");

        String expected = "/tournaments/1/players/123";
        String actual = e.toString();

        assertEquals(expected, actual);
    }

    @Test
    void test_operation_endpoint_multiple_params_replacement_query() {
        Operation op = spec.getOperations().get("checkTournamentEnrollment");
        Endpoint e = op.getEndpoint();

        e.putPathValue("pid", "123");
        e.putPathValue("tid", "1");

        e.putQueryValue("sort", "pid");
        e.putQueryValue("filter", "firstName");

        String expected1 = "/tournaments/1/players/123?sort=pid&filter=firstName";
        String expected2 = "/tournaments/1/players/123?filter=firstName&sort=pid";
        String actual = e.toString();

        assert(expected1.equals(actual) || expected2.equals(actual));
    }

    @Test
    void test_operation_endpoint_multiple_query_words() {
        Operation op = spec.getOperations().get("getPlayers");
        Endpoint e = op.getEndpoint();

        e.putQueryValue("name", "John Doe");
        e.putQueryValue("zipcode", "1100 230");

        String expected1 = "/players?name=John+Doe&zipcode=1100+230";
        String expected2 = "/players?zipcode=1100+230&name=John+Doe";
        String actual = e.toString();

        assert(expected1.equals(actual) || expected2.equals(actual));
    }

    @BeforeAll
    static void initSpec() {
        spec = Parser.parse("extended-specs/tournaments-glacier-extended.json");
    }
}
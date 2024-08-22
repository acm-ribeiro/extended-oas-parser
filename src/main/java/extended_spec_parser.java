import extended_parser_domain.*;
import parser.Parser;

public class extended_spec_parser {

    public static void main(String[] args) {
        Specification spec = Parser.parse(args[0]);
        assert spec != null;
        spec.initDerivedFields();
        spec.parseFormulas();
        System.out.println(spec);
        System.out.println("done");
    }
}

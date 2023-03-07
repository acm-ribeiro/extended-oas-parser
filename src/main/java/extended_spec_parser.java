import parser.Parser;
import extended_parser_domain.Specification;

import java.io.FileNotFoundException;

public class extended_spec_parser {

    public static void main(String[] args) throws FileNotFoundException {
        Specification spec = Parser.parse(args[0]);
        spec.initDerivedFields();

        System.out.println(spec);
    }
}

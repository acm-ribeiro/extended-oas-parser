import parser.Parser;
import parser_domain.Specification;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Specification spec = Parser.parse(args[0]);

        System.out.println(spec);
    }
}

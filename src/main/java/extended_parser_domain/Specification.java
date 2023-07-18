package extended_parser_domain;

import magmact_domain.Formula;
import magmact_parser.VisitorOrientedParser;

import java.util.*;
import java.util.Map.Entry;

public class Specification {

    private List<String> servers; // server urls
    private List<String> invariants;
    private List<Formula> invs;
    private Map<String, Map<String, Operation>> paths; // <path, <verb, operation>>
    private List<Schema> schemas;


    public Specification() {
    }

    public void addInvariant(String invariant) {
        invariants.add(invariant);
    }

    public void addInv(Formula inv) {
        invs.add(inv);
    }

    public List<String> getServers() {
        return servers;
    }

    /*
    * Initializes auxiliary fields (e.g. operation verbs and urls)
    * */
    public void initDerivedFields() {
        for(Entry<String, Map<String, Operation>> pathEntry: paths.entrySet()) {
            Map<String, Operation> operations = pathEntry.getValue();

            for(Entry<String, Operation> operationEntry : operations.entrySet()){
                Operation op = operationEntry.getValue();
                op.setUrl(pathEntry.getKey());
                op.setVerb(operationEntry.getKey());
                op.initEndpoint();
            }
        }
    }

    /**
     * Parses the invariants, pre and postconditions into Formula objects.
     */
    public void parseFormulas() {
        List<String> requires, ensures;
        VisitorOrientedParser magmact_parser = new VisitorOrientedParser();

        invs = new ArrayList<>();

        // Parsing invariants
        for(String invariant: invariants)
            invs.add(magmact_parser.parse(invariant));

        // Parsing pre and postconditions
        for(Map<String, Operation> e: paths.values())
            for (Operation op : e.values()) {
                op.initContracts();
                requires = op.getRequires();
                ensures = op.getRequires();

                for (String req: requires)
                    op.addPre(magmact_parser.parse(req));

                for (String ens: ensures)
                    op.addPos(magmact_parser.parse(ens));
            }
    }


    public List<Formula> getInvs() {
        return invs;
    }


    public List<Operation> getDeletes() {
        List<Operation> deletes = new ArrayList<>();

        for(Entry<String, Map<String, Operation>> pathEntry: paths.entrySet())
            for(Entry<String, Operation> operationEntry : pathEntry.getValue().entrySet())
                if(operationEntry.getKey().equalsIgnoreCase("DELETE"))
                    deletes.add(operationEntry.getValue());

        return deletes;
    }

    public Map<String, Operation> getOperations() {
        Map<String, Operation> operationsById = new HashMap<>();

        for(Entry<String, Map<String, Operation>> pathEntry: paths.entrySet())
            for(Entry<String, Operation> operationEntry : pathEntry.getValue().entrySet())
                operationsById.put(operationEntry.getValue().getOperationID(), operationEntry.getValue());

        return operationsById;
    }

    public Map<String, Map<String, Operation>> getPaths() {
        return paths;
    }

    public String getParameterRegex(Operation op, String p) {
        URLParameter parameter = findOperationParameter(op, p);
        URLProperty parameterSchema = parameter != null? parameter.getSchema() : null;

        return parameterSchema instanceof URLStringProperty? ((URLStringProperty) parameterSchema).getPattern(): "";
    }

    private URLParameter findOperationParameter(Operation op, String name) {
        List<URLParameter> params = op.getPathParams();

        for (URLParameter param : params)
            if (param.getName().equalsIgnoreCase(name))
                return param;

        return null;
    }

    public String getParameterType(Operation op, String p) {
        URLParameter parameter = findOperationParameter(op, p);
        URLProperty parameterSchema = parameter != null? parameter.getSchema() : null;

        return parameterSchema != null? parameterSchema.getType() : null;
    }

    public int getParameterMinimum(Operation op, String p) {
        URLParameter parameter = findOperationParameter(op, p);
        URLProperty parameterSchema = parameter != null? parameter.getSchema() : null;

        return parameterSchema instanceof URLIntegerProperty? ((URLIntegerProperty) parameterSchema).getMin(): -99;
    }

    public List<Schema> getSchemas(){
        return schemas;
    }

    // Finds the schema with the given type.
    public Schema getSchemaByType(String type) throws NoSuchElementException {
        Schema schema = null;
        System.out.println(type);
        for (Schema s : schemas) {
            if (s.getType().equalsIgnoreCase(type.toLowerCase())) {
                schema = s;
                break;
            }
        }

        if (schema != null)
            return schema;
        else
            throw new NoSuchElementException("\nCouldn't find the required schema.\n");
    }

    public Schema getSchemaByName(String name) throws NoSuchElementException {
        Schema schema = null;

        for (Schema s : schemas) {
            if (s.getName().equalsIgnoreCase(name.toLowerCase())) {
                schema = s;
                break;
            }
        }

        if (schema != null)
            return schema;
        else
            throw new NoSuchElementException("\nCouldn't find the required schema.\n");
    }

    public Schema dereferenceSchema(String name) {
        for(Schema s: schemas)
            if(s.getName().equalsIgnoreCase(name))
                return s;

        return null;
    }

    @Override
    public String toString() {
        StringBuilder print = new StringBuilder("servers: \n");

        for(String server: servers)
            print.append("   ").append(server).append("\n");

        print.append("invariants: \n");
        if(invariants.isEmpty())
            print.append("   empty\n");

        for(Formula inv: invs)
            print.append("   ").append(inv.toString()).append("\n");

        print.append("paths: \n");
        for(Entry<String, Map<String, Operation>> path: paths.entrySet()){
            print.append("   ").append(path.getKey()).append(": \n");

            for(Entry<String, Operation> operation: path.getValue().entrySet()) {
                // Operation ID
                print.append("      ").append(operation.getValue().getVerb()).append("  ").append(operation.getValue().getOperationID()).append("\n");

                // Path Parameters
                if(operation.getValue().getPathParams().isEmpty())
                    print.append("        - path parameters: []\n");
                else {
                    print.append("        - path parameters:\n");
                    for (URLParameter p : operation.getValue().getPathParams())
                        print.append("            ").append(p.getName()).append("\n");
                }

                // Query Parameters
                if(operation.getValue().getQueryParams().isEmpty())
                    print.append("        - query parameters [required]: []\n");
                else {
                    print.append("        - query parameters [required]:\n");
                    for(URLParameter p: operation.getValue().getQueryParams())
                        if(p.isRequired())
                            print.append("            ").append(p.getName());
                }

                // Request Body Schema
                if(operation.getValue().getRequestBody() == null)
                    print.append("\n");
                else {
                    RequestBodySchema body = operation.getValue().getRequestBody();
                    print.append("\n        - referenced schema:\n");
                    if(body instanceof ReferencedBodySchema)
                        print.append("            ").append(((ReferencedBodySchema) body).getRef()).append("\n");

                }

            }
        }

        // Schemas
        print.append("schemas: \n");

        for(Schema schema: schemas)
            print.append("   ").append(schema.toString()).append("\n");

        return print.toString();
    }
}

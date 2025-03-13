package extended_parser_domain;

import magmact_domain.Formula;
import magmact_parser.VisitorOrientedParser;

import java.util.*;
import java.util.Map.Entry;

public class Specification {

    private List<String> servers;                       // server urls
    private List<String> invariants;                    // Spec invariants as strings
    private List<Formula> invs;                         // Spec invariants as formulae
    private Map<String, Map<String, Operation>> paths;  // <path, <verb, operation>>
    private List<Schema> schemas;                       // resource schemas
    private Map<String, Schema> schemasByName;          // resource schemas
    private Map<String, Operation> operationsById;      // operations by id
    private List<Operation> deletes;                    // all specification delete operations


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
        Map<String, Operation> operations;
        Operation op;
        String verb;

        operationsById = new HashMap<>();
        deletes = new ArrayList<>();

        for (Entry<String, Map<String, Operation>> pathEntry : paths.entrySet()) {
            operations = pathEntry.getValue();

            for (Entry<String, Operation> operationEntry : operations.entrySet()) {
                op = operationEntry.getValue();
                verb = operationEntry.getKey();

                op.setUrl(pathEntry.getKey());
                op.setVerb(verb);
                op.initEndpoint();

                // initialising operations by id
                operationsById.put(op.getOperationID(), op);

                // initialising delete operation list
                if (verb.equalsIgnoreCase("DELETE"))
                    deletes.add(op);
            }
        }

        // initialising schemas by name
        schemasByName = new HashMap<>();
        for(Schema s : schemas)
            schemasByName.put(s.getName(), s);

    }

    /**
     * Parses the invariants, pre and postconditions into Formula objects.
     */
    public void parseFormulas() {
        List<String> requires, ensures;
        VisitorOrientedParser magmact_parser = new VisitorOrientedParser();

        invs = new ArrayList<>();

        // Parsing invariants
        for (String invariant : invariants)
            invs.add(magmact_parser.parse(invariant));

        // Parsing pre and postconditions
        for (Map<String, Operation> e : paths.values())
            for (Operation op : e.values()) {
                op.initContracts();
                requires = op.getRequires();
                ensures = op.getEnsures();

                for (String req : requires)
                    op.addPre(magmact_parser.parse(req));

                for (String ens : ensures)
                    op.addPos(magmact_parser.parse(ens));

                // this should always be true
                assert (requires.size() == op.getPre().size());
                assert (ensures.size() == op.getPos().size());
            }
    }


    /**
     * Resets the give operation's contract.
     *
     * @param operationId operation id.
     */
    public void resetOperationContract(String operationId) {
        operationsById.get(operationId).resetContract();
    }

    /**
     * Finds an operation by its id.
     *
     * @param id operation id
     * @return operation or null, when it doesn't exist.
     */
    public Operation findOperation(String id) {
        return operationsById.get(id);
    }

    /**
     * Returns the specifications' invariants.
     *
     * @return invariants.
     */
    public List<Formula> getInvs() {
        return invs;
    }

    /**
     * Returns the specifications' DELETE operations.
     *
     * @return DELETE operations.
     */
    public List<Operation> getDeletes() {
        return deletes;
    }

    /**
     * Returns the specification's operations by their ids.
     *
     * @return operations by id.
     */
    public Map<String, Operation> getOperations() {
        return operationsById;
    }

    /**
     * Returns the specifications' operations by path.
     * Each path entry has a map organised by verb.
     *
     * @return operations by path.
     */
    public Map<String, Map<String, Operation>> getPaths() {
        return paths;
    }

    /**
     * Returns the total number of path parameters of the API.
     *
     * @return number of path parameters.
     */
    public int getNumParams() {
        int params = 0;

        for (Operation op : operationsById.values())
            params += op.getPathParams().size();

        return params;
    }



    public String getParameterRegex(Operation op, String p) {
        URLParameter parameter = findOperationParameter(op, p);
        URLProperty parameterSchema = parameter != null ? parameter.getSchema() : null;

        return parameterSchema instanceof URLStringProperty ? ((URLStringProperty) parameterSchema).getPattern() : "";
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
        URLProperty parameterSchema = parameter != null ? parameter.getSchema() : null;

        return parameterSchema != null ? parameterSchema.getType() : null;
    }

    public int getParameterMinimum(Operation op, String p) {
        URLParameter parameter = findOperationParameter(op, p);
        URLProperty parameterSchema = parameter != null ? parameter.getSchema() : null;

        return parameterSchema instanceof URLIntegerProperty ? ((URLIntegerProperty) parameterSchema).getMin() : -99;
    }

    public Map<String, Schema> getSchemas() {
        return schemasByName;
    }

    public Schema getSchemaByName(String name) throws NoSuchElementException {
        if (schemasByName.containsKey(name))
            return schemasByName.get(name);
        else
            throw new NoSuchElementException("\nSchema + [" + name + "] not found.\n");
    }

    public Operation getOperation(String operationId) {
        return operationsById.get(operationId);
    }

    @Override
    public String toString() {
        StringBuilder print = new StringBuilder("servers: \n");

        for (String server : servers)
            print.append("   ").append(server).append("\n");

        print.append("invariants: \n");
        if (invariants.isEmpty())
            print.append("   empty\n");

        for (Formula inv : invs)
            print.append("   ").append(inv.toString()).append("\n");

        print.append("paths: \n");
        for (Entry<String, Map<String, Operation>> path : paths.entrySet()) {
            print.append("   ").append(path.getKey()).append(": \n");

            for (Entry<String, Operation> operation : path.getValue().entrySet()) {
                // Operation ID
                print.append("      ").append(operation.getValue().getVerb()).append("  ").append(operation.getValue().getOperationID()).append("\n");

                // Path Parameters
                if (operation.getValue().getPathParams().isEmpty())
                    print.append("        - path parameters: []\n");
                else {
                    print.append("        - path parameters:\n");
                    for (URLParameter p : operation.getValue().getPathParams())
                        print.append("            ").append(p.getName()).append("\n");
                }

                // Query Parameters
                if (operation.getValue().getQueryParams().isEmpty())
                    print.append("        - query parameters [required]: []\n");
                else {
                    print.append("        - query parameters [required]:\n");
                    for (URLParameter p : operation.getValue().getQueryParams())
                        if (p.isRequired())
                            print.append("            ").append(p.getName());
                }

                // Request Body Schema
                if (operation.getValue().getRequestBody() == null)
                    print.append("\n");
                else {
                    RequestBodySchema body = operation.getValue().getRequestBody();
                    print.append("\n        - referenced schema:\n");
                    if (body instanceof ReferencedBodySchema)
                        print.append("            ").append(((ReferencedBodySchema) body).getRef()).append("\n");
                }

                if (operation.getValue().getTags().isEmpty()) {
                    print.append("        - tags: []\n");
                } else {
                    List<String> tags = operation.getValue().getTags();
                    print.append("        - tags: [\n");
                    for(String tag : tags)
                        print.append("           " ).append(tag).append("\n");
                    print.append("]");
                }
            }
        }

        // Schemas
        print.append("schemas: \n");

        for (Entry<String, Schema> e : schemasByName.entrySet())
            print.append("   ").append(e.getValue().toString()).append("\n");

        return print.toString();
    }
}

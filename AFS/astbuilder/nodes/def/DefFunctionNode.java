package astbuilder.nodes.def;

import astbuilder.nodes.stmt.StmtNode;
import astbuilder.nodes.type.TypeNode;
import astbuilder.visitor.DefVisitor;

import java.util.List;
import java.util.stream.Collectors;

public class DefFunctionNode extends DefNode {
    private final TypeNode type;
    private final String identifier;
    private final List<Param> parameters;
    private final StmtNode statement;

    public DefFunctionNode(TypeNode type, String identifier, List<Param> parameters, StmtNode statement) {
        this.type = type;
        this.identifier = identifier;
        this.parameters = parameters;
        this.statement = statement;
    }

    public TypeNode getType() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<Param> getParameters() {
        return parameters;
    }

    public StmtNode getStatement() {
        return statement;
    }

    @Override
    public void acceptVisit(DefVisitor visitor) {
        visitor.visitDefFunctionNode(this);
    }

    @Override
    public String toString() {
        return String.format("DefFunction (%s %s(%s) %s)",
                type,
                identifier,
                parameters.stream().map(Param::toString).collect(Collectors.joining(", ")),
                statement
        );
    }
}

package afs.nodes.def;

import afs.nodes.stmt.StmtNode;
import afs.nodes.type.TypeNode;

import java.util.List;

public final class DefFunctionNode extends DefNode {
    private final TypeNode type;
    private final String identifier;
    private final List<Param> parameters;
    private final StmtNode statement;

    public DefFunctionNode(TypeNode type, String identifier, List<Param> parameters, StmtNode statement, int line, int column) {
        super(line, column);
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
    public java.lang.String toString() {
        return "fn T x(arrow(T x))S";
    }
}

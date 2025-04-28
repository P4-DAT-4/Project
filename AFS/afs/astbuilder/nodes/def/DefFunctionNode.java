package afs.astbuilder.nodes.def;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.nodes.expr.ExprIdentifierNode;
import afs.astbuilder.nodes.stmt.StmtNode;
import afs.astbuilder.nodes.type.TypeNode;
import afs.astbuilder.visitor.DefVisitor;

import java.util.List;

public class DefFunctionNode extends DefNode {
    private final TypeNode type;
    private final ExprIdentifierNode identifier;
    private final List<Param> parameters;
    private final StmtNode statement;

    public DefFunctionNode(TypeNode type, ExprIdentifierNode identifier, List<Param> parameters, StmtNode statement) {
        this.type = type;
        this.identifier = identifier;
        this.parameters = parameters;
        this.statement = statement;
    }

    public TypeNode getType() {
        return type;
    }

    public ExprIdentifierNode getIdentifier() {
        return identifier;
    }

    public List<Param> getParameters() {
        return parameters;
    }

    public StmtNode getStatement() {
        return statement;
    }

    @Override
    public AFSType acceptVisit(DefVisitor visitor) {
        return visitor.visitDefFunctionNode(this);
    }

    @Override
    public String toString() {
        return "fn T x(arrow(T x))S";
    }
}

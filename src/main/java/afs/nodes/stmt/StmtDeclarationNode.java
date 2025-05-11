package afs.nodes.stmt;

import afs.nodes.expr.ExprNode;
import afs.nodes.type.TypeNode;

public final class StmtDeclarationNode extends StmtNode {
    private final TypeNode type;
    private final String identifier;
    private final ExprNode expression;
    private final StmtNode statement;

    public StmtDeclarationNode(TypeNode type, String identifier, ExprNode expression, StmtNode statement, int line, int column) {
        super(line, column);
        this.type = type;
        this.identifier = identifier;
        this.expression = expression;
        this.statement = statement;
    }

    public TypeNode getType() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ExprNode getExpression() {
        return expression;
    }

    public StmtNode getStatement() {
        return statement;
    }

    @Override
    public String toString() {
        return "T x = e; S";
    }
}

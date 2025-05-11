package afs.nodes.stmt;

import afs.nodes.expr.ExprNode;

public final class StmtAssignmentNode extends StmtNode {
    private final String identifier;
    private final ExprNode expression;

    public StmtAssignmentNode(String identifier, ExprNode expression, int line, int column)
    {
        super(line, column);
        this.expression = expression;
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ExprNode getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "x = e";
    }
}

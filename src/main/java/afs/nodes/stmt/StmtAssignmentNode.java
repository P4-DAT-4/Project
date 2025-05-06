package afs.nodes.stmt;

import afs.nodes.expr.ExprNode;

public final class StmtAssignmentNode extends StmtNode {
    private final ExprNode leftExpression;
    private final ExprNode rightExpression;

    public StmtAssignmentNode(ExprNode leftExpression, ExprNode rightExpression, int line, int column)
    {
        super(line, column);
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    public ExprNode getLeftExpression() {
        return leftExpression;
    }

    public ExprNode getRightExpression() {
        return rightExpression;
    }

    @Override
    public String toString() {
        return "x = e";
    }
}

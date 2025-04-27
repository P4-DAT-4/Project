package afs.astbuilder.nodes.stmt;

import afs.astbuilder.nodes.expr.ExprNode;
import afs.astbuilder.visitor.StmtVisitor;

public class StmtAssignmentNode extends StmtNode {
    private final ExprNode leftExpression;
    private final ExprNode rightExpression;

    public StmtAssignmentNode(ExprNode leftExpression, ExprNode rightExpression)
    {
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
    public void acceptVisit(StmtVisitor visitor) {
        visitor.visitStmtAssignmentNode(this);
    }

    @Override
    public String toString() {
        return "x = e";
    }
}

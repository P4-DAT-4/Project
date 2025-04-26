package afs.astbuilder.nodes.expr;

import afs.astbuilder.visitor.ExprVisitor;

public class ExprLessThanNode extends ExprNode {
    private final ExprNode leftExpression;
    private final ExprNode rightExpression;

    public ExprLessThanNode(ExprNode leftExpression, ExprNode rightExpression) {
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
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprLessThanNode(this);
    }

    @Override
    public String toString() {
        return String.format("ExprLessThan (%s < %s)", leftExpression, rightExpression);
    }
}

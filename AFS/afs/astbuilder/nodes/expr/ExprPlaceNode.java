package afs.astbuilder.nodes.expr;

import afs.astbuilder.visitor.ExprVisitor;

public class ExprPlaceNode extends ExprNode {
    private final ExprNode leftExpression;
    private final ExprNode rightExpression;

    public ExprPlaceNode(ExprNode leftExpression, ExprNode rightExpression) {
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
        visitor.visitExprPlaceNode(this);
    }

    @Override
    public String toString() {
        return String.format("ExprPlace (%s at %s)", leftExpression, rightExpression);
    }
}

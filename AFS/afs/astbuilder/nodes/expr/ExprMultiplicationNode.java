package afs.astbuilder.nodes.expr;

import afs.astbuilder.visitor.ExprVisitor;

public class ExprMultiplicationNode extends ExprNode{
    private final ExprNode leftExpression;
    private final ExprNode rightExpression;

    public ExprMultiplicationNode(ExprNode leftExpression, ExprNode rightExpression) {
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
        visitor.visitExprMultiplicationNode(this);
    }

    @Override
    public String toString() {
        return String.format("ExprMultiplication (%s * %s)", leftExpression, rightExpression);
    }
}

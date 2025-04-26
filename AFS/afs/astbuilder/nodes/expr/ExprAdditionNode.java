package afs.astbuilder.nodes.expr;

import afs.astbuilder.visitor.ExprVisitor;

public class ExprAdditionNode extends ExprNode{
    private final ExprNode leftExpression;
    private final ExprNode rightExpression;

    public ExprAdditionNode(ExprNode leftExpression, ExprNode rightExpression) {
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
        visitor.visitExprAdditionNode(this);
    }

    @Override
    public String toString() {
        return String.format("ExprAddition (%s + %s)", leftExpression, rightExpression);
    }
}

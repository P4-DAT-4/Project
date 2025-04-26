package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprDivisionNode extends ExprNode{
    private final ExprNode leftExpression;
    private final ExprNode rightExpression;

    public ExprDivisionNode(ExprNode leftExpression, ExprNode rightExpression) {
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
        visitor.visitExprDivisionNode(this);
    }

    @Override
    public String toString() {
        return String.format("ExprDivision (%s / %s)", leftExpression, rightExpression);
    }
}

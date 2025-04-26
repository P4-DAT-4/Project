package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprSubtractionNode extends ExprNode {
    private final ExprNode leftExpression;
    private final ExprNode rightExpression;

    public ExprSubtractionNode(ExprNode leftExpression, ExprNode rightExpression) {
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
        visitor.visitExprSubtractionNode(this);
    }

    @Override
    public String toString() {
        return String.format("ExprSubtraction (%s - %s)", leftExpression, rightExpression);
    }
}

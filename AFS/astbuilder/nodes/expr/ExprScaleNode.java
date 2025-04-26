package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprScaleNode extends ExprNode {
    private final ExprNode leftExpression;
    private final ExprNode rightExpression;

    public ExprScaleNode(ExprNode leftExpression, ExprNode rightExpression) {
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
        visitor.visitExprScaleNode(this);
    }

    @Override
    public String toString() {
        return String.format("ExprScale (%s by %s)", leftExpression, rightExpression);
    }
}

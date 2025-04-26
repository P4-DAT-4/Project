package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprEqualsNode extends ExprNode {
    private final ExprNode leftExpression;
    private final ExprNode rightExpression;

    public ExprEqualsNode(ExprNode leftExpression, ExprNode rightExpression) {
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
        visitor.visitExprEqualNode(this);
    }

    @Override
    public String toString() {
        return String.format("ExprEquals (%s == %s)", leftExpression, rightExpression);
    }
}

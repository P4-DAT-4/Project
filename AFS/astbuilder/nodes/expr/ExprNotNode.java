package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprNotNode extends ExprNode {
    private final ExprNode expression;

    public ExprNotNode(ExprNode expression) {
        this.expression = expression;
    }

    public ExprNode getExpression() {
        return expression;
    }

    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprNotNode(this);
    }

    @Override
    public String toString() {
        return String.format("ExprNot (!%s)", expression);
    }
}

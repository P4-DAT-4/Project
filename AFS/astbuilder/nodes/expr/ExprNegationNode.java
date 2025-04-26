package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprNegationNode extends ExprNode {
    private final ExprNode expression;

    public ExprNegationNode(ExprNode expression) {
        this.expression = expression;
    }

    public ExprNode getExpression() {
        return expression;
    }

    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprNegationNode(this);
    }

    @Override
    public String toString() {
        return String.format("ExprNegation (-%s)", expression);
    }
}

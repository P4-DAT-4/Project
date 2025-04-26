package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprTextNode extends ExprNode {
    private final ExprNode expression;

    public ExprTextNode(ExprNode expression) {
        this.expression = expression;
    }

    public ExprNode getExpression() {
        return expression;
    }

    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprTextNode(this);
    }

    @Override
    public String toString() {
        return String.format("ExprText (%s)", expression);
    }
}

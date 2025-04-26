package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprDoubleNode extends ExprNode {
    private final Double value;

    public ExprDoubleNode(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprDoubleNode(this);
    }

    @Override
    public String toString() {
        return String.format("ExprDouble (%s)", value);
    }
}
